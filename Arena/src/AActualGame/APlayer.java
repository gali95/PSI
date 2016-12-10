package AActualGame;

import java.io.IOException;

/**
 * Created by Lach on 2016-11-03.
 */
public class APlayer extends ACharacter {


    public APlayer() throws IOException {
        super(3);
        moveSpeed = 20;
    }
    public void Destroy()
    {
        location.player = null;
        super.Destroy();
    }

}
