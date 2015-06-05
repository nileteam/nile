package com.view.beans;

import com.view.utility.ADFUtils;
import com.view.utility.JSFUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.SQLException;

import java.util.Calendar;
import java.util.Date;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;
import org.apache.myfaces.trinidad.model.UploadedFile;  
import org.apache.myfaces.trinidad.util.Service;
import oracle.adf.share.ADFContext;
import oracle.jbo.JboException;

public class StartContract {
    private RichInputFile uploadfileVar;
    private String fileName;
    private String path;
    private String  tableFlag;
    private RichInputText reqNoVar;
    private RichInputText itemDescriptionVar;
    private RichInputDate needByDateVar;
    private RichInputText costCentreVar;
    private RichInputText nameOfSupplierVar;
    private RichInputText paymentTermsVar;
    private RichInputText specialTermsConditions;
    private RichInputText commentsVar;
    private RichInputText valOfItemVar;
    private RichPopup savePopup;
    private RichPopup submitpopup;
    private String flagForFlow;


    public StartContract() {
    }

    public String onLoad() {
       
     System.out.println("Inside the onload method ");
     
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlHierBinding treeBinding = (JUCtrlHierBinding) bindings.findCtrlBinding("task");
        System.out.println("TREE BINDING IS ======" + treeBinding);
        JUCtrlHierNodeBinding rootNode = null;
        rootNode = treeBinding.getRootNodeBinding();
        System.out.println("rootNode is " + rootNode);

        System.out.println("rootNode.getChildren" + rootNode.getChildren());
        System.out.println("rootNode.getChildren.size" + rootNode.getChildren().size());
        
        if(rootNode.getChildren()!=null) {
            
            for(int i=0;i<rootNode.getChildren().size();i++) {
                
                System.out.println("inside loop for "+(i+1)+"   times");
                
                JUCtrlHierNodeBinding startContract = (JUCtrlHierNodeBinding) rootNode.getChildren().get(0);
                System.out.println("startContract is----" + startContract);

                String[] attributeNames = startContract.getAttributeNames();
                
                for(String attribute:attributeNames) {
                    
                    System.out.println("attribute is :"+attribute);
                }
                
                
                JUCtrlHierNodeBinding startContractPayload = (JUCtrlHierNodeBinding) startContract.getChildren().get(0);
                System.out.println("startContractPayload -------" + startContractPayload);

                String[] names = startContractPayload.getAttributeNames();

                for (String name:names) {

                    System.out.println("attribute name for startContractPayload" + name);

                }
                
                String flag1= (String)startContractPayload.getAttribute("Flag");
                System.out.println("flag1======"+flag1);
           
                if(flag1.equalsIgnoreCase("StartContract")) {
                     System.out.println("inside editable condition");
                     flagForFlow="editable";
                    
                }
           
                else if(flag1.equalsIgnoreCase("CAsign")) {
                      System.out.println("inside non editable ");
                     flagForFlow="noneditable";
                }
                
                System.out.println("flag for flow ==================="+flagForFlow);
                
                JUCtrlHierNodeBinding inputContract=(JUCtrlHierNodeBinding)startContractPayload.getChildren().get(0);
                System.out.println("Input contract is "+inputContract);

                String[] attributeNames_2 = inputContract.getAttributeNames();

                for(String attributeName:attributeNames_2) {
                    
                    System.out.println("attribute names for input contract is:"+attributeName);
                }

                   
                JUCtrlHierNodeBinding contractType=(JUCtrlHierNodeBinding)startContractPayload.getChildren().get(0);
                System.out.println("contractType is "+contractType);
                
                
                

                String[] attributeNames_3 = contractType.getAttributeNames();

                for(String attributeName:attributeNames_3) {
                    
                    System.out.println("attribute names for  contractType is:"+attributeName);
                }


                JUCtrlHierNodeBinding contractTypeValueNode=(JUCtrlHierNodeBinding)contractType.getChildren().get(0);
                System.out.println("contractTypeValueNode is "+contractTypeValueNode);

                String[] attributeName_4=contractTypeValueNode.getAttributeNames();
                
                for(String attr:attributeName_4) {
                    
                    System.out.println("attribute names attr"+attr);
                }
                    
                java.math.BigInteger poNo= (java.math.BigInteger)contractTypeValueNode.getAttribute("Purchase_Requisition_No");
                
                    System.out.println("poNo--------------"+poNo);
                BindingContext ctx = BindingContext.getCurrent();
                DCBindingContainer bc = (DCBindingContainer)ctx.getCurrentBindingsEntry();
                DCIteratorBinding iterator = bc.findIteratorBinding("contractVO1Iterator");
                ViewObject conDocVO = iterator.getViewObject();
                conDocVO.setNamedWhereClauseParam("conid", poNo);
                conDocVO.executeQuery();

                int countRowContract = conDocVO.getRowCount();
                
                if(countRowContract==0) {
                    tableFlag="stop";
                }
              else {
                    
                    tableFlag="go";
                    
                }

                DCBindingContainer bindingCon = (DCBindingContainer)ctx.getCurrentBindingsEntry();
                DCIteratorBinding quoteIter = bindingCon.findIteratorBinding("quoteVO1Iterator");
                ViewObject quoteVO = quoteIter.getViewObject();
                quoteVO.setNamedWhereClauseParam("conid", poNo);
                quoteVO.executeQuery();
                
                
            }
            
            
            
        }
        
        return flagForFlow; 
    }

