package com.glintt.cvm.ui.customfields;

import com.glintt.cvm.model.PersonalInfo.BirthInfo;
import com.glintt.cvm.ui.api.AbstractItemField;
import com.vaadin.data.Item;
import com.vaadin.ui.PopupDateField;

public class BirthdateField extends AbstractItemField {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4086207825102448423L;

	public BirthdateField(Item item, Object propertyId) {
        super(item, propertyId);
        BirthInfo birthInfo = (BirthInfo) item.getItemProperty(propertyId).getValue();

        PopupDateField birthDate = new PopupDateField();
        // if (birthInfo != null) {
        // birthDate.setValue(birthInfo.getBirthDate());
        // }
        birthDate.setResolution(PopupDateField.RESOLUTION_DAY);
        setCompositionRoot(birthDate);
    }

    @Override
    public void setInternalValue(Object newValue) throws ReadOnlyException, ConversionException {
        // create the address if not given
        BirthInfo birthInfo = (newValue instanceof BirthInfo) ? (BirthInfo) newValue : new BirthInfo();
    }

}
