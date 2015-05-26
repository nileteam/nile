package com.view.utility;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Utilities {
    
    public static void showMsg(String msg, String severity) {
    FacesContext fc1 = FacesContext.getCurrentInstance();
    FacesMessage message = new FacesMessage("Success", msg);
    if (severity != null) {
    if (severity.equalsIgnoreCase("info")) {
    message.setSeverity(FacesMessage.SEVERITY_INFO);
    } else if (severity.equalsIgnoreCase("warn")) {
    message.setSeverity(FacesMessage.SEVERITY_WARN);
    } else if (severity.equalsIgnoreCase("error")) {
    message.setSeverity(FacesMessage.SEVERITY_ERROR);
    } else {
    message.setSeverity(FacesMessage.SEVERITY_FATAL);
    }
    fc1.addMessage(null, message);
    }
    }

}
