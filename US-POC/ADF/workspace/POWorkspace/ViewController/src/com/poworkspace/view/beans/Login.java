package com.poworkspace.view.beans;

import com.poworkspace.view.utils.BPMContextUtils;
import com.poworkspace.view.utils.JSFUtils;
import com.poworkspace.view.utils.WorkflowContextUtils;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.security.auth.Subject;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpel.services.workflow.WorkflowException;
import oracle.bpel.services.workflow.verification.IWorkflowContext;

import weblogic.security.URLCallbackHandler;
import weblogic.security.services.Authentication;


public class Login {

    private String _username;
    private String _password;

    public void setUsername(String _username) {
        this._username = _username.toLowerCase();
    }

    public String getUsername() {
        return _username;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public String getPassword() {
        return _password;
    }
    
    public String doLogin() {
        String un = _username;
        byte[] pw = _password.getBytes();
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
        try {
            Subject subject = Authentication.login(new URLCallbackHandler(un, pw));
            weblogic.servlet.security.ServletAuthentication.runAs(subject, request);
            
            IWorkflowContext workflowContext = WorkflowContextUtils.initBPMContext(_username, _password);
            JSFUtils.setManagedBeanValue("sessionScope.workflowContext", workflowContext);
            
            IBPMContext ibpmContext = BPMContextUtils.getIBPMContext(_username, _password);
            JSFUtils.setManagedBeanValue("sessionScope.ibpmContext", ibpmContext);
            
            String loginUrl = "/adfAuthentication?success_url=/faces/main";
            HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
            sendForward(request, response, loginUrl);
        } catch (FailedLoginException fle) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect Username or Password", "An incorrect Username or Password was specified");
            ctx.addMessage(null, msg);
        } catch (LoginException le) {
            reportUnexpectedLoginError("LoginException", le);
        } catch (WorkflowException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendForward(HttpServletRequest request, HttpServletResponse response, String forwardUrl) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException se) {
            reportUnexpectedLoginError("ServletException", se);
        } catch (IOException ie) {
            reportUnexpectedLoginError("IOException", ie);
        }
        ctx.responseComplete();
    }

    private void reportUnexpectedLoginError(String errType, Exception e) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error during login", "Unexpected error during login (" + errType + "), please consult logs for detail");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        e.printStackTrace();
    }
    
    public String onLogout() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();

        String url = ectx.getRequestContextPath() + "/adfAuthentication?logout=true&end_url=/faces/main.jspx";     

        try {
            ectx.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        fctx.responseComplete();

        return null;
    }
}
