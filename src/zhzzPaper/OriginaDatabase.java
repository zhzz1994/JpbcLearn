package zhzzPaper;

import com.sun.corba.se.impl.protocol.FullServantCacheLocalCRDImpl;

import javax.print.DocFlavor;
import java.awt.*;
import java.sql.*;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.util.encoders.Hex;


public class OriginaDatabase {
    Connection connection;
    boolean isConnected = false;
    String tableName;

    public OriginaDatabase(String databaseName,String tablename)
    {
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名login
        String url = "jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false";
        System.out.println(url);
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "1094835165";

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,user,password);
            isConnected = true;
            tableName = tablename;
            System.out.println("数据库数据成功获取！！");
        }
        catch(ClassNotFoundException e)
        {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            //数据库连接失败异常处理
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public  void closeSQL()
    {
        if(isConnected)
        {
            try
            {
                connection.close();
                System.out.println("数据库关闭！！");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private List<Object> convertList(ResultSet rs) throws SQLException
    {
        List<Object> list = new ArrayList<Object>();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.next()) {
            List<Object> rowData = new ArrayList<Object>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.add(rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    public List<Object> getDataInOneRow(int RowID) throws Exception
    {
        Statement state = connection.createStatement();
        String sql = "SELECT * FROM " + tableName + " WHERE ID = " + RowID;
        ResultSet resultset = state.executeQuery(sql);
        List<Object> list = convertList(resultset);
        return list;
    }

    public List<Object> getDataInRow() throws Exception
    {
        Statement state = connection.createStatement();
        String sql = "SELECT * FROM " + tableName;
        ResultSet resultset = state.executeQuery(sql);
        List<Object> list = convertList(resultset);
        return list;
    }

    public static List<Object> dataReverse(List<Object> list) throws Exception
    {
        List<Object> Colunmlist = new ArrayList<Object>();
        if(list.isEmpty())
        {
            return list;
        } else {
            int colunmLength = 0;
            List<Object> zeroRow = (ArrayList<Object>) list.get(0);
            colunmLength = zeroRow.size();
            for(int i = 0; i<colunmLength ; i++ ){
                List<Object> colunm = new ArrayList<Object>();
                Colunmlist.add(colunm);
            }
            for(Object row :list)
            {
                List<Object> rowlist = (ArrayList<Object>) row;
                for(int i = 0; i<colunmLength ; i++ ) {
                    List<Object> colunm = (ArrayList<Object>) Colunmlist.get(i);
                    colunm.add(rowlist.get(i));
                    }
            }
        }
        return Colunmlist;
    }
}
