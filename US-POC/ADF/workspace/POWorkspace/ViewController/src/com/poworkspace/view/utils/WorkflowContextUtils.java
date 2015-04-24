package com.poworkspace.view.utils;

import java.util.HashMap;
import java.util.Map;

import oracle.bpel.services.workflow.WorkflowException;
import oracle.bpel.services.workflow.client.IWorkflowServiceClient;
import oracle.bpel.services.workflow.client.IWorkflowServiceClientConstants;
import oracle.bpel.services.workflow.client.WorkflowServiceClientFactory;
import oracle.bpel.services.workflow.query.ITaskQueryService;
import oracle.bpel.services.workflow.verification.IWorkflowContext;

public class WorkflowContextUtils {
    public WorkflowContextUtils() {
        super();
    }

    public static IWorkflowContext initBPMContext(String userName, String password) throws WorkflowException {

        IWorkflowServiceClient wfSvcClient =
            WorkflowServiceClientFactory.getWorkflowServiceClient(LoadProperties.getIWorkflowConnectionPropertyMap(), null);

        ITaskQueryService querySvc = wfSvcClient.getTaskQueryService();
        IWorkflowContext ctx =
            querySvc.authenticate(LoadProperties.fetchProperty().getProperty("EJB_SECURITY_PRINCIPAL"),
                                  LoadProperties.fetchProperty().getProperty("EJB_SECURITY_CREDENTIALS").toCharArray(),
                                  null);
        ctx = querySvc.authenticate(userName, password.toCharArray(), null);
        //        ctx = querySvc.authenticateOnBehalfOf(ctx, userName);
        //
        return ctx;
    }
    
    public static void detroyContxt(IWorkflowContext ctx) throws WorkflowException {
        IWorkflowServiceClient wfSvcClient =
            WorkflowServiceClientFactory.getWorkflowServiceClient(LoadProperties.getIWorkflowConnectionPropertyMap(), null);

        ITaskQueryService querySvc = wfSvcClient.getTaskQueryService();
        querySvc.destroyWorkflowContext(ctx);
    }

    //    public static void main(String[] a) {
    //        try {
    //            IWorkflowContext workflowContext = initBPMContext("nile", "Welcome1");
    //            System.out.println("t->" + workflowContext.getToken());
    //        } catch (WorkflowException e) {
    //        }
    //
    //    }
}
