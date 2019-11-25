package priv.wzb.leet_code.stack_queue_heap.find_median_from_data_stream_295;

import java.util.PriorityQueue;

/**
 * @author Satsuki
 * @time 2019/11/10 20:11
 * @description:
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 *
 * 例如，
 *
 * [2,3,4] 的中位数是 3
 *
 * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 *
 * 设计一个支持以下两种操作的数据结构：
 *
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * 示例：
 *
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 * 进阶:
 *
 * 如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？
 * 如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-median-from-data-stream
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class MedianFinder {
    // 中位数：中位数左边的都小于中位数
    // 中位数右边的都大于中位数
    // 维护一个大顶堆一个小顶堆
    // 大顶堆的其他元素都小于堆顶元素（与中位数左侧性质相同
    // 小顶堆的其他元素都大于堆顶元素（与中位数右侧性质相同
    // 这两个堆把数组很好的分为了两部分
    // 在添加时只要更新一定规律将数据添加到了两个堆中就能很好的找到中位数
    private int count;
    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;
    /** initialize your data structure here. */
    public MedianFinder() {
        count = 0;
        // lambda表达式构建大顶堆就是一种倒序排序
        maxHeap = new PriorityQueue<>((x,y)->y-x);
        minHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {
        count+=1;
        maxHeap.offer(num);
        minHeap.add(maxHeap.poll());
        // 如果两个堆合起来元素个数是奇数，小顶堆要拿出堆顶给大顶堆
        // 如果是奇数就要从中位数右侧的数组拿出一个最小的数放到左侧
        if ((count&1) != 0){
            maxHeap.add(minHeap.poll());
        }
    }

    public double findMedian() {
        if ((count&1) == 0){
            // 偶数
            return (double) (maxHeap.peek() + minHeap.peek())/2.0;
        }else {
            return (double) maxHeap.peek();
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
