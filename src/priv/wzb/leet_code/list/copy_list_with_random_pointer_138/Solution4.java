package priv.wzb.leet_code.list.copy_list_with_random_pointer_138;

import java.util.HashMap;

/**
 * @author Satsuki
 * @time 2019/11/13 21:01
 * @description:
 * 方法 3：O(1)O(1) 空间的迭代
 * 想法
 *
 * 与上面提到的维护一个旧节点和新节点对应的字典不同，我们通过扭曲原来的链表，并将每个拷贝节点都放在原来对应节点的旁边。这种旧节点和新节点交错的方法让我们可以在不需要额外空间的情况下解决这个问题。让我们看看这个算法如何工作
 *
 * 作者：LeetCode
 * 链接：https://leetcode-cn.com/problems/copy-list-with-random-pointer/solution/fu-zhi-dai-sui-ji-zhi-zhen-de-lian-biao-by-leetcod/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class Solution4 {

    public Node copyRandomList(Node head) {
        if (head == null){
            return null;
        }

        // 创建一个新老节点交错的新的再编织的链表
        Node ptr = head;
        while (ptr!=null){
            // 克隆节点
            Node newNode = new Node(ptr.val,null,null);

            // 将克隆形成的新节点插入到源节点的下一个
            // If A->B->C is the original linked list,
            // Linked list after weaving cloned nodes would be A->A'->B->B'->C->C'
            newNode.next = ptr.next;
            ptr.next = newNode;
            // 后移ptr指针
            ptr = newNode.next;
        }
        ptr = head;

        // 现在链接新创建节点的random指针
        // 迭代新创建的链表使用源节点的random指针来为克隆节点分配random指针引用
        while (ptr!=null){
            ptr.next.random = (ptr.random!=null)?ptr.random.next:null;
            ptr = ptr.next.next;
        }

        // 去除编织即可得到原链表与新的深度拷贝链表
        Node ptr_old_list = head;
        Node ptr_new_list = head.next;
        // 记录一个节点，以防之后移动后找不到头节点
        // 这个是深度拷贝后的新节点
        Node head_old = head.next;
        while (ptr_old_list!=null){
            ptr_old_list.next = ptr_old_list.next.next;
            // 判空，如果下一个老节点存在，那么这个老节点对应的新节点也必然存在
            ptr_new_list.next = (ptr_new_list!=null)?ptr_new_list.next.next:null;
            ptr_old_list = ptr_old_list.next;
            ptr_new_list = ptr_new_list.next;
        }
        return head_old;
    }
}
