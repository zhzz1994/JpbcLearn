package zhzzPaper;

import java.math.BigInteger;
import java.util.*;
import java.util.List;

public class NewMerkleTree {

    public TreeNode rootNode;
    public List<TreeNode> bondNodesList;

    public NewMerkleTree(){
        bondNodesList = new ArrayList<TreeNode>();
    }

    public void inseertNode(TreeNode node) {
        if (rootNode == null) {
            node.hashHead = new byte[3];
            node.hashHead[0] = 0;
            node.hashHead[1] = 0;
            node.hashHead[2] = 0;
            rootNode = node;
        } else {
            TreeNode parentnode = rootNode;
            while (parentnode.isTreeLeafNode == false) {
                if (node.getBigintegerKey().compareTo(parentnode.getBigintegerKey()) < 0) {
                    if (node.getBigintegerKey().compareTo(parentnode.leftSon.getBigintegerKey()) < 0) {
                        parentnode = parentnode.leftSon;
                    } else {
                        TreeNode bondnode = new TreeNode(parentnode.leftSon, node);
                        node.hashHead = bondnode.gethashhead().get(1);
                        parentnode.leftSon.hashHead = bondnode.gethashhead().get(0);
                        if(parentnode.equals(rootNode)){
                            rootNode = bondnode;
                        }
                        bondNodesList.add(bondnode);
                        break;
                    }
                } else {
                    if (node.getBigintegerKey().compareTo(parentnode.rightSon.getBigintegerKey()) > 0) {
                        parentnode = parentnode.rightSon;
                    } else {
                        TreeNode bondnode = new TreeNode(node, parentnode.rightSon);
                        node.hashHead = bondnode.gethashhead().get(0);
                        parentnode.rightSon.hashHead = bondnode.gethashhead().get(1);
                        if(parentnode.equals(rootNode)){
                            rootNode = bondnode;
                        }
                        bondNodesList.add(bondnode);
                        break;
                    }
                }
            }
            if (parentnode.isTreeLeafNode) {
                if (node.getBigintegerKey().compareTo(parentnode.getBigintegerKey()) < 0) {
                    TreeNode bondnode = new TreeNode(node, parentnode);
                    node.hashHead = bondnode.gethashhead().get(0);
                    parentnode.hashHead = bondnode.gethashhead().get(1);
                    bondNodesList.add(bondnode);
                    if(parentnode.equals(rootNode)){
                        rootNode = bondnode;
                    }
                } else {
                    TreeNode bondnode = new TreeNode(parentnode, node);
                    node.hashHead = bondnode.gethashhead().get(1);
                    parentnode.hashHead = bondnode.gethashhead().get(0);
                    bondNodesList.add(bondnode);
                    if(parentnode.equals(rootNode)){
                        rootNode = bondnode;
                    }
                }
            }
        }
    }



    //对叶子结点排序，即比较每位hash值，0在前，1在后。之后依次方法比较下一位，直至完成。
    public static List<TreeNode> sortNodes(List<TreeNode> nodeList){
        Collections.sort(nodeList,new Comparator<TreeNode>(){
            public int compare(TreeNode tn1,TreeNode tn2){
                return tn1.getBigintegerKey().compareTo(tn2.getBigintegerKey());
            }
        });
        return  nodeList;
    }
}
