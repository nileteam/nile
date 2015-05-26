package com.view.beans;

import com.view.utility.ADFUtils;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ApproveQuote {
    private RichInputText commentVar;
    private RichPopup rejectPopuUpVar;

    public ApproveQuote() {
    }

    public String reject_Action() {
        // TODO: Add catch code "Put validation and pop up"
        if(commentVar.getValue()!=null) {
            OperationBinding rejectOP = ADFUtils.findOperation("REJECT");
                       rejectOP.execute();
                       FacesContext facesContext = FacesContext.getCurrentInstance();
                       org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
                           org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext,
                                                                                        ExtendedRenderKitService.class);
                       service.addScript(facesContext,
                                         "window.close();window.opener.location.href = window.opener.location.href;");
                       service.addScript(facesContext, "closeMe()");
                
            
        }
     
     else {
            
        /***
         * Show popup here
         * 
         */
            
        showPopup(rejectPopuUpVar, true);
            
            
            
            
        }
     
        return null;
       
    }

    public void setCommentVar(RichInputText commentVar) {
        this.commentVar = commentVar;
    }

    public RichInputText getCommentVar() {
        return commentVar;
    }

    public void setRejectPopuUpVar(RichPopup rejectPopuUpVar) {
        this.rejectPopuUpVar = rejectPopuUpVar;
    }

    public RichPopup getRejectPopuUpVar() {
        return rejectPopuUpVar;
    }

    public void closeRejectPopup(ActionEvent actionEvent) {
        rejectPopuUpVar.cancel();
    }
    
    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
