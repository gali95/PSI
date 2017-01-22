package AverTimeMeasure;

/**
 * Created by Lach on 2017-01-04.
 */
public class PartTimeMeasure {

    boolean started;
    long start;
    double suma;
    int times;
    String label;

    @Override
    public String toString() {
        return (label+": "+suma+"("+suma/times+")");
    }

    public PartTimeMeasure(String label)
    {
        started = false;
        this.label = label;
    }

    public boolean Start()
    {
        if(started) return false;
        started = true;
        start = System.nanoTime();
        return true;
    }

    public boolean Stop()
    {
        if(!started) return false;
        started = false;
        suma += (double)(System.nanoTime() - start) / 1000000000;
        times++;
        return true;
    }
    public void Reset()
    {
        if(started) started = false;
        suma = 0;
        times = 0;
    }
    public double GetWholeTime()
    {
        return suma;
    }
    public double GetAverageTime()
    {
        return suma/times;
    }

}
