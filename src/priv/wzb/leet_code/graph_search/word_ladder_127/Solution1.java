package priv.wzb.leet_code.graph_search.word_ladder_127;

import javafx.util.Pair;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Satsuki
 * @time 2019/11/30 23:32
 * @description:
 */
public class Solution1 {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 所有单词的长度一致
        int L = beginWord.length();

        //字典可容纳可从任何给定单词组成的单词组合。 通过一次更改一个字母。
        // 邻接表
        // 记录了每个单词的通用状态
        HashMap<String, ArrayList<String>> allComboDict = new HashMap<>();

        //对 Iterable的每个元素执行给定的操作，直到所有元素都被处理或动作引发异常。
        // 拆词，把每个单词
        // 对给定的 wordList 做预处理，找出所有的通用状态。将通用状态记录在字典中，键是通用状态，值是所有具有通用状态的单词。
        wordList.forEach(
                word->{
                    for (int i = 0; i < L; i++) {
                        // Key是通用词（generic word)
                        // Value是具有相同中间通用词的词的列表。
                        // 这里是否可能出错
                        String newWord = word.substring(0,i)+'*'+word.substring(i+1,L);
                        ArrayList<String> transformations = allComboDict.getOrDefault(newWord,new ArrayList<>());
                        transformations.add(word);
                        allComboDict.put(newWord,transformations);
                    }
                }
        );

        // 将包含 beginWord 和 1 的元组放入队列中，1 代表节点的层次。我们需要返回 endWord 的层次也就是从 beginWord 出发的最短距离。
        Queue<Pair<String,Integer>> Q = new LinkedList<>();
        ((LinkedList<Pair<String, Integer>>) Q).add(new Pair<>(beginWord,1));

        // 设置visited保存单词是否访问过
        // 为了防止出现环，使用访问数组记录。
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        visited.put(beginWord, true);

        while (!Q.isEmpty()){
            //当队列中有元素的时候，取出第一个元素，记为 current_word
            Pair<String, Integer> node = ((LinkedList<Pair<String, Integer>>) Q).remove();
            String word = node.getKey();
            int level = node.getValue();
            // 找到 current_word 的所有通用状态，并检查这些通用状态是否存在其它单词的映射，这一步通过检查 all_combo_dict 来实现。
            for (int i = 0; i < L; i++) {
                // 当前单词的中间词
                // 找到当前单词的所有通用状态
                String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);
                // Next states are all the words which share the same intermediate state.
                // 检查是否存在其他单词的映射
                for (String adjacentWord : allComboDict.getOrDefault(newWord, new ArrayList<String>())) {
                    // If at any point if we find what we are looking for
                    // i.e. the end word - we can return with the answer.
                    // 最终当你到达期望的单词，对应的层次就是最短变换序列的长度。
                    if (adjacentWord.equals(endWord)) {
                        return level + 1;
                    }
                    // 否则将该通用状态对应的单词都放入队列继续宽度优先搜索
                    // Otherwise, add it to the BFS Queue. Also mark it visited
                    if (!visited.containsKey(adjacentWord)) {
                        visited.put(adjacentWord, true);
                        Q.add(new Pair(adjacentWord, level + 1));
                    }
                }

            }

        }
        return 0;
    }
}
