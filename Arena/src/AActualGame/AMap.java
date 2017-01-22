package AActualGame;

import AActualGame.ALabirynth.AWall;
import Matho.Vector2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lach on 2016-10-13.
 */
public class AMap {

    public AMapTile[][] tiles;
    List<AObject> allObjects;
    List<AFireBall> fireballs;
    public APlayer player;
    public AGame zarzadzacz;


    public AMap()
    {
        allObjects = new LinkedList<AObject>();
    }
    public void InitEmpty(int x,int y,String tilesImgPath)
    {
        tiles = new AMapTile[x][y];
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                try {
                    tiles[i][j] = new AMapTile(tilesImgPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void InsertWalls(Vector2[] locs)
    {
        for(int i=0;i<locs.length;i++)
        {
            InsertObject(new AWall(),(int)locs[i].x,(int)locs[i].y);
        }
    }
    public void InitEmpty(int x,int y,int imgIndex)
    {
        tiles = new AMapTile[x][y];
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                try {
                    tiles[i][j] = new AMapTile(imgIndex);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Boolean TileExist(int x,int y)
    {
        if(x<0) return false;
        if(y<0) return false;
        if(x>=tiles.length) return false;
        if(y>=tiles[0].length) return false;
        return true;
    }
    public void MoveObject(AObject target,int xMove,int yMove)
    {
        if(xMove==0 && yMove==0) return;
        int newX = target.positionX+xMove;
        int newY = target.positionY+yMove;

        /*
        if(target.density && tiles[newX][newY].IsDense())
        {
            return;
        }
        */

        tiles[newX][newY].content.add(target);
        tiles[target.positionX][target.positionY].content.remove(target);

        target.positionX = newX;
        target.positionY = newY;

        tiles[newX][newY].InteractionWithAll(target);

    }
    public void InsertObject(AObject target, int x, int y)
    {
        /*
        if(target.density && tiles[x][y].IsDense())
        {
            return;
        }
        */

        tiles[x][y].content.add(target);
        target.positionY = y;
        target.positionX = x;
        target.location = this;



        tiles[x][y].InteractionWithAll(target);
        allObjects.add(target);
    }
    public void AddPlayer(int x,int y)
    {
        try {
            player = new APlayer();
            InsertObject(player,x,y);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void PlayerMouseReleased(int x,int y)
    {
        player.CastFireball(x,y);
        //System.out.println(x + " + " + y);
    }
    public void PhysicRoutine(double dTime)
    {
        for(int i=0;i<allObjects.size();i++)
        {
            Vector2 ret = allObjects.get(i).physics.CountMovement(dTime);
            allObjects.get(i).MyRoutine(dTime);
            MoveObject(allObjects.get(i),(int)ret.x,(int)ret.y);
            //System.out.println(allObjects.get(i).physics.detailedPositionY);
        }
        GarbageRoutine();
        if(zarzadzacz!=null)zarzadzacz.MyRoutine(dTime);
    }
    public void RemoveAllObjects()
    {
        for(int i=0;i<allObjects.size();i++)
        {
            if(allObjects.get(i).location != null)
            {
                tiles[allObjects.get(i).positionX][allObjects.get(i).positionY].content.remove(allObjects.get(i));
                allObjects.remove(allObjects.get(i));
                i--;
            }
        }
    }
    public void GarbageRoutine()
    {
        for(int i=0;i<allObjects.size();i++)
        {
            if(allObjects.get(i).garbageDay)
            {
                if(allObjects.get(i).location != null)
                {
                    tiles[allObjects.get(i).positionX][allObjects.get(i).positionY].content.remove(allObjects.get(i));
                    allObjects.remove(allObjects.get(i));
                    i--;
                }
            }
        }
    }
    public Boolean[][] IsThereFloor(Vector2 middleLoc, Vector2 Range)
    {
        Boolean[][] ret = new Boolean[(int)Range.x*2+1][(int)Range.y*2+1];
        ADetailedObjectMovement test= new ADetailedObjectMovement();
        for(int i=0;i<(int)Range.x*2+1;i++)
        {
            for(int j=0;j<(int)Range.y*2+1;j++)
            {
                ret[i][j] = TileExist((int)middleLoc.x-(int)Range.x+i,(int)middleLoc.y-(int)Range.y+j);
            }
        }
        return ret;
    }
    public Boolean[][] IsThereFireball(Vector2 middleLoc, Vector2 Range)
    {
        Boolean[][] ret = new Boolean[(int)Range.x*2+1][(int)Range.y*2+1];
        ADetailedObjectMovement test= new ADetailedObjectMovement();
        int startX=0,startY=0,endX=(int)Range.x*2+1,endY=(int)Range.y*2+1;
        for(int i=startX;i<endX;i++)
        {
            for(int j=startY;j<endY;j++)
            {
                if(TileExist((int)middleLoc.x-(int)Range.x+i,(int)middleLoc.y-(int)Range.y+j)) {
                    ret[i][j] = tiles[(int) middleLoc.x - (int) Range.x + i][(int) middleLoc.y - (int) Range.y + j].ContainsClass(AFireBall.class);
                }
                else ret[i][j] = false;
            }
        }
        return ret;
    }
    public Boolean[][] IsThereObject(Vector2 middleLoc, Vector2 Range, AObject targeto)
    {
        Boolean[][] ret = new Boolean[(int)Range.x*2+1][(int)Range.y*2+1];
        ADetailedObjectMovement test= new ADetailedObjectMovement();
        int startX=0,startY=0,endX=(int)Range.x*2+1,endY=(int)Range.y*2+1;
        for(int i=startX;i<endX;i++)
        {
            for(int j=startY;j<endY;j++)
            {
                if(TileExist((int)middleLoc.x-(int)Range.x+i,(int)middleLoc.y-(int)Range.y+j)) {
                    ret[i][j] = tiles[(int) middleLoc.x - (int) Range.x + i][(int) middleLoc.y - (int) Range.y + j].ContainsObject(targeto);
                }
                else ret[i][j] = false;
            }
        }
        return ret;
    }
    public double[] getFireballsData()
    {
        double[] ret = new double[6];
        int s=0;
        for(int i=0;i<ret.length;i++) ret[i] = 0;
        for(int i=0;i<allObjects.size() && s<2;i++)
        {
            if(allObjects.get(i).getClass()==AFireBall.class)
            {
                ret[s*3] = 1;
                ret[s*3+1] = allObjects.get(i).positionX;
                ret[s*3+2] = allObjects.get(i).positionY;
                s++;
            }
        }
        return ret;

    }

    public int GetSizeX()
    {
        return tiles.length;
    }
    public int GetSizeY()
    {
        return tiles[0].length;
    }

}
