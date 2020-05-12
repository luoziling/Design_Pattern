package priv.wzb.leet_code.list.LFU_Cache_460;

import priv.wzb.interview.test.P;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2020/4/5 16:00
 * @description:
 */
public class LFUCache1 {
    // 存储缓存内容
    Map<Integer,Node> cache;
    // 使用频次最大
    DoublyLinkedList firstLinkedList;
    // 使用频次最小
    DoublyLinkedList lastLinkedList;
    // 链表个数
    int size;
    int capacity;

    public LFUCache1(int capacity) {
        cache = new HashMap<>();
        firstLinkedList = new DoublyLinkedList();
        lastLinkedList = new DoublyLinkedList();
        firstLinkedList.post = lastLinkedList;
        lastLinkedList.pre = firstLinkedList;
        this.capacity = capacity;

    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null){
            return -1;
        }
        // 访问次数增加
        freqInc(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity==0){
            return;
        }
        Node node = cache.get(key);
        // 弱存在则更新，访问频次增加
        if (node!=null){
            node.value = value;
            freqInc(node);
        }else {
            // 不存在
            if (size == capacity){
                // 缓存满了，删除lastLinkedList.pre这个链表（最近最小访问频次的
                cache.remove(lastLinkedList.pre.tail.pre.key);
                size--;
            }
            if (lastLinkedList.pre.head.post == lastLinkedList.pre.tail){
                removeDoublyLinkedList(lastLinkedList.pre);
            }
        }
        // cache中put新Key-Node对儿，并将新node加入表示freq为1的DoublyLinkedList中，若不存在freq为1的DoublyLinkedList则新建。
        Node newNode = new Node(key,value);
        cache.put(key,newNode);
        if (lastLinkedList.pre.freq!=1){
            DoublyLinkedList newDoublyLinkedList = new DoublyLinkedList(1);
            addDoublyLinkedList(newDoublyLinkedList,lastLinkedList);
            newDoublyLinkedList.addNode(newNode);
        }else {
            lastLinkedList.pre.addNode(newNode);
        }
        size++;


    }

    // 增加访问次数
    void freqInc(Node node){
        // 将node从原freq对应的双链表移除，链表空了则删除链表
        DoublyLinkedList linkedList = node.doublyLinkedList;
        DoublyLinkedList preLinkedList = linkedList.pre;
        linkedList.removeNode(node);
        if (linkedList.head.post == linkedList.tail){
            removeDoublyLinkedList(linkedList);
        }
        // 将Node加入新freq对应的双向链表，链表不存在则创建
        node.freq++;
        if (preLinkedList.freq != node.freq){
            DoublyLinkedList newDoublyLinkedList = new DoublyLinkedList(node.freq);
            addDoublyLinkedList(newDoublyLinkedList,preLinkedList);
            newDoublyLinkedList.addNode(node);

        }else {
            preLinkedList.addNode(node);
        }
    }

    // 添加某频次的双向链表
    void addDoublyLinkedList(DoublyLinkedList newDoublyLinkedList,DoublyLinkedList preLinkedList){
        newDoublyLinkedList.post = preLinkedList.post;
        newDoublyLinkedList.post.pre = new DoublyLinkedList();
        newDoublyLinkedList.pre = preLinkedList;
        preLinkedList.post = new DoublyLinkedList();
    }
    // 删除
    void removeDoublyLinkedList(DoublyLinkedList doublyLinkedList){
        doublyLinkedList.pre.post = doublyLinkedList.post;
        doublyLinkedList.post.pre = doublyLinkedList.pre;
    }

    public static void main(String[] args) {
        LFUCache1 cache = new LFUCache1( 2 /* capacity (缓存容量) */ );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // 返回 1
        cache.put(3, 3);    // 去除 key 2
        cache.get(2);       // 返回 -1 (未找到key 2)
        cache.get(3);       // 返回 3
        cache.put(4, 4);    // 去除 key 1
        cache.get(1);       // 返回 -1 (未找到 key 1)
        cache.get(3);       // 返回 3
        cache.get(4);       // 返回 4

    }
}

/**
 *  节点
 */
class Node{
    int key;
    int value;
    // 访问次数？
    int freq = 1;
    Node pre; // Node所在频次的双向链表的前继Node
    Node post; // Node所在频次的双向链表的后继Node
    // node所在频次的双向链表
    DoublyLinkedList doublyLinkedList;
    public Node(){

    }

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}


// 双向循环链表
class DoublyLinkedList{
    // 改双向链表表示的频次
    int freq;
    // 该链表的前继链表pre.freq < this.freq
    DoublyLinkedList pre;
    // 该链表的后继链表post.freq > this.freq
    DoublyLinkedList post;
    // 头节点，新头节点从头部加入，表示最近访问
    Node head;
    // 尾节点，删除节点从尾部删除，表示最久访问
    Node tail;

    public DoublyLinkedList() {
        head = new Node();
        tail = new Node();
        head.post = tail;
        tail.pre = head;
    }

    public DoublyLinkedList(int freq) {
        head = new Node();
        tail = new Node();
        head.post = tail;
        tail.pre = head;
        this.freq = freq;

    }

    // 链表的删除
    void removeNode(Node node){
        node.pre.post = node.post;
        node.post.pre = node.pre;
    }

    void addNode(Node node){
        node.post = head.post;
        head.post.pre = node;
        head.post = node;
        node.pre = head;
        node.doublyLinkedList = this;
    }

}
