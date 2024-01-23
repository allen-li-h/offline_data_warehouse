import org.apache.commons.beanutils.BeanUtils;
import tools.DruidTools;
import tools.GenerateDataTool;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Author：Allen
 * @Project：bigdata_demo
 * @Name：Test
 * @Date：2024年01月18日 0018 14:01:43
 */
public class Test {
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        BufferedReader reader  = GenerateDataTool.getInputStream("C:\\Users\\Administrator\\Desktop\\location.txt");
        ProvinceBean nowbean = new ProvinceBean();
        ArrayList<ProvinceBean> provinceBeans = new ArrayList<>();


        String line = "";

        while ((line = reader.readLine()) != null) {
            String[] split = line.split("\\s+");

            if (split[0].endsWith("0000")) {
                if (nowbean.provincecode != "") {
                    if (nowbean.averacode == "") {
                        System.out.println(nowbean);

                        if (nowbean.averacode == "") {
                            nowbean.averacode = nowbean.provincecode;
                            nowbean.averaame = nowbean.provincename;
                        }
                        ProvinceBean beannew = new ProvinceBean();
                        BeanUtils.copyProperties(beannew, nowbean);
                        provinceBeans.add(beannew);
                    } else {
                        nowbean.clear();
                    }

                }

                nowbean.provincecode = split[0];
                nowbean.provincename = split[1];
            } else if (split[0].endsWith("00")) {
                nowbean.citycode = "";
                nowbean.cityname = "";

                nowbean.citycode = split[0];
                nowbean.cityname = split[1];
            } else {
                nowbean.averacode = "";
                nowbean.averaame = "";


                if (nowbean.citycode == "") {
                    nowbean.citycode = nowbean.provincecode;
                    nowbean.cityname = nowbean.provincename;
                }
                nowbean.averacode = split[0];
                nowbean.averaame = split[1];
                System.out.println(nowbean);

                if (nowbean.averacode == "") {
                    nowbean.averacode = nowbean.provincecode;
                    nowbean.averaame = nowbean.provincename;
                }
                ProvinceBean beannew = new ProvinceBean();
                BeanUtils.copyProperties(beannew, nowbean);
                provinceBeans.add(beannew);
            }

        }

        if (nowbean.averacode == "") {
            System.out.println(nowbean);

            if (nowbean.averacode == "") {
                nowbean.averacode = nowbean.provincecode;
                nowbean.averaame = nowbean.provincename;
            }
            ProvinceBean beannew = new ProvinceBean();
            BeanUtils.copyProperties(beannew, nowbean);
            provinceBeans.add(beannew);
        }

        reader.close();

        insertIntoTable(provinceBeans);



//        for(ProvinceBean bean: provinceBeans) {
//            System.out.println(bean);
//        }
    }




    public static int insertIntoTable(ArrayList<ProvinceBean> provinceBeans) {
        Connection connection = DruidTools.getConnection("dim");
        try {
            // 关闭事务自动提交
            connection.setAutoCommit(false);
            String sql = "insert into edw_dim_company(comcode2,comname2,comcode3,comname3,comcode4,comname4) values (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (ProvinceBean bean: provinceBeans) {
                preparedStatement.setString(1, bean.provincecode);
                preparedStatement.setString(2, bean.provincename);
                preparedStatement.setString(3, bean.citycode);
                preparedStatement.setString(4, bean.cityname);
                preparedStatement.setString(5, bean.averacode);
                preparedStatement.setString(6, bean.averaame);

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

            // 提交事务
            connection.commit();

            DruidTools.close(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
