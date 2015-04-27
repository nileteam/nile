package com.view.beans;

import java.math.BigInteger;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

import oracle.security.xml.ws.addressing.bindings.To;

public class POBean {
    private RichInputText operunitBVar;
    private RichInputText toatlVal;
    private RichTable table;

    int totalvalue;

    public POBean() {
    }
    private RichSelectOneChoice typeBVar;

    public void setTotalvalue(int totalvalue) {
        this.totalvalue = totalvalue;
    }

    public int getTotalvalue() {
        return totalvalue;
    }
    private RichInputText desciptionBVar;
    private RichInputText codeBVar;
    private RichInputText quantityBVar;
    private RichInputText rateBVar;
    private RichSelectOneChoice preferedsuppBVar;
    private RichInputDate needbyBVar;
    private RichSelectOneChoice projectBVar;
    private RichSelectOneChoice taskBVar;

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

    public String cancel() {
        System.out.println("cancel button is called....");

        typeBVar.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(typeBVar);

        desciptionBVar.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(desciptionBVar);

        codeBVar.setValue(null);
        quantityBVar.setValue(null);
        rateBVar.setValue(null);
        preferedsuppBVar.setValue(null);
        needbyBVar.setValue(null);
        projectBVar.setValue(null);
        taskBVar.setValue(null);
        operunitBVar.setValue(null);
        // RichInputText input = (RichInputText)JSFUtils.findComponentInRoot(it2);
        //input.setSubmittedValue(null);
        //input.resetValue();
        //AdfFacesContext.getCurrentInstance().addPartialTarget(input);

        return null;
    }

    public void OnLoad() {
        System.out.println("into onload****************************");
        OperationBinding createInsertOP = ADFUtils.findOperation("CreateInsert1");
        createInsertOP.execute();
        OperationBinding createInsertOP1 = ADFUtils.findOperation("CreateInsert");
        createInsertOP1.execute();
        //table.setVisible(false);
    }

    public void setOperunitBVar(RichInputText operunitBVar) {
        this.operunitBVar = operunitBVar;
    }

    public RichInputText getOperunitBVar() {
        return operunitBVar;
    }

    public String saveButton() {

        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        ViewObjectImpl TotalValue =
            (ViewObjectImpl) bindings.findIteratorBinding("Item_DetailsIterator").getViewObject();

        RowSetIterator rit = TotalValue.createRowSetIterator(null);

        while (rit.hasNext()) {
            Row row = rit.next();

            System.out.println("---------------------" + (Number) row.getAttribute("Rate"));

            System.out.println("---------------------" + (Number) row.getAttribute("Quantity"));
            //if(row.getAttribute("Rate")!=null){

            Number quantity = (Number) row.getAttribute("Quantity");
            Number rate = (Number) row.getAttribute("Rate");
            int rateint = rate.intValue();
            int qtyint = quantity.intValue();


            //BigInteger rate = new BigInteger((String)row.getAttribute("Rate"));

            //System.out.println("rate "+rate);

            //System.out.println(" rate.intValue() "+ rate.intValue());

            //int i =  row.getAttribute("Rate");

            totalvalue += rateint * qtyint;
            // }
           // toatlVal.setValue(totalvalue);


        }
        System.out.println("total value is " + totalvalue);
        return null;
    }

    public void setToatlVal(RichInputText toatlVal) {
        this.toatlVal = toatlVal;
    }

    public RichInputText getToatlVal() {
        return toatlVal;
    }


    public static void main(String ar[]) {
        Object o = 1;


        BigInteger rate = new BigInteger((String) o);

        System.out.println("rage " + rate);

    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }

    public String createinsertbutton() {
        // table.setVisible(true);
        return null;
    }

    public String cancel_action() {
        System.out.println("inside cancel button action");
        
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        ViewObjectImpl vo1 =
            (ViewObjectImpl) bindings.findIteratorBinding("Item_DetailsIterator").getViewObject();

        Row currentRow = vo1.getCurrentRow();
        System.out.println("currentRow-----"+currentRow);
        
        
        System.out.println("Rate*************"+currentRow.getAttribute("Rate"));
       
        currentRow.remove();
        
        
        desciptionBVar.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(desciptionBVar);

        codeBVar.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(codeBVar);

        quantityBVar.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(quantityBVar);

        typeBVar.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(typeBVar);

        rateBVar.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(rateBVar);

        preferedsuppBVar.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(preferedsuppBVar);

        needbyBVar.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(needbyBVar);

        projectBVar.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(projectBVar);

        taskBVar.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(taskBVar);

        operunitBVar.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(operunitBVar);

        return null;
    }
}
