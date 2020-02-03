package priv.wzb.leet_code.graph_search.trapping_rain_water_two_407;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Satsuki
 * @time 2019/12/1 23:41
 * @description:
 */
public class Solution1 {
    private class Node {
        public Node(int x, int y, int h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }
        int x;
        int y;
        int h;
    }
    public int trapRainWater(int[][] heightMap) {
        if (heightMap.length < 3) return 0;
        class Util {
            private int len1 = heightMap.length, len2 = heightMap[0].length;
            boolean valid(int x, int y) {
                return x >= 0 && x < len1 && y >= 0 && y < len2;
            }
        }
        Util util = new Util();
        int ret = 0, len1 = heightMap.length, len2 = heightMap[0].length;
        boolean[][] vis = new boolean[len1][len2];
        Queue<Node> q = new PriorityQueue<>((a, b) -> a.h - b.h);
        for (int i = 0; i < len1; i ++) {
            q.offer(new Node(i, 0, heightMap[i][0]));
            q.offer(new Node(i, len2 - 1, heightMap[i][len2 - 1]));
            vis[i][0] = vis[i][len2 - 1] = true;
        }
        for (int j = 1; j < len2 - 1; j++) {
            q.offer(new Node(0, j, heightMap[0][j]));
            q.offer(new Node(len1 - 1, j, heightMap[len1 - 1][j]));
            vis[0][j] = vis[len1 - 1][j] = true;
        }
        int[] dx = new int[]{-1, 0 , 1, 0}, dy = new int[]{0, 1, 0, -1};
        while (!q.isEmpty()) {
            Node cur = q.poll();
            for (int i = 0; i < 4; i++) {
                int newx = cur.x + dx[i], newy = cur.y + dy[i];
                if (util.valid(newx, newy) && !vis[newx][newy]) {
                    vis[newx][newy] = true;
                    int tempHeight = heightMap[newx][newy];
                    if (cur.h > tempHeight) {
                        ret += (cur.h - tempHeight);
                    }
                    q.offer(new Node(newx, newy, Math.max(tempHeight, cur.h)));
                }
            }
        }
        return ret;
    }
}

