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

import oracle.bpm.services.common.exception.BPMException;
import oracle.bpm.services.processmetadata.ProcessMetadataServiceException;

public class BPMTaskHelper {

    public static String initiateBpmTask(String processName, String CompositeDN) {

        //        IWorkflowContext workflowContext = WorkflowContextUtils.initBPMContext("nile", "Welcome1"); CompositeDNPlusProcess
        //        IBPMContext ibpmContext = BPMContextUtils.getIBPMContext("nile", "welcome1");
        IBPMContext ibpmContext = (IBPMContext) JSFUtils.getManagedBeanValue("sessionScope.ibpmContext");

        System.out.println("---------------------------> " + ibpmContext);
        System.out.println("---------------------------> " + ibpmContext.getToken());

        IProcessMetadataService service = BPMContextUtils.getBPMServiceClient().getProcessMetadataService();
        //        List<ProcessMetadataSummary> initiableTasks = service.getInitiatableProcesses(ibpmContext);
        //        //service.
        //
        String taskId = null;
        //        //        System.out.println("Size of initiatable tasks list >> " + initiableTasks.size());
        //        for (int i = 0; i < initiableTasks.size(); i++) {

        ProcessMetadataSummary pms;
        try {
            //pms = service.getProcessMetadataSummary(ibpmContext, CompositeDN, processName);
            //if (pms != null && pms.isHasSwimlaneRole()) {
            IInstanceManagementService ims = BPMContextUtils.getBPMServiceClient().getInstanceManagementService();
            Task task = ims.createProcessInstanceTask(ibpmContext, CompositeDN + "/" + processName);
            taskId = task.getSystemAttributes().getTaskId();
            //            } else {
            //                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Unable to initiate the task !");
            //                FacesContext facesContext = FacesContext.getCurrentInstance();
            //                facesContext.addMessage(null, msg);
            //            }
        } catch (ProcessMetadataServiceException e) {
            e.printStackTrace();
        } catch (BPMException e) {
            e.printStackTrace();
        }

        return taskId;
    }

    public static List<ProcessMetadataSummary> fetchInitiatorProcessList() throws Exception {

        List<ProcessMetadataSummary> initiatorProcessList = new ArrayList<ProcessMetadataSummary>();
        //IBPMContext ibpmContext = BPMContextUtils.getIBPMContext(username, password);
        IBPMContext ibpmContext = (IBPMContext) JSFUtils.getManagedBeanValue("sessionScope.ibpmContext");

        IProcessMetadataService service = BPMContextUtils.getBPMServiceClient().getProcessMetadataService();
        List<ProcessMetadataSummary> initiableTasks = service.getInitiatableProcesses(ibpmContext);

        //        System.out.println("Size of initiatable tasks list >> " + initiableTasks.size());
        for (int i = 0; i < initiableTasks.size(); i++) {

            ProcessMetadataSummary pms = initiableTasks.get(i);

            System.out.println(pms.getCompositeDN() + "/" + pms.getProcessName());
            initiatorProcessList.add(pms);
        }

        return initiatorProcessList;
    }

