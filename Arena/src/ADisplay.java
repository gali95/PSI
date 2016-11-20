import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

/**
 * Created by Lach on 2016-10-31.
 */
public class ADisplay extends JPanel {

    int tileSize;
    int sizeX,sizeY;
    public AMap source;
    int sourceStartX,sourceStartY;
    int sourceEndX,sourceEndY;
    Graphics2D g2d;
    BufferedImage backbuffer;

    public ADisplay()
    {
        tileSize =50;
        sizeX = 10;
        sizeY = 10;
        sourceStartY = 0;
        sourceStartY = 0;
        CreateBackBuffer();
        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {

                PlayerMouseReleased(evt);
            }
        });
    }
    public void CreateBackBuffer()
    {
        backbuffer = new BufferedImage(tileSize*sizeX,tileSize*sizeY,BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();
    }
    public Boolean Displayable(int x,int y)
    {
        if(x<sourceStartX) return false;
        if(y<sourceStartY) return false;
        if(x>sourceEndX) return false;
        if(y>sourceEndY) return false;
        return true;
    }

    public void physicComponent(double dTime)
    {
        source.PhysicRoutine(dTime);
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        try {
            paintTurf(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
        paintAllObjects(g);
        paintFromBackBuffer(g);
    }

    public void paintTurf(Graphics g) throws IOException {
        BufferedImage turf = ImageIO.read(new File("tile.jpg"));
        if(sourceStartX+sizeX > source.tiles.length) sourceEndX = source.tiles.length-1;
        else sourceEndX = sourceStartX+sizeX-1;
        if(sourceStartY+sizeY > source.tiles[0].length) sourceEndY = source.tiles[0].length-1;
        else sourceEndY = sourceStartY+sizeY-1;

        int i=sourceStartX,j=sourceStartY;
        int iC=0,jC=0;
        while(i<=sourceEndX)
        {
            while(j<=sourceEndY)
            {
                g2d.drawImage(source.tiles[i][j].turf, tileSize*iC,tileSize*jC,null);
                //g.drawImage(turf, 0,0,null);
                j++;
                jC++;
            }
            j=0;
            jC=0;
            iC++;
            i++;
        }
    }
    void PlayerMouseReleased(MouseEvent evt)
    {

        Vector2 entry = PixelsIntoCoordinates(evt.getX(),evt.getY());
        source.PlayerMouseReleased((int)entry.x,(int)entry.y);
    }
    public Vector2 PixelsIntoCoordinates(int x,int y)
    {
        Vector2 ret = new Vector2();
        ret.x = sourceStartX+(x/tileSize);
        ret.y = sourceStartY+(y/tileSize);
        return ret;
    }
    public void paintAllObjects(Graphics g)
    {
        for(int i=0;i<source.allObjects.size();i++)
        {
            g2d.drawImage(source.allObjects.get(i).img,(int)(((double)source.allObjects.get(i).positionX+source.allObjects.get(i).physics.detailedPositionX)*tileSize),(int)(((double)source.allObjects.get(i).positionY+source.allObjects.get(i).physics.detailedPositionY)*tileSize),null);
        }
    }
    public void paintFromBackBuffer(Graphics g)
    {
        g.drawImage(backbuffer,0,0,null);
    }

}
