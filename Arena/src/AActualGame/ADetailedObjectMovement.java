package AActualGame;

import Matho.Math2D;
import Matho.Vector2;

/**
 * Created by Lach on 2016-10-30.
 */
public class ADetailedObjectMovement {

    double speedX,speedY;
    int finishX,finishY;
    double detailedPositionX,detailedPositionY;
    Boolean immobilized;
    double immobilizedTimeLeft;
    AObject target;
    AObject lockedFollowTarget;
    Vector2 lockedFollowOffset;

    public ADetailedObjectMovement()
    {
        speedY = 0;
        speedY = 0;
        immobilized = false;
        lockedFollowTarget = null;
    }
    public void FollowSomeone(AObject target, Vector2 offset)
    {
        lockedFollowTarget = target;
        lockedFollowOffset = new Vector2((int)offset.x,(int)offset.y);
    }
    public void UnfollowSomeone()
    {
        lockedFollowTarget = null;
    }
    public void ImmobilizeForTime(double ammount)
    {
        if(lockedFollowTarget!=null) return;
        if(speedX != 0 || speedY != 0) return;
        if(immobilized) return;
        if(ammount<=0) return;
        immobilized = true;
        immobilizedTimeLeft = ammount;
    }
    public Vector2 CountMovement(double deltaTime)
    {

        Vector2 ret = new Vector2();
        ret.x = 0;
        ret.y = 0;
        if(lockedFollowTarget!=null)
        {
            return CountFollow();
        }
        if(immobilized)
        {
            immobilizedTimeLeft -= deltaTime;
            if(immobilizedTimeLeft<=0) immobilized = false;
            return ret;
        }
        if(speedX == 0 && speedY == 0)
        {
            //System.out.println("nic sie nie dzieje, nananana");
            return ret;
        }

        double posWithDetailsX = target.positionX + detailedPositionX;
        double posWithDetailsY = target.positionY + detailedPositionY;

        Boolean ShortenDistanceFlag = false;

        double preToEndDistance = Math2D.CountDistance(posWithDetailsX,posWithDetailsY,finishX,finishY);
        if(preToEndDistance==0)
        {

            speedX=0;
            speedY=0;
            return ret;
        }
        else
        {
            double afterToEndDistance = Math2D.CountDistance(posWithDetailsX+speedX*deltaTime,posWithDetailsY+speedY*deltaTime,finishX,finishY);
            if(preToEndDistance <= afterToEndDistance)
            {
                ShortenDistanceFlag = true;
            }
            else
            {
                double nextToEndDistance = Math2D.CountDistance(posWithDetailsX+speedX*deltaTime*2,posWithDetailsY+speedY*deltaTime*2,finishX,finishY);
                if(preToEndDistance < nextToEndDistance)
                {
                    ShortenDistanceFlag = true;
                }
            }
        }



        if(ShortenDistanceFlag)
        {
            detailedPositionX = (double)finishX - target.positionX;
            detailedPositionY = (double)finishY - target.positionY;
            //speedX = 0;
            //speedY = 0;
        }
        else {
            detailedPositionX += speedX * deltaTime;
            detailedPositionY += speedY * deltaTime;
        }
        //System.out.println(speedX * deltaTime + " + " + speedY * deltaTime);

        int movementX=0,movementY=0;

        while (detailedPositionX > 0.5)
        {
            detailedPositionX -= 1;
            movementX++;
        }
        while (detailedPositionX <= -0.5)
        {
            detailedPositionX += 1;
            movementX--;
        }
        while (detailedPositionY > 0.5)
        {
            detailedPositionY -= 1;
            movementY++;
        }
        while (detailedPositionY <= -0.5)
        {
            detailedPositionY += 1;
            movementY--;
        }

        ret.x = movementX;
        ret.y = movementY;
        return ret;
        // przesun obiekt na mapie o (movementX,movementY);


    }
    public Vector2 CountFollow()
    {
        Vector2 ret = new Vector2();
        ret.y = 0;
        ret.x = 0;
        if(!IsMovePossible(lockedFollowTarget.positionX + (int)lockedFollowOffset.x,lockedFollowTarget.positionY + (int)lockedFollowOffset.y))
        {
            target.Destroy();
            return ret;
        }
        detailedPositionX = lockedFollowTarget.physics.detailedPositionX;
        detailedPositionY = lockedFollowTarget.physics.detailedPositionY;
        ret.x = (lockedFollowTarget.positionX + lockedFollowOffset.x) -  target.positionX;
        ret.y = (lockedFollowTarget.positionY + lockedFollowOffset.y) -  target.positionY;
        return ret;
    }
    public Boolean IsMovePossible(int targetX,int targetY)
    {
        if(targetX<0) return false;
        if(targetY<0) return false;
        if(targetX>=target.location.tiles.length) return false;
        if(targetY>=target.location.tiles[0].length) return false;
        return true;
    }
    public void MoveToInTime(int targetX,int targetY,double duration)
    {
        if(!IsMovePossible(targetX,targetY)) return;
        if(lockedFollowTarget!=null) return;
        if(immobilized) return;
        if(duration <= 0) return;
        if(speedX != 0 || speedY != 0) return;
        finishX = targetX;
        finishY = targetY;
        speedX = (targetX - target.positionX)/duration;
        speedY = (targetY - target.positionY)/duration;
    }
    public void MoveToWithSpeed(int targetX,int targetY,double speed)
    {
        if(!IsMovePossible(targetX,targetY)) return;
        if(lockedFollowTarget!=null) return;
        if(immobilized) return;
        if(speed <= 0 ) return;
        if(speedX != 0 || speedY != 0) return;
        double fullLength = Math2D.CountDistance(target.positionX,target.positionY,targetX,targetY);
        double duration = fullLength/speed;
        speedX = (targetX - target.positionX) / duration;
        speedY = (targetY - target.positionY) / duration;
        finishX = targetX;
        finishY = targetY;

        //System.out.println("FAS");
        //System.out.println(targetX + " + " + target.positionX);
    }

}
