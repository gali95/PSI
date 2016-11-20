import java.io.IOException;

/**
 * Created by Lach on 2016-11-12.
 */
public class ABasicSIEnemy extends ACharacter{

    public ACharacter Enemy;
    public ANodeAvoider SIHelper;

    public ABasicSIEnemy() throws IOException {
        super("player.png");
        moveSpeed = 10;

        distanceToRun = 4;
    }
    public void Destroy()
    {
        super.Destroy();
    }

    public void AttackTarget()
    {
        if(Enemy != null)
        {
            if(fireballRemainingCooldown>0) return;
            CastFireball(Enemy.positionX,Enemy.positionY);
        }
    }

    public double distanceToRun;
    public Boolean MaintainDistance()
    {
        if(Math2D.CountDistance(positionX,positionY,Enemy.positionX,Enemy.positionY) < distanceToRun)
        {
            StepIntoDirection((int)SIHelper.bestPath().x,(int)SIHelper.bestPath().y);
            return true;
        }
        return false;
    }

    public void MyRoutine(double dTime)
    {
        super.MyRoutine(dTime);
        if(SIHelper == null) {
            SIHelper = new ANodeAvoider();
            SIHelper.owner = this;

            SIHelper.source = new ANodeToAvoid[5];
            SIHelper.source[0] = new ANodeToAvoidEnemy();
            ((ANodeToAvoidEnemy) (SIHelper.source[0])).source = location.player;
            for (int i = 0; i < 4; i++) {
                SIHelper.source[i + 1] = new ANodeToAvoidCorner();
            }
            SIHelper.source[1].locX = 0;
            SIHelper.source[1].locY = 0;

            SIHelper.source[2].locX = 0;
            SIHelper.source[2].locY = location.tiles.length;

            SIHelper.source[3].locX = location.tiles[0].length;
            SIHelper.source[3].locY = 0;

            SIHelper.source[4].locX = location.tiles[0].length;
            SIHelper.source[4].locY = location.tiles.length;

            Enemy = location.player;
        }

        if(!MaintainDistance())
        {
            AttackTarget();
        }
    }
}
