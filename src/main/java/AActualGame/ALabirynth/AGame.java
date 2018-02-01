package AActualGame.ALabirynth;

import AActualGame.*;
import GUIStuff.MainWindow;
import GUIStuff.ProgressLabel;
import Matho.Vector2;
import com.sun.media.sound.AlawCodec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lach on 2017-01-01.
 */
public class AGame {   // logika całej rozrywki labiryntu, umożliwiajac jego stworzenie, wraz z wczytaniem pozycji scian z pliku, oraz przebiegu calej rozgrywki, umieszczeniem obiektu "myszy" na planszy


    public AGameTimes times;
    public AMap map;
    public ADisplay dsp;
    public Vector2 serLoc,startLoc;
    public ACharacter hero;
    public boolean zakoncz;
    public int testNum;
    public ProgressLabel progressu;
    public MainWindow fata;
    public double timeTick,maxTime;
    public double actTime;

    public AGame(ADisplay disp)
    {
        zakoncz = false;
        dsp = disp;
        map = new AMap();
        map.InitEmpty(10,10,0);
        dsp.source = map;
        serLoc = new Vector2();
        startLoc = new Vector2();
        try {
            hero = new ASimpleMoue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //map.player = (APlayer)hero;
    }

    public AGame(ACharacter hero,int testNum)
    {
        times = new AGameTimes();
        times.init.Start();
        zakoncz = false;
        map = new AMap();
        map.InitEmpty(10,10,0);
        serLoc = new Vector2();
        startLoc = new Vector2();
        this.testNum = testNum;
        this.hero = hero;

    }

    public void SetDisplay(ADisplay disp)
    {
        dsp = disp;
        dsp.source = map;
    }

    public void Sciany()
    {
        Vector2[][] gained = ReadCordsFromFile();
        map.InsertWalls(gained[0]);
        InsertCheckpoints(gained[1]);
        map.InsertObject(hero,(int)startLoc.x,(int)startLoc.y);
        map.InsertObject(new ACheese(this),(int)serLoc.x,(int)serLoc.y);
        try {
            map.InsertObject(new AMarkSeeder(hero),(int)startLoc.x,(int)startLoc.y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void InsertCheckpoints(Vector2[] locs)
    {
        if(locs==null) return;
        for(int i=0;i<locs.length;i++)
        {
            try {
                map.InsertObject(new ACheckPoint(),(int)locs[i].x,(int)locs[i].y);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Vector2[][] ReadCordsFromFile()
    {
        times.readFromFile.Start();
        if(ALabirynthFromFile.readed!=null)
        {
            startLoc = ALabirynthFromFile.start;
            serLoc = ALabirynthFromFile.koniec;
            times.readFromFile.Stop();
            return ALabirynthFromFile.readed;
        }
        times.readFromFile.Start();
        Scanner fl = null;
        fl = new Scanner(this.getClass().getResourceAsStream("/labirynt.txt"));
        int[] chars = new int[100];
        int sum = 0,sum2 = 0;
        for(int i=0;i<100;i++)
        {
            int chara = fl.nextInt();
            chars[i] = chara;
            if(chara==1)
            {
                sum++;
            }
            if(chara==2)
            {
                sum2++;
            }
        }
        Vector2[] ret = new Vector2[sum],ret2 = new Vector2[sum2];
        int j=0,j2=0;
        for(int i=0;i<100;i++)
        {
            if(chars[i]==1)
            {
                ret[j++] = new Vector2(i%10,i/10);
            }
            else if(chars[i]==2)
            {
                ret2[j2++] = new Vector2(i%10,i/10);
            }
        }
        startLoc.Set((double)fl.nextInt(),(double)fl.nextInt());
        serLoc.Set((double)fl.nextInt(),(double)fl.nextInt());

        Vector2[][] lastRet = new Vector2[2][];
        lastRet[0] = ret;
        lastRet[1] = ret2;

        fl.close();
        ALabirynthFromFile.readed = lastRet;
        ALabirynthFromFile.start = startLoc;
        ALabirynthFromFile.koniec = serLoc;
        times.readFromFile.Stop();
        return lastRet;
    }

    public void RunWIthoutGraphic()
    {
        times.spawning.Start();
        Sciany();
        times.spawning.Stop();
        actTime = 0;
        while(!zakoncz)
        {
            times.physicRoutine.Start();
            /*
            try {
                TimeUnit.MILLISECONDS.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            map.PhysicRoutine(timeTick);
            actTime+=timeTick;
            times.physicRoutine.Stop();
            if(actTime>maxTime)
            {
                times.reset.Start();
                Reset();
                actTime = 0;
                times.reset.Stop();
            }
        }

        /*
        {
            System.out.println(times.init);
            System.out.println(times.spawning);
            System.out.println(times.readFromFile);
            System.out.println(times.physicRoutine);
            System.out.println(times.reset);
        }
        */
    }

    public void GameFinished()
    {
        Reset();
    }

    public void Reset()
    {
        actTime = 0;
        if(progressu.Increase())
        {
            fata.SortBreeder();
            fata.UpdateStatistic();
        }
        hero.physics = new ADetailedObjectMovement();
        hero.physics.target = hero;
        testNum--;
        if(testNum>0) {
            map.RemoveAllObjects();
            //hero = this.hero;

            //map.player = (APlayer)hero;

            Sciany();
        }
        else
        {
            zakoncz = true;
        }
    }




}