    //    public static List getTaskdetails(String user, String password) {
    //
    //        List tasks = null;
    //        try {
    //            //Create JAVA WorflowServiceClient
    //            //            IWorkflowServiceClient wfSvcClient =
    //            //                WorkflowServiceClientFactory.getWorkflowServiceClient(WorkflowServiceClientFactory.REMOTE_CLIENT,
    //            //                                                                      LoadProperties.getConnectionPropertyMap(), null);
    //            IWorkflowServiceClient wfSvcClient =
    //                WorkflowServiceClientFactory.getWorkflowServiceClient(LoadProperties.getIWorkflowConnectionPropertyMap(), null);
    //
    //            ITaskQueryService querySvc = wfSvcClient.getTaskQueryService();
    //
    //            IWorkflowContext ctx = WorkflowContextUtils.initBPMContext(user, password);
    //
    //            System.out.println("Done");
    //
    //            Predicate statePredicate =
    //                new Predicate(TableConstants.WFTASK_STATE_COLUMN, Predicate.OP_EQ,
    //                              IWorkflowConstants.TASK_STATE_ASSIGNED);
    //
    //            //Set up list of columns to query
    //            List queryColumns = new ArrayList();
    //            queryColumns.add("TASKID");
    //            queryColumns.add("TASKNUMBER");
    //            queryColumns.add("TITLE");
    //            queryColumns.add("TASKDISPLAYURL");
    //            queryColumns.add("TASKDEFINITIONID");
    //            queryColumns.add("TASKDEFINITIONNAME");
    //
    //            //Query a list of tasks assigned to jcooper
    //            tasks = querySvc.queryTasks(ctx, queryColumns, null, //Do not query additional info
    //                                        ITaskQueryService.AssignmentFilter.MY, null, //No keywords
    //                                        statePredicate, //No custom predicate
    //                                        null, //No special ordering
    //                                        0, //Do not page the query result
    //                                        0);
    //
    //            System.out.println(tasks.size());
    //            for (int i = 0; i < tasks.size(); i++) {
    //                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    //                Task task = (Task) tasks.get(i);
    //
    //                int taskNumber = task.getSystemAttributes().getTaskNumber();
    //                String title = task.getTitle();
    //                String taskId = task.getSystemAttributes().getTaskId();
    //
    //                Date assidnedDate = task.getSystemAttributes().getAssignedDate().getTime();
    //                SimpleDateFormat ft = new SimpleDateFormat("E dd-MMM-yyyy hh:mm:ss a");
    //
    //                //System.out.println(task.;
    //                System.out.println(task.getSystemAttributes().getTaskDefinitionId() + " @@ " +
    //                                   task.getSystemAttributes().getTaskDefinitionName());
    //                System.out.println("Application URI : " + LoadProperties.fetchProperty().getProperty("TASK."+task.getSystemAttributes().getTaskDefinitionName()+".APPLICATION.URI"));
    //
    //                System.out.println("Task Id: " + taskId);
    //                System.out.println("Task Title: " + title);
    //                System.out.println("taskNumber: " + taskNumber);
    //                // Assigned Date
    //                System.out.println(ft.format(assidnedDate));
    //                // process name
    //                System.out.println(task.getProcessInfo());
    //
    //                List assigneeUsers = task.getSystemAttributes().getAssigneeUsers();
    //                //                System.out.println(assigneeUsers.size());
    //                String names = "";
    //                for (int j = 0; j < assigneeUsers.size(); j++) {
    //                    IdentityType type = (IdentityType) assigneeUsers.get(j);
    //                    String n = type.getId();
    //                    if (names.equalsIgnoreCase("")) {
    //                        names = n;
    //                    } else {
    //                        names += "," + n;
    //                    }
    //                }
    //
    //            }
    //        } catch (Exception e) {
    //            //Handle any exceptions raised here...
    //            System.out.println("Caught workflow exception: " + e.getMessage());
    //
    //        }
    //
    //        return tasks;
    //    }

