package generateData.bean;

/**
 * @Author：Allen
 * @Project：bigdata_demo
 * @Name：EmployeeBean
 * @Date：2024年01月19日 0019 21:27:36
 */
public class EmployeeBean {
    String userName; // 员工名称
    String sex; // 性别
    String idCard; // 员工身份证号码
    String employmentDate; // 入职时间
    String comcode; // 归属机构

    public EmployeeBean() {
    }

    public EmployeeBean(String userName, String sex, String idCard, String employmentDate, String comcode) {
        this.userName = userName;
        this.sex = sex;
        this.idCard = idCard;
        this.employmentDate = employmentDate;
        this.comcode = comcode;
    }

    @Override
    public String toString() {
        return "EmployeeBean{" +
                "userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", idCard='" + idCard + '\'' +
                ", employmentDate='" + employmentDate + '\'' +
                ", comcode='" + comcode + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }
}
