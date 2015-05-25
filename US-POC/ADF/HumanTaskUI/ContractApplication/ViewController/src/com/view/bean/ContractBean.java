package com.view.bean;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.sql.*;
import java.sql.SQLException;

import javax.faces.event.ValueChangeEvent;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import oracle.adf.view.rich.component.rich.input.RichInputFile;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class ContractBean {
    private RichInputFile uploadfile;
    private String fileName;
    private String path;
    
    public ContractBean() {
    }

    public void setUploadfile(RichInputFile uploadfile) {
        this.uploadfile = uploadfile;
    }

    public RichInputFile getUploadfile() {
        return uploadfile;
    }

    public String uploadfile_Action() {
       System.out.println("in upload method");
        try {

            File pdfFile = new File(path);
            byte[] pdfData = new byte[(int) pdfFile.length()];
            DataInputStream dis = new DataInputStream(new FileInputStream(pdfFile));
            dis.readFully(pdfData);
            dis.close();      
            Connection dbConnection = null;
            dbConnection = getConnection("jdbc/NileDBDS");
            PreparedStatement ps =
            dbConnection.prepareStatement("INSERT INTO CONTRACT_DOCUMENT (" + "CONTRACT_ID, " + "CONTRACT_DOC, " +
                                              "FILE_NAME" + ") VALUES (?,?,?)");
            ps.setString(1, "testpdf");
            ps.setBytes(2, pdfData);
            ps.setString(3, fileName);
            ps.executeUpdate();
            ps.close();
            dbConnection.close();
            System.out.println("Data Inserted Successfully.");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch(NamingException e) {
            // TODO: Add catch code
           
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }
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

    public String download_Action() {
        try
        {
        Connection conn=getConnection("jdbc/NileDBDS");
        PreparedStatement pstmt = conn.prepareStatement("select CONTRACT_DOC from CONTRACT_DOCUMENT where CONTRACT_ID=? ");
        pstmt.setString(1,"testpdf");
        ResultSet rs = pstmt.executeQuery();
                       rs.next();
            PreparedStatement pstmt1 = conn.prepareStatement("select FILE_NAME from CONTRACT_DOCUMENT where CONTRACT_ID=?");
            pstmt1.setString(1,"testpdf");
            ResultSet rs1 = pstmt1.executeQuery();
                       rs1.next(); 
                       String fname=rs1.getString(1);  
                       String pth="D:\\"+fname; 
                       System.out.println("name of file ----  "+fname);
                       System.out.println(pth); 
                       InputStream f = rs.getBinaryStream("CONTRACT_DOC");
                       FileOutputStream f1 = new FileOutputStream(pth);
                       int i=0;
                       while((i=f.read())!=-1)
                       {
                        f1.write(i);
                       }
                       f1.flush();
                           f1.close();
                           f.close();
                           rs.close();
                           pstmt.close();
                           conn.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }
}
