package com.glintt.cvm.model;

import java.io.Serializable;
import java.util.List;

import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.vaadin.data.util.BeanItemContainer;

public class UserContainer extends BeanItemContainer<CVUser> implements Serializable {
    private static final long serialVersionUID = -1859725796039126537L;

    public UserContainer() throws InstantiationException, IllegalAccessException {
        super(CVUser.class);
    }

    public static void populatedContainer(UserContainer container) {
        if (container == null) {
            return;
        }

        container.removeAllItems();

        List<CVUser> users = FacadeFactory.getFacade().list(CVUser.class);

        if (users != null) {
            for (CVUser user : users) {
                container.addItem(user);
            }
        }

    }

    public static UserContainer createAndPopulatedContainer() {
        UserContainer container = null;
        try {
            container = new UserContainer();
            populatedContainer(container);
        } catch (InstantiationException e) {
            // @todo Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // @todo Auto-generated catch block
            e.printStackTrace();
        }
        return container;
    }
}
