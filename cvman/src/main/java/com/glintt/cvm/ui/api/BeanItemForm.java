package com.glintt.cvm.ui.api;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.terminal.Paintable.RepaintRequestListener;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Window;

public abstract class BeanItemForm<ITEM_ADAPTER extends AbstractBeanItemAdapter<? extends AbstractPojo>> extends Form implements
		RepaintRequestListener {

	private static final long serialVersionUID = -134463363910505697L;

	private final ITEM_ADAPTER adapter;

	private final Button saveBtn;
	private final Button resetBtn;

	protected abstract void executeOnRepaint();

	public BeanItemForm(ITEM_ADAPTER adapter) {
		this.adapter = adapter;
		setWriteThrough(false);
		setImmediate(true);

		this.saveBtn = new Button("Save", this, "commit");
		this.resetBtn = new Button("Cancel", this, "discard");

		Button showState = new Button("Show Pojo State", new ClickListener() {
			private static final long serialVersionUID = 472558219751562947L;

			@Override
			public void buttonClick(ClickEvent event) {
				showPojoState(getItemDataSource());
			}
		});

		Layout formLayout = getFormLayout();
		if (formLayout != null) {
			setLayout(formLayout);
		}

		Layout footerLayout = getFooter();
		footerLayout.addComponent(this.saveBtn);
		footerLayout.addComponent(this.resetBtn);
		footerLayout.addComponent(showState);
		if (footerLayout instanceof AbstractOrderedLayout) {
			((AbstractOrderedLayout) footerLayout).setSpacing(true);
		}

		setData(this.adapter);
	}

	protected Layout getFormLayout() {
		return null;
	}

	protected ITEM_ADAPTER getAdapter() {
		return this.adapter;
	}

	@Override
	public void setData(Object beanItemAdapter) {
		if (beanItemAdapter instanceof AbstractBeanItemAdapter<?>) {
			setFormFieldFactory((AbstractBeanItemAdapter<?>) beanItemAdapter);
			setItemDataSource((AbstractBeanItemAdapter<?>) beanItemAdapter);
			setDescription(((AbstractBeanItemAdapter<?>) beanItemAdapter).getBean().getClass().getSimpleName() + " Form");
		}
	}

	@Override
	public void repaintRequested(RepaintRequestEvent event) {
		boolean visible = isVisible();
		// prevent repaint from being called recursively
		setVisible(false);
		executeOnRepaint();
		setVisible(visible);
	}

	protected Button getSaveBtn() {
		return this.saveBtn;
	}

	protected Button getResetBtn() {
		return this.resetBtn;
	}

	private void showPojoState(Item item) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Object propId : item.getItemPropertyIds()) {
			Property objectProp = item.getItemProperty(propId);
			stringBuffer.append(((BuildableObjectProperty) objectProp).getCaption() + ": " + objectProp.getValue() + "<br/>");
		}

		Window.Notification n = new Window.Notification("POJO state", Window.Notification.TYPE_TRAY_NOTIFICATION);
		n.setPosition(Window.Notification.POSITION_BOTTOM_RIGHT);
		n.setDescription(stringBuffer.toString());
		getWindow().showNotification(n);
	}
}