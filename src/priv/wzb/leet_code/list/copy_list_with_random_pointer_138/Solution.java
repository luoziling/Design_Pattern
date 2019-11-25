package priv.wzb.leet_code.list.copy_list_with_random_pointer_138;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2019/11/13 20:31
 * @description:
 * 给定一个链表，每个节点包含一个额外增加的随机指针，该指针可以指向链表中的任何节点或空节点。
 *
 * 要求返回这个链表的深拷贝。 
 * 深度拷贝就是创建一个完全全新的链表其中节点也是全新的
 * 对于原链表做任何操作都不会影响新链表反之亦然
 *
 *  
 *
 * 示例：
 *
 *
 *
 * 输入：
 * {"$id":"1","next":{"$id":"2","next":null,"random":{"$ref":"2"},"val":2},"random":{"$ref":"2"},"val":1}
 *
 * 解释：
 * 节点 1 的值是 1，它的下一个指针和随机指针都指向节点 2 。
 * 节点 2 的值是 2，它的下一个指针指向 null，随机指针指向它自己。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/copy-list-with-random-pointer
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public Node copyRandomList(Node head) {
        Node help = head;
        // 难点在于random指针
        // 用map保存random指针然后建立list去list中寻找
        Node copyNode = new Node(0,null,null);
        Map<Node,Integer> oldNodeMap = new HashMap<>();
        List<Integer> oldNodeList = new LinkedList<>();
        Map<Integer,Node> newNodeMap = new HashMap<>();
        List<Node> nodeList = new LinkedList<>();
        int index= 0;
        // 第一遍遍历将原来链表的节点按顺序存入oldNodeMap
        // 并且按照原先的值创建新节点
        while (help!=null){
            oldNodeMap.put(head,index);

            // 创建新节点
            newNodeMap.put(index,new Node(head.val,null,null));
            index++;
            help = help.next;
        }
        help = head;
        // 需要得到random指针指向的节点编号即可
        for (int i = 0; i < oldNodeMap.size(); i++) {
            oldNodeList.add(oldNodeMap.get(help));
            help = help.next;
        }

        // 节点连接并且添加random指针
        int n = newNodeMap.size();
        // 临时节点
        Node node = null;
        for (int i = 0; i < n; i++) {
            node = newNodeMap.get(i);
            // 节点相连
            if (i<n-1){
                node.next = newNodeMap.get(i+1);
            }
            node.random = newNodeMap.get(oldNodeList.get(i));

        }

        return newNodeMap.get(0);

    }

}
class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};
