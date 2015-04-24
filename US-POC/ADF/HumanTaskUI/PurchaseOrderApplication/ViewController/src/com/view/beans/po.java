package com.view.beans;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.OperationBinding;

public class po {


    private RichSelectOneChoice typeBVar;
    private RichInputText desciptionBVar;
    private RichInputText codeBVar;
    private RichInputText quantityBVar;
    private RichInputText rateBVar;
    private RichSelectOneChoice preferedsuppBVar;
    private RichInputDate needbyBVar;
    private RichSelectOneChoice projectBVar;
    private RichSelectOneChoice taskBVar;

    public po() {
    }

    public String cancelButton() {
        System.out.println("in cancel butoon");
        typeBVar.setValue(null);
        desciptionBVar.setValue(null);
        codeBVar.setValue(null);
        quantityBVar.setValue(null);
        rateBVar.setValue(null);
        preferedsuppBVar.setValue(null);
        needbyBVar.setValue(null);
        projectBVar.setValue(null);
        taskBVar.setValue(null);    
        return null;
    }
 
    public void OnLoad() {
        System.out.println("into onload");
        OperationBinding createInsertOP =
                     ADFUtils.findOperation("CreateInsert1");
                    createInsertOP.execute(); 
        OperationBinding createInsertOP1 =
                     ADFUtils.findOperation("CreateInsert");
                    createInsertOP1.execute();              
    }

    public void setTypeBVar(RichSelectOneChoice typeBVar) {
        this.typeBVar = typeBVar;
    }

    public RichSelectOneChoice getTypeBVar() {
        return typeBVar;
    }

    public void setDesciptionBVar(RichInputText desciptionBVar) {
        this.desciptionBVar = desciptionBVar;
    }

    public RichInputText getDesciptionBVar() {
        return desciptionBVar;
    }

    public void setCodeBVar(RichInputText codeBVar) {
        this.codeBVar = codeBVar;
    }

    public RichInputText getCodeBVar() {
        return codeBVar;
    }

    public void setQuantityBVar(RichInputText quantityBVar) {
        this.quantityBVar = quantityBVar;
    }

    public RichInputText getQuantityBVar() {
        return quantityBVar;
    }

    public void setRateBVar(RichInputText rateBVar) {
        this.rateBVar = rateBVar;
    }

    public RichInputText getRateBVar() {
        return rateBVar;
    }

    public void setPreferedsuppBVar(RichSelectOneChoice preferedsuppBVar) {
        this.preferedsuppBVar = preferedsuppBVar;
    }

    public RichSelectOneChoice getPreferedsuppBVar() {
        return preferedsuppBVar;
    }

    public void setNeedbyBVar(RichInputDate needbyBVar) {
        this.needbyBVar = needbyBVar;
    }

    public RichInputDate getNeedbyBVar() {
        return needbyBVar;
    }

    public void setProjectBVar(RichSelectOneChoice projectBVar) {
        this.projectBVar = projectBVar;
    }

    public RichSelectOneChoice getProjectBVar() {
        return projectBVar;
    }

    public void setTaskBVar(RichSelectOneChoice taskBVar) {
        this.taskBVar = taskBVar;
    }

    public RichSelectOneChoice getTaskBVar() {
        return taskBVar;
    }

    public String cancelTask() {
        // Add event code here...
        return null;
    }
}
