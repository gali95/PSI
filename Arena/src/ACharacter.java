import java.io.IOException;
import java.util.Objects;

/**
 * Created by Lach on 2016-10-31.
 */
public class ACharacter extends AObject{
    
    int HP;
    double moveSpeed;
    AGame jury;
    AHeritage watashiwasoru;
    double fireballCooldown,fireballRemainingCooldown;
    double attackCooldown,attackRemainingCooldown;
    
    public Boolean MoveUpState,MoveDownState,MoveRightState,MoveLeftState,AttackUpState,AttackDownState,AttackRightState,AttackLeftState;

    public ACharacter(String imgPath) throws IOException {
        super(imgPath);
        HP=100;
        density = true;
        jury = null;
        fireballCooldown = 2;
        attackCooldown = 1;
        fireballRemainingCooldown = 0;
        attackRemainingCooldown = 0;

        MoveUpState = false;
        MoveDownState = false;
        MoveRightState = false;
        MoveLeftState = false;
        AttackUpState= false;
        AttackDownState = false;
        AttackRightState = false;
        AttackLeftState = false;
    }


    public void StepIntoDirection(int x, int y)
    {
        if (x<-1) x = -1;
        if (x > 1) x = 1;
        if (y<-1) y = -1;
        if (y > 1) y = 1;

        if(x != 0 && y!=0 ) y = 0;

        if(physics.IsMovePossible(positionX + x,positionY+y))
        {
            if(density && location.tiles[positionX + x ][positionY + y].IsDense()) return;
            physics.MoveToWithSpeed(positionX+ x,positionY+y,moveSpeed);
        }
        return;
    }
    public void AttackOnDirection(int x,int y)
    {
        if (x<-1) x = -1;
        if (x > 1) x = 1;
        if (y<-1) y = -1;
        if (y > 1) y = 1;

        if(x != 0 && y!=0 ) y = 0;
        if(physics.speedX != 0 || physics.speedY != 0) return;
        if(attackRemainingCooldown>0) return;

        if(physics.IsMovePossible(positionX + x,positionY+y))
        {
            try {
                location.InsertObject(new AAOEAttack(this,attackCooldown,new Vector2(x,y)),positionX + x,positionY+y);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //physics.ImmobilizeForTime(attackCooldown);
            attackRemainingCooldown = attackCooldown;
        }
        return;
    }
    public void MoveUp(Boolean keyState)
    {
        StepIntoDirection(0,-1);
    }
    public void MoveDown(Boolean keyState)
    {
        StepIntoDirection(0,1);
    }
    public void MoveRight(Boolean keyState)
    {
        StepIntoDirection(1,0);
    }
    public void MoveLeft(Boolean keyState)
    {
        StepIntoDirection(-1,0);
    }

    public void AttackUp(Boolean keyState)
    {
        AttackOnDirection(0,-1);
    }
    public void AttackDown(Boolean keyState)
    {
        AttackOnDirection(0,1);
    }
    public void AttackRight(Boolean keyState)
    {
        AttackOnDirection(1,0);
    }
    public void AttackLeft(Boolean keyState)
    {
        AttackOnDirection(-1,0);
    }

    public void MoveUp()
    {
        StepIntoDirection(0,-1);
    }
    public void MoveDown()
    {
        StepIntoDirection(0,1);
    }
    public void MoveRight()
    {
        StepIntoDirection(1,0);
    }
    public void MoveLeft()
    {
        StepIntoDirection(-1,0);
    }

    public void AttackUp()
    {
        AttackOnDirection(0,-1);
    }
    public void AttackDown()
    {
        AttackOnDirection(0,1);
    }
    public void AttackRight()
    {
        AttackOnDirection(1,0);
    }
    public void AttackLeft()
    {
        AttackOnDirection(-1,0);
    }

    public void CastFireball(int x,int y)
    {
        if(fireballRemainingCooldown>0) return;
        try {
            location.InsertObject(new AFireBall(this,x,y),positionX,positionY);
            fireballRemainingCooldown = fireballCooldown;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Hurt(int damage)
    {
        HP -= damage;
        if (HP <= 0) this.Destroy();
    }
    public void Destroy()
    {
        super.Destroy();
        if(jury!=null) jury.EndGame();
    }
    public void MyRoutine(double dTime)
    {
        if(fireballRemainingCooldown>0)
        {
            fireballRemainingCooldown -= dTime;
        }
        if(attackRemainingCooldown>0)
        {
            attackRemainingCooldown -= dTime;
        }
    }
    
}
