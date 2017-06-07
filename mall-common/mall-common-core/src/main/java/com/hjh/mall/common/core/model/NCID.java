package com.hjh.mall.common.core.model;

import java.io.Serializable;
import java.util.Date;

import com.hjh.mall.common.core.util.NCIDUtil;



public class NCID implements Serializable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    private String fullCID;
    
    private String areaCode;
    
    private String birthDateStr;
    
    private Date birthDate;
    
    private String inDateSN;
    
    private String genderMark;
    
    private String checkCode;
    
    private boolean validated;
    
    private boolean valid;
    
    public boolean isMale() {
        return isValid() ? 1 == (genderMark.charAt(0) - '0') % 2 : false;
    }
    
    public String getFullCID() {
        if (null == fullCID) {
            if (isValid()) {
                fullCID = NCIDUtil.toString(this);
            }
        }
        return fullCID;
    }
    
    public String getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        fullCID = null;
        this.areaCode = areaCode;
    }
    
    public String getBirthDateStr() {
        return birthDateStr;
    }
    
    public void setBirthDateStr(String birthDateStr) {
        fullCID = null;
        birthDate = NCIDUtil.parseBirthDateString(birthDateStr);
        this.birthDateStr = birthDateStr;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        fullCID = null;
        birthDateStr = NCIDUtil.formatBirthDateString(birthDate);
        this.birthDate = birthDate;
    }
    
    public String getInDateSN() {
        return inDateSN;
    }
    
    public void setInDateSN(String inDateSN) {
        fullCID = null;
        genderMark = NCIDUtil.parseGenderMarkFromInDateSN(inDateSN);
        this.inDateSN = inDateSN;
    }
    
    public String getGenderMark() {
        return genderMark;
    }
    
    public String getCheckCode() {
        return checkCode;
    }
    
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
    
    public boolean isValid() {
        if (!validated) {
            valid = NCIDUtil.validateNCID(this);
        }
        return valid;
    }
    
    @Override
    public String toString() {
        return "NCID [fullCID=" + fullCID + ", areaCode=" + areaCode + ", birthDateStr=" + birthDateStr
                + ", inDateSN=" + inDateSN + ", genderMark=" + genderMark + ", checkCode=" + checkCode + "]";
    }
    
}
