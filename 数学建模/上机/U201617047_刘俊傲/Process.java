package graph;

/**
 * @author yinren
 * @date 2018/12/26
 */
public class Process {
    final static int NUM = 7;
    int[][] graph = new int[NUM][NUM];//邻接矩阵
    int[] degree = new int[NUM];//入度
    int[] top = new int[NUM]; //存储拓扑排序后的结果
    int[] time = new int[NUM]; //存储每个节点花费时间

    public static void main(String[] args) {
        Process process = new Process();
        process.init();//初始化
        process.topSort();//拓扑排序
        process.getResult();//得到最优解
    }

    //初始化各参数
    public void init() {
        for (int i = 0; i < NUM; i++) {
            for (int j = 0; j < NUM; j++) {
                graph[i][j] = 0;
            }
        }
        for (int i = 0; i < NUM; i++) {
            degree[i] = 1;
        }
        degree[0] = 0;
        degree[4] = 2;

        graph[0][0] = 1;
        graph[0][1] = 1;
        graph[0][2] = 1;
        graph[1][4] = 1;
        graph[2][3] = 1;
        graph[2][5] = 1;
        graph[3][4] = 1;
        graph[4][6] = 1;

        time[0] = 0;
        time[1] = 3;
        time[2] = 5;
        time[3] = 2;
        time[4] = 6;
        time[5] = 8;
        time[6] = 3;
    }

    //拓扑排序
    private void topSort() {
        int num = 0;
        for (int i = 0; i < NUM; i++) { //遍历邻接矩阵
            for (int j = 0; j < NUM; j++) {
                if (graph[i][j] == 1) {//是否存在节点
                    if (degree[j] > 1) {//该节点入度是否为 0
                        degree[j]--;
                    } else {
                        top[num] = j;
                        num++;
                    }
                }
            }
        }
    }

    //得到最优解
    private void getResult() {
        int tmp = 0;
        String str = "";
        for (int i = 0; i < NUM; i++) {//便利
            if (tmp + time[top[i]] > 10) {
                System.out.println(str);
                tmp = 0;
                str = "";
            }
            tmp += time[top[i]];
            str += "," + top[i];
        }
        System.out.println(str);//打印最后结果
    }
}
