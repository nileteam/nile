package com.view.beans;

import com.view.utility.ADFUtils;
import com.view.utility.JSFUtils;

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

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

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

public class InitiateQuote {
    private RichSelectOneChoice directorVar;

    List<SelectItem> customList;
    private RichSelectOneChoice directSoc;
    String directors;
    String[] direct;
    private RichInputFile uploadfileVar;
    private String fileName;
    private String path;
    private RichInputText quoteID;
    private RichInputText quoteIdVar;

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
    
    public void onload() {
        DCBindingContainer bindings =(DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlHierBinding treeBinding =(JUCtrlHierBinding)bindings.findCtrlBinding("task");
         System.out.println("TREE BINDING IS ======"+treeBinding);
        JUCtrlHierNodeBinding rootNode = null;
        rootNode = treeBinding.getRootNodeBinding();
        System.out.println("rootNode is "+rootNode);
        
        System.out.println("rootNode.getChildren"+rootNode.getChildren());
        System.out.println("rootNode.getChildren.size"+rootNode.getChildren().size());
        
        if(rootNode.getChildren()!=null) {
            for(int i=0; i< rootNode.getChildren().size();i++) {
            System.out.println("bingo"+(i+1));

                JUCtrlHierNodeBinding initiateQuote = (JUCtrlHierNodeBinding)rootNode.getChildren().get(0);
                System.out.println("task----"+initiateQuote);


                String[] attributeNames = initiateQuote.getAttributeNames();
                
                for(int z=0; i< attributeNames.length;i++) {
                    
                    System.out.println("Attribute name is "+attributeNames[z]);
                }
          
             JUCtrlHierNodeBinding child= (JUCtrlHierNodeBinding)initiateQuote.getChildren().get(0);
             System.out.println("child is -------"+child);

                String[] names = child.getAttributeNames();
                
                for(int z=0;z<names.length;z++){
                    
                    System.out.println("attribute names in child is"+names[z]);
                    
                }
                
              JUCtrlHierNodeBinding childToChild   =(JUCtrlHierNodeBinding)child.getChildren().get(0);
              System.out.println("Childt to child"+ childToChild);
                
                
            

                String[] attributeNames_2 = childToChild.getAttributeNames();
                
                for(int z=0;z<attributeNames_2.length;z++) {
                    
                    System.out.println(" Attributre name to child to child are "+attributeNames_2[z]);
                }
                             
                     directors=(String)childToChild.getAttribute("Directors");
                    System.out.println("directors are --------?>>>>>>>"+directors);
                
                    direct= directors.split(",");
                    
                for(String dir:direct) {
                    System.out.println(dir);
                    
                }

            }
                
            
        }
        
    
    
    }
    
    public void setUploadfileVar(RichInputFile uploadfileVar) {
           this.uploadfileVar = uploadfileVar;
       }

       public RichInputFile getUploadfileVar() {
           return uploadfileVar;
       }

       public String uploadfile_Action() {
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
                int f=0;
                    for(int ii=0;ii<ch.length;ii++){  
                        if(ch[ii]=='.'){
                            f=1;
                        }
                        if(f==0){
                            sbf.append(ch[ii]);
                        }else{             
                        sbe.append(ch[ii]);
                        }
                       }  
                System.out.println("NAME OF THE FILE  "+sbf);
                System.out.println("EXTENSION OF THE FILE  "+sbe);




//               String fileCompare = str[0];
//               System.out.println(str[0]+"--------str[0]");
//                for(i = 0; i < rws.length; i++)
//                {
//                  Row row = rws[i];
//                  String file = row.getAttribute("FileName").toString();
//                    if(file.contains(fileCompare))
//                    {
//                    count++;    
//                        }
//                    
//                    }
//                  fileName = fileName.concat("V_"+rws.length);
//                  fileName = fileCompare.concat("_V"+count).concat(str[1]);
//          System.out.println("in upload method");
//          
//           try {
//            
//            System.out.println("quote------------------"+quote);
//
//            File pdfFile = new File(path);
//               byte[] pdfData = new byte[(int) pdfFile.length()];
//               DataInputStream dis = new DataInputStream(new FileInputStream(pdfFile));
//               dis.readFully(pdfData);
//               dis.close();      
//               Connection dbConnection = null;
//               dbConnection = getConnection("jdbc/NileDBDS");
//               PreparedStatement ps =
//               dbConnection.prepareStatement("INSERT INTO CONTRACT_DOC_UPLOAD (" + "CONTRACT_ID, " + "CONTRACT_DOC, " +
//                                                 "FILE_NAME" + ") VALUES (?,?,?)");
//               ps.setString(1, quote);
//               ps.setBytes(2, pdfData);
//               ps.setString(3, fileName);
//               ps.executeUpdate();
//               ps.close();
//               dbConnection.close();
//               File f = new File(path);
//               f.delete();
//               System.out.println("Data Inserted Successfully.");
//           } catch (SQLException sqle) {
//               // TODO: Add catch code
//               sqle.printStackTrace();
//           } catch(NamingException e) {
//               // TODO: Add catch code
//              
//           } catch (IOException ioe) {
//               // TODO: Add catch code
//               ioe.printStackTrace();
//           }
//           
//                  logoView.setWhereClause(null);
//                  logoView.executeQuery();
           return null;
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
           Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.149:1522:orcl12c","c##niledb","welcome1");
            PreparedStatement pstat = con.prepareStatement("select CONTRACT_DOC,FILE_NAME from CONTRACT_DOC_UPLOAD where CONTRACT_ID = ?");
            pstat.setString(1,"testpdf");
            ResultSet rs = pstat.executeQuery();
            rs.next();
            String fileName = rs.getString("FILE_NAME");
            System.out.println(fileName);
            InputStream f = rs.getBinaryStream("CONTRACT_DOC");
            FileOutputStream f1 = new FileOutputStream("D:\\image\\"+fileName);
            int i=0;
            while((i=f.read())!=-1)
                f1.write(i);
        f1.close();
        pstat.close();
        rs.close();
            con.close();
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
           return null;
       }

       public String initiate_Quote_Save() {
           // TODO: Add catch code "Put validation and pop up"
           OperationBinding SaveOP = ADFUtils.findOperation("update");
                      SaveOP.execute();

                      FacesContext facesContext = FacesContext.getCurrentInstance();
                      org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
                          org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext,
                                                                                       ExtendedRenderKitService.class);
                      service.addScript(facesContext,
                                        "window.close();window.opener.location.href = window.opener.location.href;");
                      service.addScript(facesContext, "closeMe()");
           return null;
       }


    public void setQuoteID(RichInputText quoteID) {
        this.quoteID = quoteID;
    }

    public RichInputText getQuoteID() {
        return quoteID;
    }

    public void setQuoteIdVar(RichInputText quoteIdVar) {
        this.quoteIdVar = quoteIdVar;
    }

    public RichInputText getQuoteIdVar() {
        return quoteIdVar;
    }

    public void testDownloadList(FacesContext facesContext, OutputStream outputStream)  throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
         Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.149:1522:orcl12c","c##niledb","welcome1");
         PreparedStatement pstat = con.prepareStatement("select CONTRACT_DOC,FILE_NAME from CONTRACT_DOCUMENT where CONTRACT_ID = ?");
         pstat.setString(1,"testpdf");
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
}
