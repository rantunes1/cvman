package com.glintt.cvm.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ErrorMessage;
import com.vaadin.terminal.Paintable.RepaintRequestListener;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;

public abstract class AbstractBaseForm extends Form implements RepaintRequestListener {
    private static final long serialVersionUID = 852508767733733785L;

    private final Button saveBtn = new Button();
    private final Button resetBtn = new Button();

    protected <AP extends AbstractPojo> AbstractBaseForm(AP datasource) {
        super();
        initFormComponent();
        setInternalItemDataSource(datasource);
        setValidationVisible(false);
        setValidationVisibleOnCommit(true);
        setImmediate(true);
        setWriteThrough(false);
        addListener((RepaintRequestListener) this);
        setFormFieldFactory(createFormFactory());
        List<FormFieldDefinition> editableFields = getEditableFieldsList();
        if (editableFields != null) {
            List<String> fieldsOrder = new ArrayList<String>();
            for (FormFieldDefinition fieldDefinition : editableFields) {
                fieldsOrder.add(fieldDefinition.getName());
            }
            this.setVisibleItemProperties(fieldsOrder);
        }

        HorizontalLayout footer = new HorizontalLayout();
        this.saveBtn.addListener(getOnSaveListener());
        footer.addComponent(this.saveBtn);

        this.resetBtn.addListener(getOnResetListener());
        footer.addComponent(this.resetBtn);

        this.setFooter(footer);
    }

    protected <AP extends AbstractPojo> void setInternalItemDataSource(AP datasource) {
        if (datasource != null) {
            this.setItemDataSource(new BeanItem<AP>(datasource));
        }
    }

    protected boolean isEditableproperty(String propertyId) {
        return getFieldDefinition(propertyId) != null;
    }

    protected FormFieldDefinition getFieldDefinition(String propertyId) {
        List<FormFieldDefinition> editableFields = getEditableFieldsList();
        if (editableFields != null) {
            for (FormFieldDefinition fieldDefinition : editableFields) {
                if (fieldDefinition.getName().equals(propertyId)) {
                    return fieldDefinition;
                }
            }
        }
        return null;
    }

    protected Button getSaveBtn() {
        return this.saveBtn;
    }

    protected Button getResetBtn() {
        return this.resetBtn;
    }

    protected ClickListener getOnSaveListener() {
        return new ClickListener() {
            private static final long serialVersionUID = 2434106748369874951L;

            @Override
            public void buttonClick(ClickEvent event) {
                AbstractBaseForm.this.commit();

            }
        };
    }

    protected ClickListener getOnResetListener() {
        return new ClickListener() {
            private static final long serialVersionUID = 6125951514664930640L;

            @Override
            public void buttonClick(ClickEvent event) {
                AbstractBaseForm.this.discard();
            }
        };
    }

    @Override
    public void repaintRequested(RepaintRequestEvent event) {
        boolean visible = isVisible();
        // prevent repaint from being called recursively
        setVisible(false);
        getSaveBtn().setCaption(Lang.getMessage("FormsDefaults.UI.commit.caption"));
        getResetBtn().setCaption(Lang.getMessage("FormsDefaults.UI.discard.caption"));
        executeOnRepaint();
        setVisible(visible);
    }

    @Override
    public ErrorMessage getErrorMessage() {
        ErrorMessage message = super.getErrorMessage();
        if (message != null) {
            return new UserError(Lang.getMessage("FormsDefaults.Validation.generic"));
        }
        return null;
    }

    protected abstract void initFormComponent();

    protected abstract FormFieldFactory createFormFactory();

    protected abstract List<FormFieldDefinition> getEditableFieldsList();

    protected abstract void executeOnRepaint();

}
