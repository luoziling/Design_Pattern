package priv.wzb.leet_code.list.copy_list_with_random_pointer_138;

import java.util.HashMap;

/**
 * @author Satsuki
 * @time 2019/11/13 21:01
 * @description:
 * 这是方法1
 * 图解法回溯法/递归
 * 回溯算法的第一想法是将链表想象成一张图。链表中每个节点都有 2 个指针（图中的边）。因为随机指针给图结构添加了随机性，所以我们可能会访问相同的节点多次，这样就形成了环。
 *
 *
 *
 * 上图中，我们可以看到随机指针指向了前一个节点，因此成环。我们需要考虑这种环的实现。
 *
 * 此方法中，我们只需要遍历整个图并拷贝它。拷贝的意思是每当遇到一个新的未访问过的节点，你都需要创造一个新的节点。遍历按照深度优先进行。我们需要在回溯的过程中记录已经访问过的节点，否则因为随机指针的存在我们可能会产生死循环。
 *
 * 作者：LeetCode
 * 链接：https://leetcode-cn.com/problems/copy-list-with-random-pointer/solution/fu-zhi-dai-sui-ji-zhi-zhen-de-lian-biao-by-leetcod/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * 图的深度优先遍历
 */
public class Solution3 {
    // HashMap将老节点作为key将新节点作为value
    HashMap<Node,Node> visitedHash = new HashMap<>();

    public Node copyRandomList(Node head) {
        if (head == null){
            return null;
        }

        // 如果已经处理了当前节点那么只需简单返回它的克隆版本
        if (this.visitedHash.containsKey(head)){
            return this.visitedHash.get(head);
        }

        // 创建一个新节点与老节点有同样的值
        Node node = new Node(head.val,null,null);

        // 在hashMap中保存创建的新节点。可以避免当next或random指针导致循环时不出现死循环
        this.visitedHash.put(head,node);

        // 图的深度优先遍历
        // 从next指针和random指针递归拷贝剩余链表
        node.next = this.copyRandomList(head.next);
        node.random = this.copyRandomList(head.random);

        return node;
    }
}
