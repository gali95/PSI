package Matho;

/**
 * Created by Lach on 2016-10-30.
 */
public class Math2D {

    public static double CountDistance(double x1,double y1,double x2,double y2)
    {

        double ret = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
        ret = Math.sqrt(ret);
        return ret;

    }

}
