package zhzzTest;

import cryptMethod.SymmetricBlockEnc;
import cryptMethod.SymmetricStreamEnc;
import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

import cryptMethod.GeneralHash;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.encoders.Hex;

import java.security.*;
import java.sql.*;


public class Testjpbc {

    private Element s, r, P, Ppub, Su, Qu, V, T1, T2;
    private Field G1, Zr;
    private Pairing pairing;

    public  void testG1e_p_G2e(String []args){
        TypeACurveGenerator pg = new TypeACurveGenerator(160, 512);
        PairingParameters typeAParams = pg.generate();
        Pairing pairing = PairingFactory.getPairing(typeAParams);
        Element Z_p = pairing.getZr().newRandomElement();
        Element G_1 = pairing.getG1().newRandomElement();
        Element G_2 = pairing.getG2().newRandomElement();
        Element G_T = pairing.getGT().newRandomElement();
        Element Z_1 = pairing.getZr().newRandomElement().set(5).getImmutable();
        Element Z_2 = pairing.getZr().newRandomElement().set(3).getImmutable();
        Element G_p_G = pairing.pairing(G_1, G_2);
        Element Z_m_Z = Z_1.mul(Z_2);
        Element G_p_G_e_ZmZ = G_p_G.powZn(Z_m_Z);
        Element G_1_e_ZmZ = G_1.powZn(Z_1);
        Element G_2_e_ZmZ = G_2.powZn(Z_2);
        Element G1e_p_G2e = pairing.pairing(G_1_e_ZmZ, G_2_e_ZmZ);
        byte[] bytes_G_1 = G_1.toBytes();
        Element G_Te = pairing.getGT().newRandomElement();
        int bytesRead = G_Te.setFromBytes(bytes_G_1);
    }

    public  void testEnandDe(){
        TypeACurveGenerator pg = new TypeACurveGenerator(160, 512);
        PairingParameters typeAParams = pg.generate();
        Pairing pairing = PairingFactory.getPairing(typeAParams);
        System.out.println("-------------------密钥提取阶段----------------------");
        Zr = pairing.getZr();
        s =  Zr.newRandomElement().getImmutable();
        P = pairing.getG1().newRandomElement().getImmutable();
        Ppub = P.mulZn(s);
        System.out.println("P=" + P);
        System.out.println("s=" + s);
        System.out.println("Ppub=" + Ppub);
        Qu = pairing.getG1().newElement().setFromHash("IDu".getBytes(), 0, 3)
                .getImmutable();
        Su = Qu.mulZn(s).getImmutable();
        System.out.println("Qu=" + Qu);
        System.out.println("Su=" + Su);
        System.out.println("-------------------加密阶段----------------------");
        r = Zr.newRandomElement().getImmutable();
        V = P.mulZn(r);
        T1 = pairing.pairing(Ppub, Qu).getImmutable();// 计算e（Ppub,Qu）
        T1 = T1.powZn(r).getImmutable();
        System.out.println("r=" + r);
        System.out.println("V=" + V);
        System.out.println("T1=e（Ppub,Qu）^r=" + T1);
        System.out.println("-------------------解密阶段----------------------");
        T2 = pairing.pairing(V, Su).getImmutable();
        System.out.println("e(V,Su)=" + T2);
        int byt = V.getLengthInBytes();// 求V的字节长度，假设消息长度为128字节
        System.out.println("文本长度" + (byt + 128));
    }


    public static void mainss(String []args){
//        Testjpbc mytest = new Testjpbc();
//        mytest.testEnandDe();
//        String str = "adsknfkjeknnrernjfdfvnddkf";
//        byte[] byte256 = GeneralHash.Hash(GeneralHash.HashMode.SHA256, str.getBytes());
//        String sha256 = new String(Hex.encode(byte256));
//        System.out.println(sha256);

        System.out.println("-------列出加密服务提供者-----");
        Provider[] pro=Security.getProviders();
        for(Provider p:pro){
            System.out.println("Provider:"+p.getName()+" - version:"+p.getVersion());
            System.out.println(p.getInfo());
        }
        System.out.println("");
        System.out.println("-------列出系统支持的消息摘要算法：");
        for(String s:Security.getAlgorithms("MessageDigest")){
            System.out.println(s);
        }
        System.out.println("-------列出系统支持的生成公钥和私钥对的算法：");
        for(String s:Security.getAlgorithms("KeyPairGenerator")){
            System.out.println(s); }

        SymmetricBlockEnc.Test_AES_String();

    }


    public static void main(String[] args)
        {
            //声明Connection对象
            Connection con;
            //驱动程序名
            String driver = "com.mysql.jdbc.Driver";
            //URL指向要访问的数据库名login
            String url = "jdbc:mysql://localhost:3306/zhzz?useSSL=false";
            //MySQL配置时的用户名
            String user = "root";
            //MySQL配置时的密码
            String password = "1094835165";
            //遍历查询结果集
            try
            {
                //加载驱动程序
                Class.forName(driver);
                //1.getConnection()方法，连接MySQL数据库！！
                con = DriverManager.getConnection(url,user,password);
                if(!con.isClosed())
                    System.out.println("Succeeded connecting to the Database!");
                //2.创建statement类对象，用来执行SQL语句！！
                Statement statement = con.createStatement();
                //要执行的SQL语句
                String sql = "select * from login_message";    //从建立的login数据库的login——message表单读取数据
                //3.ResultSet类，用来存放获取的结果集！！
                ResultSet rs = statement.executeQuery(sql);
                System.out.println("-----------------");
                System.out.println("执行结果如下所示:");
                System.out.println("-----------------");
                System.out.println(" 姓名" + "\t" + " 密码");
                System.out.println("-----------------");
                String name = null;
                String login_password = null;
                while(rs.next())
                {
                    //获取stuname这列数据
                    name = rs.getString("name");
                    //获取stuid这列数据
                    login_password = rs.getString("password");
                    //首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
                    //然后使用GB2312字符集解码指定的字节数组。
                    name = new String(name.getBytes("ISO-8859-1"),"gb2312");
                    //输出结果
                    System.out.println(name + "\t" + login_password);
                }
                rs.close();
                con.close();
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
            finally
            {
                System.out.println("数据库数据成功获取！！");
            }


    }
}

