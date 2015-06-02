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

public class InitiateQuote {
    private RichSelectOneChoice directorVar;

    List<SelectItem> customList;
    private RichSelectOneChoice clientVar;

    public void setMinValForEndDate1(oracle.jbo.domain.Date minValForEndDate1) {
        this.minValForEndDate1 = minValForEndDate1;
    }

    public oracle.jbo.domain.Date getMinValForEndDate1() {
        return minValForEndDate1;
    }
    private RichSelectOneChoice directSoc;
    String directors;
    String[] direct;

    
    private RichInputFile uploadfileVar;
    private String fileName;
    private String path;
    private RichInputText quoteID;
    private RichInputText quoteIdVar;
    private RichInputDate endDate;
    private RichInputDate startDate;
    private String period;
    private RichInputText contractTerm;
    oracle.jbo.domain.Date minValForEndDate1;
    String difference;

    public void setMinValForEndDateJBO(oracle.jbo.domain.Date minValForEndDateJBO) {
        this.minValForEndDateJBO = minValForEndDateJBO;
    }

    public oracle.jbo.domain.Date getMinValForEndDateJBO() {
        return minValForEndDateJBO;
    }
    oracle.jbo.domain.Date minValForEndDateJBO;

    public void setStartDate1(oracle.jbo.domain.Date startDate1) {
        this.startDate1 = startDate1;
        
    }

    public oracle.jbo.domain.Date getStartDate1() {
        return startDate1;
    }
    oracle.jbo.domain.Date startDate1 ;
    private RichInputText quoteVar;
    private RichSelectOneChoice lobVar;
    private RichSelectOneChoice officeVar;
    private RichInputText descriptionVar;
    private RichPopup submitPopup;
    private RichPopup savePopup;
    private String  tableFlag;
    private Date minDate = new Date();
    private java.sql.Date dateStart ;
    public InitiateQuote() {
    }

    public void setDirectorVar(RichSelectOneChoice directorVar) {
        this.directorVar = directorVar;
    }

    public RichSelectOneChoice getDirectorVar() {
        return directorVar;
    }
    
    public List<SelectItem> getCustomList() {
        
        if (customList == null) {
                 customList = new ArrayList<SelectItem>();
                 
                 for(String dir:direct) {
                     customList.add(new SelectItem(dir,dir));
                     
                 }
    //                 customList.add(new SelectItem(,));
    //                 customList.add(new SelectItem(,));
    //                 customList.add(new SelectItem(,));
    //                 customList.add(new SelectItem(,));
    //                 customList.add(new SelectItem(,));
             }
        
        
        return customList;
    }
    
//    public void onload() {
//        DCBindingContainer bindings =(DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
//        JUCtrlHierBinding treeBinding =(JUCtrlHierBinding)bindings.findCtrlBinding("task");
//         System.out.println("TREE BINDING IS ======"+treeBinding);
//        JUCtrlHierNodeBinding rootNode = null;
//        rootNode = treeBinding.getRootNodeBinding();
//        System.out.println("rootNode is "+rootNode);
//        
//        System.out.println("rootNode.getChildren"+rootNode.getChildren());
//        System.out.println("rootNode.getChildren.size"+rootNode.getChildren().size());
//        
//        if(rootNode.getChildren()!=null) {
//            for(int i=0; i< rootNode.getChildren().size();i++) {
//            System.out.println("bingo"+(i+1));
//
//                JUCtrlHierNodeBinding initiateQuote = (JUCtrlHierNodeBinding)rootNode.getChildren().get(0);
//                System.out.println("task----"+initiateQuote);
//
//
//                String[] attributeNames = initiateQuote.getAttributeNames();
//                
//                for(int z=0; i< attributeNames.length;i++) {
//                    
//                    System.out.println("Attribute name is "+attributeNames[z]);
//                }
//          
//             JUCtrlHierNodeBinding child= (JUCtrlHierNodeBinding)initiateQuote.getChildren().get(0);
//             System.out.println("child is -------"+child);
//
//                String[] names = child.getAttributeNames();
//                
//                for(int z=0;z<names.length;z++){
//                    
//                    System.out.println("attribute names in child is"+names[z]);
//                    
//                }
//                
//              JUCtrlHierNodeBinding childToChild   =(JUCtrlHierNodeBinding)child.getChildren().get(0);
//              System.out.println("Childt to child"+ childToChild);
//                
//                
//            
//
//                String[] attributeNames_2 = childToChild.getAttributeNames();
//                
//                for(int z=0;z<attributeNames_2.length;z++) {
//                    
//                    System.out.println(" Attributre name to child to child are "+attributeNames_2[z]);
//                }
//                             
//                     directors=(String)childToChild.getAttribute("Directors");
//                    System.out.println("directors are --------?>>>>>>>"+directors);
//                
//                    direct= directors.split(",");
//                    
//                for(String dir:direct) {
//                    System.out.println(dir);
//                    
//                }
//
//            }
//                
//            
//        }
//        
//    
//    
//    }
    
