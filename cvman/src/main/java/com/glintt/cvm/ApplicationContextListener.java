package com.glintt.cvm;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.vaadin.appfoundation.i18n.InternationalizationServlet;
import org.vaadin.appfoundation.i18n.TmxSourceReader;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

public class ApplicationContextListener implements ServletContextListener {

    private static Logger logger = Logger.getLogger(ApplicationContextListener.class.getCanonicalName());

    @Override
    public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
        // do nothing
        logger.info("CONTEXT DESTROYED");
    }

    @Override
    public void contextInitialized(ServletContextEvent paramServletContextEvent) {
        logger.info("INITIALIZING I18N FILES");
        // @todo move to properties
        List<URL> translationFiles = new ArrayList<URL>();
        translationFiles.add(this.getClass().getClassLoader().getResource("header.xml"));
        translationFiles.add(this.getClass().getClassLoader().getResource("footer.xml"));
        translationFiles.add(this.getClass().getClassLoader().getResource("formsDefaults.xml"));
        translationFiles.add(this.getClass().getClassLoader().getResource("homePage.xml"));
        translationFiles.add(this.getClass().getClassLoader().getResource("loginPage.xml"));
        translationFiles.add(this.getClass().getClassLoader().getResource("createUserPage.xml"));
        translationFiles.add(this.getClass().getClassLoader().getResource("personalInfoForm.xml"));
        logger.info("I18N INITITALIZED");

        for (URL translation : translationFiles) {
            File file = new File(translation.getPath());
            InternationalizationServlet.loadTranslations(new TmxSourceReader(file));
        }

        logger.info("INITIALIZING DATABASE");
        // @todo inject facade name
        if (FacadeFactory.getFacade() == null) {
            try {
                FacadeFactory.registerFacade("mysql", true);
            } catch (InstantiationException iex) {
                logger.severe(iex.getMessage());
                throw new IllegalStateException(iex);
            } catch (IllegalAccessException iaex) {
                logger.severe(iaex.getMessage());
                throw new IllegalStateException(iaex);
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
        logger.info("DATABASE INITITALIZED");
    }

}
