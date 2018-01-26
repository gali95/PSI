package AActualGame.ALabirynth;

import AActualGame.ACharacter;
import AActualGame.AObject;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import Matho.Vector2;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Lach on 2017-01-01.
 */
public class ASIMouse extends ACharacter implements Geneable{  // rodzaj myszy posiadajacej genotyp i przystosowanej do brania udziału w algorytmie genetycznym

    Integer[] scenarioTable;
    double grade;
    public boolean stop;
    public boolean dontGrade;

    private Vector2[] lastMoves;

    public ASIMouse() throws IOException {
        super(10);
        scenarioTable = new Integer[81];
        for(int i=0;i<scenarioTable.length;i++)
        {
            scenarioTable[i] = 0;
        }
        stop = false;
        moveSpeed = 10;
        dontGrade = false;
    }

    public int GetScenarioID()
    {
        int ret=0;
        Vector2[] locs = new Vector2[4];

        locs[0] = new Vector2();
        locs[0].x = 0;
        locs[0].y = 1;

        locs[1] = new Vector2();
        locs[1].x = 0;
        locs[1].y = -1;

        locs[2] = new Vector2();
        locs[2].x = 1;
        locs[2].y = 0;

        locs[3] = new Vector2();
        locs[3].x = -1;
        locs[3].y = 0;


        for(int i=0;i<4;i++)
        {
            int c;
            if(location.tiles[positionX+(int)locs[i].x][positionY+(int)locs[i].y].IsDense())
            {
                c = 1;
            }
            else
            {
                if(location.tiles[positionX+(int)locs[i].x][positionY+(int)locs[i].y].ContainsClass(AMarker.class))
                {
                    c = 2;
                }
                else
                {
                    c=0;
                }
            }
            int mnoznik=1;
            for(int j=0;j<i;j++) mnoznik *= 3;
            //System.out.println(i+": "+(c*mnoznik));
            ret+=c*mnoznik;
        }
        return ret;
    }

    public boolean IsMouseStuck(Vector2 plannedMove)
    {
        return false;
    }

    public void MoveByScenario()
    {
        int scenarioNumber = GetScenarioID();
        int choice = scenarioTable[scenarioNumber];
        //System.out.println(choice);
        switch (choice)
        {
            case 1:
                StepIntoDirection(1,0);
                break;
            case 2:
                StepIntoDirection(0,1);
                break;
            case 3:
                StepIntoDirection(-1,0);
                break;
            case 4:
                StepIntoDirection(0,-1);
                break;
        }

    }

    @Override
    public Object[] GetGenes() {
        return scenarioTable;
    }

    @Override
    public void SetGenes(Object[] genes) {
        scenarioTable = new Integer[genes.length];
        for(int i=0;i<genes.length;i++) scenarioTable[i] = (Integer)genes[i];
    }

    @Override
    public double GetGrades() {
        return grade;
    }

    @Override
    public void SetGrades(double grade) {
        if(!dontGrade)
        this.grade = grade;
    }

    @Override
    public Geneable CreateEmptyChild() {

        Geneable ret = null;
        try {
            ret = new ASIMouse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void Mutate(double chanceTo) {
        Random random = new Random();

        for(int i=0;i<scenarioTable.length;i++)
        {
            double randChange = random.nextDouble()%1.0;
            if(randChange<chanceTo)
            {
                scenarioTable[i] = random.nextInt(4)+1;
            }
        }

    }

    @Override
    public void MyRoutine(double dTime) {
        super.MyRoutine(dTime);

        if(stop) return;
        if(location == null) return;

        if(moveRemainingCooldown <= 0)
        {
            MoveByScenario();
        }

    }
}
