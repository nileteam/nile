package com.poworkspace.view.beans;

import com.poworkspace.view.utils.ADFUtils;
import com.poworkspace.view.utils.JSFUtils;
import com.poworkspace.view.utils.LoadProperties;
import com.poworkspace.view.utils.WorkflowContextUtils;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.output.RichInlineFrame;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.bpel.services.workflow.WorkflowException;
import oracle.bpel.services.workflow.task.model.IdentityType;
import oracle.bpel.services.workflow.task.model.Task;
import oracle.bpel.services.workflow.verification.IWorkflowContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import viewcontroller.HomeBean;

public class PoWorkSpaceBean {
    private static Logger log;
    public IWorkflowContext workflowContext = null;
    public String contextString = "";

    private boolean processIsAsc = true;
    private boolean tittleIsAsc = true;
    private boolean priorityIsAsc = true;
    private boolean assignedIsAsc = true;

    private String filter = "Assigned";
    private String groupFilter = "Me_My_Group_All";
    private String viewTypeReceived;

    public void setViewTypeReceived(String viewTypeReceived) {
        this.viewTypeReceived = viewTypeReceived;
    }

    public String getViewTypeReceived() {
        return viewTypeReceived;
    }

    public void setGroupFilter(String groupFilter) {
        this.groupFilter = groupFilter;
    }

    public String getGroupFilter() {
        return groupFilter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    private String taskId;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setContextString(String contextString) {
        this.contextString = contextString;
    }

    public String getContextString() {
        return contextString;
    }

    public String onLoad() {
        System.out.println(" in onload method");
        return null;
    }

    public String fetchTaskOnLoad() {
        System.out.println(" inside fetchTaskOnLoad");

        System.out.println("Onload Called................");
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprFactory = fctx.getApplication().getExpressionFactory();

        ValueExpression ve1 = exprFactory.createValueExpression(elctx, "#{pageFlowScope.taskId}", Object.class);
        String taskIdReceived = (String) ve1.getValue(elctx);
        System.out.println("Workspace Task Id Received : " + taskIdReceived);
        setTaskId(taskIdReceived);
        
        ValueExpression ve = exprFactory.createValueExpression(elctx, "#{pageFlowScope.viewTypw}", Object.class);
        String viewTypeReceived = (String) ve.getValue(elctx);
        System.out.println("Workspace View Type Received : " + viewTypeReceived);


        try {

            BindingContext bctx = BindingContext.getCurrent();
            System.out.println("bctx::" + bctx);
            DCBindingContainer bindings = (DCBindingContainer) bctx.getCurrentBindingsEntry();
            System.out.println("bindings::" + bindings);
            DCIteratorBinding dcIter = bindings.findIteratorBinding("TaskListVO1Iterator");
            System.out.println("dcIter::" + dcIter);
            ViewObject vo = dcIter.getViewObject();

            System.out.println("vo::" + vo);
            //            JSFUtils.setManagedBeanValue("sessionScope.workflowContext", workflowContext);
            workflowContext = (IWorkflowContext) JSFUtils.getManagedBeanValue("sessionScope.workflowContext");

            //            workflowContext = WorkflowContextUtils.initBPMContext("initiatortest", "welcome1");
            setContextString(workflowContext.getToken());
            System.out.println("Workflow context String has been set");

            //            List list = BPMTaskHelper.getTaskdetails("initiatortest", "welcome1");

            //List list = BPMTaskHelper.getTaskdetails(workflowContext, viewTypeReceived);
            
            if(viewTypeReceived != null) {
                setGroupFilter(viewTypeReceived);
                setViewTypeReceived(viewTypeReceived);
            }
            
            
            List list = BPMTaskHelper.getTasksForStatus(workflowContext, getFilter(), getGroupFilter());
            
            populateTaskVO(list, vo);

        } catch (Exception we) {
            // TODO: Add catch code
            we.printStackTrace();
        }

        StringBuffer script = new StringBuffer();
        ExtendedRenderKitService service =
            (ExtendedRenderKitService) Service.getRenderKitService(FacesContext.getCurrentInstance(),
                                                                   ExtendedRenderKitService.class);
        script.append("test()");
        service.addScript(FacesContext.getCurrentInstance(), script.toString());
        System.out.println("Called>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return "go";
    }

    public void populateTaskVO(List list, ViewObject vo) {

        vo.executeEmptyRowSet();
        String uri = "";
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Row row = vo.createRow();

                Task task = (Task) list.get(i);

                String title = task.getTitle();
                String taskId = task.getSystemAttributes().getTaskId();

                System.out.println("Task Id: " + taskId);
                System.out.println("Task Title: " + title);
                System.out.println("Task URI : " + "http://" +
                                   LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.IP") + ":" +
                                   LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.PORT") +
                                   LoadProperties.fetchProperty().getProperty("TASK." +
                                                                              task.getSystemAttributes().getTaskDefinitionName() +
                                                                              ".APPLICATION.URI"));
                if (task.getSystemAttributes().getTaskDefinitionName() != null) {
                    uri =
                        "http://" + LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.IP") + ":" +
                        LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.PORT") +
                        LoadProperties.fetchProperty().getProperty("TASK." +
                                                                   task.getSystemAttributes().getTaskDefinitionName() +
                                                                   ".APPLICATION.URI") +
                        LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.URI.CONSTANT");
                    row.setAttribute("TaskFlowURI", uri);
                }

                row.setAttribute("TaskID", taskId);
                row.setAttribute("Title", title);

                row.setAttribute("Priority", task.getPriority());

                Date assidnedDate = task.getSystemAttributes().getAssignedDate().getTime();

                row.setAttribute("Assigned", assidnedDate);

                List assigneeUsers = task.getSystemAttributes().getAssigneeUsers();
                System.out.println(assigneeUsers.size());
                String names = "";
                for (int j = 0; j < assigneeUsers.size(); j++) {
                    IdentityType type = (IdentityType) assigneeUsers.get(j);
                    String n = type.getId();
                    if (names.equalsIgnoreCase("")) {
                        names = n;
                    } else {
                        names += " , " + n;
                    }
                }
                System.out.println(names);
                row.setAttribute("Assignees", names);
                vo.insertRow(row);
            }
        }

    }

