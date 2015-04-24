package viewcontroller;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.context.AdfFacesContext;

public class HomeBean {
    private RichRegion workspaceRegion;
    
    private String taskId;

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
}
