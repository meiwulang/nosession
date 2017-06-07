package com.hjh.mall.common.core.filed;

public enum IdKind {
    
    NCID("0", "身份证"),
    
    FOREIGN_PASSPORT("1", "外国护照"),
    BUSINESS_LICENSE("2", "营业执照"),
    OFFICER_ID("3", "军官证 "),
    SOCIAL_NO("4", "社会保障号"),
    ADMIN_ORG("7", "行政机关"),
    PRO_COR("9", "法人社团"),
    PRO_NONCOR("A", "非法人社团"),
    ARMY("B", "军队"),
    ARMED_POLICE("C", "武警"),
    SUBORDINATE_BODY("D", "下属机构"),
    FOUNDATION("F", "基金会"),
    TECHNICAL_BUREAU("G", "技术监督局"),
    REENTRY_PERMIT("H", "回乡证"),
    PEOPLES_CARD("C", "解放军文职干部证"),
    OFFICERS_CARD("D", "警官证"),
    PEOPLES_LIBERATION("E", "解放军士兵证"),
    RESIDENCE_BOOKLET("F", "户口簿"),
    PASSCARD("G", "港澳居民来往内地通行证"),
    TAIWAN_PASSCARD("H", "台湾居民来往大陆通行证"),
    FOREIGNER_PERMANENT_CERTIFICATE("I", "外国人永久居留证"),
    THEIR_PASSPORT("J", "本国护照"),
    ARMED_CIVILIAN_CADRES("K", "武警文职干部证 "),
    ARMED_POLICE_SOLDIERS("L", "武警士兵证"),
    SOCIAL_ORG("M", "社会团体"),
    INTERIM_IDENTITY_CARD("N", "临时身份证"),
    NATIONAL_ORGANIZATION_CODE("P", "全国组织机构代码"),
    OVERSEAS_CUSTOMER_NUMBER("Q", "海外客户编号"),
    FOREIGN_IDENTITY_CARD("R", "境外身份证"),
    RESIDENT_IDENTITY_CARD("S", "港澳台居民身份证"),
    REGISTRATION_CERTIFICATE("T", "事业法人登记证书"),
    OTHER_VALID_CERTIFICATES("X", "其他有效证件")
    ;
    
    private final String val;
    
    private final String description;
    
    private String toString;
    
    private IdKind(String val, String description) {
        this.val = val;
        this.description = description;
    }
    
    public String getVal() {
        return val;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        if (null == toString) {
            toString = new StringBuilder().append("IdKind[").append(val).append(':').append(description)
                    .append(']').toString();
        }
        return toString;
    }
    
}
