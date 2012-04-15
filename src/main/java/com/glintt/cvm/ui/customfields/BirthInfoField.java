package com.glintt.cvm.ui.customfields;

import java.util.ArrayList;
import java.util.List;

import com.glintt.cvm.model.PersonalInfo.BirthInfo;
import com.glintt.cvm.ui.api.AbstractFormFieldFactory;
import com.glintt.cvm.ui.api.AbstractItemField;
import com.glintt.cvm.ui.forms.EmbeddedForm;
import com.vaadin.data.Buffered;
import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;

public class BirthInfoField extends AbstractItemField {
    private static final long serialVersionUID = -68160211596410488L;

    private final Form parentForm;

    public BirthInfoField(Item item, Object propertyId, Form parentForm) {
        super(item, propertyId);
        this.parentForm = (parentForm != null) ? new EmbeddedForm(parentForm) : new Form();

        this.parentForm.setFormFieldFactory(new DefaultFieldFactory() {
            private static final long serialVersionUID = 3383707616773239325L;
        });
        setCompositionRoot(this.parentForm);
    }

    @Override
    public void commit() throws Buffered.SourceException, InvalidValueException {
        super.commit();
        this.parentForm.commit();
    }

    @Override
    public void discard() throws Buffered.SourceException {
        super.discard();
        this.parentForm.discard();
    }

    @Override
    public void setInternalValue(Object newValue) throws ReadOnlyException, ConversionException {
        // create the address if not given
        BirthInfo birthInfo = (newValue instanceof BirthInfo) ? (BirthInfo) newValue : new BirthInfo();

        super.setInternalValue(birthInfo);

        // set item data source and visible properties in a single operation to
        // avoid creating fields multiple times
        List<String> visibleProperties = new ArrayList<String>();
        visibleProperties.add("birthDate");
        visibleProperties.add("birthLocation");
        this.parentForm.setItemDataSource(new BeanItem<BirthInfo>(birthInfo), visibleProperties);
    }

    protected static class BirthInfoFieldFormFactory extends AbstractFormFieldFactory {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1232885743926110813L;

		protected BirthInfoFieldFormFactory(Form ownerForm) {
            super(ownerForm);
        }

        @Override
        protected Field createPropertyField(Item item, String propertyId, Component uiContext) {
            return null;
        }
    }

}
