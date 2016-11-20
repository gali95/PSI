/**
 * Created by Lach on 2016-11-14.
 */
public class ANodeToAvoid {

    public int locX,locY;

    public double CountAversion(int targetX, int targetY)
    {

        return CountAversion(Math2D.CountDistance(locX,locY,targetX,targetY));

    }
    protected double CountAversion(double distance)
    {
        return 0;
    }

}
