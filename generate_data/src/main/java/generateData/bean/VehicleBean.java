package generateData.bean;

import java.math.BigDecimal;

/**
 * @Author：Allen
 * @Project：bigdata_demo
 * @Name：VehicleBean
 * @Date：2024年01月20日 0020 11:26:03
 * 车辆信息
 */
public class VehicleBean {
    private int id; // id
    private String frameno; // 车架号
    private String licensseno; // 车牌号
    private String vehicle_brand; // 车辆品牌
    private BigDecimal purchase_price; // 新车购置价
    private String car_owner_name; // 车主姓名
    private String car_owner_sex; //车主性别
    private String car_owner_idcard; // 车主身份证号码
    private String debut_date; // 车辆初登日期


    public VehicleBean() {
    }

    public VehicleBean(String frameno, String licensseno, String vehicle_brand, BigDecimal purchase_price, String car_owner_name, String car_owner_sex, String car_owner_idcard, String debut_date) {
        this.frameno = frameno;
        this.licensseno = licensseno;
        this.vehicle_brand = vehicle_brand;
        this.purchase_price = purchase_price;
        this.car_owner_name = car_owner_name;
        this.car_owner_sex = car_owner_sex;
        this.car_owner_idcard = car_owner_idcard;
        this.debut_date = debut_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrameno() {
        return frameno;
    }

    public void setFrameno(String frameno) {
        this.frameno = frameno;
    }

    public String getLicensseno() {
        return licensseno;
    }

    public void setLicensseno(String licensseno) {
        this.licensseno = licensseno;
    }

    public String getVehicle_brand() {
        return vehicle_brand;
    }

    public void setVehicle_brand(String vehicle_brand) {
        this.vehicle_brand = vehicle_brand;
    }

    public BigDecimal getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(BigDecimal purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getCar_owner_name() {
        return car_owner_name;
    }

    public void setCar_owner_name(String car_owner_name) {
        this.car_owner_name = car_owner_name;
    }

    public String getCar_owner_sex() {
        return car_owner_sex;
    }

    public void setCar_owner_sex(String car_owner_sex) {
        this.car_owner_sex = car_owner_sex;
    }

    public String getCar_owner_idcard() {
        return car_owner_idcard;
    }

    public void setCar_owner_idcard(String car_owner_idcard) {
        this.car_owner_idcard = car_owner_idcard;
    }

    public String getDebut_date() {
        return debut_date;
    }

    public void setDebut_date(String debut_date) {
        this.debut_date = debut_date;
    }


    @Override
    public String toString() {
        return "VehicleBean{" +
                "id='" + id + '\'' +
                ", frameno='" + frameno + '\'' +
                ", licensseno='" + licensseno + '\'' +
                ", vehicle_brand='" + vehicle_brand + '\'' +
                ", purchase_price=" + purchase_price +
                ", car_owner_name='" + car_owner_name + '\'' +
                ", car_owner_sex='" + car_owner_sex + '\'' +
                ", car_owner_idcard='" + car_owner_idcard + '\'' +
                ", debut_date='" + debut_date + '\'' +
                '}';
    }
}
