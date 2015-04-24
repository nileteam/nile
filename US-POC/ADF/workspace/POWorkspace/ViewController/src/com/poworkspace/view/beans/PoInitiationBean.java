package com.poworkspace.view.beans;

import com.poworkspace.view.utils.WorkflowContextUtils;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ActionListener;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

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
        setTaskId(taskId);
        BindingContainer bindingContainer = BindingContext.getCurrent().getCurrentBindingsEntry();
        JUEventBinding eventBinding = (JUEventBinding) bindingContainer.get("eventBinding");

        ActionListener actionListener = (ActionListener) eventBinding.getListener();
        actionListener.processAction(actionEvent);
        System.out.println("Fired................");


        StringBuffer script = new StringBuffer();
        ExtendedRenderKitService service =
            (ExtendedRenderKitService) Service.getRenderKitService(FacesContext.getCurrentInstance(),
                                                                   ExtendedRenderKitService.class);
        script.append("callButtonAction()");
        service.addScript(FacesContext.getCurrentInstance(), script.toString());

    }
}
