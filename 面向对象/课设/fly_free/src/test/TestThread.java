package test;

/**
 * Created by lenovo on 2017/12/3.
 */
public class TestThread extends Thread {

    public static void thread() {
        new Thread() {
            @Override
            public void run() {
                while (true) {

                    System.out.println(this.getName() + " -> 开始时间： " + System.currentTimeMillis());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(this.getName() + " -> 结束时间： " + System.currentTimeMillis());
                    System.out.println("--------------");
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        TestThread.thread();
    }
}
