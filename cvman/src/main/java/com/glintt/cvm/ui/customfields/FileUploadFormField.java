package com.glintt.cvm.ui.customfields;

//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//import javax.imageio.ImageIO;

import com.glintt.cvm.ui.api.AbstractItemField;
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

public class FileUploadFormField extends AbstractItemField {

    private static final long serialVersionUID = 4960715027303457297L;

    private ProgressIndicator progress;
    private CssLayout dropPane;
    private final String size;

    public FileUploadFormField(Item item, Object propertyId, String size) {
        super(item, propertyId);
        this.size = size;
        init();
    }

    private void init() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false, true, true, false);

        this.dropPane = new CssLayout();
        this.dropPane.setWidth(this.size);
        this.dropPane.setHeight(this.size);
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

        addListener(new ValueChangeListener() {
            private static final long serialVersionUID = 2265504318443035482L;

            @Override
            public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
                byte[] rawImage = (byte[]) event.getProperty().getValue();
//                FileUploadFormField.this.showImage("picture", "jpg", rawImage);
            }

        });

        // showImage("picture", "jpg", (byte[]) getValue());
    }

//    private BufferedImage getImage(byte[] rawImage, boolean scale) {
//        try {
//            ImageIO.setUseCache(false);
//            BufferedImage image = ImageIO.read(new ByteArrayInputStream(rawImage));
//            ImageIO.setUseCache(true);
//            return (scale) ? scaleImage(image, (int) (this.dropPane.getWidth()), (int) (this.dropPane.getHeight())) : image;
//        } catch (IOException ioex) {
//            ioex.printStackTrace();
//            return null;
//        }
//
//    }

//    private void showImage(final BufferedImage image, String name) {
//        if (name == null) {
//            // set some random name
//            name = this.dropPane.toString();
//        }
//
//        StreamResource resource = new StreamResource(new StreamSource() {
//            private static final long serialVersionUID = -72695620096190384L;
//
//            @Override
//            public InputStream getStream() {
//                System.out.println("******************************** GET STREAM ***********************************");
//
//                if (image != null) {
//                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//                    try {
//                        // @todo get image format from 'type' method
//                        // parameter
//                        ImageIO.write(image, "jpg", outStream);
//
//                        File file = File.createTempFile("XXX_", "_XXX");
//                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//                        byte[] rawImage = outStream.toByteArray();
//                        bos.write(rawImage);
//                        bos.close();
//
//                        return new ByteArrayInputStream(rawImage);
//                    } catch (IOException e) {
//                        // @todo deal with exception
//                        e.printStackTrace();
//                    }
//                }
//                return null;
//            }
//        }, name, getApplication());
//        Embedded embedded = new Embedded(name.equals(getPropertyId()) ? null : name, resource);
//
//        this.dropPane.removeAllComponents();
//        this.dropPane.addComponent(embedded);
//    }

//    private void showImage(final String name, final String type, byte[] rawImage) {
//        final BufferedImage image = getImage(rawImage, true);
//        showImage(image, name);
//    }

//    private void showFile(final String name, final String type, final ByteArrayOutputStream bas) {
//        showImage(name, type, bas.toByteArray());
//    }

//    private BufferedImage scaleImage(BufferedImage image, int w, int h) {
//        BufferedImage t = new BufferedImage(w, h, BufferedImage.SCALE_DEFAULT);
//        Graphics2D g = t.createGraphics();
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//        g.drawImage(image, 0, 0, w, h, 0, 0, image.getWidth(), image.getHeight(), null);
//        g.dispose();
//        return t;
//    }

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
//                                showFile(fileName, html5File.getType(), bas);
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

        @Override
        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }
    }

}