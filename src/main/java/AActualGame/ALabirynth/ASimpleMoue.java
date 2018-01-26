package AActualGame.ALabirynth;

import AActualGame.ACharacter;
import Matho.Vector2;

import java.io.IOException;

/**
 * Created by Lach on 2017-01-02.
 */
public class ASimpleMoue extends ACharacter {  // uproszczona mysz ktora działa na ręcznie zaimplementowanej prostej inteligencji

    public ASimpleMoue() throws IOException {
        super(10);
        moveSpeed = 10;
    }

    public void Move()
    {
        Vector2 ch1=null,ch2=null;
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
            if(!location.tiles[positionX+(int)locs[i].x][positionY+(int)locs[i].y].IsDense()) {
                if (location.tiles[positionX + (int) locs[i].x][positionY + (int) locs[i].y].ContainsClass(AMarker.class)) {
                    System.out.println("2");
                    ch2 = locs[i];
                } else {
                    System.out.println("1");
                    ch1 = locs[i];
                }
            }

        }
        System.out.println(" ");
        if(ch1 != null)
        {
            System.out.println("stepam 1: "+ (positionX+(int)ch1.x) + " " + (positionY+(int)ch1.y));
            StepIntoDirection((int)ch1.x,(int)ch1.y);
            return;
        }
        if(ch2 != null)
        {
            System.out.println("stepam 2: "+ (positionX+(int)ch2.x) + " " + (positionY+(int)ch2.y));
            StepIntoDirection((int)ch2.x,(int)ch2.y);
        }

    }

    @Override
    public void MyRoutine(double dTime) {
        super.MyRoutine(dTime);

        if(moveRemainingCooldown <= 0)
        {
            //System.out.println("myszu myszu");
            Move();
        }

    }
}
