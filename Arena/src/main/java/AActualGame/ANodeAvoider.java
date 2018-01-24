package AActualGame;

import Matho.Vector2;

/**
 * Created by Lach on 2016-11-14.
 */
public class ANodeAvoider {

    public ANodeToAvoid[] source;
    public ACharacter owner;

    public Vector2 bestPath()
    {
        double highest = sumOfAversion(owner.positionX,owner.positionY);
        Vector2 ret = new Vector2();
        ret.x = 0;
        ret.y = 0;
        Vector2[] options= new Vector2[4];
        for(int i=0;i<4;i++)
        {
            options[i] = new Vector2();
        }
        options[0].x = 0;
        options[0].y = 1;
        options[1].x = 0;
        options[1].y = -1;
        options[2].x = 1;
        options[2].y = 0;
        options[3].x = -1;
        options[3].y = 0;
        for(int i=0;i<4;i++)
        {
            if(sumOfAversion( owner.positionX + (int)options[i].x,owner.positionY +(int)options[i].y) > highest)
            {
                if(owner.physics.IsMovePossible(owner.positionX + (int)options[i].x,owner.positionY +(int)options[i].y))
                {
                    highest = sumOfAversion(owner.positionX + (int) options[i].x, owner.positionY + (int) options[i].y);
                    ret.x = options[i].x;
                    ret.y = options[i].y;
                }
            }
        }
        return ret;
    }
    private double sumOfAversion(int targetX,int targetY)
    {
        double sum=0;
        //System.out.println("ZAGROZENIA: ("+targetX+" "+targetY+")");
        for(int i=0;i<source.length;i++)
        {
            sum += source[i].CountAversion(targetX,targetY);
            //System.out.println(i+1 + ". " + source[i].CountAversion(targetX,targetY));
        }
       // System.out.println(source.length);
        return sum;
    }

}
