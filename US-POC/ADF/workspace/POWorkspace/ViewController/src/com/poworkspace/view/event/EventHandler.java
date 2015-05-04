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
    
    public void getViewTypeHandler(String payload){
        System.out.println("--+++++++++++++++++-->"+ payload);
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprFactory = fctx.getApplication().getExpressionFactory();
        ValueExpression ve1 = exprFactory.createValueExpression(elctx, "#{pageFlowScope.HomeBean}", Object.class);
        HomeBean homeBean = (HomeBean)ve1.getValue(elctx);
        System.out.println("Main Bean: " + homeBean);
        
        homeBean.setViewType(payload);
        
        homeBean.getWorkspaceRegion().refresh(fctx);
        AdfFacesContext.getCurrentInstance().addPartialTarget(homeBean.getWorkspaceRegion());
        
    }
    
    public void getGeneratedTaskId(String payload) {
        System.out.println("--***************************************+++++++++++++++++-->"+ payload);
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprFactory = fctx.getApplication().getExpressionFactory();
        ValueExpression ve1 = exprFactory.createValueExpression(elctx, "#{pageFlowScope.HomeBean}", Object.class);
        HomeBean homeBean = (HomeBean)ve1.getValue(elctx);
        System.out.println("Main Bean: " + homeBean);
        
        homeBean.setTaskId(payload);
        
        homeBean.getWorkspaceRegion().refresh(fctx);
        AdfFacesContext.getCurrentInstance().addPartialTarget(homeBean.getWorkspaceRegion());
    }
}
