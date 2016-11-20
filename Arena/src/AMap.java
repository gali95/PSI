import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lach on 2016-10-13.
 */
public class AMap {

    AMapTile[][] tiles;
    List<AObject> allObjects;
    APlayer player;
    AGame zarzadzacz;

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
        zarzadzacz.MyRoutine(dTime);
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

}
