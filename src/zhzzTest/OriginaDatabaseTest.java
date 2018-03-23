package zhzzTest;

import com.sun.javafx.image.impl.BaseIntToByteConverter;
import cryptMethod.DataTools;
import org.bouncycastle.util.encoders.Hex;
import zhzzPaper.OriginaDatabase;

import java.util.List;

public class OriginaDatabaseTest {

    public static void connectSQLTest()
    {
        OriginaDatabase db = new OriginaDatabase("zhzz","city1");
        db.closeSQL();
    }

    public static void getDataInOneRowTest() throws Exception
    {
        OriginaDatabase db = new OriginaDatabase("zhzz","city1");
        List<Object> list = db.getDataInOneRow(1);
        System.out.println(list);
        db.closeSQL();
    }

    public static void getDataInRowTest() throws Exception
    {
        OriginaDatabase db = new OriginaDatabase("zhzz","doubanbook4");
        List<Object> list = db.getDataInRow();
        for(Object row : list){
            System.out.print(row);
            System.out.println();
        }
        db.closeSQL();
    }

    public static void dataReverseTest() throws Exception
    {
        OriginaDatabase db = new OriginaDatabase("zhzz","doubanbook4");
        List<Object> list = db.getDataInRow();
        List<Object> listcolunm = OriginaDatabase.dataReverse(list);
        for(Object row : listcolunm){
            System.out.print(row);
            System.out.println();
        }
        db.closeSQL();
    }

    public static void main(String[] args) throws Exception
    {
        getDataInRowTest();
        dataReverseTest();
//        String key = "6206c34e2186e752c74e6df32ab8fa5b";
//        byte [] str = Hex.decode(key);
//        byte [] strs = Hex.encode(str);
//
//        String  sstr = new String(str);
//        String  sstrs = new String(strs);
//        System.out.println(sstr);
//        System.out.println(sstrs);

    }
}