    public void attributeChangeListener(AttributeChangeEvent attributeChangeEvent) {
        System.out.println(" Hi In Attribute Change Listener");
    }

    //    public String b1_action() {
    //        DCIteratorBinding dcIter = ADFUtils.findIterator("TaskListVO1Iterator");
    //        ViewObject vo = dcIter.getViewObject();
    //        vo.setSortBy("TaskID");
    //        vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_VIEW_ROWS);
    //        vo.executeQuery();
    //
    //        StringBuffer script = new StringBuffer();
    //        ExtendedRenderKitService service =
    //            (ExtendedRenderKitService) Service.getRenderKitService(FacesContext.getCurrentInstance(),
    //                                                                   ExtendedRenderKitService.class);
    //        script.append("test()");
    //        service.addScript(FacesContext.getCurrentInstance(), script.toString());
    //
    //        //            bindings.findIteratorBinding("TaskListVO1Iterator");
    //        return null;
    //    }

    public void sortTaskList(ActionEvent actionEvent) {

        String param = (String) actionEvent.getComponent().getAttributes().get("param");
        System.out.println(param);

        if (param != null && param.equalsIgnoreCase("Title")) {
            if (tittleIsAsc) {
                param = param + " asc";
                tittleIsAsc = false;
            } else {
                param = param + " desc";
                tittleIsAsc = true;
            }
        } else if (param != null && param.equalsIgnoreCase("Process")) {
            if (processIsAsc) {
                param = param + " asc";
                processIsAsc = false;
            } else {
                param = param + " desc";
                processIsAsc = true;
            }
        }


        DCIteratorBinding dcIter = ADFUtils.findIterator("TaskListVO1Iterator");
        ViewObject vo = dcIter.getViewObject();
        vo.setSortBy(param);

        //vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_VIEW_ROWS);
        //vo.executeQuery();
        vo.executeEmptyRowSet();

        StringBuffer script = new StringBuffer();
        ExtendedRenderKitService service =
            (ExtendedRenderKitService) Service.getRenderKitService(FacesContext.getCurrentInstance(),
                                                                   ExtendedRenderKitService.class);
        script.append("test()");
        service.addScript(FacesContext.getCurrentInstance(), script.toString());
    }