    public static List getTaskdetails(IWorkflowContext ctx, String viewTypeReceived) {
        System.out.println("inside getTaskDetails CTX");

        ITaskQueryService.AssignmentFilter assignmentFilter = ITaskQueryService.AssignmentFilter.MY;

        switch (viewTypeReceived) {
        case "My":
            assignmentFilter = ITaskQueryService.AssignmentFilter.MY_AND_GROUP_ALL;
            break;
        case "Initiated":
            assignmentFilter = ITaskQueryService.AssignmentFilter.CREATOR;
            break;
        case "Admin":
            assignmentFilter = ITaskQueryService.AssignmentFilter.ALL;
            break;
        }

        List tasks = null;
        try {
            IWorkflowServiceClient wfSvcClient =
                WorkflowServiceClientFactory.getWorkflowServiceClient(LoadProperties.getIWorkflowConnectionPropertyMap(),
                                                                      null);

            ITaskQueryService querySvc = wfSvcClient.getTaskQueryService();

            //            IWorkflowContext ctx = WorkflowContextUtils.initBPMContext(user, password);

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
                                        ITaskQueryService.AssignmentFilter.MY_AND_GROUP_ALL, null, //No keywords
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
                System.out.println("Application URI : " +
                                   LoadProperties.fetchProperty().getProperty("TASK." +
                                                                              task.getSystemAttributes().getTaskDefinitionName() +
                                                                              ".APPLICATION.URI"));

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


    public static List getTasksForStatus(IWorkflowContext ctx, String status, String grpFilter) {
        System.out.println("inside getTaskDetails CTX");
        List tasks = null;
        List<String> correctStates = new ArrayList<String>();
        ITaskQueryService.AssignmentFilter assignmentFilter = ITaskQueryService.AssignmentFilter.MY;

        //Group Filter Switch
        switch (grpFilter) {

        case "Me":
            System.out.println("inside Me >>>>>>");
            assignmentFilter = ITaskQueryService.AssignmentFilter.MY;

            break;
        case "My_Group":
            System.out.println("inside My Group >>>>>>");
            assignmentFilter = ITaskQueryService.AssignmentFilter.GROUP;
            break;
        case "Me_My_Group":
            System.out.println("inside Me & My Group >>>>>>");
            assignmentFilter = ITaskQueryService.AssignmentFilter.MY_AND_GROUP;
            break;
        case "Me_My_Group_All":
            System.out.println("inside Me & My Group All >>>>>>");
            assignmentFilter = ITaskQueryService.AssignmentFilter.MY_AND_GROUP_ALL;
            break;
        case "Me_Previously":
            System.out.println("inside Me Previously >>>>>>");
            assignmentFilter = ITaskQueryService.AssignmentFilter.PREVIOUS;
            break;
        case "Me_Review_Only":
            System.out.println("inside Me Review Only >>>>>>");
            assignmentFilter = ITaskQueryService.AssignmentFilter.REVIEWER;
            break;
        case "Initiated":
            System.out.println("inside Initiated >>>>>>");
            assignmentFilter = ITaskQueryService.AssignmentFilter.CREATOR;
            break;
        case "Admin":
            System.out.println("inside Admin >>>>>>");
            assignmentFilter = ITaskQueryService.AssignmentFilter.ALL;
            break;
        }

        //Status Filter Switch
        switch (status) {

        case "Any":
            System.out.println("inside any >>>>>>");
            correctStates.add(IWorkflowConstants.TASK_STATE_ASSIGNED);
            correctStates.add(IWorkflowConstants.TASK_STATE_COMPLETED);
            correctStates.add(IWorkflowConstants.TASK_STATE_SUSPENDED);
            correctStates.add(IWorkflowConstants.TASK_STATE_WITHDRAWN);
            correctStates.add(IWorkflowConstants.TASK_STATE_EXPIRED);
            correctStates.add(IWorkflowConstants.TASK_STATE_ERRORED);
            correctStates.add(IWorkflowConstants.TASK_STATE_ALERTED);
            correctStates.add(IWorkflowConstants.TASK_STATE_INFO_REQUESTED);

            break;
        case "Assigned":
            System.out.println("inside Assigned >>>>>>");
            correctStates.add(IWorkflowConstants.TASK_STATE_ASSIGNED);
            break;
        case "Completed":
            System.out.println("inside Completed >>>>>>");
            correctStates.add(IWorkflowConstants.TASK_STATE_COMPLETED);
            break;
        case "Suspended":
            System.out.println("inside Suspended >>>>>>");
            correctStates.add(IWorkflowConstants.TASK_STATE_SUSPENDED);
            break;
        case "Withdrawn":
            System.out.println("inside Withdrawn >>>>>>");
            correctStates.add(IWorkflowConstants.TASK_STATE_WITHDRAWN);
            break;
        case "Expired":
            System.out.println("inside Expired >>>>>>");
            correctStates.add(IWorkflowConstants.TASK_STATE_EXPIRED);
            break;
        case "Errored":
            System.out.println("inside Errored >>>>>>");
            correctStates.add(IWorkflowConstants.TASK_STATE_ERRORED);
            break;
        case "Alerted":
            System.out.println("inside Alerted >>>>>>");
            correctStates.add(IWorkflowConstants.TASK_STATE_ALERTED);
            break;
        case "Information Requested":
            System.out.println("inside Information Requested >>>>>>");
            correctStates.add(IWorkflowConstants.TASK_STATE_INFO_REQUESTED);
            break;

        }

        try {
            IWorkflowServiceClient wfSvcClient =
                WorkflowServiceClientFactory.getWorkflowServiceClient(LoadProperties.getIWorkflowConnectionPropertyMap(),
                                                                      null);

            ITaskQueryService querySvc = wfSvcClient.getTaskQueryService();

            System.out.println("Done");

            Predicate statePredicate =
                new Predicate(TableConstants.WFTASK_STATE_COLUMN, Predicate.OP_IN, correctStates);

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
                                        assignmentFilter, null, //No keywords
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
                System.out.println("Application URI : " +
                                   LoadProperties.fetchProperty().getProperty("TASK." +
                                                                              task.getSystemAttributes().getTaskDefinitionName() +
                                                                              ".APPLICATION.URI"));

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
    //        //        getTaskdetails("nile");
    //
    //        try {
    //
    //
    ////             initiateBpmTask("default/initiator!1.0*soa_6d6c5b25-a99a-48db-9c7a-035c96eb4ae6");
    //
    //
    //        } catch (Exception e) {
    //
    //            e.printStackTrace();
    //        }
    //    }
}
