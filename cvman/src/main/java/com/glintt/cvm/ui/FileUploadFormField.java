package com.glintt.cvm.ui;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.vaadin.addon.customfield.CustomField;

import com.vaadin.data.Item;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.service.ApplicationContext;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.terminal.StreamVariable;
import com.vaadin.terminal.gwt.server.AbstractWebApplicationContext;
import com.vaadin.terminal.gwt.server.WebBrowser;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Html5File;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class FileUploadFormField extends CustomField {
    private static final long serialVersionUID = 4960715027303457297L;

    private final ProgressIndicator progress;
    private final Item item;
    private final Object propertyId;
    private final CssLayout dropPane;

    public FileUploadFormField(Item item, Object propertyId) {
        this.item = item;
        this.propertyId = propertyId;

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        this.dropPane = new CssLayout();
        this.dropPane.setWidth("200px");
        this.dropPane.setHeight("200px");
        this.dropPane.addStyleName("image-drop-pane");

        ImageDropBox dropBox = new ImageDropBox(this.dropPane);
        dropBox.setSizeUndefined();

        Panel panel = new Panel(dropBox);
        panel.setDescription("Drag text from desktop application or image files from the "
                + "file system to the drop box below (dragging files requires HTML5 capable browser like FF 3.6, Safari or Chrome)");
        panel.setSizeUndefined();
        panel.addStyleName("no-vertical-drag-hints");
        panel.addStyleName("no-horizontal-drag-hints");
        layout.addComponent(panel);

        this.progress = new ProgressIndicator();
        this.progress.setIndeterminate(true);
        this.progress.setVisible(false);
        layout.addComponent(this.progress);

        setCompositionRoot(layout);
    }

    @Override
    public Class<?> getType() {
        return this.item.getItemProperty(this.propertyId).getType();
    }

    @Override
    public void attach() {
        super.attach();

        // warn the user if the browser does not support file drop
        ApplicationContext context = getApplication().getContext();
        if (context instanceof AbstractWebApplicationContext) {
            WebBrowser webBrowser = ((AbstractWebApplicationContext) context).getBrowser();
            // FF
            boolean supportsHtml5FileDrop = webBrowser.isFirefox()
                    && (webBrowser.getBrowserMajorVersion() >= 4 || (webBrowser.getBrowserMajorVersion() == 3 && webBrowser
                            .getBrowserMinorVersion() >= 6));
            if (!supportsHtml5FileDrop) {
                supportsHtml5FileDrop = webBrowser.isChrome() || webBrowser.isSafari() && webBrowser.getBrowserMajorVersion() > 4;
            }
            if (!supportsHtml5FileDrop) {
                // @todo hide drag indicator and show a 'single-click' upload
                // component
                getWindow().showNotification(
                        "Image file drop is only supported on Firefox 3.6 and later. "
                                + "Text can be dropped into the box on other browsers.", Notification.TYPE_WARNING_MESSAGE);
            }
        }
    }

    private class ImageDropBox extends DragAndDropWrapper implements DropHandler {
        private static final long serialVersionUID = 389003491350858041L;

        // @todo declare on AppProperties
        private static final long FILE_SIZE_LIMIT = 2 * 1024 * 1024; // 2MB

        public ImageDropBox(Component root) {
            super(root);
            setDropHandler(this);
        }

        @Override
        public void drop(DragAndDropEvent dropEvent) {

            // expecting this to be an html5 drag
            WrapperTransferable tr = (WrapperTransferable) dropEvent.getTransferable();
            Html5File[] files = tr.getFiles();
            if (files != null) {
                for (final Html5File html5File : files) {
                    final String fileName = html5File.getFileName();

                    if (html5File.getFileSize() > FILE_SIZE_LIMIT) {
                        getWindow().showNotification(
                                "File rejected. Max " + (FILE_SIZE_LIMIT / 1024 / 1024) + "Mb files are accepted.",
                                Notification.TYPE_WARNING_MESSAGE);
                    } else {

                        final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                        StreamVariable streamVariable = new StreamVariable() {
                            private static final long serialVersionUID = -4778149214665537608L;

                            @Override
                            public OutputStream getOutputStream() {
                                return bas;
                            }

                            @Override
                            public boolean listenProgress() {
                                return false;
                            }

                            @Override
                            public void onProgress(StreamingProgressEvent event) {
                            }

                            @Override
                            public void streamingStarted(StreamingStartEvent event) {
                            }

                            @Override
                            public void streamingFinished(StreamingEndEvent event) {
                                FileUploadFormField.this.progress.setVisible(false);
                                showFile(fileName, html5File.getType(), bas);
                            }

                            @Override
                            public void streamingFailed(StreamingErrorEvent event) {
                                FileUploadFormField.this.progress.setVisible(false);
                            }

                            @Override
                            public boolean isInterrupted() {
                                return false;
                            }
                        };
                        html5File.setStreamVariable(streamVariable);
                        FileUploadFormField.this.progress.setVisible(true);
                    }
                }

            } else {
                String text = tr.getText();
                // @todo check if there's something to do here
            }
        }

        private void showFile(final String name, final String type, final ByteArrayOutputStream bas) {
            try {
                final BufferedImage image = scaleImage(ImageIO.read(new ByteArrayInputStream(bas.toByteArray())),
                        (int) (FileUploadFormField.this.dropPane.getWidth()), (int) (FileUploadFormField.this.dropPane.getHeight()));

                StreamResource resource = new StreamResource(new StreamSource() {
                    @Override
                    public InputStream getStream() {
                        if (image != null) {
                            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                            try {
                                // @todo get image format from 'type' method
                                // parameter
                                ImageIO.write(image, "jpg", outStream);
                                return new ByteArrayInputStream(outStream.toByteArray());
                            } catch (IOException e) {
                                // @todo deal with exception
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                }, name, getApplication());
                Embedded embedded = new Embedded(name, resource);

                FileUploadFormField.this.dropPane.removeAllComponents();
                FileUploadFormField.this.dropPane.addComponent(embedded);
            } catch (IOException e) {
                // @todo deal with exception
                e.printStackTrace();
            }
        }

        private BufferedImage scaleImage(BufferedImage image, int w, int h) {
            BufferedImage t = new BufferedImage(w, h, BufferedImage.SCALE_DEFAULT);
            Graphics2D g = t.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g.drawImage(image, 0, 0, w, h, 0, 0, image.getWidth(), image.getHeight(), null);
            g.dispose();
            return t;
        }

        @Override
        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }
    }

}