package com.poworkspace.view.beans;

import com.poworkspace.view.utils.BPMContextUtils;
import com.poworkspace.view.utils.JSFUtils;

import com.poworkspace.view.utils.LoadProperties;
import com.poworkspace.view.utils.WorkflowContextUtils;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpel.services.workflow.IWorkflowConstants;
import oracle.bpel.services.workflow.WorkflowException;
import oracle.bpel.services.workflow.client.IWorkflowServiceClient;
import oracle.bpel.services.workflow.client.IWorkflowServiceClientConstants;
import oracle.bpel.services.workflow.client.WorkflowServiceClientFactory;
import oracle.bpel.services.workflow.query.ITaskQueryService;
import oracle.bpel.services.workflow.task.ITaskService;
import oracle.bpel.services.workflow.task.model.Task;

import oracle.bpel.services.workflow.verification.IWorkflowContext;

import oracle.bpm.services.instancemanagement.IInstanceManagementService;
import oracle.bpm.services.processmetadata.IProcessMetadataService;
import oracle.bpm.services.processmetadata.ProcessMetadataSummary;

import oracle.bpel.services.workflow.repos.Predicate;
import oracle.bpel.services.workflow.repos.TableConstants;
import oracle.bpel.services.workflow.task.model.IdentityType;
import oracle.bpel.services.workflow.task.model.ProcessType;

public class BPMTaskHelper {

    public static String initiateBpmTask() throws Exception {

        //        IWorkflowContext workflowContext = WorkflowContextUtils.initBPMContext("nile", "Welcome1");
        IBPMContext ibpmContext = BPMContextUtils.getIBPMContext("nile", "welcome1");

        IProcessMetadataService service = BPMContextUtils.getBPMServiceClient().getProcessMetadataService();
        List<ProcessMetadataSummary> initiableTasks = service.getInitiatableProcesses(ibpmContext);

        String taskId = null;
        //        System.out.println("Size of initiatable tasks list >> " + initiableTasks.size());
        for (int i = 0; i < initiableTasks.size(); i++) {

            ProcessMetadataSummary pms = initiableTasks.get(i);
            //            System.out.println(pms.getCompositeDN() + "/" + pms.getProcessName());
        }
        //        ProcessMetadataSummary pms = initiableTasks.get(0);
        //
        //        if (pms != null && pms.isHasSwimlaneRole()) {
        //            IInstanceManagementService ims = BPMContextUtils.getBPMServiceClient().getInstanceManagementService();
        //            Task task = ims.createProcessInstanceTask(ibpmContext,  pms.getCompositeDN()+"/"+pms.getProcessName());
        //            taskId = task.getSystemAttributes().getTaskId();
        //        } else {
        //            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, "There are no tasks to start !");
        //            FacesContext facesContext = FacesContext.getCurrentInstance();
        //            facesContext.addMessage(null, msg);
        //        }

        return taskId;
    }

    public static List<String> fetchInitiatorProcessList(String username, String password) throws Exception {

        List<String> initiatorProcessList = new ArrayList<String>();
        IBPMContext ibpmContext = BPMContextUtils.getIBPMContext(username, password);
        //        IBPMContext ibpmContext = (IBPMContext)JSFUtils.getManagedBeanValue("sessionScope.ibpmContext");

        IProcessMetadataService service = BPMContextUtils.getBPMServiceClient().getProcessMetadataService();
        List<ProcessMetadataSummary> initiableTasks = service.getInitiatableProcesses(ibpmContext);

        //        System.out.println("Size of initiatable tasks list >> " + initiableTasks.size());
        for (int i = 0; i < initiableTasks.size(); i++) {

            ProcessMetadataSummary pms = initiableTasks.get(i);
            System.out.println(pms.getCompositeDN() + "/" + pms.getProcessName());
            initiatorProcessList.add(pms.getProcessName());
        }

        return initiatorProcessList;
    }


    public static List getTaskdetails(String user, String password) {

        List tasks = null;
        try {
            //Create JAVA WorflowServiceClient
            //            IWorkflowServiceClient wfSvcClient =
            //                WorkflowServiceClientFactory.getWorkflowServiceClient(WorkflowServiceClientFactory.REMOTE_CLIENT,
            //                                                                      LoadProperties.getConnectionPropertyMap(), null);
            IWorkflowServiceClient wfSvcClient =
                WorkflowServiceClientFactory.getWorkflowServiceClient(LoadProperties.getIWorkflowConnectionPropertyMap(), null);

            ITaskQueryService querySvc = wfSvcClient.getTaskQueryService();

            IWorkflowContext ctx = WorkflowContextUtils.initBPMContext(user, password);

            System.out.println("Done");

            Predicate statePredicate =
                new Predicate(TableConstants.WFTASK_STATE_COLUMN, Predicate.OP_EQ,
                              IWorkflowConstants.TASK_STATE_ASSIGNED);

            //Set up list of columns to query
            List queryColumns = new ArrayList();
            queryColumns.add("TASKID");
            queryColumns.add("TASKNUMBER");
            queryColumns.add("TITLE");
            queryColumns.add("TASKDISPLAYURL");
            queryColumns.add("TASKDEFINITIONID");
            queryColumns.add("TASKDEFINITIONNAME");

            //Query a list of tasks assigned to jcooper
            tasks = querySvc.queryTasks(ctx, queryColumns, null, //Do not query additional info
                                        ITaskQueryService.AssignmentFilter.MY, null, //No keywords
                                        statePredicate, //No custom predicate
                                        null, //No special ordering
                                        0, //Do not page the query result
                                        0);

            System.out.println(tasks.size());
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Task task = (Task) tasks.get(i);

                int taskNumber = task.getSystemAttributes().getTaskNumber();
                String title = task.getTitle();
                String taskId = task.getSystemAttributes().getTaskId();

                Date assidnedDate = task.getSystemAttributes().getAssignedDate().getTime();
                SimpleDateFormat ft = new SimpleDateFormat("E dd-MMM-yyyy hh:mm:ss a");

                //System.out.println(task.;
                System.out.println(task.getSystemAttributes().getTaskDefinitionId() + " @@ " +
                                   task.getSystemAttributes().getTaskDefinitionName());
                System.out.println("Application URI : " + LoadProperties.fetchProperty().getProperty("TASK."+task.getSystemAttributes().getTaskDefinitionName()+".APPLICATION.URI"));

                System.out.println("Task Id: " + taskId);
                System.out.println("Task Title: " + title);
                System.out.println("taskNumber: " + taskNumber);
                // Assigned Date
                System.out.println(ft.format(assidnedDate));
                // process name
                System.out.println(task.getProcessInfo());

                List assigneeUsers = task.getSystemAttributes().getAssigneeUsers();
                //                System.out.println(assigneeUsers.size());
                String names = "";
                for (int j = 0; j < assigneeUsers.size(); j++) {
                    IdentityType type = (IdentityType) assigneeUsers.get(j);
                    String n = type.getId();
                    if (names.equalsIgnoreCase("")) {
                        names = n;
                    } else {
                        names += "," + n;
                    }
                }

            }
        } catch (Exception e) {
            //Handle any exceptions raised here...
            System.out.println("Caught workflow exception: " + e.getMessage());

        }

        return tasks;
    }


//    public static void main(String[] args) {
////        getTaskdetails("nile");
//
//                try {
//        
//                    List<String> pList = BPMTaskHelper.fetchInitiatorProcessList("initiatortest","welcome1");
//        
//                    for (String s : pList) {
//                        System.out.println(s);
//                    }
//        
//        
//                } catch (Exception e) {
//        
//                    e.printStackTrace();
//                }
//    }
}
