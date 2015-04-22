package com.poworkspace.view.beans;

import com.poworkspace.view.utils.JSFUtils;
import com.poworkspace.view.utils.LoadProperties;
import com.poworkspace.view.utils.WorkflowContextUtils;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.output.RichInlineFrame;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.bpel.services.workflow.WorkflowException;
import oracle.bpel.services.workflow.task.model.IdentityType;
import oracle.bpel.services.workflow.task.model.Task;
import oracle.bpel.services.workflow.verification.IWorkflowContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class PoWorkSpaceBean {
    private static Logger log;
    public IWorkflowContext workflowContext = null;
    public String contextString = "";

    public void setContextString(String contextString) {
        this.contextString = contextString;
    }

    public String getContextString() {
        return contextString;
    }

    public String onLoad() {
        System.out.println(" in onload method");
        return null;
    }

    public String fetchTaskOnLoad() {
        System.out.println(" inside fetchTaskOnLoad");

        try {

            BindingContext bctx = BindingContext.getCurrent();
            System.out.println("bctx::" + bctx);
            DCBindingContainer bindings = (DCBindingContainer) bctx.getCurrentBindingsEntry();
            System.out.println("bindings::" + bindings);
            DCIteratorBinding dcIter = bindings.findIteratorBinding("TaskListVO1Iterator");
            System.out.println("dcIter::" + dcIter);
            ViewObject vo = dcIter.getViewObject();
            System.out.println("vo::" + vo);

            workflowContext = WorkflowContextUtils.initBPMContext("initiatortest", "welcome1");
            setContextString(workflowContext.getToken());
            System.out.println("Workflow context String has been set");
            String uri = "";
            List list = BPMTaskHelper.getTaskdetails("initiatortest", "welcome1");
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Row row = vo.createRow();

                    Task task = (Task) list.get(i);

                    String title = task.getTitle();
                    String taskId = task.getSystemAttributes().getTaskId();

                    System.out.println("Task Id: " + taskId);
                    System.out.println("Task Title: " + title);
                    System.out.println("Task URI : " + "http://" +
                                       LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.IP") + ":" +
                                       LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.PORT") +
                                       LoadProperties.fetchProperty().getProperty("TASK." +
                                                                                  task.getSystemAttributes().getTaskDefinitionName() +
                                                                                  ".APPLICATION.URI"));
                    if (LoadProperties.fetchProperty().getProperty("TASK." +
                                                                   task.getSystemAttributes().getTaskDefinitionName() +
                                                                   ".APPLICATION.URI") != null ||
                        !LoadProperties.fetchProperty().getProperty("TASK." +
                                                                    task.getSystemAttributes().getTaskDefinitionName() +
                                                                    ".APPLICATION.URI").equalsIgnoreCase("")) {
                        uri =
                            "http://" + LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.IP") + ":" +
                            LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.PORT") +
                            LoadProperties.fetchProperty().getProperty("TASK." +
                                                                       task.getSystemAttributes().getTaskDefinitionName() +
                                                                       ".APPLICATION.URI") +
                            LoadProperties.fetchProperty().getProperty("TASK.APPLICATION.URI.CONSTANT");
                        row.setAttribute("TaskFlowURI", uri);
                    }

                    row.setAttribute("TaskID", taskId);
                    row.setAttribute("Title", title);

                    row.setAttribute("Priority", task.getPriority());

                    Date assidnedDate = task.getSystemAttributes().getAssignedDate().getTime();

                    row.setAttribute("Assigned", assidnedDate);

                    List assigneeUsers = task.getSystemAttributes().getAssigneeUsers();
                    System.out.println(assigneeUsers.size());
                    String names = "";
                    for (int j = 0; j < assigneeUsers.size(); j++) {
                        IdentityType type = (IdentityType) assigneeUsers.get(j);
                        String n = type.getId();
                        if (names.equalsIgnoreCase("")) {
                            names = n;
                        } else {
                            names += " , " + n;
                        }
                    }
                    System.out.println(names);
                    row.setAttribute("Assignees", names);
                    vo.insertRow(row);
                }
            }
        } catch (WorkflowException we) {
            // TODO: Add catch code
            we.printStackTrace();
        }
        return "go";
    }

    public void attributeChangeListener(AttributeChangeEvent attributeChangeEvent) {
        System.out.println(" Hi In Attribute Change Listener");
    }

}
