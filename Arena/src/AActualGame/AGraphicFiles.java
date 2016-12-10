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
        materials = new BufferedImage[5];

        try {
            materials[0] = ImageIO.read(new File("tile.jpg"));
            materials[1] = ImageIO.read(new File("claws.png"));
            materials[2] = ImageIO.read(new File("fireball.png"));
            materials[3] = ImageIO.read(new File("player.png"));
            materials[4] = ImageIO.read(new File("player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
