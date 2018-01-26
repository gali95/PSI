package AActualGame.ALabirynth;

import AActualGame.ACharacter;
import AActualGame.ADetailedObjectMovement;
import AActualGame.AObject;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import java.io.IOException;

/**
 * Created by Lach on 2017-01-01.
 */
public class ACheese extends AObject {

    public AGame messageTarget;

    public ACheese(AGame tg){
        super(7);
        density = false;
        messageTarget = tg;
    }
    public void InteractWith(AObject other)    // wejscie myszy na ten obiekt sprawia że rozgrywka sie kończy, rownież zwiekszajac ocene myszy
    {
        //System.out.println("bam");
        if(other==this) return;
        if(ACharacter.class.isAssignableFrom(other.getClass()))
        {
            //System.out.println("bamaaa");
            if(other instanceof Geneable) {
                Geneable gn = (Geneable) other;
                gn.SetGrades(gn.GetGrades()+1);
            }
            other.physics = new ADetailedObjectMovement();
            other.physics.target = other;
            messageTarget.GameFinished();
        }

    }
}
