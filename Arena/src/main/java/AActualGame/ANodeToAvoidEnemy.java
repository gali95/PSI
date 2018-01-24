package AActualGame;

/**
 * Created by Lach on 2016-11-14.
 */
public class ANodeToAvoidEnemy extends ANodeToAvoid {

    public ACharacter source;

    public double CountAversion(int targetX, int targetY)
    {
        locX = source.positionX;
        locY = source.positionY;
        return super.CountAversion(targetX,targetY);
    }

    protected double CountAversion(double distance)
    {
        double moduler = -3.4;
        if(distance<5)
        {
            return moduler * (5 - distance);
        }
        else return 0;
    }

}
