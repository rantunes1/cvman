package com.glintt.cvm.ui.forms.professionalinfo;

import com.glintt.cvm.model.ProfessionalInfo;
import com.glintt.cvm.ui.api.AbstractBeanItemAdapter;
import com.glintt.cvm.ui.api.BeanItemForm;
import com.vaadin.ui.GridLayout;

public class ProfessionalInfoForm extends BeanItemForm<AbstractBeanItemAdapter<ProfessionalInfo>> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1064972258535131567L;
	private GridLayout formLayout;

    public ProfessionalInfoForm(ProfessionalInfo professionalInfo) {
        super(new ProfessionalInfoFormAdapter(professionalInfo));
    }

    @Override
    protected void executeOnRepaint() {
        // TODO Auto-generated method stub

    }
}
