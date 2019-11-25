package priv.wzb.leet_code.list.copy_list_with_random_pointer_138;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2019/11/13 21:01
 * @description:
 * 方法 2： O(N)O(N) 空间的迭代
 * 想法
 *
 * 迭代算法不需要将链表视为一个图。当我们在迭代链表时，我们只需要为 random 指针和 next 指针指向的未访问过节点创造新的节点并赋值即可。
 *
 * 作者：LeetCode
 * 链接：https://leetcode-cn.com/problems/copy-list-with-random-pointer/solution/fu-zhi-dai-sui-ji-zhi-zhen-de-lian-biao-by-leetcod/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * 不用抽象成图那么复杂
 * 首相克隆根节点然后边遍历边克隆
 * 在这过程中如果两个指针指向的节点存在则直接返回不存在则创建新节点
 */
public class Solution2 {
    // HashMap将老节点作为key将新节点作为value
    HashMap<Node,Node> visitedHash = new HashMap<>();
    public Node getCloneNode(Node node){
        // 如果节点存在
        if (node!=null){
            // 检查时候已经创建过/访问过
            if (this.visitedHash.containsKey(node)){
                // 如果在访问过的记录中那么就返回之前创建的节点
                return this.visitedHash.get(node);
            }else {
                // 否则创建新节点并且添加到访问过的记录（visitedHash）中
                this.visitedHash.put(node,new Node(node.val,null,null));
                return visitedHash.get(node);
            }
        }
        return null;
    }

    public Node copyRandomList(Node head) {
        if (head==null){
            return null;
        }

        // 辅助指针
        Node oldNode = head;
        // 创建新头节点
        Node newNode = new Node(oldNode.val,null,null);
        // 添加到已访问列表
        visitedHash.put(oldNode,newNode);

        // 迭代整个链表直至所有节点都被克隆
        while (oldNode!=null){
            // 通过random或者next指针获得节点的克隆引用
            //
            newNode.random = this.getCloneNode(oldNode.random);
            newNode.next = this.getCloneNode(oldNode.next);

            // 前移，遍历并且克隆
            oldNode = oldNode.next;
            newNode = newNode.next;
        }

        // 通过head这个老节点在visitedHash这张记录创建的新节点的表中可以找到对应的新节点的头节点
        // 通过这张表将老节点传进去即可获取对应的深度拷贝的新节点
        return this.visitedHash.get(head);


    }
}
