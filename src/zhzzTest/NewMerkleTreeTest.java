package zhzzTest;

import cryptMethod.GeneralHash;
import zhzzPaper.NewMerkleTree;
import zhzzPaper.TreeNode;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static zhzzPaper.NewMerkleTree.sortNodes;
import static zhzzTest.TreeNodeTest.getRondomNodesList;

public class NewMerkleTreeTest {

    public static void sortNodesTest(){
        stdlib.Timer timer = new stdlib.Timer();
        timer.start(0);
        List<TreeNode> nodeList = getRondomNodesList(1000);
        System.out.println(timer.stop(0));
        timer.start(1);
        List<TreeNode> nodeListSorted = sortNodes(nodeList);
        System.out.println(timer.stop(1));
        timer.start(2);
        for(TreeNode tn:nodeListSorted){
            System.out.println(GeneralHash.hashToString(tn.hashHead));
        }
        System.out.println(timer.stop(2));
    }

    public static void nodeInsertTest(){
        List<TreeNode> nodeList = getRondomNodesList(10);

        NewMerkleTree nmt = new NewMerkleTree();
        for(TreeNode tn :nodeList){
            nmt.inseertNode(tn);
        }
        List<TreeNode> nodeListSorted = sortNodes(nodeList);
        for(TreeNode tn :nodeListSorted){
            System.out.println(GeneralHash.hashToString(tn.hashHead));
        }
        for(TreeNode tn :nmt.bondNodesList){
            System.out.println(GeneralHash.hashToString(tn.hashHead));
        }
        System.out.println(GeneralHash.hashToString(nmt.rootNode.hashHead));
    }



    public static void main(String[] args) {
        nodeInsertTest();
    }








}
