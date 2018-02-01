package AActualGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Lach on 2016-12-03.
 */
public class AGraphicFiles {

    private static BufferedImage[] materials;
    private static boolean notSetYet;

    public static BufferedImage getMaterial(int index)
    {
        if(notSetYet)
        {
            AGraphicFiles agf = new AGraphicFiles();
            agf.SetIt();
            notSetYet = false;
        }
        return materials[index];
    }

    static
    {
        notSetYet = true;
    }

    public void SetIt()
    {
        materials = new BufferedImage[11];
        String ImgSourcePath = "/graphics/";

        try {
            materials[0] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"tile.jpg"));
            materials[1] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"claws.png"));
            materials[2] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"fireball.png"));
            materials[3] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"player.png"));
            materials[4] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"enemy.png"));
            materials[5] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"mark.png"));
            materials[6] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"checkpoint.png"));
            materials[7] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"cheese.png"));
            materials[8] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"invisible.png"));
            materials[9] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"warru.png"));
            materials[10] = ImageIO.read(AGraphicFiles.class.getResourceAsStream(ImgSourcePath+"mouse.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
