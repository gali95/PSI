package AActualGame.ALabirynth;

import AActualGame.ACharacter;
import AActualGame.AObject;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import java.io.IOException;

/**
 * Created by Lach on 2017-01-01.
 */
public class ACheckPoint extends AObject {
    public ACheckPoint() throws IOException {
        super(6);
    }

    public void InteractWith(AObject other)         // obiekt ten zwieksza ocene przystosowania "myszy" która na niego wejdzie
    {
        if(other==this) return;
        if(ACharacter.class.isAssignableFrom(other.getClass()))
        {
            if(other instanceof Geneable) {
                Geneable gn = (Geneable) other;
                gn.SetGrades(gn.GetGrades()+1);
            }
            this.Destroy();
        }

    }
}
