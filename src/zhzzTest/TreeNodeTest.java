package zhzzTest;

import cryptMethod.DataTools;
import stdlib.Timer;
import zhzzPaper.KeysManager;
import zhzzPaper.TreeNode;

import java.math.BigInteger;
import java.util.*;

import static cryptMethod.GeneralHash.booleanArrayToHash;
import static cryptMethod.GeneralHash.hashToBooleanArray;
import static cryptMethod.GeneralHash.hashToString;

public class TreeNodeTest {

    public static void accTset(){
        String msg = "呵呵呵呵呵呵";
        KeysManager km = new KeysManager();
        int [] list = {1,7,9,5};
        TreeNode tn = new TreeNode(msg,list,km);
        System.out.println(tn.keyWordsACC);
    }

    public static void keyWordHashGenTest()
    {
        String msg = "呵呵呵呵呵呵";
        byte [] hashvalue = TreeNode.keyWordHashGen(msg);
        String msghash = DataTools.byteArrayToHexStr(hashvalue);
        System.out.println(msghash);
    }

    public static TreeNode getRondomNode(KeysManager km)
    {
        Random random = new Random();
        String kw = String.valueOf(random.nextInt());
        int [] list = {1,7,9,5};
        TreeNode tn = new TreeNode(kw,list,km);
        return tn;
    }

    public static List<TreeNode> getRondomNodesList(int listSize)
    {
        KeysManager km = new KeysManager();
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        for(int i = 0;i<listSize;i++){
            TreeNode node = getRondomNode(km);
            nodeList.add(node);
        }
        return nodeList;
    }

    public static void hashHeadTest(){
        List<TreeNode> nodeList = getRondomNodesList(10);
        TreeNode bondtn = new TreeNode(nodeList.get(0), nodeList.get(1));
        System.out.println(hashToString(nodeList.get(0).hashHead));
        System.out.println(hashToString(nodeList.get(1).hashHead));
        System.out.println(hashToString(bondtn.hashHead));
        System.out.println(hashToBooleanArray(nodeList.get(0).hashHead));
        System.out.println(hashToBooleanArray(nodeList.get(1).hashHead));
        System.out.println(Arrays.toString(hashToBooleanArray(bondtn.hashHead)));
    }

    public static void bondNodeGenTest(){

        Timer timer = new Timer();
        System.out.println(timer.nowTime());
        timer.start(1);
        List<TreeNode> nodeList = getRondomNodesList(100);
        System.out.println(timer.stop(1));
        timer.start(2);
        for(int i = 1;i <100;i++) {
            for(int j = 2;j<100;j++){
            TreeNode bondtn = new TreeNode(nodeList.get(i), nodeList.get(j));
            hashToBooleanArray(nodeList.get(0).hashHead);
            hashToBooleanArray(nodeList.get(i).hashHead);
            hashToBooleanArray(bondtn.hashHead);
            BigInteger bigInteger= new java.math.BigInteger(nodeList.get(i).hashHead);
            System.out.println(bigInteger);
            }
        }
        System.out.println(timer.stop(2));
    }

    public static void main(String[] args) throws Exception
    {
        BitSet bs = new BitSet(110);
        for(int i = 0;i<100;i = i +2){
            bs.set(i);
            bs.clear(i+1);
        }
        bs.set(150);
        System.out.println(hashToString(bs.toByteArray()));
        hashHeadTest();
    }



}
