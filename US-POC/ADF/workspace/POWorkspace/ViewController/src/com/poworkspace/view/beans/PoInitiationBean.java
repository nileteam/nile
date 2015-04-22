package com.poworkspace.view.beans;

import com.poworkspace.view.utils.WorkflowContextUtils;

import java.util.List;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.bpel.services.workflow.verification.IWorkflowContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class PoInitiationBean {

    public IWorkflowContext workflowContext = null;
    public String contextString = "";

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
            System.out.println("vo::" + vo);

            List<String> list = BPMTaskHelper.fetchInitiatorProcessList("initiatortest", "welcome1");

            for (String processName : list) {
                System.out.println("Process Name" + processName);
                Row row = vo.createRow();
                row.setAttribute("ProcessName", processName);
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
}
