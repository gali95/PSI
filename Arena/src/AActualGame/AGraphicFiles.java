package AActualGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Lach on 2016-12-03.
 */
public class AGraphicFiles {

    public static BufferedImage[] materials;

    static
    {

        SetIt();

    }

    public static void SetIt()
    {
        materials = new BufferedImage[11];

        try {
            materials[0] = ImageIO.read(new File("tile.jpg"));
            materials[1] = ImageIO.read(new File("claws.png"));
            materials[2] = ImageIO.read(new File("fireball.png"));
            materials[3] = ImageIO.read(new File("player.png"));
            materials[4] = ImageIO.read(new File("enemy.png"));
            materials[5] = ImageIO.read(new File("mark.png"));  ///
            materials[6] = ImageIO.read(new File("checkpoint.png"));
            materials[7] = ImageIO.read(new File("cheese.png"));
            materials[8] = ImageIO.read(new File("invisible.png"));
            materials[9] = ImageIO.read(new File("warru.png"));
            materials[10] = ImageIO.read(new File("mouse.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
