package zhzzPaper;

import cryptMethod.GeneralHash;
import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cryptMethod.GeneralHash.booleanArrayToHash;
import static cryptMethod.GeneralHash.hashToBooleanArray;
import static cryptMethod.GeneralHash.listTobytes;


public class TreeNode implements Comparable{
    public String keyWord;
    public byte [] keyWordorBondHash;
    public byte [] hashHead;   //第一、二字节byte即前16位表示HashTail长度即位数
    public Element keyWordsACC;
    public boolean isTreeLeafNode;
    public TreeNode leftSon;
    public TreeNode rightSon;
    public TreeNode parent;
    public int [] indexList;

    public TreeNode(String keyword , int [] indexlist , KeysManager km)
    {
        keyWord = keyword;
        keyWordorBondHash = keyWordHashGen(keyword);
        isTreeLeafNode = true;
        hashHead = leafNodeSetHashHead(keyWordorBondHash,256);
        indexList = indexlist;
    }

    public TreeNode(TreeNode leftson , TreeNode rightson)
    {
        leftSon = leftson;
        rightSon = rightson;
        rightSon.parent = this;
        leftSon.parent = this;
        keyWordorBondHash = hashConjunction(leftSon.keyWordorBondHash,rightSon.keyWordorBondHash);
        isTreeLeafNode = false;
    }

    public void setKeyWordsACC(KeysManager km){
        keyWordsACC = keyWordsACCGen(indexList,km);
    }

    public static Element keyWordsACCGen(int [] indexList, KeysManager km)
    {
        Element [] indexElementList = {} ;
        Element s = km.accKey;
        Element acce = km.pairing.getZr().newOneElement();
        if(indexList.length>0)
        {
            for(int rowID : indexList)
            {
                Element Element_rowID = km.pairing.getZr().newRandomElement().set(rowID);
                Element adds = Element_rowID.add(s);
                acce = acce.mul(adds);
            }
        }
        Element accValue = km.generatorValue.powZn(acce);
        return accValue;
    }

    public static byte[] keyWordHashGen(String keyword)
    {
        byte [] keywordhash = GeneralHash.Hash(GeneralHash.HashMode.SHA256, keyword.getBytes());

        return keywordhash;
    }

    public static byte[] hashConjunction(byte[] hashleft,byte[] hashright)
    {
        byte[] bond = new byte[hashleft.length + hashright.length];
        for(int i = 0;i<hashleft.length;i++){
            bond[i] = hashleft[i];
        }
        for(int i = 0;i<hashright.length;i++){
            bond[i+ hashleft.length] = hashright[i];
        }
        byte[] hashconjunction = GeneralHash.Hash(GeneralHash.HashMode.SHA256, bond);
        return hashconjunction;
    }

    public Map<Integer, byte[]> gethashhead(){
        Map<Integer, byte[]> hashhead = hashheadGen(leftSon.hashHead,rightSon.hashHead);
        hashHead = hashhead.get(2);
        return hashheadGen(leftSon.hashHead,rightSon.hashHead);
    }

    public static Map<Integer, byte[]> hashheadGen(byte[] hashheadleft, byte[] hashheadright)
    {
        byte[] hashheadleftBoolean = hashToBooleanArray(hashheadleft,2);
        byte[] hashheadrightBoolean = hashToBooleanArray(hashheadright,2);
        List<Byte> hashheadList = new ArrayList<Byte>();
        int hashheadBitIndex = 0;
        while ((hashheadleftBoolean[hashheadBitIndex] == hashheadrightBoolean[hashheadBitIndex])){
            hashheadList.add(hashheadleftBoolean[hashheadBitIndex]);
            hashheadBitIndex++;
            if(hashheadBitIndex == hashheadleftBoolean.length){
                break;
            }
        }
        List<Byte> hashhead0 = hashheadList;
        List<Byte> hashhead1 = hashheadList;
        hashhead0.add((byte)0);
        hashhead1.add((byte)1);
        byte[] hashhead0Array = listTobytes(hashhead0);
        hashhead0Array = booleanArrayToHash(hashhead0Array);
        hashhead0Array = leafNodeSetHashHead(hashhead0Array, hashheadBitIndex + 1);
        byte[] hashhead1Array = listTobytes(hashhead1);
        hashhead1Array = booleanArrayToHash(hashhead1Array);
        hashhead1Array = leafNodeSetHashHead(hashhead1Array, hashheadBitIndex + 1);
        Map<Integer,byte[]> hashhead= new HashMap<>();
        byte[] hashheadArray = listTobytes(hashheadList);
        hashheadArray = booleanArrayToHash(hashheadArray);
        hashheadArray = leafNodeSetHashHead(hashheadArray, hashheadBitIndex);
        hashhead.put(0,hashhead0Array);
        hashhead.put(1,hashhead1Array);
        hashhead.put(2,hashheadArray);
        return hashhead;
    }

    public static byte[] leafNodeSetHashHead(byte[] keyWordHash , int hashlen)
    {
        if (keyWordHash == null || keyWordHash.length < 0)
            return null;
        byte[] hashHead = new byte[keyWordHash.length + 2];
        int hashHead0 = (int) hashlen/256;
        int hashHead1 = (int) hashlen%256;
        hashHead[0] = (byte) hashHead0;
        hashHead[1] = (byte) hashHead1;
        for(int i = 0;i<keyWordHash.length;i++){
            hashHead[i + 2] = keyWordHash[i];
        }
        return hashHead;
    }

    public BigInteger getBigintegerKey(){
        BigInteger bigInteger = new java.math.BigInteger(hashHead);
        return bigInteger;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
