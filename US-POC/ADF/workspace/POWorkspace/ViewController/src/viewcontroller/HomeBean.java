package viewcontroller;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.context.AdfFacesContext;

public class HomeBean {
    private RichRegion workspaceRegion;

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
