import java.util.Date;

/**
 * Created by lenovo on 2017/12/3.
 */
public class Test {

    @org.junit.Test
    public void testTime() {
        new Thread() {
            @Override
            public void run() {
                System.out.println(this.getName() + " -> 开始时间： " + new Date().getTime());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--------------");
                System.out.println(this.getName() + " -> 结束时间： " + new Date().getTime());

                for(int i = 0; i < 100; i++){

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(getName()
                            + ": " + i);
                }
            }
        }.start();
    }
}
