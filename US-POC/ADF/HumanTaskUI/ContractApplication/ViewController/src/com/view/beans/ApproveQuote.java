package com.view.beans;

import com.view.utility.ADFUtils;

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

import java.util.Map;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ApproveQuote {
    private RichInputText commentVar;
    private RichPopup rejectPopuUpVar;
    String  directors;
    String direct[];


    public ApproveQuote() {
    }

    public String reject_Action() {
        // TODO: Add catch code "Put validation and pop up"
        if(commentVar.getValue()!=null) {
            OperationBinding rejectOP = ADFUtils.findOperation("REJECT");
                       rejectOP.execute();
                       FacesContext facesContext = FacesContext.getCurrentInstance();
                       org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
                           org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext,
                                                                                        ExtendedRenderKitService.class);
                       service.addScript(facesContext,
                                         "window.close();window.opener.location.href = window.opener.location.href;");
                       service.addScript(facesContext, "closeMe()");
                
            
        }
     
     else {
            
        /***
         * Show popup here
         * 
         */
            
        showPopup(rejectPopuUpVar, true);
            
            
            
            
        }
     
        return null;
       
    }

    public void setCommentVar(RichInputText commentVar) {
        this.commentVar = commentVar;
    }

    public RichInputText getCommentVar() {
        return commentVar;
    }

    public void setRejectPopuUpVar(RichPopup rejectPopuUpVar) {
        this.rejectPopuUpVar = rejectPopuUpVar;
    }

    public RichPopup getRejectPopuUpVar() {
        return rejectPopuUpVar;
    }

    public void closeRejectPopup(ActionEvent actionEvent) {
        rejectPopuUpVar.cancel();
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

    public String download_Action() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
   
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

    public void OnLoad() {
        
        try {


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
                  DCIteratorBinding iterator = bc.findIteratorBinding("ContractDocUploadView1Iterator");
                  ViewObject ConDocVO = iterator.getViewObject();
                  ConDocVO.setNamedWhereClauseParam("conid", quoteId);
                  ConDocVO.executeQuery();


                  

                }


            }
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        
       
    }

    public String approve_Action() {
        OperationBinding rejectOP = ADFUtils.findOperation("APPROVE");
                   rejectOP.execute();
                   FacesContext facesContext = FacesContext.getCurrentInstance();
                   org.apache.myfaces.trinidad.render.ExtendedRenderKitService service =
                       org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext,
                                                                                    ExtendedRenderKitService.class);
                   service.addScript(facesContext,
                                     "window.close();window.opener.location.href = window.opener.location.href;");
                   service.addScript(facesContext, "closeMe()");
        return null;
    }
}
