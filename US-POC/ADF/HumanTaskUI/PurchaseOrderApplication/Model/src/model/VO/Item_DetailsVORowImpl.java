package model.VO;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun May 10 17:50:35 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class Item_DetailsVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Code,
        Description,
        NeedBy,
        Operating_unit,
        Preferred_Supplier,
        Project,
        Quantity,
        Rate,
        Task,
        Type,
        TypeString,
        DescString,
        ItemView1,
        TypeView1;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int CODE = AttributesEnum.Code.index();
    public static final int DESCRIPTION = AttributesEnum.Description.index();
    public static final int NEEDBY = AttributesEnum.NeedBy.index();
    public static final int OPERATING_UNIT = AttributesEnum.Operating_unit.index();
    public static final int PREFERRED_SUPPLIER = AttributesEnum.Preferred_Supplier.index();
    public static final int PROJECT = AttributesEnum.Project.index();
    public static final int QUANTITY = AttributesEnum.Quantity.index();
    public static final int RATE = AttributesEnum.Rate.index();
    public static final int TASK = AttributesEnum.Task.index();
    public static final int TYPE = AttributesEnum.Type.index();
    public static final int TYPESTRING = AttributesEnum.TypeString.index();
    public static final int DESCSTRING = AttributesEnum.DescString.index();
    public static final int ITEMVIEW1 = AttributesEnum.ItemView1.index();
    public static final int TYPEVIEW1 = AttributesEnum.TypeView1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public Item_DetailsVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Code.
     * @return the Code
     */
    public String getCode() {
        return (String) getAttributeInternal(CODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Code.
     * @param value value to set the  Code
     */
    public void setCode(String value) {
        setAttributeInternal(CODE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Description.
     * @return the Description
     */
    public String getDescription() {
        return (String) getAttributeInternal(DESCRIPTION);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Description.
     * @param value value to set the  Description
     */
    public void setDescription(String value) {
        setAttributeInternal(DESCRIPTION, value);
    }

    /**
     * Gets the attribute value for the calculated attribute NeedBy.
     * @return the NeedBy
     */
    public Date getNeedBy() {
        return (Date) getAttributeInternal(NEEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute NeedBy.
     * @param value value to set the  NeedBy
     */
    public void setNeedBy(Date value) {
        setAttributeInternal(NEEDBY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Operating_unit.
     * @return the Operating_unit
     */
    public String getOperating_unit() {
        return (String) getAttributeInternal(OPERATING_UNIT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Operating_unit.
     * @param value value to set the  Operating_unit
     */
    public void setOperating_unit(String value) {
        setAttributeInternal(OPERATING_UNIT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Preferred_Supplier.
     * @return the Preferred_Supplier
     */
    public String getPreferred_Supplier() {
        return (String) getAttributeInternal(PREFERRED_SUPPLIER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Preferred_Supplier.
     * @param value value to set the  Preferred_Supplier
     */
    public void setPreferred_Supplier(String value) {
        setAttributeInternal(PREFERRED_SUPPLIER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Project.
     * @return the Project
     */
    public String getProject() {
        return (String) getAttributeInternal(PROJECT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Project.
     * @param value value to set the  Project
     */
    public void setProject(String value) {
        setAttributeInternal(PROJECT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Quantity.
     * @return the Quantity
     */
    public Number getQuantity() {
        return (Number) getAttributeInternal(QUANTITY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Quantity.
     * @param value value to set the  Quantity
     */
    public void setQuantity(Number value) {
        setAttributeInternal(QUANTITY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Rate.
     * @return the Rate
     */
    public Number getRate() {
        return (Number) getAttributeInternal(RATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Rate.
     * @param value value to set the  Rate
     */
    public void setRate(Number value) {
        setAttributeInternal(RATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Task.
     * @return the Task
     */
    public String getTask() {
        return (String) getAttributeInternal(TASK);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Task.
     * @param value value to set the  Task
     */
    public void setTask(String value) {
        setAttributeInternal(TASK, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Type.
     * @return the Type
     */
    public String getType() {
        return (String) getAttributeInternal(TYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Type.
     * @param value value to set the  Type
     */
    public void setType(String value) {
        setAttributeInternal(TYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TypeString.
     * @return the TypeString
     */
    public String getTypeString() {

        if (getType() != null) {
            ViewObject itemViewObject = this.getApplicationModule().findViewObject("ItemView1");

            Row[] rows = itemViewObject.getFilteredRows("ItemId", getType());

            if (rows.length > 0) {
                setAttributeInternal(TYPESTRING, rows[0].getAttribute("ItemName"));
            }
        }

        return (String) getAttributeInternal(TYPESTRING);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TypeString.
     * @param value value to set the  TypeString
     */
    public void setTypeString(String value) {
        setAttributeInternal(TYPESTRING, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DescString.
     * @return the DescString
     */
    public String getDescString() {
        
        if (getDescription() != null) {
            ViewObject typeViewObject = this.getApplicationModule().findViewObject("TypeMasterViewObj1");
            
            

            Row[] rows = typeViewObject.getFilteredRows("TypeId", getDescription() );

            if (rows.length > 0) {
                setAttributeInternal(DESCSTRING, rows[0].getAttribute("TypeName"));
            }
        }
        
        
        
        return (String) getAttributeInternal(DESCSTRING);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DescString.
     * @param value value to set the  DescString
     */
    public void setDescString(String value) {
        setAttributeInternal(DESCSTRING, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> ItemView1.
     */
    public RowSet getItemView1() {
        return (RowSet) getAttributeInternal(ITEMVIEW1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> TypeView1.
     */
    public RowSet getTypeView1() {
        return (RowSet) getAttributeInternal(TYPEVIEW1);
    }
}
