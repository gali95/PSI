import java.io.IOException;

/**
 * Created by Lach on 2016-11-03.
 */
public class AFireBall extends AObject {

    double fireballSpeed = 5;
    int damage = 20;
    AObject Acaster;
    Boolean shotIt;
    int x,y;
    public AFireBall(AObject caster,int x,int y) throws IOException {
        super("fireball.png");
        shotIt = true;
        location = caster.location;
        Acaster = caster;
        this.x = x;
        this.y = y;
    }
    public void InteractWith(AObject other)
    {
        if(other==this || other==Acaster) return;
        if(ACharacter.class.isAssignableFrom(other.getClass()))
        {
            ((ACharacter)other).Hurt(50);
            this.Destroy();
        }

    }
    public void MyRoutine(double dTime)
    {
        if(shotIt)
        {
            physics.MoveToWithSpeed(x,y,fireballSpeed);
            shotIt = false;
        }
        if((physics.speedX == 0) && (physics.speedY==0)) this.Destroy();
    }


}
