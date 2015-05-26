package com.view.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

public class ContractBean {
    private RichInputText bingo;
    List<SelectItem> customList;
    private RichSelectOneChoice directSoc;
   String directors;
    String[] direct;

    public void setCustomList(List<SelectItem> customList) {
        this.customList = customList;
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

    public ContractBean() {
    }


  

    public void setDirectSoc(RichSelectOneChoice directSoc) {
        this.directSoc = directSoc;
    }

    public RichSelectOneChoice getDirectSoc() {
        return directSoc;
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
}
