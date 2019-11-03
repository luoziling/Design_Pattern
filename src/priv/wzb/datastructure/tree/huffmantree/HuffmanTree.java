package priv.wzb.datastructure.tree.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/2 20:52
 * @description:
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int arr[] = {13,7,8,3,29,6,1};
        Node huffmanTree = createHuffmanTree(arr);

        // 测试
        preOrder(huffmanTree);// 67 29 38 15 7 8 23 10 4 1 3 6 13

    }

    public static void preOrder(Node root){
        if (root != null){
            root.preOrder();
        }else {
            System.out.println("树是空树");
        }
    }

    // 创建huffmanTree
    public static Node createHuffmanTree(int[] arr){
        // 遍历arr数组，构建为Node
        // 放入ArrayList然后进行排序
        List<Node> nodes = new ArrayList<>();
        for(int value : arr){
            nodes.add(new Node(value));
        }

        // 排序
        Collections.sort(nodes);
        System.out.println(nodes);

        while (nodes.size()>1){
            // 取出根节点权值最小的两颗二叉树
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);

            // 构建一颗新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;

            // 从ArrayList中删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);

            // 添加新的二叉树
            nodes.add(parent);
            Collections.sort(nodes);
            System.out.println("第x步" + nodes);
        }
        // 返回huffmanTree的root节点
        return nodes.get(0);

//        // 取出根节点权值最小的两颗二叉树
//        Node leftNode = nodes.get(0);
//        Node rightNode = nodes.get(1);
//
//        // 构建一颗新的二叉树
//        Node parent = new Node(leftNode.value + rightNode.value);
//        parent.left = leftNode;
//        parent.right = rightNode;
//
//        // 从ArrayList中删除处理过的二叉树
//        nodes.remove(leftNode);
//        nodes.remove(rightNode);
//
//        // 添加新的二叉树
//        nodes.add(parent);
//        Collections.sort(nodes);
//        System.out.println("第一步" + nodes);
    }
}

// 创建节点类
class Node implements Comparable<Node>{
    int value; // 节点权值
    char c;
    Node left; // 指向左子节点
    Node right; // 指向右子节点

    public Node(int value) {
        this.value = value;
    }

    // 前序遍历
    public void preOrder(){
        if (this==null){
            return;
        }
        System.out.println(this);
        if (this.left!=null){
            this.left.preOrder();
        }
        if (this.right!=null){
            this.right.preOrder();
        }

    }

    @Override
    public int compareTo(Node o) {
        return this.value-o.value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
