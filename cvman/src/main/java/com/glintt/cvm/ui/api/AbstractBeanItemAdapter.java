package com.glintt.cvm.ui.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.Paintable.RepaintRequestEvent;
import com.vaadin.terminal.Paintable.RepaintRequestListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextField;

public abstract class AbstractBeanItemAdapter<POJO extends AbstractPojo> extends BeanItem<POJO> implements FormFieldFactory {
	private static final long serialVersionUID = 9071483698415881844L;

	private final DefaultFieldFactory factory = new DefaultFieldFactory() {
		private static final long serialVersionUID = -6818852393788089079L;
	};

	private Map<Property, BuildableObjectProperty> propertyCache;

	public AbstractBeanItemAdapter(POJO bean) {
		super(bean);
		initializeItemProps();
	}

	public AbstractBeanItemAdapter(POJO bean, Collection<?> propertyIds) {
		super(bean, propertyIds);
		initializeItemProps();
	}

	public AbstractBeanItemAdapter(POJO bean, String[] propertyIds) {
		super(bean, propertyIds);
		initializeItemProps();
	}

	protected abstract void initializeItemProps();

	protected abstract Field createField(Item item, String propertyId, BuildableObjectProperty property, Component uiContext);

	@Override
	public BuildableObjectProperty getItemProperty(Object id) {
		Property property = super.getItemProperty(id);

		if (this.propertyCache == null)
			this.propertyCache = new HashMap<Property, BuildableObjectProperty>();

		BuildableObjectProperty buildableObjectProperty = this.propertyCache.get(property);
		if (buildableObjectProperty == null) {
			buildableObjectProperty = new BuildableObjectProperty(property);
			this.propertyCache.put(property, buildableObjectProperty);
		}
		return buildableObjectProperty;
	}

	@Override
	public boolean addItemProperty(Object id, Property property) {
		boolean addPropBool = super.addItemProperty(id, property);
		if (addPropBool == true) {

			if (this.propertyCache == null)
				this.propertyCache = new HashMap<Property, BuildableObjectProperty>();

			BuildableObjectProperty buildableObjectProperty = this.propertyCache.get(property);
			if (buildableObjectProperty == null) {
				buildableObjectProperty = new BuildableObjectProperty(property);
				this.propertyCache.put(property, buildableObjectProperty);
			}
		}

		return addPropBool;

	}

	@Override
	public boolean removeItemProperty(Object id) {
		Property property = super.getItemProperty(id);
		this.propertyCache.remove(property);
		return super.removeItemProperty(id);
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		BuildableObjectProperty property = (BuildableObjectProperty) item.getItemProperty(propertyId);

		Field field = createField(item, propertyId.toString(), property, uiContext);
		if (field == null) {
			field = this.factory.createField(item, propertyId, uiContext);
		}

		String caption = property.getCaption();
		if (caption == null) {
			caption = this.factory.createCaptionByPropertyId(propertyId);
		}
		if (caption.indexOf(" :") < 0) {
			caption += " :";
		}
		field.setCaption(caption);

		if (field.getClass().isAssignableFrom(TextField.class)) {
			((TextField) field).setNullRepresentation("");
		}

		String fieldWidth = property.getWidth();
		if (fieldWidth != null) {
			field.setWidth(fieldWidth);
		}

		field.setRequired(property.isRequired());
		for (Validator validator : property.getValidators()) {
			field.addValidator(validator);
		}

		RepaintRequestListener repaintListener = getRepaintRequestListener(field, property);
		if (repaintListener != null) {
			field.addListener(repaintListener);
		}

		return field;
	}

	protected RepaintRequestListener getRepaintRequestListener(final Field field, final BuildableObjectProperty property) {
		return new RepaintRequestListener() {
			private static final long serialVersionUID = -428629319667385841L;

			@Override
			public void repaintRequested(RepaintRequestEvent event) {
				if (!field.isVisible()) {
					return;
				}
				// prevent repaint from being called recursively
				field.setVisible(false);
				if (field.isRequired()) {
					System.out.println("field is required. valid : " + field.isValid());
					field.setRequiredError(Lang.getMessage("FormsDefaults.Validation.required", field.getCaption()));
				}
				final String caption = property.getCaption();
				if (caption != null && !caption.equals(((Field) event.getSource()).getCaption())) {
					field.setCaption(Lang.getMessage(caption));
				}
				field.setVisible(true);
			}

		};

	}

}
