package priv.wzb.leet_code.graph_search.word_ladder_127;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/30 22:35
 * @description:
 * 给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：
 *
 * 每次转换只能改变一个字母。
 * 转换过程中的中间单词必须是字典中的单词。
 * 说明:
 *
 * 如果不存在这样的转换序列，返回 0。
 * 所有单词具有相同的长度。
 * 所有单词只由小写字母组成。
 * 字典中不存在重复的单词。
 * 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。
 * 示例 1:
 *
 * 输入:
 * beginWord = "hit",
 * endWord = "cog",
 * wordList = ["hot","dot","dog","lot","log","cog"]
 *
 * 输出: 5
 *
 * 解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 *      返回它的长度 5。
 * 示例 2:
 *
 * 输入:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log"]
 *
 * 输出: 0
 *
 * 解释: endWord "cog" 不在字典中，所以无法进行转换。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/word-ladder
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 先将词典转换为图
 * 利用图的广搜查找是否可达并且返回搜索长度
 */
public class Solution {
    // 使用邻接表表示graph
    List<List<String>> graph;
    // 词典记为全局，后续遍历获取顶点的下标有用
    List<String> wordList1;

    /**
     * 判断是否两个单词可以连接
     * @param word1
     * @param word2
     * @return
     */
    boolean connect(String word1,String word2){
        // 记录两个单词不相等字符的个数
        int cut = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i)!=word2.charAt(i)){
                cut++;
            }
        }
        return cut==1;
    }

    void constructGraph(String beginWord){
        // 初始化
        graph = new LinkedList<>();
        // 将起始单词加入字典（wordList)
        wordList1.add(beginWord);
        // 遍历初始化图的每个邻接表
        for (int i = 0; i < wordList1.size(); i++) {
            // Java Variable expected;原因：未定义就直接使用了get(i)获取的对象其实不存在
//            graph.get(i) = new LinkedList<>();
            // 直接添加即可
            graph.add(new LinkedList<>());
        }

        for (int i = 0; i < wordList1.size(); i++) {
            // 因为是无向图，所以添加的时候是添加两遍的j可以i+1进行优化防止一定程度的多余添加
            for (int j = i+1; j < wordList1.size(); j++) {
                if (connect(wordList1.get(i),wordList1.get(j))){
                    // 无向图，在两个链表中都添加一个可达节点
                    graph.get(i).add(wordList1.get(j));
                    graph.get(j).add(wordList1.get(i));
                }
            }
        }
    }

    int BFSGraph(String beginWord,String endWord){
        // 搜索队列
        LinkedList<MyPair> queue = new LinkedList<>();
        // 记录访问过的顶点
        Set<String> visit = new HashSet<>();

        // 添加顶点，并且设置初始步数为1
        queue.add(new MyPair(beginWord,1));
        // 标记已访问
        visit.add(beginWord);
        // 队列不空继续遍历
        while (!queue.isEmpty()){
            MyPair first = queue.pop();
            String node = first.getVertex();
            int step = first.getStep();
            // 如果找到重点，返回步数
            if (node == endWord){
                return step;
            }
            // 取出node的全部邻接点
            List<String> neighbors = graph.get(wordList1.indexOf(node));
            for (int i = 0; i < neighbors.size(); i++) {
                // 未访问过该顶点
                if (!visit.contains(neighbors.get(i))){
                    // 加入队列
                    queue.add(new MyPair(neighbors.get(i),step+1));
                    // 标记为访问过
                    visit.add(neighbors.get(i));
                }
            }
        }
        return 0;
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        wordList1 = wordList;
        // 构造成graph
        constructGraph(beginWord);
        // 深搜
        return BFSGraph(beginWord,endWord);
    }

    public static void main(String[] args) {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = new LinkedList<>();
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        wordList.add("cog");
        System.out.println(new Solution().ladderLength(beginWord,endWord,wordList));
    }
}
// 用于记录图中顶点以及到达该点的步数的集合
class MyPair{
    // 图中顶点
    private String vertex;
    // 到达该点的步数
    private int step;

    public MyPair(String vertex, int step) {
        this.vertex = vertex;
        this.step = step;
    }

    public String getVertex() {
        return vertex;
    }

    public void setVertex(String vertex) {
        this.vertex = vertex;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
