package com.view.beans;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.binding.DCParameter;

import oracle.adf.share.logging.ADFLogger;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.ControlBinding;

import oracle.binding.OperationBinding;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.el.ValueExpression;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;

import org.apache.myfaces.trinidad.context.RequestContext;


/**
 * A series of convenience functions for dealing with ADF Bindings.
 * Note: Updated for JDeveloper 11
 *
 * @author Duncan Mills
 * @author Steve Muench
 *
 * $Id: ADFUtils.java 2513 2007-09-20 20:39:13Z ralsmith $.
 */
public class ADFUtils {

    public static final ADFLogger LOGGER =
        ADFLogger.createADFLogger(ADFUtils.class);

    /**
     * Get application module for an application module data control by name.
     * @param name application module data control name
     * @return ApplicationModule
     */
    public static ApplicationModule getApplicationModuleForDataControl(String name) {
        return (ApplicationModule)JSFUtils.resolveExpression("#{data." + name +
                                                             ".dataProvider}");
    }

    /**
     * A convenience method for getting the value of a bound attribute in the
     * current page context programatically.
     * @param attributeName of the bound value in the pageDef
     * @return value of the attribute
     */
    public static Object getBoundAttributeValue(String attributeName) {
        return findControlBinding(attributeName).getInputValue();
    }

    /**
     * A convenience method for setting the value of a bound attribute in the
     * context of the current page.
     * @param attributeName of the bound value in the pageDef
     * @param value to set
     */
    public static void setBoundAttributeValue(String attributeName,
                                              Object value) {
        findControlBinding(attributeName).setInputValue(value);
    }

    /**
     * Returns the evaluated value of a pageDef parameter.
     * @param pageDefName reference to the page definition file of the page with the parameter
     * @param parameterName name of the pagedef parameter
     * @return evaluated value of the parameter as a String
     */
    public static Object getPageDefParameterValue(String pageDefName,
                                                  String parameterName) {
        BindingContainer bindings = findBindingContainer(pageDefName);
        DCParameter param =
            ((DCBindingContainer)bindings).findParameter(parameterName);
        return param.getValue();
    }

    /**
     * Convenience method to find a DCControlBinding as an AttributeBinding
     * to get able to then call getInputValue() or setInputValue() on it.
     * @param bindingContainer binding container
     * @param attributeName name of the attribute binding.
     * @return the control value binding with the name passed in.
     *
     */
    public static AttributeBinding findControlBinding(BindingContainer bindingContainer,
                                                      String attributeName) {
        if (attributeName != null) {
            if (bindingContainer != null) {
                ControlBinding ctrlBinding =
                    bindingContainer.getControlBinding(attributeName);
                if (ctrlBinding instanceof AttributeBinding) {
                    return (AttributeBinding)ctrlBinding;
                }
            }
        }
        return null;
    }

    /**
     * Convenience method to find a DCControlBinding as a JUCtrlValueBinding
     * to get able to then call getInputValue() or setInputValue() on it.
     * @param attributeName name of the attribute binding.
     * @return the control value binding with the name passed in.
     *
     */
    public static AttributeBinding findControlBinding(String attributeName) {
        return findControlBinding(getBindingContainer(), attributeName);
    }

    /**
     * Return the current page's binding container.
     * @return the current page's binding container
     */
    public static BindingContainer getBindingContainer() {
        return (BindingContainer)JSFUtils.resolveExpression("#{bindings}");
    }

    /**
     * Return the Binding Container as a DCBindingContainer.
     * @return current binding container as a DCBindingContainer
     */
    public static DCBindingContainer getDCBindingContainer() {
        return (DCBindingContainer)getBindingContainer();
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName name of the value attribute to use
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsForIterator(String iteratorName,
                                                          String valueAttrName,
                                                          String displayAttrName) {
        return selectItemsForIterator(findIterator(iteratorName),
                                      valueAttrName, displayAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with description.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName name of the value attribute to use
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute to use for description
     * @return ADF Faces SelectItem for an iterator binding with description
     */
    public static List<SelectItem> selectItemsForIterator(String iteratorName,
                                                          String valueAttrName,
                                                          String displayAttrName,
                                                          String descriptionAttrName) {
        return selectItemsForIterator(findIterator(iteratorName),
                                      valueAttrName, displayAttrName,
                                      descriptionAttrName);
    }

    /**
     * Get List of attribute values for an iterator.
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName value attribute to use
     * @return List of attribute values for an iterator
     */
    public static List attributeListForIterator(String iteratorName,
                                                String valueAttrName) {
        return attributeListForIterator(findIterator(iteratorName),
                                        valueAttrName);
    }

    /**
     * Get List of Key objects for rows in an iterator.
     * @param iteratorName iterabot binding name
     * @return List of Key objects for rows
     */
    public static List<Key> keyListForIterator(String iteratorName) {
        return keyListForIterator(findIterator(iteratorName));
    }

    /**
     * Get List of Key objects for rows in an iterator.
     * @param iter iterator binding
     * @return List of Key objects for rows
     */
    public static List<Key> keyListForIterator(DCIteratorBinding iter) {
        List<Key> attributeList = new ArrayList<Key>();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getKey());
        }
        return attributeList;
    }

    /**
     * Get List of Key objects for rows in an iterator using key attribute.
     * @param iteratorName iterator binding name
     * @param keyAttrName name of key attribute to use
     * @return List of Key objects for rows
     */
    public static List<Key> keyAttrListForIterator(String iteratorName,
                                                   String keyAttrName) {
        return keyAttrListForIterator(findIterator(iteratorName), keyAttrName);
    }

    /**
     * Get List of Key objects for rows in an iterator using key attribute.
     *
     * @param iter iterator binding
     * @param keyAttrName name of key attribute to use
     * @return List of Key objects for rows
     */
    public static List<Key> keyAttrListForIterator(DCIteratorBinding iter,
                                                   String keyAttrName) {
        List<Key> attributeList = new ArrayList<Key>();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(new Key(new Object[] { r.getAttribute(keyAttrName) }));
        }
        return attributeList;
    }