    public void onload() throws Exception{
         try {
             Map map = ADFContext.getCurrent().getSessionScope();


             DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
             JUCtrlHierBinding treeBinding = (JUCtrlHierBinding) bindings.findCtrlBinding("task");
             System.out.println("TREE BINDING IS ======" + treeBinding);
             JUCtrlHierNodeBinding rootNode = null;
             rootNode = treeBinding.getRootNodeBinding();
             System.out.println("rootNode is " + rootNode);

             System.out.println("rootNode.getChildren" + rootNode.getChildren());
             System.out.println("rootNode.getChildren.size" + rootNode.getChildren().size());

             if (rootNode.getChildren() != null) {
                 for (int i = 0; i < rootNode.getChildren().size(); i++) {
                     System.out.println("bingo" + (i + 1));

                     JUCtrlHierNodeBinding initiateQuote = (JUCtrlHierNodeBinding) rootNode.getChildren().get(0);
                     System.out.println("task----" + initiateQuote);


                     String[] attributeNames = initiateQuote.getAttributeNames();

                     for (int z = 0; i < attributeNames.length; i++) {

                         System.out.println("Attribute name is " + attributeNames[z]);
                     }

                     JUCtrlHierNodeBinding child = (JUCtrlHierNodeBinding) initiateQuote.getChildren().get(0);
                     System.out.println("child is -------" + child);

                     String[] names = child.getAttributeNames();

                     for (int z = 0; z < names.length; z++) {

                         System.out.println("attribute names in child is" + names[z]);

                     }

                     JUCtrlHierNodeBinding childToChild = (JUCtrlHierNodeBinding) child.getChildren().get(0);
                     System.out.println("Childt to child" + childToChild);


                     String[] attributeNames_2 = childToChild.getAttributeNames();

                     for (int z = 0; z < attributeNames_2.length; z++) {

                         System.out.println(" Attributre name to child to child are " + attributeNames_2[z]);
                     }

                     directors = (String) childToChild.getAttribute("Directors");
                     System.out.println("directors are --------?>>>>>>>" + directors);

                     direct = directors.split(",");

                     for (String dir : direct) {
                         System.out.println(dir);
                     }

                     JUCtrlHierNodeBinding quotenode = (JUCtrlHierNodeBinding) childToChild.getChildren().get(0);
                     System.out.println("quoteNode" + quotenode);

                     String[] attributeNames_quotenode = quotenode.getAttributeNames();
                     for (String c : attributeNames_quotenode) {
                         System.out.println("attribute names for the quote node are " + c);

                     }

                     String quoteId = (String) quotenode.getAttribute("QuoteID");
                     System.out.println("QuoteID-------------" + quoteId);
                     BindingContext ctx = BindingContext.getCurrent();
                   DCBindingContainer bc = (DCBindingContainer)ctx.getCurrentBindingsEntry();
                   DCIteratorBinding iterator = bc.findIteratorBinding("DCIterator");
                   ViewObject ConDocVO = iterator.getViewObject();
                   ConDocVO.setNamedWhereClauseParam("conid", quoteId);
                   ConDocVO.executeQuery();


                    int noOfRows = ConDocVO.getRowCount();
                    System.out.println("noOfRowns------"+noOfRows);
                     
                     if(noOfRows!=0) {
                         System.out.println("inside stop condition");
                         tableFlag="go";
                         
                     }
                     else {
                         System.out.println("inside else condition onload");
                         tableFlag="stop";
                     }
                     

                }


             }
         } catch (Exception e) {
             // TODO: Add catch code
             e.printStackTrace();
         }
    }

 

    public void setUploadfileVar(RichInputFile uploadfileVar) {
           this.uploadfileVar = uploadfileVar;
       }

