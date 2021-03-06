package com.view.beans;

import java.math.BigDecimal;

import java.util.Map;

import javax.faces.context.FacesContext;

import java.math.BigInteger;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.event.PhaseEvent;
import javax.faces.event.ValueChangeEvent;

import javax.naming.NamingException;

import javax.sql.DataSource;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

import oracle.security.xml.ws.addressing.bindings.To;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//import oracle.javatools.parser.java.v2.util.BindingContext;

import oracle.jbo.domain.Number;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class PurchaseOrder {

    private RichInputText operunitBVar;
    private RichInputText toatlVal;
    private RichTable table;
    int totalvalue;
    private RichOutputText requisitionBVar;
    private String codename;
    private int rate;
    private int quantity;
    private String itemname;
    private String Requester;
    private RichInputText requisnoBVar;

    public void setItemFormEditable(String itemFormEditable) {
        this.itemFormEditable = itemFormEditable;
    }

    public String getItemFormEditable() {
        String itemId = JSFUtils.resolveExpressionAsString("#{bindings.Type.inputValue}");

        Row[] row = ADFUtils.findIterator("ItemView1Iterator").getViewObject().getFilteredRows("ItemId", itemId);

        if (row.length > 0) {
            itemFormEditable = row[0].getAttribute("ItemName").toString();
            System.out.println(itemEditableScreen);
        }
        return itemFormEditable;
    }

    public void setDescFormEditable(String descFormEditable) {
        this.descFormEditable = descFormEditable;
    }

    public String getDescFormEditable() {
        String typeId = JSFUtils.resolveExpressionAsString("#{bindings.Description.inputValue}");

        Row[] row =
            ADFUtils.findIterator("TypeMasterViewObj1Iterator").getViewObject().getFilteredRows("TypeId", typeId);

        if (row.length > 0) {
            descFormEditable = row[0].getAttribute("TypeName").toString();
            System.out.println(descEditableScreen);
        }
        return descFormEditable;
    }

    private String itemEditableScreen;
    private String descEditableScreen;

    private String itemFormEditable;
    private String descFormEditable;

    public void setItemEditableScreen(String itemEditableScreen) {
        this.itemEditableScreen = itemEditableScreen;
    }

    public String getItemEditableScreen() {

        String itemId = JSFUtils.resolveExpressionAsString("#{row.Type}");

        Row[] row = ADFUtils.findIterator("ItemView1Iterator").getViewObject().getFilteredRows("ItemId", itemId);

        if (row.length > 0) {
            itemEditableScreen = row[0].getAttribute("ItemName").toString();
            System.out.println(itemEditableScreen);
        }
        return itemEditableScreen;
    }

    public void setDescEditableScreen(String descEditableScreen) {
        this.descEditableScreen = descEditableScreen;
    }

    public String getDescEditableScreen() {
        String typeId = JSFUtils.resolveExpressionAsString("#{row.Description}");

        Row[] row =
            ADFUtils.findIterator("TypeMasterViewObj1Iterator").getViewObject().getFilteredRows("TypeId", typeId);

        if (row.length > 0) {
            descEditableScreen = row[0].getAttribute("TypeName").toString();
            System.out.println(descEditableScreen);
        }
        return descEditableScreen;
    }


    private boolean flagForCode = false;
    private boolean flagForRate = false;

    private String itemForTable;
    private RichInputText ecode1;
    private RichInputText erate1;

    public void setRequester(String Requester) {
        this.Requester = Requester;
    }

    public String getRequester() {
        return Requester;
    }

    public void setRequisitionNo(String RequisitionNo) {
        this.RequisitionNo = RequisitionNo;
    }

    public String getRequisitionNo() {
        return RequisitionNo;
    }
    private String RequisitionNo;
    private RichInputText commentBVar;
    private RichPopup reject_popup;
    private RichInputText codeBVar;
    private RichInputText quantityBVar;
    private RichInputText rateBVar;
    private RichSelectOneChoice preferedsuppBVar;
    private RichInputDate needbyBVar;
    private RichSelectOneChoice projectBVar;
    private RichSelectOneChoice taskBVar;
    private Date minDate = new Date();
    private RichSelectOneChoice typeBVar;
    //private RichOutputText requesterBVar;
    private RichPopup save_popup;
    private RichPopup submit_popup;
    private RichSelectOneChoice desciptionBVar;
    private RichOutputText item_idoutputtext;
    private RichInputText requestBVar;
    private RichCommandImageLink approve_NonEditable;
    private RichInputText erate;
    private RichInputText equantity;
    private RichPopup saveeditable_popup;
    private RichTable etable;
    private RichSelectOneChoice epreferedsupplier;
    private RichInputText eoperatingUnit;
    private RichSelectOneChoice eproject;
    private RichSelectOneChoice etask;
    private RichInputText etotalvalue;
    private RichSelectOneChoice eitem;
    private RichSelectOneChoice etype;
    private RichInputText ecode;
    private RichInputDate eneedby;
    private RichInputText totalBVar;
    private RichPopup resubmit_popup;
    private RichCommandImageLink createinsertbutton;
    private RichCommandImageLink e_createinsertbutton;
    private String flagForPlus;


    public void setFlagForPlus(String flagForPlus) {
        this.flagForPlus = flagForPlus;
    }

    public String getFlagForPlus() {
        return flagForPlus;
    }

    public PurchaseOrder() {
    }

    public void OnLoad() {
        Number tVal = null;
        DCIteratorBinding HeaderIteratorBindings = ADFUtils.findIterator("HeaderIterator");
        ViewObject vo = HeaderIteratorBindings.getViewObject();

        Row rowHeader = null;
        RowSetIterator ritHeader = vo.createRowSetIterator(null);

        DCIteratorBinding ItemIteratorBindings = ADFUtils.findIterator("Item_DetailsIterator");
        ViewObject vo1 = ItemIteratorBindings.getViewObject();
        RowSetIterator rit1 = vo1.createRowSetIterator(null);

        if (!rit1.hasNext()) {

            System.out.println("into onload of new bean");
            OperationBinding createInsertOP = ADFUtils.findOperation("CreateInsert");
            createInsertOP.execute();
            System.out.println("into onload#####");
            flagForPlus = "disable";
        } else {

            flagForPlus = "enable";
            ViewObjectImpl itemsDetailVO =
                (ViewObjectImpl) ADFUtils.findIterator("Item_DetailsVO1Iterator").getViewObject();

            boolean firstFlag = true;

            int total = 0;

            while (rit1.hasNext()) {
                Row row = rit1.next();


                System.out.println("TYPE ISSSSSSSSSSSSSSSSSSS" + row.getAttribute("Type"));
                codename = (String) row.getAttribute("Code");
                System.out.println("Code from payload is ---->" + codename);


                rate = ((BigInteger) row.getAttribute("Rate")).intValue();
                quantity = ((BigInteger) row.getAttribute("Quantity")).intValue();


                Row voRow = itemsDetailVO.createRow();

                System.out.println("Onload code : " + row.getAttribute("Code"));
                System.out.println("Onload code : " + row.getAttribute("Rate"));


                voRow.setAttribute("Code", row.getAttribute("Code"));
                voRow.setAttribute("Description", row.getAttribute("Description"));
                voRow.setAttribute("NeedBy", row.getAttribute("Need_by"));
                voRow.setAttribute("Operating_unit", row.getAttribute("Operating_unit"));
                voRow.setAttribute("Preferred_Supplier", row.getAttribute("Preferred_Supplier"));
                voRow.setAttribute("Project", row.getAttribute("Project"));
                voRow.setAttribute("Quantity", row.getAttribute("Quantity"));
                voRow.setAttribute("Rate", row.getAttribute("Rate"));
                voRow.setAttribute("Task", row.getAttribute("Task"));
                voRow.setAttribute("Type", row.getAttribute("Type"));

                itemsDetailVO.insertRow(voRow);

                total = total + (rate * quantity);


                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                                   row.getAttribute("Code"));
             
            }

            if (ritHeader.hasNext()) {
                rowHeader = ritHeader.next();
                rowHeader.setAttribute("Total_value", total);
                totalvalue = total;
                System.out.println("Total : " + total);
            }
        }
    }

    public String saveButton() {

        int calculateTotal = 0;
        int tVal=0;
        if (typeBVar.getValue() != null && desciptionBVar.getValue() != null && codeBVar.getValue() != null &&
            rateBVar.getValue() != null && quantityBVar.getValue() != null && preferedsuppBVar.getValue() != null &&
            needbyBVar.getValue() != null && operunitBVar.getValue() != null && projectBVar.getValue() != null &&
            taskBVar.getValue() != null) {
            flagForPlus = "enable";
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            ViewObjectImpl TotalValue =
                (ViewObjectImpl) bindings.findIteratorBinding("Item_DetailsVO1Iterator").getViewObject();

            Row currentRow = TotalValue.getCurrentRow();
            currentRow.setAttribute("Rate", rate);
            rateBVar.setValue(rate);

            currentRow.setAttribute("Code", codename);
       
            RowSetIterator rit = TotalValue.createRowSetIterator(null);
            while (rit.hasNext()) {

                Row[] allRowsInRange = TotalValue.getAllRowsInRange();
                System.out.println("allrowsin rage" + allRowsInRange);

                for (int i = 0; i < allRowsInRange.length; i++) {

                    if (allRowsInRange[i].getAttribute("TypeString") != null &&
                        allRowsInRange[i].getAttribute("DescString") != null &&
                        allRowsInRange[i].getAttribute("NeedBy") != null &&
                        allRowsInRange[i].getAttribute("Operating_unit") != null &&
                        allRowsInRange[i].getAttribute("Preferred_Supplier") != null &&
                        allRowsInRange[i].getAttribute("Project") != null &&
                        allRowsInRange[i].getAttribute("Quantity") != null &&
                        allRowsInRange[i].getAttribute("Rate") != null &&
                        allRowsInRange[i].getAttribute("Task") != null &&
                        allRowsInRange[i].getAttribute("Code") != null) {

                        Row row = rit.next();
                        System.out.println("-------++++++++--" + (Number) row.getAttribute("Rate"));
                        System.out.println("---------------------" + (Number) row.getAttribute("Quantity"));

                        Number quantity = (Number) row.getAttribute("Quantity");
                        Number rate = (Number) row.getAttribute("Rate");
                        int rateint = rate.intValue();
                        int qtyint = quantity.intValue();
                        calculateTotal += rateint * qtyint;
                        System.out.println("requester is " + requestBVar.getValue());
                        typeBVar.setDisabled(true);
                        desciptionBVar.setDisabled(true);
                        codeBVar.setDisabled(true);
                        quantityBVar.setDisabled(true);
                        rateBVar.setDisabled(true);
                        preferedsuppBVar.setDisabled(true);
                        needbyBVar.setDisabled(true);
                        operunitBVar.setDisabled(true);
                        projectBVar.setDisabled(true);
                        taskBVar.setDisabled(true);
                        totalBVar.setDisabled(true);
                        ViewObjectImpl HeaderItem =
                            (ViewObjectImpl) bindings.findIteratorBinding("HeaderIterator").getViewObject();
                        RowSetIterator rit1 = HeaderItem.createRowSetIterator(null);
                        totalvalue = calculateTotal;
                        while (rit1.hasNext()) {
                            Row row1 = rit1.next();
                            row1.setAttribute("Total_value", totalvalue);
                            row1.setAttribute("Requester", requestBVar.getValue());
                            Requester = row1.getAttribute("Requester").toString();
                            RequisitionNo = row1.getAttribute("Requisition_no").toString();
                            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + RequisitionNo);
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + requisnoBVar.getValue());
                        }
                   
                        createinsertbutton.setDisabled(false);
                    }
                  else {

                        showPopup(save_popup, true);
                    }

                }
            }
        }
        else {
            System.out.println("going  to open pop up on clicking save");
            showPopup(save_popup, true);
            return null;
        }
        System.out.println("total value is " + totalvalue);
        System.out.println("requestor is" + Requester);
        return null;
    }


    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Date getMaxDateVal() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 300);
        return cal.getTime();

    }

    public Date getMinDate() {
        try {
            Calendar cal = Calendar.getInstance();
            java.util.Date date = cal.getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = formatter.format(date);
            minDate = formatter.parse(currentDate);

            return formatter.parse(currentDate);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveInPayload() {
        if (typeBVar.getValue() != null && desciptionBVar.getValue() != null && codeBVar.getValue() != null &&
            rateBVar.getValue() != null && quantityBVar.getValue() != null && preferedsuppBVar.getValue() != null &&
            needbyBVar.getValue() != null && operunitBVar.getValue() != null && projectBVar.getValue() != null &&
            taskBVar.getValue() != null) {
        ViewObjectImpl itemsDetailVO =
            (ViewObjectImpl) ADFUtils.findIterator("Item_DetailsVO1Iterator").getViewObject();
        RowSetIterator rit1 = itemsDetailVO.createRowSetIterator(null);

        ViewObjectImpl itemDetailPayloadVO =
            (ViewObjectImpl) ADFUtils.findIterator("Item_DetailsIterator").getViewObject();


        DCIteratorBinding HeaderIteratorBindings = ADFUtils.findIterator("HeaderIterator");
        ViewObject voHeaader = HeaderIteratorBindings.getViewObject();

        RowSetIterator ritHeader = voHeaader.createRowSetIterator(null);

        while (ritHeader.hasNext()) {

            Row rHeader = ritHeader.next();
            System.out.println("rHeader---" + rHeader);

            rHeader.setAttribute("Total_value", totalvalue);
            System.out.println("total value is saved now");
            System.out.println("total value is" + rHeader.getAttribute("Total_value"));

        }

        System.out.println("???????????????????????????????? " + itemDetailPayloadVO.getRowCount());

        RowSetIterator rowItr = itemDetailPayloadVO.createRowSetIterator(null);
        while (rowItr.hasNext()) {
            rowItr.next().remove();
        }

        System.out.println("???????????????????????????????? " + itemDetailPayloadVO.getRowCount());

        for (int i = 0; i < itemDetailPayloadVO.getRowCount(); i++) {

            ADFUtils.findIterator("Item_DetailsIterator").getCurrentRow().remove();
        }


        while (rit1.hasNext()) {
            Row row = rit1.next();

            Row itemDetailsRow = itemDetailPayloadVO.createRow();

            itemDetailsRow.setAttribute("Code", row.getAttribute("Code"));
            itemDetailsRow.setAttribute("Description", row.getAttribute("Description"));
            itemDetailsRow.setAttribute("Need_by", row.getAttribute("NeedBy"));
            itemDetailsRow.setAttribute("Operating_unit", row.getAttribute("Operating_unit"));
            itemDetailsRow.setAttribute("Preferred_Supplier", row.getAttribute("Preferred_Supplier"));
            itemDetailsRow.setAttribute("Project", row.getAttribute("Project"));
            itemDetailsRow.setAttribute("Quantity", row.getAttribute("Quantity"));
            itemDetailsRow.setAttribute("Rate", row.getAttribute("Rate"));
            itemDetailsRow.setAttribute("Task", row.getAttribute("Task"));
            itemDetailsRow.setAttribute("Type", row.getAttribute("Type"));
            itemDetailPayloadVO.insertRow(itemDetailsRow);

        }
            }
        else{
                System.out.println("going  to open pop up on clicking save");
                showPopup(save_popup, true);
                
            }
    }

    public String Submit_Initiator() {

        if (typeBVar.getValue() != null && desciptionBVar.getValue() != null && codeBVar.getValue() != null &&
            rateBVar.getValue() != null && quantityBVar.getValue() != null && preferedsuppBVar.getValue() != null &&
            needbyBVar.getValue() != null && operunitBVar.getValue() != null && projectBVar.getValue() != null &&
            taskBVar.getValue() != null) {

            System.out.println("rate is qu" + rateBVar.getValue());

            saveInPayload();

            OperationBinding saveOP = ADFUtils.findOperation("update");
            saveOP.execute();

            OperationBinding SubmitOP = ADFUtils.findOperation("SUBMIT");
            SubmitOP.execute();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
                org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext,
                                                                             ExtendedRenderKitService.class);
            service.addScript(facesContext,
                              "window.close();window.opener.location.href = window.opener.location.href;");
            service.addScript(facesContext, "closeMe()");


        }

        else {

            showPopup(submit_popup, true);
            System.out.println("popup created ");

        }

        return null;
    }

    private void executeCreateInsertOperation() {
        OperationBinding createInsertOP = ADFUtils.findOperation("CreateInsert1");
        createInsertOP.execute();
    }

    private void deleteOperation(String operationName) {

        //OperationBinding deleteOp = ADFUtils.findOperation(operationName);
        //deleteOp.execute();
    }


    public String createinsert_button() {

        if (flagForPlus != null && flagForPlus.trim().equalsIgnoreCase("enable")) {
            OperationBinding CreateInsert = ADFUtils.findOperation("CreateInsert2");
            CreateInsert.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
            typeBVar.setDisabled(false);
            desciptionBVar.setDisabled(false);
            codeBVar.setDisabled(true);
            quantityBVar.setDisabled(false);
            rateBVar.setDisabled(true);
            preferedsuppBVar.setDisabled(false);
            needbyBVar.setDisabled(false);
            operunitBVar.setDisabled(false);
            projectBVar.setDisabled(false);
            taskBVar.setDisabled(false);
            typeBVar.setValue("");
            desciptionBVar.setValue("");
            codeBVar.setValue("");
            rateBVar.setValue("");
            flagForPlus = "disable";
        }


        return null;
    }

    public void table_selection(SelectionEvent selectionEvent) {
        System.out.println(")))))))))))))))))))))))in selection listner");
        ADFUtils.invokeEL("#{bindings.Item_DetailsVO1.collectionModel.makeCurrent}", new Class[] {
                          SelectionEvent.class }, new Object[] { selectionEvent });
        // get the selected row , by this you can get any attribute of that row
        //Row selectedRow = (Row) ADFUtils.evaluateEL("#{bindings.Item_DetailsIterator.currentRow}");
        //        String Type=selectedRow.getAttribute("Type").toString();
        //        System.out.println("value is"+ selectedRow.getAttribute("Type"));
        //        typeBVar.setValue(Type);
        //        String Description=selectedRow.getAttribute("Description").toString();
        //        desciptionBVar.setValue(Description);
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(typeBVar);
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(desciptionBVar);
        //
        Row selectedRow = (Row) ADFUtils.evaluateEL("#{bindings.Item_DetailsVO1.currentRow}");
        if (selectedRow.getAttribute("Rate") != null && selectedRow.getAttribute("Code") != null) {


            codename = (String) selectedRow.getAttribute("Code");
            System.out.println("Code from payload is ---->" + codename);
            rate = ((Number) selectedRow.getAttribute("Rate")).intValue();

            typeBVar.setDisabled(false);
            desciptionBVar.setDisabled(false);
            codeBVar.setDisabled(false);
            quantityBVar.setDisabled(false);
            rateBVar.setDisabled(false);
            preferedsuppBVar.setDisabled(false);
            needbyBVar.setDisabled(false);
            operunitBVar.setDisabled(false);
            projectBVar.setDisabled(false);
            taskBVar.setDisabled(false);

        }

    }

    public static Connection getConnection(String dsName) throws NamingException, SQLException {
        Connection con = null;
        DataSource datasource = null;
        Context initialContext = new InitialContext();
        if (initialContext == null) {
        } else {
            datasource = (DataSource) initialContext.lookup(dsName);
            if (datasource != null) {
                con = datasource.getConnection();
            } else {

            }
        }
        return con;
    }

    public void Item_idSOC(ValueChangeEvent vce) {
        String socval = typeBVar.getValue().toString();
        System.out.println("soc bind value" + socval);
        System.out.println("vce value" + vce.getNewValue());
        if (vce.getNewValue() != null) {

            Connection conn = null;
            PreparedStatement psTotal = null;
            ResultSet rsTotal = null;
            codeBVar.setValue("");
            try {
                conn = getConnection("jdbc/NileDBDS");
                String Query = "select ITEM_NAME FROM ITEM I where I.ITEM_ID='" + socval + "'";

                psTotal = conn.prepareStatement(Query);
                rsTotal = psTotal.executeQuery();
                System.out.println("query is" + Query);
                while (rsTotal.next()) {
                    itemname = rsTotal.getString(1);
                    System.out.println("item name is" + itemname);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
            } finally {
                try {
                    if (psTotal != null) {
                        psTotal.close();
                    }
                    if (rsTotal != null) {
                        rsTotal.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                    // TODO: Add catch code
                    e.printStackTrace();
                }


            }
        }
    }

    public void typeSelectedValue(ValueChangeEvent valueChangeEvent) {
        String socval = desciptionBVar.getValue().toString();
        System.out.println("soc5 bind value" + socval);
        Connection conn = null;
        PreparedStatement psTotal = null;
        ResultSet rsTotal = null;
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            conn = getConnection("jdbc/NileDBDS");
            String Query1 = "select TYPE_NAME FROM TYPE T where T.TYPE_ID='" + socval + "'";
            String Query = "select CODE_NAME, RATE FROM CODE C where C.TYPE_ID='" + socval + "'";
       
            ps2 = conn.prepareStatement(Query1);
            rs2 = ps2.executeQuery();
            System.out.println("query1 is" + Query1);
            while (rs2.next()) {
                typename = rs2.getString(1);
                System.out.println("TYpe name is" + typename);
            }
            psTotal = conn.prepareStatement(Query);
            rsTotal = psTotal.executeQuery();
            System.out.println("query is" + Query);
            while (rsTotal.next()) {
                codename = rsTotal.getString(1);
                rate = rsTotal.getInt(2);
                System.out.println("item name is" + codename);
                System.out.println("rate is" + rate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
        } finally {
            try {
                if (psTotal != null) {
                    psTotal.close();
                }
                if (rsTotal != null) {
                    rsTotal.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
        }
    }

    public String Reject_NonEditable() {
        System.out.println("in Reject Method");
        if (commentBVar.getValue() != null) {
            OperationBinding createInsertOP = ADFUtils.findOperation("REJECT");
            createInsertOP.execute();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
                org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext,
                                                                             ExtendedRenderKitService.class);
            service.addScript(facesContext,
                              "window.close();window.opener.location.href = window.opener.location.href;");
            service.addScript(facesContext, "closeMe()");
        } else {
            showPopup(reject_popup, true);
        }
        return null;
    }

    public void setTotalvalue(int totalvalue) {
        this.totalvalue = totalvalue;
    }

    public int getTotalvalue() {
        return totalvalue;
    }

    public void setTypeBVar(RichSelectOneChoice typeBVar) {
        this.typeBVar = typeBVar;
    }

    public RichSelectOneChoice getTypeBVar() {
        return typeBVar;
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

    public void setOperunitBVar(RichInputText operunitBVar) {
        this.operunitBVar = operunitBVar;
    }

    public RichInputText getOperunitBVar() {
        return operunitBVar;
    }

    public void setToatlVal(RichInputText toatlVal) {
        this.toatlVal = toatlVal;
    }

    public RichInputText getToatlVal() {
        return toatlVal;
    }

    public void setRequisitionBVar(RichOutputText requisitionBVar) {
        this.requisitionBVar = requisitionBVar;
    }

    public RichOutputText getRequisitionBVar() {
        return requisitionBVar;
    }

    public void setSave_popup(RichPopup save_popup) {
        this.save_popup = save_popup;
    }

    public RichPopup getSave_popup() {
        return save_popup;
    }

    public void setSubmit_popup(RichPopup submit_popup) {
        this.submit_popup = submit_popup;
    }

    public RichPopup getSubmit_popup() {
        return submit_popup;
    }


    public void setDesciptionBVar(RichSelectOneChoice desciptionBVar) {
        this.desciptionBVar = desciptionBVar;
    }

    public RichSelectOneChoice getDesciptionBVar() {
        return desciptionBVar;
    }


    public void setItem_idoutputtext(RichOutputText item_idoutputtext) {
        this.item_idoutputtext = item_idoutputtext;
    }

    public RichOutputText getItem_idoutputtext() {
        return item_idoutputtext;
    }


    public void setCommentBVar(RichInputText commentBVar) {
        this.commentBVar = commentBVar;
    }

    public RichInputText getCommentBVar() {
        return commentBVar;
    }

    public void setReject_popup(RichPopup reject_popup) {
        this.reject_popup = reject_popup;
    }

    public RichPopup getReject_popup() {
        return reject_popup;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemname() {
        return itemname;
    }
    private String typename;

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getCodename() {
        return codename;
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }

    public String createinsertbutton() {
        return null;
    }

    public void setRequestBVar(RichInputText requestBVar) {
        this.requestBVar = requestBVar;
    }

    public RichInputText getRequestBVar() {
        return requestBVar;
    }

    public void setApprove_NonEditable(RichCommandImageLink approve_NonEditable) {
        this.approve_NonEditable = approve_NonEditable;
    }

    public RichCommandImageLink getApprove_NonEditable() {
        return approve_NonEditable;
    }

    public String Approve_NonEditable() {
        System.out.println("in Approve Method");
        OperationBinding createInsertOP = ADFUtils.findOperation("APPROVE");
        createInsertOP.execute();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
            org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
        service.addScript(facesContext, "window.close();window.opener.location.href = window.opener.location.href;");

        return null;
    }

    public void Save_Editable() {
        int calculateTotal = 0;
        System.out.println("inside iffffffffffffffffffffff");
        if (eitem.getValue() != null && etype.getValue() != null && ecode.getValue() != null &&
            erate.getValue() != null && equantity.getValue() != null && epreferedsupplier.getValue() != null &&
            eneedby.getValue() != null && eoperatingUnit.getValue() != null && eproject.getValue() != null &&
            etask.getValue() != null) {
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            ViewObjectImpl TotalValue =
                (ViewObjectImpl) bindings.findIteratorBinding("Item_DetailsVO1Iterator").getViewObject();
            RowSetIterator rit = TotalValue.createRowSetIterator(null);

            while (rit.hasNext()) {
                Row row = rit.next();
                System.out.println("---------------------" + (Number) row.getAttribute("Rate"));
                System.out.println("---------------------" + (Number) row.getAttribute("Quantity"));
                Number quantity = (Number) row.getAttribute("Quantity");
                Number rate = (Number) row.getAttribute("Rate");
                int rateint = rate.intValue();
                int qtyint = quantity.intValue();
                calculateTotal += rateint * qtyint;
            
                eitem.setDisabled(true);
                etype.setDisabled(true);
                ecode.setDisabled(true);
                equantity.setDisabled(true);
                erate.setDisabled(true);
                epreferedsupplier.setDisabled(true);
                eneedby.setDisabled(true);
                eoperatingUnit.setDisabled(true);
                eproject.setDisabled(true);
                etask.setDisabled(true);
            }
            ViewObjectImpl HeaderItem = (ViewObjectImpl) bindings.findIteratorBinding("HeaderIterator").getViewObject();
            RowSetIterator rit1 = HeaderItem.createRowSetIterator(null);
            ViewObjectImpl ItemVO =
                (ViewObjectImpl) bindings.findIteratorBinding("Item_DetailsVO1Iterator").getViewObject();
            RowSetIterator ritItemVO = ItemVO.createRowSetIterator(null);

            totalvalue = calculateTotal;
            etotalvalue.setVisible(true);
            while (rit1.hasNext()) {
                Row row1 = rit1.next();
                row1.setAttribute("Total_value", totalvalue);
          
            }
            OperationBinding Save = ADFUtils.findOperation("Commit");
            Save.execute();
            e_createinsertbutton.setDisabled(false);
        }

        else {
            System.out.println("going to show popup =-=0=0=-=");
            showPopup(saveeditable_popup, true);
        }

        System.out.println("total value is " + totalvalue);

    }


    public void setErate(RichInputText erate) {
        this.erate = erate;
    }

    public RichInputText getErate() {
        return erate;
    }

    public void setEquantity(RichInputText equantity) {
        this.equantity = equantity;
    }

    public RichInputText getEquantity() {
        return equantity;
    }

    public void setSaveeditable_popup(RichPopup saveeditable_popup) {
        this.saveeditable_popup = saveeditable_popup;
    }

    public RichPopup getSaveeditable_popup() {
        return saveeditable_popup;
    }

    public String Resubmit_Editable() {
        if (eitem.getValue() != null && etype.getValue() != null && ecode.getValue() != null &&
            erate.getValue() != null && equantity.getValue() != null && epreferedsupplier.getValue() != null &&
            eneedby.getValue() != null && eoperatingUnit.getValue() != null && eproject.getValue() != null &&
            etask.getValue() != null) {
            saveInPayload();
            OperationBinding createInsertOP = ADFUtils.findOperation("RESUBMIT");
            createInsertOP.execute();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
                org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext,
                                                                             ExtendedRenderKitService.class);
            service.addScript(facesContext,
                              "window.close();window.opener.location.href = window.opener.location.href;");
            service.addScript(facesContext, "closeMe()");
        } else {
            showPopup(resubmit_popup, true);
        }
        return null;
    }

    public String Ecreate_insertButton() {
           
           OperationBinding CreateInsert = ADFUtils.findOperation("CreateInsert1");
           CreateInsert.execute();
           AdfFacesContext.getCurrentInstance().addPartialTarget(etable);
           eitem.setDisabled(false);
           etype.setDisabled(false);
           ecode.setDisabled(false);
           equantity.setDisabled(false);
           erate.setDisabled(true);
           epreferedsupplier.setDisabled(false);
           eneedby.setDisabled(false);
           eoperatingUnit.setDisabled(false);
           eproject.setDisabled(false);
           etask.setDisabled(false);
           eitem.setValue("");
           etype.setValue("");
           erate.setValue("");
        return null;
    }

    public void setEtable(RichTable etable) {
        this.etable = etable;
    }

    public RichTable getEtable() {
        return etable;
    }

    public void setEpreferedsupplier(RichSelectOneChoice epreferedsupplier) {
        this.epreferedsupplier = epreferedsupplier;
    }

    public RichSelectOneChoice getEpreferedsupplier() {
        return epreferedsupplier;
    }

    public void setEoperatingUnit(RichInputText eoperatingUnit) {
        this.eoperatingUnit = eoperatingUnit;
    }

    public RichInputText getEoperatingUnit() {
        return eoperatingUnit;
    }

    public void setEproject(RichSelectOneChoice eproject) {
        this.eproject = eproject;
    }

    public RichSelectOneChoice getEproject() {
        return eproject;
    }

    public void setEtask(RichSelectOneChoice etask) {
        this.etask = etask;
    }

    public RichSelectOneChoice getEtask() {
        return etask;
    }

    public void E_Load() {
        System.out.println("In Editable load method");

    }


    public void setEtotalvalue(RichInputText etotalvalue) {
        this.etotalvalue = etotalvalue;
    }

    public RichInputText getEtotalvalue() {
        return etotalvalue;
    }

    public void setEitem(RichSelectOneChoice eitem) {
        this.eitem = eitem;
    }

    public RichSelectOneChoice getEitem() {
        return eitem;
    }

    public void setEtype(RichSelectOneChoice etype) {
        this.etype = etype;
    }

    public RichSelectOneChoice getEtype() {
        return etype;
    }

    public void e_item_vcl(ValueChangeEvent vce) {
        String socval = eitem.getValue().toString();
        System.out.println("soc bind value" + socval);
        System.out.println("vce value" + vce.getNewValue().toString());
        Connection conn = null;
        PreparedStatement psTotal = null;
        ResultSet rsTotal = null;

        try {
            conn = getConnection("jdbc/NileDBDS");
            String Query = "select ITEM_NAME FROM ITEM I where I.ITEM_ID='" + socval + "'";

            psTotal = conn.prepareStatement(Query);
            rsTotal = psTotal.executeQuery();
            System.out.println("query is" + Query);
            while (rsTotal.next()) {
                itemname = rsTotal.getString(1);
                System.out.println("item name is" + itemname);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
        }

        finally {
            try {
                if (psTotal != null) {
                    psTotal.close();
                }
                if (rsTotal != null) {
                    rsTotal.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }


        }
    }

    public void e_type_vcl(ValueChangeEvent valueChangeEvent) {
        System.out.println("flag for code---" + flagForCode);
        System.out.println("flag for rate----" + flagForRate);
        flagForCode = true;
        flagForRate = true;
        System.out.println("flag for code new--" + flagForCode);
        System.out.println("flag for rate new---" + flagForRate);
        String socval = etype.getValue().toString();
        System.out.println("soc5 bind value" + socval);
        Connection conn = null;
        PreparedStatement psTotal = null;
        ResultSet rsTotal = null;
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            conn = getConnection("jdbc/NileDBDS");
            String Query1 = "select TYPE_NAME FROM TYPE T where T.TYPE_ID='" + socval + "'";
            String Query = "select CODE_NAME,RATE FROM CODE C where C.TYPE_ID='" + socval + "'";
            ps2 = conn.prepareStatement(Query1);
            rs2 = ps2.executeQuery();
            System.out.println("query1 is" + Query1);
            while (rs2.next()) {
                typename = rs2.getString(1);
                System.out.println("TYpe name is" + typename);
            }
            psTotal = conn.prepareStatement(Query);
            rsTotal = psTotal.executeQuery();
            System.out.println("query is" + Query);
            while (rsTotal.next()) {
                codename = rsTotal.getString(1);
                rate = rsTotal.getInt(2);

                DCBindingContainer bindings =
                    (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

                ViewObjectImpl ItemVO =
                    (ViewObjectImpl) bindings.findIteratorBinding("Item_DetailsVO1Iterator").getViewObject();

                Row currentRow = ItemVO.getCurrentRow();
                currentRow.setAttribute("Rate", rate);
                System.out.println("rate is set-------");
                currentRow.setAttribute("Code", codename);
                System.out.println("item name is" + codename);
                System.out.println("rATE is" + rate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
        } finally {
            try {
                if (psTotal != null) {
                    psTotal.close();
                }
                if (rsTotal != null) {
                    rsTotal.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }


        }
    }

    public void setFlagForCode(boolean flagForCode) {
        this.flagForCode = flagForCode;
    }

    public boolean isFlagForCode() {
        return flagForCode;
    }

    public void setFlagForRate(boolean flagForRate) {
        this.flagForRate = flagForRate;
    }

    public boolean isFlagForRate() {
        return flagForRate;
    }

    public void setEcode(RichInputText ecode) {
        this.ecode = ecode;
    }

    public RichInputText getEcode() {
        return ecode;
    }

    public void setEneedby(RichInputDate eneedby) {
        this.eneedby = eneedby;
    }

    public RichInputDate getEneedby() {
        return eneedby;
    }

    public void scb_1(ValueChangeEvent valueChangeEvent) {
        System.out.println("In SBC1");
        boolean value = (Boolean) valueChangeEvent.getNewValue();
        System.out.println("value is" + value);
        if (value == true) {
            typeBVar.setDisabled(false);
            desciptionBVar.setDisabled(false);
            codeBVar.setDisabled(false);
            quantityBVar.setDisabled(false);
            rateBVar.setDisabled(false);
            preferedsuppBVar.setDisabled(false);
            needbyBVar.setDisabled(false);
            operunitBVar.setDisabled(false);
            projectBVar.setDisabled(false);
            taskBVar.setDisabled(false);
            RowKeySet rks = new RowKeySetImpl();
            CollectionModel model = (CollectionModel) table.getValue();
            int rowcount = model.getRowCount();
            for (int i = 0; i < rowcount; i++) {
                model.setRowIndex(i);
                Object key = model.getRowKey();
                rks.add(key);
            }
            table.setSelectedRowKeys(rks);

        } else {
            table.getSelectedRowKeys().clear();

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);

    }

    public void setTotalBVar(RichInputText totalBVar) {
        this.totalBVar = totalBVar;
    }

    public RichInputText getTotalBVar() {
        return totalBVar;
    }

    public String delete_button() {
        int calculateTotal = 0;
        System.out.println("in delete method");
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        ViewObjectImpl itemDetailVO =
            (ViewObjectImpl) bindings.findIteratorBinding("Item_DetailsVO1Iterator").getViewObject();
        Row[] allRowsInRange = itemDetailVO.getAllRowsInRange();
        int length = allRowsInRange.length;
        System.out.println("length before removal-----" + length);
        ADFUtils.findIterator("Item_DetailsVO1Iterator").getCurrentRow().remove();
        Row[] allRowsInRange1 = itemDetailVO.getAllRowsInRange();
        int length1 = allRowsInRange1.length;
        System.out.println("length after removal-----" + length1);
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        ViewObjectImpl vo2 = (ViewObjectImpl) bindings1.findIteratorBinding("Item_DetailsVO1Iterator").getViewObject();
        Row[] allRowsInRange2 = vo2.getAllRowsInRange();
        for (int i = 0; i < allRowsInRange2.length; i++) {
            
                if (allRowsInRange2[i].getAttribute("TypeString") != null &&
                    allRowsInRange2[i].getAttribute("DescString") != null &&
                    allRowsInRange2[i].getAttribute("NeedBy") != null &&
                    allRowsInRange2[i].getAttribute("Operating_unit") != null &&
                    allRowsInRange2[i].getAttribute("Preferred_Supplier") != null &&
                    allRowsInRange2[i].getAttribute("Project") != null &&
                    allRowsInRange2[i].getAttribute("Quantity") != null &&
                    allRowsInRange2[i].getAttribute("Rate") != null &&
                    allRowsInRange2[i].getAttribute("Task") != null &&
                    allRowsInRange2[i].getAttribute("Code") != null)
                {
                    
                    allRowsInRange2[i].getAttribute("Rate");
                    allRowsInRange2[i].getAttribute("Quantity");
                    System.out.println("Quantity after deletion is" +  allRowsInRange2[i].getAttribute("Quantity"));
                    Number quantity = (Number)  allRowsInRange2[i].getAttribute("Quantity");
                    Number rate = (Number)  allRowsInRange2[i].getAttribute("Rate");
                    int rateint = rate.intValue();
                    int qtyint = quantity.intValue();
                    calculateTotal += rateint * qtyint;
                    System.out.println("total value after deletion is" + calculateTotal);
                    totalBVar.setValue(calculateTotal);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(totalBVar);
                    
                }
            else {
                    
                    System.out.println("in the else condition");
                            codeBVar.setValue("");
                            rateBVar.setValue("");
                      totalBVar.setValue("");
        
                }

        }

        if (length1 == 0) {
            OperationBinding CreateInsert = ADFUtils.findOperation("CreateInsert2");
            CreateInsert.execute();
        }
        createinsertbutton.setDisabled(false);
        typeBVar.setDisabled(false);
        desciptionBVar.setDisabled(false);
        codeBVar.setDisabled(false);
        quantityBVar.setDisabled(false);
        rateBVar.setDisabled(false);
        preferedsuppBVar.setDisabled(false);
        needbyBVar.setDisabled(false);
        operunitBVar.setDisabled(false);
        projectBVar.setDisabled(false);
        taskBVar.setDisabled(false);
      
        return null;
    }

    public void setResubmit_popup(RichPopup resubmit_popup) {
        this.resubmit_popup = resubmit_popup;
    }

    public RichPopup getResubmit_popup() {
        return resubmit_popup;
    }

    public void setCreateinsertbutton(RichCommandImageLink createinsertbutton) {
        this.createinsertbutton = createinsertbutton;
    }

    public RichCommandImageLink getCreateinsertbutton() {
        return createinsertbutton;
    }

    public String e_deletebutton() {
        int calculateTotal = 0;
        System.out.println("in delete method");
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        ViewObjectImpl itemDetailVO =
            (ViewObjectImpl) bindings.findIteratorBinding("Item_DetailsVO1Iterator").getViewObject();
        Row[] allRowsInRange = itemDetailVO.getAllRowsInRange();
        int length = allRowsInRange.length;
        System.out.println("length before removal-----" + length);
        // Row currentRow = itemDetailVO.getCurrentRow();
        //currentRow.remove();
        ADFUtils.findIterator("Item_DetailsVO1Iterator").getCurrentRow().remove();
        Row[] allRowsInRange1 = itemDetailVO.getAllRowsInRange();
        int length1 = allRowsInRange1.length;
        System.out.println("length after removal-----" + length1);
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        ViewObjectImpl vo2 = (ViewObjectImpl) bindings1.findIteratorBinding("Item_DetailsVO1Iterator").getViewObject();
        Row[] allRowsInRange2 = vo2.getAllRowsInRange();
        for (int i = 0; i < allRowsInRange2.length; i++) {

            Row itemRow = allRowsInRange2[i];
            itemRow.getAttribute("Rate");
            System.out.println("Rate after deletion is" + itemRow.getAttribute("Rate"));
            itemRow.getAttribute("Quantity");
            System.out.println("Quantity after deletion is" + itemRow.getAttribute("Quantity"));
            Number quantity = (Number) itemRow.getAttribute("Quantity");
            Number rate = (Number) itemRow.getAttribute("Rate");
            int rateint = rate.intValue();
            int qtyint = quantity.intValue();
            calculateTotal += rateint * qtyint;

            System.out.println("total value after deletion is" + calculateTotal);
            etotalvalue.setValue(calculateTotal);
            AdfFacesContext.getCurrentInstance().addPartialTarget(etotalvalue);
            
            ViewObjectImpl HeaderItem = (ViewObjectImpl) bindings.findIteratorBinding("HeaderIterator").getViewObject();
            RowSetIterator rit1 = HeaderItem.createRowSetIterator(null);
            totalvalue = calculateTotal;
            while (rit1.hasNext()) {
                Row row1 = rit1.next();
                row1.setAttribute("Total_value", calculateTotal);
                System.out.println("total value after deletion is set in payload"+calculateTotal);
            }
            
        }

        if (length1 == 0) {
            etotalvalue.setValue("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(etotalvalue);
            OperationBinding CreateInsert = ADFUtils.findOperation("CreateInsert1");
            CreateInsert.execute();
        }
        e_createinsertbutton.setDisabled(false);
        eitem.setDisabled(false);
        etype.setDisabled(false);
        ecode.setDisabled(true);
        equantity.setDisabled(false);
        erate.setDisabled(true);
        epreferedsupplier.setDisabled(false);
        eneedby.setDisabled(false);
        eoperatingUnit.setDisabled(false);
        eproject.setDisabled(false);
        etask.setDisabled(false);
        return null;


    }

    public void setE_createinsertbutton(RichCommandImageLink e_createinsertbutton) {
        this.e_createinsertbutton = e_createinsertbutton;
    }

    public RichCommandImageLink getE_createinsertbutton() {
        return e_createinsertbutton;
    }

    public void e_tableselectionlistner(SelectionEvent selectionEvent) {
        System.out.println("In Editable listner");
        ADFUtils.invokeEL("#{bindings.Item_DetailsVO1.collectionModel.makeCurrent}", new Class[] {
                          SelectionEvent.class }, new Object[] { selectionEvent });
        // get the selected row , by this you can get any attribute of that row
        Row selectedRow = (Row) ADFUtils.evaluateEL("#{bindings.Item_DetailsVO1Iterator.currentRow}");
        
               String Type=selectedRow.getAttribute("Project").toString();
                System.out.println("Project issssssssssssssssssssssss"+ selectedRow.getAttribute("Project"));
        eitem.setDisabled(false);
        etype.setDisabled(false);
        ecode.setDisabled(true);
        equantity.setDisabled(false);
        erate.setDisabled(true);
        epreferedsupplier.setDisabled(false);
        eneedby.setDisabled(false);
        eoperatingUnit.setDisabled(false);
        eproject.setDisabled(false);
        etask.setDisabled(false);

        //to show popup, you can write your logic here , what you wanna do

    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    public void load_method(PhaseEvent phaseEvent) {
        System.out.println("in load methoid");
        System.out.println("************************" + erate.getValue());
    }

    public String SaveInPayloadButton() {

        System.out.println("Saving in payload");
        saveInPayload();
        OperationBinding saveOP = ADFUtils.findOperation("update");
        saveOP.execute();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
            org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
        service.addScript(facesContext, "window.close();window.opener.location.href = window.opener.location.href;");
        return null;
    }

    public void setRequisnoBVar(RichInputText requisnoBVar) {
        this.requisnoBVar = requisnoBVar;
    }

    public RichInputText getRequisnoBVar() {
        return requisnoBVar;
    }

    public void setEcode1(RichInputText ecode1) {
        this.ecode1 = ecode1;
    }

    public RichInputText getEcode1() {
        return ecode1;
    }

    public void setErate1(RichInputText erate1) {
        this.erate1 = erate1;
    }

    public RichInputText getErate1() {
        return erate1;
    }

    public String saveInPayloadfromEditable() {
        System.out.println("Saving in payload");

         saveInPayload();

        OperationBinding saveOP = ADFUtils.findOperation("update");
        saveOP.execute();
        return null;
    }

    public void closePopup(ActionEvent actionEvent) {
        save_popup.cancel();
    }
    
    

    public void closePopupWindow(ActionEvent actionEvent) {
        reject_popup.cancel();
    }

    public void closeSaveEditablePopup(ActionEvent actionEvent) {
        saveeditable_popup.cancel();
    }

    public void closeResubmitPopup(ActionEvent actionEvent) {
        resubmit_popup.cancel();
    }

    public void closeSubmitPopup(ActionEvent actionEvent) {
        submit_popup.cancel();
    }
}
