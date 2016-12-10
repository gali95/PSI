package AActualGame;

import Matho.Vector2;

import java.io.IOException;

/**
 * Created by Lach on 2016-11-12.
 */
public class ABasicSIEnemy extends ACharacter{

    public ACharacter Enemy;
    public ANodeAvoider SIHelper;

    public ABasicSIEnemy() throws IOException {
        super(4);
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
        Vector2 besto = SIHelper.bestPath();
        if(besto.x != 0 || besto.y != 0)
        {
            StepIntoDirection((int)besto.x,(int)besto.y);
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
            ((ANodeToAvoidEnemy) (SIHelper.source[0])).source = enemy;
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

            Enemy = enemy;
        }

        if(!MaintainDistance())
        {
            AttackTarget();
        }
    }
}
