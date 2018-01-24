package AverTimeMeasure;

/**
 * Created by Lach on 2016-12-25.
 */
public class TempClass implements Runnable {
    @Override
    public void run() {

        try {
            synchronized (this)
            {
                wait(2000);
            }

            System.out.println(Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
