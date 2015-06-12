package com.view.beans;

import com.view.utility.ADFUtils;

import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ReviewContract {
    private RichInputText commentVar;
    private RichPopup rejectPopuUpVar;
    private String  tableFlag;
    public ReviewContract() {
    }

    public void onLoad() {
     System.out.println("inside onload method");
     
        System.out.println("Inside the onload method ");
        
           DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
           JUCtrlHierBinding treeBinding = (JUCtrlHierBinding) bindings.findCtrlBinding("task");
           System.out.println("TREE BINDING IS ======" + treeBinding);
           JUCtrlHierNodeBinding rootNode = null;
           rootNode = treeBinding.getRootNodeBinding();
           System.out.println("rootNode is " + rootNode);

           System.out.println("rootNode.getChildren" + rootNode.getChildren());
           System.out.println("rootNode.getChildren.size" + rootNode.getChildren().size());
           
           if(rootNode.getChildren()!=null) {
               
               for(int i=0;i<rootNode.getChildren().size();i++) {
                   
                   System.out.println("inside loop for "+(i+1)+"   times");
                                 
                                 JUCtrlHierNodeBinding startContract = (JUCtrlHierNodeBinding) rootNode.getChildren().get(0);
                                 System.out.println("startContract is----" + startContract);

                                 String[] attributeNames = startContract.getAttributeNames();
                                 
                                 for(String attribute:attributeNames) {
                                     
                                     System.out.println("attribute is :"+attribute);
                                 }
                                 
                                 
                                 JUCtrlHierNodeBinding startContractPayload = (JUCtrlHierNodeBinding) startContract.getChildren().get(0);
                                 System.out.println("startContractPayload -------" + startContractPayload);

                                 String[] names = startContractPayload.getAttributeNames();

                                 for (String name:names) {

                                     System.out.println("attribute name for startContractPayload" + name);

                                 }
                                 
                                 JUCtrlHierNodeBinding inputContract=(JUCtrlHierNodeBinding)startContractPayload.getChildren().get(0);
                                 System.out.println("Input contract is "+inputContract);

                                 String[] attributeNames_2 = inputContract.getAttributeNames();

                                 for(String attributeName:attributeNames_2) {
                                     
                                     System.out.println("attribute names for input contract is:"+attributeName);
                                 }

                                    
                                 JUCtrlHierNodeBinding contractType=(JUCtrlHierNodeBinding)startContractPayload.getChildren().get(0);
                                 System.out.println("contractType is "+contractType);
                                 
                                 
                                 

                                 String[] attributeNames_3 = contractType.getAttributeNames();

                                 for(String attributeName:attributeNames_3) {
                                     
                                     System.out.println("attribute names for  contractType is:"+attributeName);
                                 }


                                 JUCtrlHierNodeBinding contractTypeValueNode=(JUCtrlHierNodeBinding)contractType.getChildren().get(0);
                                 System.out.println("contractTypeValueNode is "+contractTypeValueNode);

                                 String[] attributeName_4=contractTypeValueNode.getAttributeNames();
                                 
                                 for(String attr:attributeName_4) {
                                     
                                     System.out.println("attribute names attr"+attr);
                                 }
                                     
                                 java.math.BigInteger poNo= (java.math.BigInteger)contractTypeValueNode.getAttribute("Purchase_Requisition_No");
                                 
                                     System.out.println("poNo--------------"+poNo);

                   BindingContext ctx = BindingContext.getCurrent();
                   DCBindingContainer bc = (DCBindingContainer)ctx.getCurrentBindingsEntry();
                   DCIteratorBinding iterator = bc.findIteratorBinding("contractVO1Iterator");
                   ViewObject conDocVO = iterator.getViewObject();
                   conDocVO.setNamedWhereClauseParam("conid", poNo);
                   conDocVO.executeQuery();

                   int countRowContract = conDocVO.getRowCount();
                   
                   if(countRowContract==0) {
                       tableFlag="stop";
                   }
                   else {
                       
                       tableFlag="go";
                       
                   }

                   DCBindingContainer bindingCon = (DCBindingContainer)ctx.getCurrentBindingsEntry();
                   DCIteratorBinding quoteIter = bindingCon.findIteratorBinding("quoteVO1Iterator");
                   ViewObject quoteVO = quoteIter.getViewObject();
                   quoteVO.setNamedWhereClauseParam("conid", poNo);
                   quoteVO.executeQuery();
                   
                   
               }
               
               
               
           }
     
    }

    public void review_Approve(ActionEvent actionEvent) {
        OperationBinding rejectOP = ADFUtils.findOperation("APPROVE");
                   rejectOP.execute();
                   FacesContext facesContext = FacesContext.getCurrentInstance();
                   org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
                       org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext,
                                                                                    ExtendedRenderKitService.class);
                   service.addScript(facesContext,
                                     "window.close();window.opener.location.href = window.opener.location.href;");
                   service.addScript(facesContext, "closeMe()");
    }

    public void review_Reject(ActionEvent actionEvent) {
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

    public void downloadTestListener(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...

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
}
