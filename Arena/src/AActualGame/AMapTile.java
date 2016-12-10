package AActualGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lach on 2016-10-13.
 */
public class AMapTile {

    public BufferedImage turf;
    public List<AObject> content;
    public AMapTile(String imgPath) throws IOException {
        turf = ImageIO.read(new File(imgPath));
        content = new LinkedList<AObject>();
    }
    public AMapTile(int imgIndex) throws IOException {
        turf = AGraphicFiles.materials[0];
        content = new LinkedList<AObject>();
    }
    public void InteractionWithAll(AObject initiazer)
    {
        for(int i=0;i<content.size();i++)
        {
            initiazer.InteractWith(content.get(i));

        }
    }

    public Boolean ContainsClass(Class<?> type)
    {
        for(int i=0;i<content.size();i++)
        {
            if(content.get(i).getClass()==type) return true;
        }
        return false;
    }
    public Boolean ContainsSubClass(Class<?> type)
    {
        for(int i=0;i<content.size();i++)
        {
            if(type.isAssignableFrom(content.get(i).getClass())) return true;//.isAssignableFrom(type)) return true;
        }
        return false;
    }
    public Boolean ContainsObject(AObject target)
    {
        for(int i=0;i<content.size();i++)
        {
            if(target == content.get(i)) return true;
        }
        return false;
    }
    public Boolean IsDense()
    {
        for(int i=0;i<content.size();i++)
        {
            if(content.get(i).density) return true;
        }
        return false;
    }

}
