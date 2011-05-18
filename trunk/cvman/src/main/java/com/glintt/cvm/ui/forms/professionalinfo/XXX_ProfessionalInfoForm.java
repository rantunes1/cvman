package com.glintt.cvm.ui.forms.professionalinfo;

import com.glintt.cvm.model.ProfessionalInfo;
import com.glintt.cvm.ui.api.XXX_AbstractBeanItemAdapter;
import com.glintt.cvm.ui.api.XXX_BeanItemForm;
import com.vaadin.ui.GridLayout;

public class XXX_ProfessionalInfoForm extends XXX_BeanItemForm<XXX_AbstractBeanItemAdapter<ProfessionalInfo>> {

    private GridLayout formLayout;

    public XXX_ProfessionalInfoForm(ProfessionalInfo professionalInfo) {
        super(new XXX_ProfessionalInfoAdapter(professionalInfo));
    }

    @Override
    protected void executeOnRepaint() {
        // TODO Auto-generated method stub

    }
}
