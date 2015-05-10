package viewcontroller;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.context.AdfFacesContext;

public class HomeBean {
    private RichRegion workspaceRegion;
    private RichRegion processInitiationRegion;
    private String viewType;

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getViewType() {
        return viewType;
    }

    public void setProcessInitiationRegion(RichRegion processInitiationRegion) {
        this.processInitiationRegion = processInitiationRegion;
    }

    public RichRegion getProcessInitiationRegion() {
        return processInitiationRegion;
    }
    private String taskId;
    private String renderView;

    public void setRenderView(String renderView) {
        this.renderView = renderView;
    }

    public String getRenderView() {
        return renderView;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public HomeBean() {
    }

    public String refreshTaskListAction() {
        workspaceRegion.refresh(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(workspaceRegion);
        return null;
    }

    public void setWorkspaceRegion(RichRegion workspaceRegion) {
        this.workspaceRegion = workspaceRegion;
    }

    public RichRegion getWorkspaceRegion() {
        return workspaceRegion;
    }
    
    public void setInitiatorView(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Setting InitiatorProcess View....");
        setRenderView("InitiatorProcessView");
        processInitiationRegion.refresh(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(processInitiationRegion);
        System.out.println("Refreshed----------------------> " + getRenderView());
    }

    public void setView(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Setting TaskType View....");
        setRenderView("TaskTypeView");
        processInitiationRegion.refresh(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(processInitiationRegion);
    }
}
