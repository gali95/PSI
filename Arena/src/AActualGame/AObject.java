package AActualGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Lach on 2016-10-13.
 */
public class AObject {

    BufferedImage img;
    AMap location;
    int positionX,positionY;
    ADetailedObjectMovement physics;
    Boolean garbageDay;
    Boolean density;

    public AObject(String imgPath) throws IOException {
        physics = new ADetailedObjectMovement();
        physics.target = this;
        img  = ImageIO.read(new File(imgPath));
        garbageDay = false;
        density = false;
        location = null;
    }
    public AObject(int graphicFileIndex)
    {
        physics = new ADetailedObjectMovement();
        physics.target = this;
        img = AGraphicFiles.materials[graphicFileIndex];
        garbageDay = false;
        density = false;
        location = null;
    }
    public void InteractWith(AObject other)
    {

    }
    public void Destroy()
    {
        garbageDay = true;
        //if(location != null)
        //{
        //    location.tiles[positionX][positionY].content.remove(this);
        //    location.allObjects.remove(this);
        //}
    }
    public void MyRoutine(double dTime)
    {

    }

}
