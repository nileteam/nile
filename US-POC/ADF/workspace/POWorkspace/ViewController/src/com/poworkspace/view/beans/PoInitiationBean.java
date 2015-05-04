package com.poworkspace.view.beans;

import com.poworkspace.view.utils.JSFUtils;
import com.poworkspace.view.utils.WorkflowContextUtils;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ActionListener;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.model.events.EventDispatcher;

import oracle.binding.BindingContainer;

import oracle.bpel.services.workflow.verification.IWorkflowContext;

import oracle.bpm.services.processmetadata.ProcessMetadataSummary;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import oracle.jbo.uicli.binding.JUEventBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import viewcontroller.HomeBean;

public class PoInitiationBean {

    public IWorkflowContext workflowContext = null;
    public String contextString = "";

    private String taskId;
    private String viewType;
    private boolean processView = true;
    private boolean taskTypeView = false;
    private String generatedTaskId;

    public void setGeneratedTaskId(String generatedTaskId) {
        this.generatedTaskId = generatedTaskId;
    }

    public String getGeneratedTaskId() {
        return generatedTaskId;
    }

    public void setProcessView(boolean processView) {
        this.processView = processView;
    }

    public boolean isProcessView() {
        return processView;
    }

    public void setTaskTypeView(boolean taskTypeView) {
        this.taskTypeView = taskTypeView;
    }

    public boolean isTaskTypeView() {
        return taskTypeView;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getViewType() {
        return viewType;
    }

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


    public String fetchInitiatableProcessList() {
        // Add event code here...
        System.out.println(" inside fetchInitiatableProcessList");

        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprFactory = fctx.getApplication().getExpressionFactory();

        ValueExpression ve1 = exprFactory.createValueExpression(elctx, "#{pageFlowScope.RenderView}", Object.class);
        String viewReceived = (String) ve1.getValue(elctx);
        System.out.println("View Received : " + viewReceived);


        if (viewReceived != null) {
            if (viewReceived.equalsIgnoreCase("InitiatorProcessView")) {
                setProcessView(true);
                setTaskTypeView(false);
            } else if (viewReceived.equalsIgnoreCase("TaskTypeView")) {
                setTaskTypeView(true);
                setProcessView(false);
            }
        }

        try {
            BindingContext bctx = BindingContext.getCurrent();
            System.out.println("bctx::" + bctx);
            DCBindingContainer bindings = (DCBindingContainer) bctx.getCurrentBindingsEntry();
            System.out.println("bindings::" + bindings);
            DCIteratorBinding dcIter = bindings.findIteratorBinding("InitiatableProcessVO1Iterator");
            System.out.println("dcIter::" + dcIter);
            ViewObject vo = dcIter.getViewObject();
            vo.executeEmptyRowSet();
            System.out.println("vo::" + vo);

            List<ProcessMetadataSummary> list = BPMTaskHelper.fetchInitiatorProcessList();

            for (ProcessMetadataSummary pms : list) {
                String processName = pms.getProcessName();
                String compositeDN = pms.getCompositeDN();
                System.out.println("Process Name" + processName);
                System.out.println("CompositeDN" + compositeDN);

                Row row = vo.createRow();
                row.setAttribute("ProcessName", processName);
                row.setAttribute("CompositeDN", compositeDN);
                vo.insertRow(row);
            }

        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return "go";
    }

    public static void main(String[] a) {
        PoInitiationBean pObj = new PoInitiationBean();
        pObj.fetchInitiatableProcessList();
    }

    public void initiateProcessActionListener(ActionEvent actionEvent) {
        String procParam = (String) actionEvent.getComponent().getAttributes().get("procParam");
        String compositeDNParam = (String) actionEvent.getComponent().getAttributes().get("compositeDNParam");
        System.out.println(procParam);
        System.out.println(compositeDNParam);
        String taskid = BPMTaskHelper.initiateBpmTask(procParam, compositeDNParam);
        System.out.println("Initiated Task Id: " + taskid);

        //Setting TaskId for contextual event and fire the event eventBinding
        setGeneratedTaskId(taskid);
        //JSFUtils.setManagedBeanValue("sessionScope.initiatedTaskId", taskId);
        BindingContainer bindingContainer = BindingContext.getCurrent().getCurrentBindingsEntry();
        JUEventBinding eventBinding = (JUEventBinding) bindingContainer.get("sendGeneratedTaskIdEventBinding");

        ActionListener actionListener = (ActionListener) eventBinding.getListener();
        actionListener.processAction(actionEvent);

        System.out.println("Fired................");

    }

    public void applyViewActionListener(ActionEvent actionEvent) {
        String viewType = (String) actionEvent.getComponent().getAttributes().get("viewType");
        System.out.println("View Type: " + viewType);

        //Setting TaskId for contextual event and fire the event eventBinding
        setViewType(viewType);
        //JSFUtils.setManagedBeanValue("sessionScope.initiatedTaskId", taskId);
        BindingContainer bindingContainer = BindingContext.getCurrent().getCurrentBindingsEntry();
        JUEventBinding eventBinding = (JUEventBinding) bindingContainer.get("sendViewTypeEventBinding");

        ActionListener actionListener = (ActionListener) eventBinding.getListener();
        actionListener.processAction(actionEvent);

        System.out.println("Fired................");
    }
}
