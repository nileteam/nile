package com.poworkspace.view.utils;

import java.util.HashMap;
import java.util.Map;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpel.services.workflow.client.IWorkflowServiceClient;
import oracle.bpel.services.workflow.client.IWorkflowServiceClientConstants;
import oracle.bpel.services.workflow.client.WorkflowServiceClientFactory;

import oracle.bpel.services.workflow.query.ITaskQueryService;
import oracle.bpel.services.workflow.task.ITaskService;

import oracle.bpm.client.BPMServiceClientFactory;
import oracle.bpm.services.client.IBPMServiceClient;

public class BPMContextUtils {

    public static BPMServiceClientFactory getBPMServiceClientFactory() {

          return BPMServiceClientFactory.getInstance(LoadProperties.getIBPMConnectionPropertyMap(), null, null);
    }

    public static IBPMContext getIBPMContext(String userName, String password) throws Exception {
        IBPMContext ctx =
            getBPMServiceClientFactory().getBPMUserAuthenticationService().authenticate(LoadProperties.fetchProperty().getProperty("EJB_SECURITY_PRINCIPAL"),
                                                                                        LoadProperties.fetchProperty().getProperty("EJB_SECURITY_CREDENTIALS").toCharArray(),
                                                                                        null);
        ctx = getBPMServiceClientFactory().getBPMUserAuthenticationService().authenticateOnBehalfOf(ctx, userName);
        return ctx;
    }

    public static IWorkflowServiceClient getIWorkflowServiceClient() {
        return getBPMServiceClientFactory().getWorkflowServiceClient();
    }

    public static ITaskService getTaskService() {
        return getBPMServiceClientFactory().getWorkflowServiceClient().getTaskService();
    }

    public static ITaskQueryService getTaskQueryService() {
        return getBPMServiceClientFactory().getWorkflowServiceClient().getTaskQueryService();
    }

    public static IBPMServiceClient getBPMServiceClient() {
        return getBPMServiceClientFactory().getBPMServiceClient();
    }
}
