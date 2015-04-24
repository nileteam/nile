package com.poworkspace.view.event;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import viewcontroller.HomeBean;

public class EventHandler {
    public EventHandler() {
        super();
    }
    
    public void getName(String payload) {
        System.out.println("Payload: " + payload);
        
    //        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
    //        Map pageFlowScope = adfFacesContext.getPageFlowScope();
    //        pageFlowScope.put("name", payload);
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprFactory = fctx.getApplication().getExpressionFactory();
        
        
        ValueExpression ve1 = exprFactory.createValueExpression(elctx, "#{pageFlowScope.HomeBean}", Object.class);
        HomeBean homeBean = (HomeBean)ve1.getValue(elctx);
        System.out.println("Main Bean: " + homeBean);
        
        System.out.println("TaskId received: " + payload);
        homeBean.setTaskId(payload);
        
        homeBean.getWorkspaceRegion().refresh(fctx);
        AdfFacesContext.getCurrentInstance().addPartialTarget(homeBean.getWorkspaceRegion());
        
        
        //mainBean.setName(payload.toString());
        
        //mainBean.getConsumerRegion().refresh(fctx);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(mainBean.getConsumerRegion());
        
    /*
        ValueExpression ve = exprFactory.createValueExpression(elctx, "#{pageFlowScope.ConsumerBean}", Object.class);
        ConsumerBean pageBean = (ConsumerBean) ve.getValue(elctx);
        pageBean.setName(payload.toString());*/
        System.out.println("Got it");

    }
}