    public void setFlagForFlow(String flagForFlow) {
        this.flagForFlow = flagForFlow;
    }

    public String getFlagForFlow() {
        return flagForFlow;
    }


    public void testDownloadList(FacesContext facesContext, OutputStream outputStream)  throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
    //        Class.forName("oracle.jdbc.driver.OracleDriver");
    //         Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.149:1522:orcl12c","c##niledb","welcome1");
    //         PreparedStatement pstat = con.prepareStatement("select CONTRACT_DOC,FILE_NAME from CONTRACT_DOCUMENT where CONTRACT_ID = ?");
    //         pstat.setString(1,"testpdf");
    //         ResultSet rs = pstat.executeQuery();
    //         rs.next();
    //         String fileName = rs.getString("FILE_NAME");
    //         System.out.println(fileName);
    //         InputStream f = rs.getBinaryStream("CONTRACT_DOC");
    //        //         FileOutputStream f1 = new FileOutputStream("D:\\image\\"+fileName);
    //         int i=0;
    //         while((i=f.read())!=-1)
    //             outputStream.write(i);
    //        //        f1.close();
    //         outputStream.close();
    //        pstat.close();
    //        rs.close();
    //         con.close();
        

    }



    public void setUploadfileVar(RichInputFile uploadfileVar) {
        this.uploadfileVar = uploadfileVar;
    }

    public RichInputFile getUploadfileVar() {
        return uploadfileVar;
    }

    public void uploadfile_vcl(ValueChangeEvent valueChangeEvent) {
        try {
            // The event give access to an Uploade dFile which contains data about the file and its content
            OutputStream outputStream; 
            UploadedFile file = (UploadedFile) valueChangeEvent.getNewValue();
            fileName = file.getFilename();
            path="/home/oracle/Oracle/Middleware12c/Oracle_Home/user_projects/domains/bpm_domain/uploadcontract/";
            path=path.concat(fileName);
            InputStream in = file.getInputStream();
            outputStream =new FileOutputStream(new File(path));
             
                            int read = 0;
                            byte[] bytes = new byte[8192222];
             
                            while ((read = in.read(bytes)) != -1) {
                                    outputStream.write(bytes, 0, read);
                            }
            outputStream.flush();
            outputStream.close();
            
          System.out.println("file written");
        
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
    }

    public void setTableFlag(String tableFlag) {
        this.tableFlag = tableFlag;
    }

    public String getTableFlag() {
        return tableFlag;
    }

    public String uploadfile_Action() {
        System.out.println("inside upload action");
        tableFlag="go";
         java.math.BigInteger reqNo1 = (java.math.BigInteger)reqNoVar.getValue();
        String reqNo= reqNo1.toString();
        BindingContext ctx = BindingContext.getCurrent();
                DCBindingContainer bc = (DCBindingContainer)ctx.getCurrentBindingsEntry();
                       DCIteratorBinding iterator = bc.findIteratorBinding("contractVO1Iterator");
               ViewObject logoView = iterator.getViewObject();
             Row [] rws =   logoView.getFilteredRows("ContractId", reqNo);
             int i = 0;
             int count = 0;
             char ch[]=fileName.toCharArray();
             StringBuffer sbf=new StringBuffer();
             StringBuffer sbe=new StringBuffer();
             int fl=0;
                 for(int ii=0;ii<ch.length;ii++){  
                     if(ch[ii]=='.'){
                         fl=1;
                     }
                     if(fl==0){
                         sbf.append(ch[ii]);
                     }else{             
                     sbe.append(ch[ii]);
                     }
                    }  
             System.out.println("NAME OF THE FILE  "+sbf);
             System.out.println("EXTENSION OF THE FILE  "+sbe);




           String fileCompare = sbf.toString();
            System.out.println(fileCompare+"--------str[0]");
            for(i = 0; i < rws.length; i++)
            {
              Row row = rws[i];
              String file = row.getAttribute("FileName").toString();
                 if(file.contains(fileCompare))
                 {
                 count++;    
                    }
                 
                }
        //                  fileName = fileName.concat("V_"+rws.length);
        //                  fileName = fileCompare.concat("_V"+count).concat(sbe.toString());
               System.out.println(fileName);
        System.out.println("in upload method");
        
        try {
         
         System.out.println("quote------------------"+reqNo);

         File pdfFile = new File(path);
            byte[] pdfData = new byte[(int) pdfFile.length()];
            DataInputStream dis = new DataInputStream(new FileInputStream(pdfFile));
            dis.readFully(pdfData);
            dis.close();      
            Connection dbConnection = null;
            dbConnection = getConnection("jdbc/NileDBDS");
            PreparedStatement ps =
            dbConnection.prepareStatement("INSERT INTO CONTRACT_DOC_UPLOAD (" + "CONTRACT_ID, " + "CONTRACT_DOC, " + "FILE_TYPE, "+ "VERSION, "+
                                              "FILE_NAME" + ") VALUES (?,?,?,?,?)");
            ps.setString(1, reqNo);
            ps.setBytes(2, pdfData);
            ps.setString(3, "Contract");
            ps.setInt(4, count);
            ps.setString(5, fileName);
            ps.executeUpdate();
            ps.close();
            dbConnection.close();
            File f = new File(path);
            f.delete();
            System.out.println("Data Inserted Successfully.");
            uploadfileVar.resetValue();
            
           
            BindingContext ctx1 = BindingContext.getCurrent();
            DCBindingContainer bc1 = (DCBindingContainer)ctx.getCurrentBindingsEntry();
            DCIteratorBinding iterator1 = bc1.findIteratorBinding("contractVO1Iterator");
            ViewObject conDocVO = iterator1.getViewObject();
            conDocVO.setNamedWhereClauseParam("conid", reqNo);
            conDocVO.executeQuery();   
            
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch(NamingException e) {
            // TODO: Add catch code
           
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
        
               logoView.setWhereClause(null);
               logoView.executeQuery();
              
        return null;
       
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
    public void setReqNoVar(RichInputText reqNoVar) {
        this.reqNoVar = reqNoVar;
    }

    public RichInputText getReqNoVar() {
        return reqNoVar;
    }

    public String start_contract_save() {
        
        if(reqNoVar.getValue()!=null && itemDescriptionVar.getValue()!=null &&
            valOfItemVar.getValue()!=null && needByDateVar.getValue()!=null &&
            costCentreVar.getValue()!=null && nameOfSupplierVar.getValue()!=null
            && paymentTermsVar.getValue()!=null && specialTermsConditions.getValue()!=null
            && commentsVar.getValue()!=null) {

            OperationBinding SaveOP = ADFUtils.findOperation("update");
            SaveOP.execute();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
                org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext,
                                                                             ExtendedRenderKitService.class);
            service.addScript(facesContext,
                              "window.close();window.opener.location.href = window.opener.location.href;");
            service.addScript(facesContext, "closeMe()");


        }
        else {
            /**
             * show popup code here
             */
            showPopup(savePopup,true);
            
        }
        
       
        return null;
    }

    public void setItemDescriptionVar(RichInputText itemDescriptionVar) {
        this.itemDescriptionVar = itemDescriptionVar;
    }

    public RichInputText getItemDescriptionVar() {
        return itemDescriptionVar;
    }

    public void setNeedByDateVar(RichInputDate needByDateVar) {
        this.needByDateVar = needByDateVar;
    }

    public RichInputDate getNeedByDateVar() {
        return needByDateVar;
    }

    public void setCostCentreVar(RichInputText costCentreVar) {
        this.costCentreVar = costCentreVar;
    }

    public RichInputText getCostCentreVar() {
        return costCentreVar;
    }

    public void setNameOfSupplierVar(RichInputText nameOfSupplierVar) {
        this.nameOfSupplierVar = nameOfSupplierVar;
    }

    public RichInputText getNameOfSupplierVar() {
        return nameOfSupplierVar;
    }

    public void setPaymentTermsVar(RichInputText paymentTermsVar) {
        this.paymentTermsVar = paymentTermsVar;
    }

    public RichInputText getPaymentTermsVar() {
        return paymentTermsVar;
    }

    public void setSpecialTermsConditions(RichInputText specialTermsConditions) {
        this.specialTermsConditions = specialTermsConditions;
    }

    public RichInputText getSpecialTermsConditions() {
        return specialTermsConditions;
    }

    public void setCommentsVar(RichInputText commentsVar) {
        this.commentsVar = commentsVar;
    }

    public RichInputText getCommentsVar() {
        return commentsVar;
    }

    public void setValOfItemVar(RichInputText valOfItemVar) {
        this.valOfItemVar = valOfItemVar;
    }

    public RichInputText getValOfItemVar() {
        return valOfItemVar;
    }

    public void setSavePopup(RichPopup savePopup) {
        this.savePopup = savePopup;
    }

    public RichPopup getSavePopup() {
        return savePopup;
    }

    public void savePopupClose(ActionEvent actionEvent) {
     savePopup.cancel();
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

    public String start_contract_submit() {
        if(reqNoVar.getValue()!=null && itemDescriptionVar.getValue()!=null &&
            valOfItemVar.getValue()!=null && needByDateVar.getValue()!=null &&
            costCentreVar.getValue()!=null && nameOfSupplierVar.getValue()!=null
            && paymentTermsVar.getValue()!=null && specialTermsConditions.getValue()!=null
            && commentsVar.getValue()!=null) {

            OperationBinding SubmitOP = ADFUtils.findOperation("APPROVE");
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
            /**
             * show popup code here
             */
            showPopup(submitpopup,true);
            
        }
        return null;
    }

    public void setSubmitpopup(RichPopup submitpopup) {
        this.submitpopup = submitpopup;
    }

    public RichPopup getSubmitpopup() {
        return submitpopup;
    }

    public void closeSubmitPopup(ActionEvent actionEvent) {
        submitpopup.cancel();
    }
}
