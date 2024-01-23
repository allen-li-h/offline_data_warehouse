package generateData.generateEmployee;

import generateData.bean.EmployeeBean;
import tools.DruidTools;
import tools.GenerateDataTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：Allen
 * @Project：bigdata_demo
 * @Name：GenerateEmployee
 * @Date：2024年01月19日 0019 19:20:26
 */
public class GenerateEmployee {
    public static List<EmployeeBean> employeeList = new ArrayList<EmployeeBean>();


    public static void main(String[] args) {
        for (int i = 0; i <= 500; i++) {
            String userName = GenerateDataTool.generateRandomChineseName();
            String idCard = GenerateDataTool.generateRandomChineseID();
            String sex = GenerateDataTool.generateSex();
            String comcode = GenerateDataTool.generateComcode();
            String date = GenerateDataTool.generateRandomData("yyyy-MM-dd", "2015-01-01");
            EmployeeBean employeeBean = new EmployeeBean(userName, sex, idCard, date, comcode);
            employeeList.add(employeeBean);
        }

        insertEmployeeInfo();
    }


    /**
     * 将员工信息插入到表中
     */
    public static void insertEmployeeInfo() {
        Connection connection = DruidTools.getConnection("dim");
        try {
            // 关闭事务自动提交
            connection.setAutoCommit(false);
            String sql = "insert into dim_db.edw_dim_employee(username,sex,idcard,employmentdate,comcode) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (EmployeeBean bean: employeeList) {
                preparedStatement.setString(1, bean.getUserName());
                preparedStatement.setString(2, bean.getSex());
                preparedStatement.setString(3, bean.getIdCard());
                preparedStatement.setString(4, bean.getEmploymentDate());
                preparedStatement.setString(5, bean.getComcode());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.commit();
            DruidTools.close(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }





}
