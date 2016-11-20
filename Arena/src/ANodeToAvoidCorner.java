/**
 * Created by Lach on 2016-11-14.
 */
public class ANodeToAvoidCorner extends ANodeToAvoid{

    protected double CountAversion(double distance)
    {
        double moduler = -3.1;
        if(distance<3)
        {
            return moduler * (3 - distance);
        }
        else return 0;
    }

}
