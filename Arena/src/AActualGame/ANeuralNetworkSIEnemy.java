package AActualGame;

import Matho.Vector2;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;
import NNetworks.NeuronBetter;

import java.io.IOException;

/**
 * Created by Lach on 2016-12-03.
 */
public class ANeuralNetworkSIEnemy extends ACharacter{

    private NPCNetwork si;
    private int viewRange;

    public ANeuralNetworkSIEnemy() throws IOException {
        super(4);
        moveSpeed = 20;
        viewRange = 1;
    }
    public void setRange(int nRange)
    {
        viewRange=nRange;
    }

    public NPCNetwork getSi() {
        return si;
    }

    public void setSi(NPCNetwork si) {
        this.si = si;
    }

    public void setRandomSi()
    {
        si = new NPCNetwork(new NeuronBetter());
        int[] wymiary = new int[3];
        wymiary[0] = (viewRange*2+1)*(viewRange*2+1)*3+3;
        wymiary[2] = 5;
        for(int i=1;i<2;i++)
        {
            wymiary[i] = wymiary[0] * 2;
        }
        si.attack.Init(wymiary);
        si.movement.Init(wymiary);
    }

    public void MyRoutine(double dTime)
    {
        super.MyRoutine(dTime);
        if(location == null || enemy==null) return;
        if(attackRemainingCooldown <= 0 || moveRemainingCooldown <= 0)
        {
            double[] entry = new double[(viewRange*2+1)*(viewRange*2+1)*3+3];
            Boolean[][] fields = location.IsThereFloor(new Vector2(positionX,positionY),new Vector2(viewRange,viewRange));
            Boolean[][] fireballs = location.IsThereFireball(new Vector2(positionX,positionY),new Vector2(viewRange,viewRange));
            Boolean[][] enemyLoc = location.IsThereFireball(new Vector2(positionX,positionY),new Vector2(viewRange,viewRange));
            int k=0;
            for(int i=0;i<viewRange*2+1;i++)
            {
                for(int j=0;j<viewRange*2+1;j++)
                {
                    if(fields[i][j])
                        entry[k++] = 1;
                    else
                        entry[k++] = 0;
                }
            }
            for(int i=0;i<viewRange*2+1;i++)
            {
                for(int j=0;j<viewRange*2+1;j++)
                {
                    if(fireballs[i][j])
                        entry[k++] = 1;
                    else
                        entry[k++] = 0;
                }
            }
            for(int i=0;i<viewRange*2+1;i++)
            {
                for(int j=0;j<viewRange*2+1;j++)
                {
                    if(enemyLoc[i][j])
                        entry[k++] = 1;
                    else
                        entry[k++] = 0;
                }
            }
            entry[entry.length-3] = (double)HP/100;
            entry[entry.length-2] = (double)enemy.HP/100;
            entry[entry.length-1] = jury.GetRemainingTime();

            if(attackRemainingCooldown <= 0)
            {
                double[] attackChoice = si.attack.RunNetwork(entry);
                int highest=0;
                for(int i=1;i<attackChoice.length;i++)
                {
                    if(attackChoice[i]>attackChoice[highest]) highest = i;
                }
                int x=0,y=0;
                switch (highest)
                {
                    case 0:
                       x=0;
                       y=0;
                    break;
                    case 1:
                        x=0;
                        y=1;
                        break;
                    case 2:
                        x=1;
                        y=0;
                        break;
                    case 3:
                        x=-1;
                        y=0;
                        break;
                    case 4:
                        x=0;
                        y=-1;
                        break;
                }
                AttackOnDirection(x,y);
            }
            if(moveRemainingCooldown <= 0)
            {
                double[] moveChoice = si.movement.RunNetwork(entry);
                int highest=0;
                for(int i=1;i<moveChoice.length;i++)
                {
                    if(moveChoice[i]>moveChoice[highest]) highest = i;
                }
                int x=0,y=0;
                switch (highest)
                {
                    case 0:
                        x=0;
                        y=0;
                        break;
                    case 1:
                        x=0;
                        y=1;
                        break;
                    case 2:
                        x=1;
                        y=0;
                        break;
                    case 3:
                        x=-1;
                        y=0;
                        break;
                    case 4:
                        x=0;
                        y=-1;
                        break;
                }
                StepIntoDirection(x,y);
            }
        }
    }

}
