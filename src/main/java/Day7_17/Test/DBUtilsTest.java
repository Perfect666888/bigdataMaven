package Day7_17.Test;

import Day7_17.Utils.DBUtils;
import Day7_17.bean.student;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;

public class DBUtilsTest {
    public static void main(String[] args) {
        //键盘录入需要查询的名字
        //创建键盘录入对象
        Scanner sc = new Scanner(System.in);
        System.out.println("输入需要查询的名字");
        String getName = sc.nextLine();
        //释放资源
        sc.close();
        //创建连接
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtils.getConnection();
            //创建sql语句
            String sql = "select * from student where name = ?";

            //创建预处理对象
            preparedStatement = connection.prepareStatement(sql);

            //赋值
            preparedStatement.setObject(1, getName);

            //执行语句
            resultSet = preparedStatement.executeQuery();

            //新建student对象存储查询结果
            student student = null;

            //遍历结果
            while (resultSet.next()) {
                //获得结果
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String clazz = resultSet.getString("clazz");
                student=new student(id,name,age,gender,clazz);
                System.out.println("类本身的toString方法");
                System.out.println(student);
                //获得gson
                Gson gson = new Gson();
                String s = gson.toJson(student);
                System.out.println("转换为Gson的输出");
                System.out.println(s);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            DBUtils.close(connection,preparedStatement);
        }

    }
}
