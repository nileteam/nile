package com.view.beans;

import java.math.BigInteger;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

public class EditableBeanDC {
    public EditableBeanDC() {
        super();
    }
    
    public void OnLoad() {
        
        DCIteratorBinding HeaderIteratorBindings = ADFUtils.findIterator("HeaderIterator");
        ViewObject vo = HeaderIteratorBindings.getViewObject();
        //Row Header = vo.getCurrentRow();
        RowSetIterator rit = vo.createRowSetIterator(null);
        while (rit.hasNext()) {
            Row row = rit.next();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                               row.getAttribute("Requester"));
            //            System.out.println("4444" + row.getAttribute("Total_value"));
            //            tVal = (Number) row.getAttribute("Total_value");
        }

        DCIteratorBinding ItemIteratorBindings = ADFUtils.findIterator("Item_DetailsIterator");
        ViewObject vo1 = ItemIteratorBindings.getViewObject();
        //Row Header = vo.getCurrentRow();
        RowSetIterator rit1 = vo1.createRowSetIterator(null);

        if (!rit1.hasNext()) {
            System.out.println("into onload of new bean");
            OperationBinding createInsertOP = ADFUtils.findOperation("CreateInsert");
            createInsertOP.execute();
            System.out.println("into onload#####");
        } else {


            ViewObjectImpl itemsDetailVO =
                (ViewObjectImpl) ADFUtils.findIterator("Item_DetailsVO1Iterator").getViewObject();

            boolean firstFlag = true;

            while (rit1.hasNext()) {
                Row row = rit1.next();


//                codename = (String) row.getAttribute("Code");
//                System.out.println("Code from payload is ---->" + codename);
//
//
//                rate = ((BigInteger) row.getAttribute("Rate")).intValue();


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

                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                                   row.getAttribute("Code"));
                //            System.out.println("4444" + row.getAttribute("Total_value"));
                //            tVal = (Number) row.getAttribute("Total_value");
            }
        }


        /*DCIteratorBinding HeaderIteratorBindings = ADFUtils.findIterator("HeaderIterator");
        ViewObject vo = HeaderIteratorBindings.getViewObject();
        Row Header = vo.getCurrentRow();
        RowSetIterator rit = vo.createRowSetIterator(null);
        while (rit.hasNext()) {
            Row row = rit.next();
            row.getAttribute("Total_value");
            System.out.println("4444"+ row.getAttribute("Total_value"));
            tVal=(Number) row.getAttribute("Total_value");
        }

        DCIteratorBinding ItemDetailsIteratorBindings = ADFUtils.findIterator("Item_DetailsIterator");

        ViewObject vo1 = ItemDetailsIteratorBindings .getViewObject();

        DCIteratorBinding ItemDetailsVOIteratorBindings = ADFUtils.findIterator("Item_DetailsVO1Iterator3");

        ViewObject vo2 = ItemDetailsVOIteratorBindings.getViewObject();

        if ( tVal != null) {
            System.out.println("Total Value is" + Header.getAttribute("Total_value"));
            Row[] allRowsInRange = vo1.getAllRowsInRange();
            for (int i = 0; i < allRowsInRange.length; i++) {
            Row itemRow = allRowsInRange[i];
            itemRow.getAttribute("Type");
            itemRow.getAttribute("Code");
            itemRow.getAttribute("Description");
            System.out.println("value is"+ itemRow.getAttribute("Type"));
             }
        } else {*/

        //}
    }
}
