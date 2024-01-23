package generateData.generateVehicle;

import generateData.bean.VehicleBean;
import tools.DruidTools;
import tools.GenerateDataTool;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author：Allen
 * @Project：bigdata_demo
 * @Name：GenerateVehicle
 * @Date：2024年01月20日 0020 11:32:05
 */
public class GenerateVehicle {



    public static VehicleBean generateVehicle() {
        VehicleBean bean = null;
        String vinno = "";
        /**
         * 当生成的数据在数据库中不存在时结束
         */
        while (true) {
            // 生成车牌号
            vinno = GenerateDataTool.generateRandomVIN();
            if (getVinnoInfo(vinno) == 0) {
                break;
            }
        }


        String licenseno = GenerateDataTool.generateRandomLicensePlate();
        String chineseCarBrandInfo = GenerateDataTool.getRandomChineseCarBrand();
        String chineseCarBrand = chineseCarBrandInfo.split("-")[0];
        BigDecimal purchase_price = new BigDecimal(Integer.parseInt(chineseCarBrandInfo.split("-")[1]));
        String car_owner_name = GenerateDataTool.generateRandomChineseName();
        String car_owner_sex = GenerateDataTool.generateSex();
        String car_owner_idcard = GenerateDataTool.generateRandomChineseID();
        String debut_date = GenerateDataTool.generateRandomData("yyyy-MM-dd", "2021-01-01");

        bean = new VehicleBean(vinno, licenseno, chineseCarBrand, purchase_price, car_owner_name, car_owner_sex, car_owner_idcard, debut_date);

        insertVehicleInfo(bean);
        int id = getIdByVinno(bean.getFrameno());
        bean.setId(id);
        return bean;
    }


    /**
     * 获取指定车牌的数据是否已经存在
     * @param vinno
     * @return
     */
    public static int getVinnoInfo(String vinno) {
        Connection connection = DruidTools.getConnection("dim");
        int totalRow = 0;
        try {
            String sql = "select frameno from edw_dim_vehicle where frameno = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, vinno);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                totalRow++;
            }

            DruidTools.close(connection, statement, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRow;
    }



    /**
     * 获取指定车牌的数据是否已经存在
     * @param vinno
     * @return
     */
    public static int getIdByVinno(String vinno) {
        Connection connection = DruidTools.getConnection("dim");
        int id = 0;
        try {
            String sql = "select id from edw_dim_vehicle where frameno = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, vinno);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            DruidTools.close(connection, statement, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public static void insertVehicleInfo(VehicleBean bean) {
        Connection connection = DruidTools.getConnection("dim");
        try {
            connection.setAutoCommit(false);
            String sql = "insert into edw_dim_vehicle(frameno, licenseno, vehicle_brand, purchase_price, car_owner_name, car_owner_sex, car_owner_idcard, debut_date) values (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, bean.getFrameno());
            statement.setString(2, bean.getLicensseno());
            statement.setString(3, bean.getVehicle_brand());
            statement.setBigDecimal(4, bean.getPurchase_price());
            statement.setString(5, bean.getCar_owner_name());
            statement.setString(6, bean.getCar_owner_sex());
            statement.setString(7, bean.getCar_owner_idcard());
            statement.setString(8, bean.getDebut_date());

            statement.executeUpdate();
            connection.commit();

            DruidTools.close(connection, statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
