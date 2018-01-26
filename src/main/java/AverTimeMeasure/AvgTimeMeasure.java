package AverTimeMeasure;

/**
 * Created by Lach on 2016-12-03.
 */
public class AvgTimeMeasure {

    public double[] times;
    public int iterations;

    public AvgTimeMeasure(int times)
    {
        this.times = new double[times];
        for(int i=0;i<times;i++)
        {
            this.times[i] = 0;
        }
        iterations = 0;
    }

    public double getTime(int index)
    {
        return times[index] / iterations;
    }

}
