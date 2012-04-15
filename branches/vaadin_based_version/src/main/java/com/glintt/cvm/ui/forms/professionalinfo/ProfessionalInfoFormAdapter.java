package com.glintt.cvm.ui.forms.professionalinfo;

import com.glintt.cvm.model.ProfessionalInfo;
import com.glintt.cvm.ui.api.AbstractBeanItemAdapter;
import com.glintt.cvm.ui.api.BuildableObjectProperty;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

public class ProfessionalInfoFormAdapter extends AbstractBeanItemAdapter<ProfessionalInfo> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -982295758968930691L;

	public ProfessionalInfoFormAdapter(ProfessionalInfo professionalInfo) {
        super(professionalInfo);
    }

    @Override
    protected void initializeItemProps() {
    }

    @Override
    protected Field createField(Item item, String propertyId, BuildableObjectProperty property, Component uiContext) {
        // TODO Auto-generated method stub
        return null;
    }
}
