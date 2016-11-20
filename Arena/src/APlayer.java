import java.io.IOException;

/**
 * Created by Lach on 2016-11-03.
 */
public class APlayer extends ACharacter {


    public APlayer() throws IOException {
        super("player.png");
        moveSpeed = 25;
    }
    public void Destroy()
    {
        location.player = null;
        super.Destroy();
    }

}