    public void sorting(ActionEvent actionEvent) {
        // Sorting in Ascending Order...
        String param = (String) actionEvent.getComponent().getAttributes().get("param");
        System.out.println(param);

        DCIteratorBinding dcIter = ADFUtils.findIterator("TaskListVO1Iterator");
        ViewObject vo = dcIter.getViewObject();
        vo.setSortBy(param);

        vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_VIEW_ROWS);
        vo.executeQuery();

        StringBuffer script = new StringBuffer();
        ExtendedRenderKitService service =
            (ExtendedRenderKitService) Service.getRenderKitService(FacesContext.getCurrentInstance(),
                                                                   ExtendedRenderKitService.class);
        script.append("test()");
        service.addScript(FacesContext.getCurrentInstance(), script.toString());
    }

//    public void statusChangeListener(ValueChangeEvent valueChangeEvent) {
//        // Add event code here...
//        System.out.println("Status selected in dropdown >>>>>>>>>>>>>>>>>>>> " + valueChangeEvent.getNewValue());
//        System.out.println(" Workflow context token ======================= " + workflowContext.getToken());
//        String status = (String) valueChangeEvent.getNewValue();
//        try {
//            DCIteratorBinding dcIter = ADFUtils.findIterator("TaskListVO1Iterator");
//            ViewObject vo = dcIter.getViewObject();
//            List list = BPMTaskHelper.getTasksForStatus(workflowContext, status );
//            vo.executeEmptyRowSet();
//            //populateTaskVO(list, vo);
//
//        } catch (Exception e) {
//            // TODO: Add catch code
//            e.printStackTrace();
//        }
//
//        StringBuffer script = new StringBuffer();
//        ExtendedRenderKitService service =
//            (ExtendedRenderKitService) Service.getRenderKitService(FacesContext.getCurrentInstance(),
//                                                                   ExtendedRenderKitService.class);
//        script.append("test()");
//        service.addScript(FacesContext.getCurrentInstance(), script.toString());
//        System.out.println("Called>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//    }

    public void filterActionListener(ActionEvent actionEvent) {
        System.out.println("Filter------------> " + getFilter());
        System.out.println("Group Filter------------> " + getGroupFilter());
        try {
            DCIteratorBinding dcIter = ADFUtils.findIterator("TaskListVO1Iterator");
            ViewObject vo = dcIter.getViewObject();
            
            List list = null;
            if(getGroupFilter() != null) {
                list = BPMTaskHelper.getTasksForStatus(workflowContext, getFilter(), getGroupFilter());
            } else {
                list = BPMTaskHelper.getTasksForStatus(workflowContext, getFilter(), getViewTypeReceived());
            }
            
            
            
            System.out.println("Task Count >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + list.size());
            
            vo.executeEmptyRowSet();
            populateTaskVO(list, vo);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@VO Size : " + vo.getAllRowsInRange().length);

        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }

        StringBuffer script = new StringBuffer();
        ExtendedRenderKitService service =
            (ExtendedRenderKitService) Service.getRenderKitService(FacesContext.getCurrentInstance(),
                                                                   ExtendedRenderKitService.class);
        script.append("test()");
        service.addScript(FacesContext.getCurrentInstance(), script.toString());
        System.out.println("Called>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
