package priv.wzb.leet_code.list.copy_list_with_random_pointer_138;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2019/11/13 21:01
 * @description:
 */
public class Solution1 {
    public Node copyRandomList(Node head) {
        if (head == null){
            return null;
        }
        Node help = head;
        // 难点在于random指针
        // 用map保存random指针然后建立list去list中寻找
        Node copyNode = new Node(0,null,null);
        Map<Node,Integer> oldNodeMap = new HashMap<>();
        List<Node> nodeList = new LinkedList<>();
        int index= 0;
        // 第一遍遍历将原来链表的节点按顺序存入oldNodeMap
        // 并且按照原先的值创建新节点
        while (help!=null){
            // 记录旧节点对应下标
            oldNodeMap.put(help,index);
            // 创建新节点
            nodeList.add(new Node(help.val,null,null));
            index++;
            help = help.next;
        }

//        nodeList.add(0);
        help = head;
        index = 0;
        // 再次遍历连接next和random
        while (help!=null){
            if (help.next!=null){
                nodeList.get(index).next = nodeList.get(index+1);
            }
//            nodeList.get(index).next = nodeList.get(index+1);

            // 当random不指空
            if (help.random!=null){
                int id = oldNodeMap.get(help.random);
                nodeList.get(index).random = nodeList.get(id);
            }

            help = help.next;
            index++;
        }

        return nodeList.get(0);

    }
}
