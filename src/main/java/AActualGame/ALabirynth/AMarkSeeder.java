package AActualGame.ALabirynth;

import AActualGame.AObject;
import Matho.Vector2;

import java.io.IOException;

/**
 * Created by Lach on 2017-01-02.
 */
public class AMarkSeeder extends AObject {  // niewidzialny obiekt podążający za myszą i sprawiający że zostawia za sobą ślady

    boolean following;
    AObject target;

    public AMarkSeeder(AObject follow) throws IOException {
        super(8);
        following = false;
        target = follow;
    }

    @Override
    public void MyRoutine(double dTime) {
        super.MyRoutine(dTime);

        if(location != null && following)
        {
            if(!(location.tiles[positionX][positionY].ContainsClass(AMarker.class)))
            {
                try {
                    location.InsertObject(new AMarker(),positionX,positionY);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(location != null && !following)
        {
            following = true;
            //location.MoveObject(this,target.positionX-positionX,target.positionY-positionY);
            physics.FollowSomeone(target,new Vector2(0,0));
        }

    }
}
