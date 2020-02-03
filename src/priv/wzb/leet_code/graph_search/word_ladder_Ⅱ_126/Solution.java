    package priv.wzb.leet_code.graph_search.word_ladder_Ⅱ_126;

    import java.util.*;

    /**
     * @author Satsuki
     * @time 2019/12/1 15:51
     * @description:
     * 给定两个单词（beginWord 和 endWord）和一个字典 wordList，找出所有从 beginWord 到 endWord 的最短转换序列。转换需遵循如下规则：
     *
     * 每次转换只能改变一个字母。
     * 转换过程中的中间单词必须是字典中的单词。
     * 说明:
     *
     * 如果不存在这样的转换序列，返回一个空列表。
     * 所有单词具有相同的长度。
     * 所有单词只由小写字母组成。
     * 字典中不存在重复的单词。
     * 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。
     * 示例 1:
     *
     * 输入:
     * beginWord = "hit",
     * endWord = "cog",
     * wordList = ["hot","dot","dog","lot","log","cog"]
     *
     * 输出:
     * [
     *   ["hit","hot","dot","dog","cog"],
     *   ["hit","hot","lot","log","cog"]
     * ]
     * 示例 2:
     *
     * 输入:
     * beginWord = "hit"
     * endWord = "cog"
     * wordList = ["hot","dot","dog","lot","log"]
     *
     * 输出: []
     *
     * 解释: endWord "cog" 不在字典中，所以不存在符合要求的转换序列。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/word-ladder-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public class Solution {
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            List<List<String>> ans = new ArrayList<>();
            // 如果单词数组中不包括endWord就说明永远找不到答案直接返回空集合即可
            // 也防止死循环
            if (!wordList.contains(endWord)){
                return ans;
            }
            bfs(beginWord,endWord,wordList,ans);
            return ans;
        }

        /**
         * 宽搜，在搜索过程中边搜索边记录路径，一旦达到endWord就保存路径
         * 全部搜索完成后返回即可
         * @param beginWord 起始单词
         * @param endWord 结束单词（也是在宽搜中判定结束的依据之一
         * @param wordList 单词数组（词典一样的存在，要在这个数组中寻找
         * @param ans 保存结果
         */
        public void bfs(String beginWord,String endWord,List<String> wordList,List<List<String>> ans){
            // 用于辅助宽搜的队列 ，与树中的层次遍历相同都需要队列辅助搜索，还需要visited判断是否搜索过
            Queue<List<String>> queue = new LinkedList<>();
            // 用于保存搜索过程中中间结果（搜索路径）
            List<String> path = new ArrayList<>();
            // 一开始就访问beginWord，将初始单词记入path，作为path的第一个单词
            path.add(beginWord);
            // 将起始路径计入队列，方便后续进行搜索
            queue.offer(path);
            // 是否找到
            boolean isFound = false;
            // 使用Set存储单词数组以及访问过的单词
            Set<String> dict = new HashSet<>(wordList);
            Set<String> visited = new HashSet<>();
            // 记录初始单词已经访问过
            visited.add(beginWord);
            // 辅助队列不为空就继续宽搜
            while (!queue.isEmpty()){
                // 记录辅助队列的大小
                int size = queue.size();
                // 使用Set记录某条路径的访问记录
                Set<String> subVisited = new HashSet<>();

                for (int j = 0; j < size; j++) {
                    // p记录（指向）辅助队列的对头数组
                    List<String> p = queue.poll();
                    // 得到当前路径的末尾单词
                    String temp = p.get(p.size()-1);
                    // 一次性得到所有的下一个节点
                    // 也就是当前路径的末尾单词可以转换的所有其他单词
                    ArrayList<String> neighbors = getNeighbors(temp,dict);
                    // 遍历
                    for (String neighbor : neighbors){
                        // 只考虑之前没有出现过的单词
                        if (!visited.contains(neighbor)){
                            // 达到结束单词
                            if (neighbor.equals(endWord)){
                                // 记录找到路径
                                isFound = true;
                                // 把最后一个单词计入p
                                p.add(neighbor);
                                // 把路径保存到结果
    //                            ans.add(p);
                                // 一定要new防止后续对该p进行修改
                                ans.add(new ArrayList<>(p));
                                // 移除最后一个单词，为了方便后续不管有没有找到都将当前Neighbor加入当前路径中
                                p.remove(p.size()-1);
                            }
                            // 加入当前单词
                            p.add(neighbor);
                            // 记录新的路径
                            queue.offer(new ArrayList<>(p));
                            p.remove(p.size()-1);
                            // 记录当前单词访问过
                            subVisited.add(neighbor);
                        }
                    }

                }
                // 跳出条件
                // 将遍历子路径时访问的节点全部加入visited
                // 在bfs的过程中只有跟当前结果一样长或者短的路径才需要加入答案

                visited.addAll(subVisited);
                if (isFound){
                    break;
                }
            }
        }

        /**
         * 找到temp可以转换的所有单词
         * @param temp 原始单词
         * @param dict 字典
         * @return 原始单词temp符合转换准则的所有转换后的单词
         */
        private ArrayList<String> getNeighbors(String temp, Set<String> dict){
            ArrayList<String> res = new ArrayList<>();
            char[] chars = temp.toCharArray();
            // 将temp这个单词中的所有字母依次进行替换为a-z 26个小写英文字母
            // 如果替换后的单词可以在字典中找到那么就说明是可以转换的单词放入res
            for(char ch = 'a';ch<='z';ch++){
                for (int i = 0; i < chars.length; i++) {
                    // 如果在替换过程中替换到了自身就跳过
                    if (chars[i] == ch){
                        continue;
                    }
                    // 旧字母/字符
                    char oldChar = chars[i];
                    // 替换为新字母
                    chars[i] = ch;
                    // 如果字典中包含替换后的（满足要求的）单词
                    if (dict.contains(String.valueOf(chars))){
                        res.add(String.valueOf(chars));
                    }
                    // 还原单词
                    chars[i] = oldChar;
                }
            }
            return res;
        }
    }
