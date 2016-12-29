package AActualGame;

import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import Matho.Math2D;
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
    public Geneable thingToGrade;
    private double thingToGradeParam;
    private double thingToGradeStartTime;
    private double thingToGradeActualTime;

    public ANeuralNetworkSIEnemy() throws IOException {
        super(4);
        moveSpeed = 20;
        viewRange = 1;
        thingToGradeParam = 2;
        thingToGradeStartTime = 1;
        thingToGradeActualTime = thingToGradeStartTime;
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
    public double[] getNetworkEntry()
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

        return entry;
    }
    public double[] getShorterEntry()
    {
        double[] ret = new double[15];
        double[] partRet = location.getFireballsData();
        double paramIt = 10;
        for(int i=0;i<partRet.length;i++) ret[i] = partRet[i];
        for(int i=0;i<2;i++)
        {
            ret[i*3+1] -= positionX;
            ret[i*3+1] /= paramIt;
            ret[i*3+2] -= positionY;
            ret[i*3+2] /= paramIt;
        }
        ret[6] = (enemy.positionX - positionX) / paramIt;
        ret[7] = (enemy.positionY - positionY) / paramIt;
        ret[8] = positionX/ paramIt;
        ret[9] = positionY/ paramIt;
        ret[10] = (location.GetSizeX() - positionX)/ paramIt;
        ret[11] = (location.GetSizeY() - positionY)/ paramIt;
        ret[12] = (double)HP/100;
        ret[13] = (double)enemy.HP/100;
        ret[14] = jury.GetRemainingTime();

        return ret;
    }
    public void MyRoutine(double dTime)
    {
        super.MyRoutine(dTime);
        if(location == null || enemy==null) return;


        if(thingToGrade != null) {
            thingToGradeActualTime -= dTime;
            if (thingToGradeActualTime <= 0) {
                thingToGradeActualTime = thingToGradeStartTime;
                double distance = Math2D.CountDistance(positionX,positionY,enemy.positionX,enemy.positionY);
                if(distance<3) distance = (3-distance)*4;
                else distance = 0;
                thingToGrade.SetGrades(thingToGrade.GetGrades() + (thingToGradeParam*distance));
            }
        }


        if(attackRemainingCooldown <= 0 || moveRemainingCooldown <= 0)
        {
            double[] entry = getShorterEntry();
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
                /*
                if(thingToGrade!=null) {
                    double distChange = Math2D.CountDistance(positionX, positionY, enemy.positionX, enemy.positionY) - Math2D.CountDistance((positionX + x), (positionY + y), enemy.positionX, enemy.positionY);
                    if (distChange > 0) thingToGrade.SetGrades(thingToGrade.GetGrades() + distChange);
                }*/
            }
        }
    }

}
