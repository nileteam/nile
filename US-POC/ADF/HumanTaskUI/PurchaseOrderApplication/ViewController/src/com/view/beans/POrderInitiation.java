package com.view.beans;

import java.util.Map;

import javax.faces.context.FacesContext;

public class POrderInitiation {
    public POrderInitiation() {
    }

    public String programmaticSubmit() {
        // Add event code here...
        System.out.println("Inside Programmatic Approval method.....");
        Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        oracle.bpel.services.workflow.worklist.adf.InvokeActionBean invokeActionBean =
            (oracle.bpel.services.workflow.worklist.adf.InvokeActionBean) map.get("invokeActionBean");
        String result = invokeActionBean.invokeOperation();

        // FacesContext fctx = FacesContext.getCurrentInstance();
        // ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        // // Invoke a java script method name called showConfPopup()' with two parameters, You can pass any value
        //        erks.addScript(fctx, "test()");


        System.out.println("Inside Programmatic Approval method.....success");


        return result;
    }
}
