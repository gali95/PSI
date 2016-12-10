package NNetworks.DoubleEvolutionNetwork;


import Interfaces.AHeritage;
import NNetworks.EvolutionaryNeuronNetwork;
import NNetworks.Neuron;
import NNetworks.NeuronNetwork;

import java.io.Serializable;

/**
 * Created by Lach on 2016-12-03.
 */
public class NPCNetwork implements Comparable<NPCNetwork>, AHeritage, Serializable {

    public EvolutionaryNeuronNetwork movement,attack;
    private double grade;
    private int gradesDone;
    private int repeatMultipleNumber;
    public Boolean gamesInProcess;

    public double getGrade() {
        System.out.print("NPCNetwork");
        return grade;
    }

    public void setGrade(double grade) {
        System.out.print("NPCNetwork");
        this.grade = grade;
    }

    public int getRepeatMultipleNumber() {
        System.out.print("NPCNetwork");
        return repeatMultipleNumber;
    }

    public int getGradesDone() {
        return gradesDone;
    }

    public void ResetGradesDone() {
        this.gradesDone = 0;
    }

    public void setRepeatMultipleNumber(int repeatMultipleNumber) {
        System.out.print("NPCNetwork");
        this.repeatMultipleNumber = repeatMultipleNumber;
    }

    public NPCNetwork()
    {
        movement = new EvolutionaryNeuronNetwork();
        attack = new EvolutionaryNeuronNetwork();
        gradesDone = 0;
        gamesInProcess = false;
    }
    public NPCNetwork(Neuron neuronType)
    {
        movement = new EvolutionaryNeuronNetwork(neuronType);
        attack = new EvolutionaryNeuronNetwork(neuronType);
    }

    public int compareTo(NPCNetwork other)
    {
        Double first = new Double(this.grade);
        Double second = new Double(other.grade);
        return (first.compareTo(second));
    }

    @Override
    public void GameEnded(double characterPoints) {
        grade += characterPoints;
        gradesDone++;
    }


}
