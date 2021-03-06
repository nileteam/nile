package model;

import model.common.ContractAM;

import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;
import java.sql.CallableStatement;
import java.sql.SQLException;import java.sql.Types;

import java.sql.Date;

import oracle.jbo.JboException;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jun 01 12:11:00 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ContractAMImpl extends ApplicationModuleImpl implements ContractAM {
    /**
     * This is the default constructor (do not remove).
     */
    public ContractAMImpl() {
    }

    /**
     * Container's getter for ContractDocUploadView1.
     * @return ContractDocUploadView1
     */
    public ViewObjectImpl getContractDocUploadView1() {
        return (ViewObjectImpl) findViewObject("ContractDocUploadView1");
    }

    /**
     * Container's getter for socVo1.
     * @return socVo1
     */
    public socVoImpl getsocVo1() {
        return (socVoImpl) findViewObject("socVo1");
    }
    
    public String getDateDifference(Date d1,Date d2) {
        
        CallableStatement cs=null;
        try{

        cs=getDBTransaction().createCallableStatement("begin ? := DATE_DIFF(?,?); end;",0);

        cs.registerOutParameter(1, Types.VARCHAR);

        cs.setDate(2, d1);
            
        cs.setDate(3, d2);    

        cs.executeUpdate();

        return cs.getString(1);

        }catch(SQLException e){

        throw new JboException(e);

        }
        
        
    }

    /**
     * Container's getter for quoteVO1.
     * @return quoteVO1
     */
    public ViewObjectImpl getquoteVO1() {
        return (ViewObjectImpl) findViewObject("quoteVO1");
    }

    /**
     * Container's getter for contractVO1.
     * @return contractVO1
     */
    public ViewObjectImpl getcontractVO1() {
        return (ViewObjectImpl) findViewObject("contractVO1");
    }
}

