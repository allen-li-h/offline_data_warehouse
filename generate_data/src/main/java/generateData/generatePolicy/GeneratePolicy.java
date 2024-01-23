package generateData.generatePolicy;

import generateData.bean.PolicyBean;
import generateData.bean.VehicleBean;
import generateData.generateVehicle.GenerateVehicle;
import tools.DruidTools;
import tools.GenerateDataTool;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author：Allen
 * @Project：bigdata_demo
 * @Name：GeneratePolicy
 * @Date：2024年01月20日 0020 13:24:42
 */
public class GeneratePolicy {

    public static PolicyBean generatePolicy() {
        PolicyBean bean = new PolicyBean();
        StringBuilder stringBuilder = new StringBuilder("P");
        String classcode = GenerateDataTool.generateClasscode();
        if ("U".equals(classcode)) {
            VehicleBean vehicleBean = GenerateVehicle.generateVehicle();
            String start_data = GenerateDataTool.generateRandomData("yyyy-MM-dd", vehicleBean.getDebut_date());
            String end_date = (Integer.parseInt(start_data.substring(0 ,4)) + 1) + start_data.substring(4);
            BigDecimal premium = new BigDecimal(GenerateDataTool.random.nextDouble() * 0.5 * vehicleBean.getPurchase_price().intValue());
            BigDecimal amount = new BigDecimal(vehicleBean.getPurchase_price().intValue()/5000*50000);
            bean.setClass_code("U");
            bean.setItemid(vehicleBean.getId());
            bean.setInsured_name(vehicleBean.getCar_owner_name());
            bean.setStart_date(start_data);
            bean.setEnd_date(end_date);
            bean.setPremium(premium);
            bean.setAmount(amount);
        }
        stringBuilder.append(classcode);
        String comcode = GenerateDataTool.averaCodes[GenerateDataTool.random.nextInt(GenerateDataTool.averaCodes.length - 2)];
        stringBuilder.append(comcode);
        stringBuilder.append(bean.getStart_date().replaceAll("-", ""));
        stringBuilder.append(String.format("%04d", GenerateDataTool.random.nextInt(10000)));
        bean.setPolicyno(stringBuilder.toString());
        bean.setComcode(comcode);
        bean.setHandler_code(GenerateDataTool.random.nextInt(501));
        if ("U".equals(bean.getClass_code())) {
            insertPolicyInfo(bean);
        }
        return bean;
    }


    /**
     * 插入保单信息表
     * @param bean
     */
    public static void insertPolicyInfo(PolicyBean bean) {
        Connection connection = DruidTools.getConnection("policy");

        String sql = "insert into edw_c_policy(policyno,class_code,comcode,itemid,handler_code,insured_name,start_date,end_date,premium,amount) values(?,?,?,?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bean.getPolicyno());
            preparedStatement.setString(2, bean.getClass_code());
            preparedStatement.setString(3, bean.getComcode());
            preparedStatement.setInt(4, bean.getItemid());
            preparedStatement.setInt(5, bean.getHandler_code());
            preparedStatement.setString(6, bean.getInsured_name());
            preparedStatement.setString(7, bean.getStart_date());
            preparedStatement.setString(8, bean.getEnd_date());
            preparedStatement.setBigDecimal(9, bean.getPremium());
            preparedStatement.setBigDecimal(10, bean.getAmount());

            preparedStatement.executeUpdate();

            connection.commit();

            DruidTools.close(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        generatePolicy();
    }
}
