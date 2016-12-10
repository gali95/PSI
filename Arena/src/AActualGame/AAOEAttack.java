package AActualGame;

import Matho.Vector2;

import java.io.IOException;

/**
 * Created by Lach on 2016-11-04.
 */
public class AAOEAttack extends AObject{

    int damage;
    ACharacter Acaster;
    double remainingTime;

    public AAOEAttack(ACharacter caster, double duration, Vector2 offset) throws IOException {
        super(1);
        remainingTime = duration;
        Acaster = caster;
        physics.FollowSomeone(caster,offset);
    }

    public void InteractWith(AObject other)
    {
        if(other==this || other==Acaster) return;
        if(ACharacter.class.isAssignableFrom(other.getClass()))
        {
            ((ACharacter)other).Hurt(25);
            this.Destroy();
        }
    }
    public void MyRoutine(double dTime)
    {
        //System.out.println("To sie kurwa odpala");
        remainingTime -= dTime;
        if(remainingTime <= 0)
        {
            //System.out.println("czas minel");
            this.Destroy();
        }
    }
}
