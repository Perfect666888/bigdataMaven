package Day7_17.Utils;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DBUtils {
    static String Driver;
    static String Url;
    static String Username;
    static String Password;

    static {
        Properties properties = new Properties();
        try {
            properties.load(DBUtils.class.getClassLoader().getResourceAsStream("DBUtils.properties"));
            //获得Driver,等值
            Driver = properties.getProperty("Driver");
            Url = properties.getProperty("Url");
            Username = properties.getProperty("Username");
            Password = properties.getProperty("Password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创建获得连接方法，返回connetion对象
    public static Connection getConnection() throws SQLException {
        //加载驱动
        try {
            Class.forName(Driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //创建连接
        Connection connection = null;

        connection = DriverManager.getConnection(Url, Username, Password);

        return connection;
    }

    public static ArrayList<ArrayList<String>> select(String sql) {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        //创建集合存储数据
        ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();

        try {
            //连接数据库
            connection = getConnection();
            //创建sql执行器
            statement = connection.createStatement();

            //执行sql
            resultSet = statement.executeQuery(sql);

            //获得有多少列
            int columnCount = resultSet.getMetaData().getColumnCount();

            //把数据保存到集合中
           while(resultSet.next()){
               //创建子集合
               ArrayList<String> values = new ArrayList<>();
               //把一行的每列数据，添加到集合中，一个字段为一个集合元素
               for (int i = 1; i <= columnCount; i++) {
                   //sql中索引从1开始，最大为传入的列表数
                   values.add(resultSet.getString(i));
               }
               //把子集合存入到集合中
               arrayLists.add(values);
           }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            close(connection, statement);
        }


        //返回集合
        return arrayLists;
    }

    public static void close(Connection connection, Statement statement) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
