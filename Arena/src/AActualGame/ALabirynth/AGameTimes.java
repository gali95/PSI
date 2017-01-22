package AActualGame.ALabirynth;

import AverTimeMeasure.PartTimeMeasure;

/**
 * Created by Lach on 2017-01-04.
 */
public class AGameTimes {

    public PartTimeMeasure physicRoutine,spawning,readFromFile,init,reset;

    public AGameTimes() {

        physicRoutine = new PartTimeMeasure("physicRoutine");
        spawning = new PartTimeMeasure("spawning");
        readFromFile = new PartTimeMeasure("readFromFile");
        init = new PartTimeMeasure("init");
        reset = new PartTimeMeasure("reset");

    }
}