    /**
     * Get a List of attribute values for an iterator.
     *
     * @param iter iterator binding
     * @param valueAttrName name of value attribute to use
     * @return List of attribute values
     */
    public static List attributeListForIterator(DCIteratorBinding iter,
                                                String valueAttrName) {
        List attributeList = new ArrayList();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getAttribute(valueAttrName));
        }
        return attributeList;
    }

    /**
     * Find an iterator binding in the current binding container by name.
     *
     * @param name iterator binding name
     * @return iterator binding
     */
    public static DCIteratorBinding findIterator(String name) {
        DCIteratorBinding iter =
            getDCBindingContainer().findIteratorBinding(name);
        if (iter == null) {
            throw new RuntimeException("Iterator '" + name + "' not found");
        }
        return iter;
    }

    /**
     * @param bindingContainer
     * @param iterator
     * @return
     */
    public static DCIteratorBinding findIterator(String bindingContainer,
                                                 String iterator) {
        DCBindingContainer bindings =
            (DCBindingContainer)JSFUtils.resolveExpression("#{" +
                                                           bindingContainer +
                                                           "}");
        if (bindings == null) {
            throw new RuntimeException("Binding container '" +
                                       bindingContainer + "' not found");
        }
        DCIteratorBinding iter = bindings.findIteratorBinding(iterator);
        if (iter == null) {
            throw new RuntimeException("Iterator '" + iterator +
                                       "' not found");
        }
        return iter;
    }

    /**
     * @param name
     * @return
     */
    public static JUCtrlValueBinding findCtrlBinding(String name) {
        JUCtrlValueBinding rowBinding =
            (JUCtrlValueBinding)getDCBindingContainer().findCtrlBinding(name);
        if (rowBinding == null) {
            throw new RuntimeException("CtrlBinding " + name + "' not found");
        }
        return rowBinding;
    }

    /**
     * Find an operation binding in the current binding container by name.
     *
     * @param name operation binding name
     * @return operation binding
     */
    public static OperationBinding findOperation(String name) {
        OperationBinding op =
            getDCBindingContainer().getOperationBinding(name);
        if (op == null) {
            throw new RuntimeException("Operation '" + name + "' not found");
        }
        return op;
    }

    /**
     * Find an operation binding in the current binding container by name.
     *
     * @param bindingContianer binding container name
     * @param opName operation binding name
     * @return operation binding
     */
    public static OperationBinding findOperation(String bindingContianer,
                                                 String opName) {
        DCBindingContainer bindings =
            (DCBindingContainer)JSFUtils.resolveExpression("#{" +
                                                           bindingContianer +
                                                           "}");
        if (bindings == null) {
            throw new RuntimeException("Binding container '" +
                                       bindingContianer + "' not found");
        }
        OperationBinding op = bindings.getOperationBinding(opName);
        if (op == null) {
            throw new RuntimeException("Operation '" + opName + "' not found");
        }
        return op;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with description.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param valueAttrName name of value attribute to use for key
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with description
     */
    public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter,
                                                          String valueAttrName,
                                                          String displayAttrName,
                                                          String descriptionAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getAttribute(valueAttrName),
                                           (String)r.getAttribute(displayAttrName),
                                           (String)r.getAttribute(descriptionAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param valueAttrName name of value attribute to use for key
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter,
                                                          String valueAttrName,
                                                          String displayAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getAttribute(valueAttrName),
                                           (String)r.getAttribute(displayAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName,
                                                               String displayAttrName) {
        return selectItemsByKeyForIterator(findIterator(iteratorName),
                                           displayAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with discription.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with discription
     */
    public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName,
                                                               String displayAttrName,
                                                               String descriptionAttrName) {
        return selectItemsByKeyForIterator(findIterator(iteratorName),
                                           displayAttrName,
                                           descriptionAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with discription.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with discription
     */
    public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter,
                                                               String displayAttrName,
                                                               String descriptionAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getKey(),
                                           (String)r.getAttribute(displayAttrName),
                                           (String)r.getAttribute(descriptionAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return List of ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter,
                                                               String displayAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getKey(),
                                           (String)r.getAttribute(displayAttrName)));
        }
        return selectItems;
    }

    /**
     * Find the BindingContainer for a page definition by name.
     *
     * Typically used to refer eagerly to page definition parameters. It is
     * not best practice to reference or set bindings in binding containers
     * that are not the one for the current page.
     *
     * @param pageDefName name of the page defintion XML file to use
     * @return BindingContainer ref for the named definition
     */
    private static BindingContainer findBindingContainer(String pageDefName) {
        BindingContext bctx = getDCBindingContainer().getBindingContext();
        BindingContainer foundContainer =
            bctx.findBindingContainer(pageDefName);
        return foundContainer;
    }

    /**
     * @param opList
     */
    public static void printOperationBindingExceptions(List opList) {
        if (opList != null && !opList.isEmpty()) {
            for (Object error : opList) {
                LOGGER.severe(error.toString());
            }
        }
    }
    
    // Addded bu aurko on May 22, 2013
    public static Object invokeEL(String el, Class[] paramTypes,
        Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory =
        facesContext.getApplication().getExpressionFactory();
        MethodExpression exp =
        expressionFactory.createMethodExpression(elContext, el,
        Object.class, paramTypes);

        return exp.invoke(elContext, params);
        }
    // Addded bu aurko on May 22, 2013    
        public static Object evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory =
        facesContext.getApplication().getExpressionFactory();
        ValueExpression exp =
        expressionFactory.createValueExpression(elContext, el,
        Object.class);

        return exp.getValue(elContext);
        }

	// Get Parameter Value from Pageflow Scope
	public static Object getParameterFrmPageFlowScope(String key)
	{
		return getAdfFacesContext().getPageFlowScope().get(key);
	}

	// Remove Parameter Value from Pageflow Scope
	public static void removeParameterFrmPageFlowScope(String key)
	{
		getAdfFacesContext().getPageFlowScope().remove(key);
	}

	// Add Parameter Value to Pageflow Scope
	public static void putParameterinPageFlowScope(String key, Object value)
	{
		getAdfFacesContext().getPageFlowScope().put(key, value);
	}


	// Get AdfFacesContext Object
	public static AdfFacesContext getAdfFacesContext()
	{
		return AdfFacesContext.getCurrentInstance();
	}

	// Get RequestContext Object
	public static RequestContext getRequestContext()
	{
		return RequestContext.getCurrentInstance();
	}
        
    /**
    * Sets a value into an EL object. 
    * 
    * @param el EL object to assign a value
    * @param val Value to assign
    */
   public static void setEL(String el, Object val) {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      ELContext elContext = facesContext.getELContext();
      ExpressionFactory expressionFactory =
      facesContext.getApplication().getExpressionFactory();
      ValueExpression exp =
      expressionFactory.createValueExpression(elContext, el, Object.class);
      exp.setValue(elContext, val);
  }
    

    public static BindingContext getBindingContext()
    {
      return BindingContext.getCurrent();
    }

    public static OperationBinding getOperationBinding(String op)
    {
      return getBindingContext().getCurrentBindingsEntry().getOperationBinding(op);
    }

    
    public static Object invokeOperationBinding(HashMap<String, ?> hmap,
                                                String op)
    {  
      if (hmap != null)
      {
        Map m = getOperationBinding(op).getParamsMap();
        for (String key: hmap.keySet())
        {
          Object value = hmap.get(key);
          m.put(key, value);
        }
      }
      Object o = null;
      o = getOperationBinding(op).execute();
      if(getOperationBinding(op).getErrors().size()>0)
      {
       // throw new OssStopExException("");
      }
      return o;
    }
    
    //Refresh the UI component
    
    public static void refreshComponent(UIComponent componentBindId){
            AdfFacesContext.getCurrentInstance().addPartialTarget(componentBindId);
        }
    
    //Refresh page or view Id
    
    public static void refreshViewId() {
            FacesContext context = FacesContext.getCurrentInstance();
            String viewId = context.getViewRoot().getViewId();
            ViewHandler viewhandler = context.getApplication().getViewHandler();
            UIViewRoot page = viewhandler.createView(context, viewId);
            context.setViewRoot(page);
        }
    
    
  

}