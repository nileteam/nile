package com.poworkspace.view.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import oracle.bpel.services.workflow.client.IWorkflowServiceClientConstants;
import oracle.bpel.services.workflow.client.WorkflowServiceClientFactory;

public class LoadProperties {

    public static final String FILEPATH = "D:\\Nishant\\";

    public static Properties fetchProperty() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            //input = new FileInputStream(FILEPATH + "config.properties");
            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            //            System.out.println(prop.getProperty("TASK.FirstTask.APPLICATION.URI"));


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    public static Map<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String> getIWorkflowConnectionPropertyMap() {
        System.out.println("Loading IWorkflow Map...");
        Map<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String> properties =
            new HashMap<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String>();

        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.CLIENT_TYPE,
                       LoadProperties.fetchProperty().getProperty("CLIENT_TYPE"));
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_PROVIDER_URL,
                       LoadProperties.fetchProperty().getProperty("EJB_PROVIDER_URL"));
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_SECURITY_CREDENTIALS,
                       LoadProperties.fetchProperty().getProperty("EJB_SECURITY_CREDENTIALS"));
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_SECURITY_PRINCIPAL,
                       LoadProperties.fetchProperty().getProperty("EJB_SECURITY_PRINCIPAL"));
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_INITIAL_CONTEXT_FACTORY,
                       LoadProperties.fetchProperty().getProperty("EJB_INITIAL_CONTEXT_FACTORY"));
        System.out.println("Loading Map...success");
        return properties;
    }
    
    public static Map<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String> getIBPMConnectionPropertyMap() {
        System.out.println("Loading IBPM Map...");
        Map<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String> properties =
            new HashMap<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String>();

        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.CLIENT_TYPE,
                       WorkflowServiceClientFactory.REMOTE_CLIENT);
        
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_PROVIDER_URL,
                       LoadProperties.fetchProperty().getProperty("EJB_PROVIDER_URL"));

        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_INITIAL_CONTEXT_FACTORY,
                       "weblogic.jndi.WLInitialContextFactory");
        
        System.out.println("Loading Map...success");
        return properties;
    }

//    public static void main(String[] s) {
////        System.out.println("Application URI : " + "http://" +
////                           LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.IP") + ":" +
////                           LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.PORT") +
////                           LoadProperties.fetchProperty().getProperty("TASK.FirstTask.APPLICATION.URI"));
////        System.out.println(LoadProperties.fetchProperty().getProperty("CLIENT_TYPE"));
//        
//    }
}
