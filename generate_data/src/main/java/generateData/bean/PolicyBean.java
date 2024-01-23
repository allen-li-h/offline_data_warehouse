package generateData.bean;

import java.math.BigDecimal;

/**
 * @Author：Allen
 * @Project：bigdata_demo
 * @Name：PolicyBean
 * @Date：2024年01月20日 0020 13:37:42
 */
public class PolicyBean {
    String policyno; // 保单号
    String class_code; // 险类代码: U车险，P财产险，E意外险，H健康险，X信用保障保险
    String comcode; // 机构代码
    int itemid; // 标的信息
    int handler_code; // 业务员工号
    String insured_name; // 用户姓名
    String start_date; // 保单起保日期
    String end_date; // 保单终保时间
    BigDecimal premium; // 保费
    BigDecimal amount; // 保额


    public PolicyBean() {
    }

    public PolicyBean(String policyno, String class_code, String comcode, int itemid, int handler_code, String insured_name, String start_date, String end_date, BigDecimal premium, BigDecimal amount) {
        this.policyno = policyno;
        this.class_code = class_code;
        this.comcode = comcode;
        this.itemid = itemid;
        this.handler_code = handler_code;
        this.insured_name = insured_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.premium = premium;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PolicyBean{" +
                "policyno='" + policyno + '\'' +
                ", class_code='" + class_code + '\'' +
                ", comcode='" + comcode + '\'' +
                ", itemid=" + itemid +
                ", handler_code=" + handler_code +
                ", insured_name='" + insured_name + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", premium=" + premium +
                ", amount=" + amount +
                '}';
    }

    public String getPolicyno() {
        return policyno;
    }

    public void setPolicyno(String policyno) {
        this.policyno = policyno;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getHandler_code() {
        return handler_code;
    }

    public void setHandler_code(int handler_code) {
        this.handler_code = handler_code;
    }

    public String getInsured_name() {
        return insured_name;
    }

    public void setInsured_name(String insured_name) {
        this.insured_name = insured_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
