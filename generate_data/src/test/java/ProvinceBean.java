/**
 * @Author：Allen
 * @Project：bigdata_demo
 * @Name：ProvinceBean
 * @Date：2024年01月18日 0018 14:26:17
 */
public class ProvinceBean {
    String provincecode = "";
    String provincename = "";
    String citycode = "";
    String cityname = "";
    String averacode = "";
    String averaame = "";



    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode;
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getAveracode() {
        return averacode;
    }

    public void setAveracode(String averacode) {
        this.averacode = averacode;
    }

    public String getAveraame() {
        return averaame;
    }

    public void setAveraame(String averaame) {
        this.averaame = averaame;
    }


    public void clear() {
        provincecode = "";
        provincename = "";
        citycode = "";
        cityname = "";
        averacode = "";
        averaame = "";
    }


    public void exchange(ProvinceBean bean) {
        bean.provincecode = provincecode;
        bean.provincename = provincename;
        bean.citycode = citycode;
        bean.cityname = cityname;
        bean.averacode = averacode;
        bean.averaame = averaame;
    }


    @Override
    public String toString() {
        return provincecode + "\t" + provincename + "\t" + citycode + "\t" + cityname + "\t" + averacode + "\t" + averaame;
    }
}