       public RichInputFile getUploadfileVar() {
           return uploadfileVar;
       }

       public String uploadfile_Action() {
           System.out.println("inside upload action");
           tableFlag="go";
           String quote = (String)quoteIdVar.getValue();
           BindingContext ctx = BindingContext.getCurrent();
                   DCBindingContainer bc = (DCBindingContainer)ctx.getCurrentBindingsEntry();
                          DCIteratorBinding iterator = bc.findIteratorBinding("ContractDocUploadView1Iterator");
                  ViewObject logoView = iterator.getViewObject();
                Row [] rws =   logoView.getFilteredRows("ContractId", quote);
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
            
            System.out.println("quote------------------"+quote);

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
               ps.setString(1, quote);
               ps.setBytes(2, pdfData);
               ps.setString(3, "Quote");
               ps.setInt(4, count);
               ps.setString(5, fileName);
               ps.executeUpdate();
               ps.close();
               dbConnection.close();
               File f = new File(path);
               f.delete();
               System.out.println("Data Inserted Successfully.");
               uploadfileVar.resetValue();
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

    public void setTableFlag(String tableFlag) {
        this.tableFlag = tableFlag;
    }

    public String getTableFlag() {
        return tableFlag;
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
       public String download_Action() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
//           try
//           {
//           Connection conn=getConnection("jdbc/NileDBDS");
//           PreparedStatement pstmt = conn.prepareStatement("select CONTRACT_DOC from CONTRACT_DOCUMENT where CONTRACT_ID=? ");
//           pstmt.setString(1,"testpdf");
//           ResultSet rs = pstmt.executeQuery();
//                          rs.next();
//               PreparedStatement pstmt1 = conn.prepareStatement("select FILE_NAME from CONTRACT_DOCUMENT where CONTRACT_ID=?");
//               pstmt1.setString(1,"testpdf");
//               ResultSet rs1 = pstmt1.executeQuery();
//                          rs1.next(); 
//                          String fname=rs1.getString(1);  
//                          String pth="D:\\"+fname; 
//                          System.out.println("name of file ----  "+fname);
//                          System.out.println(pth); 
//                          InputStream f = rs.getBinaryStream("CONTRACT_DOC");
//                          FileOutputStream f1 = new FileOutputStream(pth);
//                          int i=0;
//                          while((i=f.read())!=-1)
//                          {
//                           f1.write(i);
//                          }
//                          f1.flush();
//                              f1.close();
//                              f.close();
//                              rs.close();
//                              pstmt.close();
//                              conn.close();
//           }catch(Exception e) {
//               e.printStackTrace();
//           }
//           return null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con =
                DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.149:1522:orcl12c", "c##niledb", "welcome1");
            //Connection con=getConnection("jdbc/NileDBDS");
            PreparedStatement pstat =
                con.prepareStatement("select CONTRACT_DOC,FILE_NAME from CONTRACT_DOC_UPLOAD where CONTRACT_ID = ?");
            pstat.setString(1, "testpdf");
            ResultSet rs = pstat.executeQuery();
            rs.next();
            String fileName = rs.getString("FILE_NAME");
            System.out.println(fileName);
            InputStream f = rs.getBinaryStream("CONTRACT_DOC");
            FileOutputStream f1 = new FileOutputStream("D:\\image\\" + fileName);
            int i = 0;
            while ((i = f.read()) != -1)
                f1.write(i);
            f1.close();
            pstat.close();
            rs.close();
            con.close();
          
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            // TODO: Add catch code
            cnfe.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            // TODO: Add catch code
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
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
       public String initiate_Quote_Submit() {
           // TODO: Add catch code "Put validation and pop up"
           if(directorVar.getValue()!=null && quoteVar.getValue()!=null && officeVar.getValue()!=null && descriptionVar.getValue()!=null
              && lobVar.getValue()!=null && startDate.getValue()!=null && endDate.getValue()!=null && clientVar.getValue()!=null) {
               String directors=(String)directorVar.getValue();
               System.out.println("directors---------------"+directors);
               
               DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
               ViewObjectImpl inputQuoteVO =(ViewObjectImpl) bindings.findIteratorBinding("QuoteIterator").getViewObject();
               RowSetIterator rit1 = inputQuoteVO.createRowSetIterator(null);
               
               while (rit1.hasNext()) {
                                    Row row1 = rit1.next();
                                   row1.setAttribute("Director", directors);  
               
               }
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
               showPopup(submitPopup
                         ,true);
           }
      
           return null;
       }

    public String initiate_Quote_Save() {
            // TODO: Add catch code "Put validation and pop up"
         if(directorVar.getValue()!=null && quoteVar.getValue()!=null && officeVar.getValue()!=null && descriptionVar.getValue()!=null
            && lobVar.getValue()!=null && startDate.getValue()!=null && endDate.getValue()!=null &&  clientVar.getValue()!=null) {
             
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
             showPopup(savePopup,true);
         }
         
        
            return null;
        }

    public void setQuoteID(RichInputText quoteID) {
        this.quoteID = quoteID;
    }

    public RichInputText getQuoteID() {
        return quoteID;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public void setQuoteIdVar(RichInputText quoteIdVar) {
        this.quoteIdVar = quoteIdVar;
    }

    public RichInputText getQuoteIdVar() {
        return quoteIdVar;
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

    public void rowWiseDownload(FacesContext facesContext, OutputStream outputStream) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        BindingContext ctx = BindingContext.getCurrent();
                DCBindingContainer bc = (DCBindingContainer)ctx.getCurrentBindingsEntry();
                       DCIteratorBinding iterator = bc.findIteratorBinding("ContractDocUploadView1Iterator");
               ViewObject logoView = iterator.getViewObject();
               Row row = logoView.getCurrentRow();
               String contract_id = row.getAttribute("SNo").toString();
      
        Class.forName("oracle.jdbc.driver.OracleDriver");
         Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.149:1522:orcl12c","c##niledb","welcome1");
         PreparedStatement pstat = con.prepareStatement("select CONTRACT_DOC,FILE_NAME from CONTRACT_DOCUMENT where s_no = ?");
         pstat.setString(1,contract_id);
         ResultSet rs = pstat.executeQuery();
         rs.next();
         String fileName = rs.getString("FILE_NAME");
         System.out.println(fileName);
         InputStream f = rs.getBinaryStream("CONTRACT_DOC");
        //         FileOutputStream f1 = new FileOutputStream("D:\\image\\"+fileName);
         int i=0;
         while((i=f.read())!=-1)
             outputStream.write(i);
        //        f1.close();
         outputStream.close();
        pstat.close();
        rs.close();
         con.close();

    }

    public void valueChange_endDate (ValueChangeEvent valueChangeEvent) {
       
        java.sql.Date enddate = ( java.sql.Date)endDate.getValue();
        System.out.println("endDate"+enddate);
        oracle.jbo.domain.Date endDate1=getJboDateFromSqlDate(enddate);
        System.out.println("endDate1------"+endDate1);
        // if (startDate1 == null)
        // startDate1 = new Date(Date.getCurrentDate()); // assume today
        //
        // if (endDate1 == null)
        // endDate1 = new Date(Date.getCurrentDate()); // asume today again
        //
        Timestamp tsStart = startDate1.timestampValue();
        Timestamp tsEnd = endDate1.timestampValue();
        //
        // long ndays = (tsEnd.getTime() - tsStart.getTime()) ;
        // System.out.println("NDAYSSSSSSSSSSSSSS"+ndays);
        //
        // int days = (int) ((ndays / (1000*60*60*24)) % 7);
        // System.out.println("days are -----------"+days);
        // return new Number(ndays);
       
//        OperationBinding binding=ADFUtils.findOperation("getDateDifference");
//        binding.getParamsMap().put("d1",dateStart);
//        binding.getParamsMap().put("d2",enddate);
//        binding.execute();

               
     
//        try{
//
//        cs=getDBTransaction().createCallableStatement("begin ? := DATE_DIFF(?,?); end;",0);
//
//        cs.registerOutParameter(1, Types.VARCHAR);
//
//        cs.setDate(2, d1);
//            
//        cs.setDate(3, d2);    
//
//        cs.executeUpdate();
//
//        return cs.getString(1);
//
//        }catch(SQLException e){
//
//        throw new JboException(e);
//
//        }
        try {
            Connection dbConnection = null;
            dbConnection = getConnection("jdbc/NileDBDS");
            String sql = "{ ? = call date_diff(?,?) }";
            CallableStatement statement = dbConnection.prepareCall(sql);
            statement.setDate(2,dateStart);
            statement.setDate(3,enddate);
            statement.registerOutParameter(1, Types.VARCHAR);
            
            
            statement.execute();   
            //this is the main line
            difference = statement.getString(1);
            System.out.println("difference is "+difference);
            
            int x=difference.indexOf("years");
            System.out.println("index of years is"+x);

           String subString1= difference.substring(0);
           System.out.println("subString1"+subString1);
            
            if(subString1.equals("0")) {
//              String monthIndex=difference.indexOf("month");
            }


        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (NamingException ne) {
            // TODO: Add catch code
            ne.printStackTrace();
        }

        contractTerm.setValue(difference);

    }

    public void setEndDate(RichInputDate endDate) {
        this.endDate = endDate;
    }

    public RichInputDate getEndDate() {
        return endDate;
    }

    public void setStartDate(RichInputDate startDate) {
        this.startDate = startDate;
    }

    public RichInputDate getStartDate() {
        return startDate;
    }
    

    public static oracle.jbo.domain.Date getJboDateFromSqlDate(java.sql.Date sqlDate) {
    oracle.jbo.domain.Date jboDate = null;
    if (sqlDate != null) {
    jboDate = new oracle.jbo.domain.Date(sqlDate);
    }
    return jboDate;
    }

    public void setContractTerm(RichInputText contractTerm) {
        this.contractTerm = contractTerm;
    }

    public RichInputText getContractTerm() {
        return contractTerm;
    }

    public void valueChange_startDate(ValueChangeEvent valueChangeEvent) {
        dateStart = ( java.sql.Date)startDate.getValue();
                   System.out.println("dateStart-----"+dateStart);
                   
                  startDate1 = getJboDateFromSqlDate(dateStart);
                  System.out.println("startDate1----------"+startDate1);
                  
        Date dateStartUtil=getUtilDateFromSqlDate(dateStart);   
                  
        java.util.Date minValForEndDate=addDays(dateStartUtil,1);
        java.sql.Date minValForEndDateUtil = new java.sql.Date(minValForEndDate.getTime());
        minValForEndDateJBO = new oracle.jbo.domain.Date(minValForEndDateUtil);
           System.out.println("minval"+minValForEndDateJBO);
//        minValForEndDate1=getJboDateFromSqlDate(minValForEndDate);
                  
    }

    public void setQuoteVar(RichInputText quoteVar) {
        this.quoteVar = quoteVar;
    }

    public RichInputText getQuoteVar() {
        return quoteVar;
    }

    public void setLobVar(RichSelectOneChoice lobVar) {
        this.lobVar = lobVar;
    }

    public RichSelectOneChoice getLobVar() {
        return lobVar;
    }

    public void setOfficeVar(RichSelectOneChoice officeVar) {
        this.officeVar = officeVar;
    }

    public RichSelectOneChoice getOfficeVar() {
        return officeVar;
    }

    public void setDescriptionVar(RichInputText descriptionVar) {
        this.descriptionVar = descriptionVar;
    }

    public RichInputText getDescriptionVar() {
        return descriptionVar;
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
        /**
         * this method is used for the validation of date component.
         */
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 300);
        return cal.getTime();

    }

    public Date getMinDate() {
        /**
         * this method is used for the validation of date component.
         */
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
    
    public Date getendMinDate() {
            /**
             * this method is used for the validation of date component.
             */
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
    
    public void closeSavePopup(ActionEvent actionEvent) {
         savePopup.cancel();
       }
    
    public void closeSubmitPopup(ActionEvent actionEvent) {
        submitPopup.cancel();
        
       }


    public void setSubmitPopup(RichPopup submitPopup) {
        this.submitPopup = submitPopup;
    }

    public RichPopup getSubmitPopup() {
        return submitPopup;
    }

    public void setSavePopup(RichPopup savePopup) {
        this.savePopup = savePopup;
    }

    public RichPopup getSavePopup() {
        return savePopup;
    }
    
    public static Date addDays(Date date, int days)
      {
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          cal.add(Calendar.DATE, days); //minus number would decrement the days
          return cal.getTime();
      }
    public static java.util.Date getUtilDateFromSqlDate(java.sql.Date sqlDate) {  
       return new java.util.Date(sqlDate.getTime());  
     }

    public void setClientVar(RichSelectOneChoice clientVar) {
        this.clientVar = clientVar;
    }

    public RichSelectOneChoice getClientVar() {
        return clientVar;
    }
}
