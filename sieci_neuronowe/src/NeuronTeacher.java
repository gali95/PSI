import java.util.Random;

/**
 * Created by Lach on 2016-10-09.
 */
public class NeuronTeacher {

    Neuron subject;
    double n;
    protected double[] testEntries;
    double testResult;
    double neuronResult;
    Boolean correctResult;

    public void CreateFreshman()
    {
        subject = new Neuron();
        n = 1;
    }
    public void CreateEntries(int x)
    {
        Random random = new Random();

        for(int i=0;i<x;i++)
        {
            subject.AddEntry();
        }
        for(int i=0;i<x;i++)
        {
            subject.AccessEntry(i).setWeight(((double)random.nextInt(1001)/1000));
        }
        subject.setB(0);
    }
    public void GenerateANDTest(int i)
    {
        i = i%4;
        testEntries = new double[2];
        testEntries[0] = i%2;
        testEntries[1] = i/2;
        if(testEntries[0]+testEntries[1]>=2) testResult = 1;
        else testResult = 0;
    }
    public void GenerateORTest(int i)
    {
        i = i%4;
        testEntries = new double[2];
        testEntries[0] = i%2;
        testEntries[1] = i/2;
        if(testEntries[0]+testEntries[1]>=1) testResult = 1;
        else testResult = 0;
    }
    public void GenerateNOTTest(int i)
    {
        testEntries = new double[1];
        testEntries[0] = i;
        if(testEntries[0]==1) testResult = 0;
        else testResult = 1;
    }
    public void GenerateRedPointTest()
    {
        testEntries = new double[3];
        Random rnd = new Random();
        testEntries[0] = rnd.nextInt(256);
        testEntries[1] = rnd.nextInt(256);
        testEntries[2] = rnd.nextInt(256);
        if(testEntries[0] >= 0.5*(testEntries[0]+testEntries[2]+testEntries[1]))  testResult = 1;
        else testResult = 0;
    }
    public void TestNeuron()
    {
        if(testEntries.length != subject.GetEntriesSize())
        {
            correctResult = false;
            return;
        }
        for(int i=0;i<testEntries.length;i++)
        {
            subject.AccessEntry(i).setValue(testEntries[i]);
        }
        subject.CalcExit();
        correctResult = true;
        neuronResult = subject.getExitValue();
    }

    public void ModifyWeights()
    {
        for(int i=0;i<subject.GetEntriesSize();i++)
        {
            subject.AccessEntry(i).setWeight(subject.AccessEntry(i).getWeight()+(n*(testResult-neuronResult)*subject.AccessEntry(i).getValue()));
        }
        subject.setB(subject.getB()+(n*(testResult-neuronResult)));
    }

    public void LearningAND() {
        int learningPhases = 10;

        double[] results = new double[learningPhases + 1];

        CreateFreshman();
        CreateEntries(2);

        results[0] = CheckANDQuality();

        for (int i = 0; i < learningPhases; i++) {
            for(int j=0;j<4;j++) {
                GenerateANDTest(j);
                TestNeuron();
                if (neuronResult != testResult) ModifyWeights();
            }
                results[i + 1] = CheckANDQuality();
        }
        for (int i = 0; i < learningPhases; i++) {
            System.out.println((i+1) + ". " + results[i]);
        }
        return;
    }

    public int StepsToLearnAND(double minEfficency, double newN) {


        CreateFreshman();

        double prevN = n;
        n = newN;

        CreateEntries(2);

        double actEfficency = CheckANDQuality();
        if(actEfficency >= minEfficency)
        {
            n = prevN;
            return 0;
        }

        int i=0;

        for (; ; i++) {
            for(int j=0;j<4;j++) {
                GenerateANDTest(j);
                TestNeuron();
                if (neuronResult != testResult) ModifyWeights();
            }
            actEfficency = CheckANDQuality();
            if(actEfficency >= minEfficency) break;
        }

        n = prevN;
        return i+1;
    }

    public int StepsToLearnOR(double minEfficency, double newN)
    {
        double prevN = n;
        n = newN;
        CreateFreshman();
        CreateEntries(2);

        double actEfficency = CheckORQuality();
        if(actEfficency >= minEfficency)
        {
            n = prevN;
            return 0;
        }

        int i=0;

        for (; ; i++) {
            for(int j=0;j<4;j++) {
                GenerateORTest(j);
                TestNeuron();
                if (neuronResult != testResult) ModifyWeights();
            }
            actEfficency = CheckORQuality();
            if(actEfficency >= minEfficency) break;
        }

        n = prevN;
        return i+1;
    }

    public int StepsToLearnNOT(double minEfficency, double newN)
    {

        double prevN = n;
        n = newN;
        CreateFreshman();
        CreateEntries(1);

        double actEfficency = CheckNOTQuality();
        if(actEfficency >= minEfficency)
        {
            n = prevN;
            return 0;
        }

        int i=0;

        for (; ; i++) {
            for(int j=0;j<2;j++) {
                GenerateNOTTest(j);
                TestNeuron();
                if (neuronResult != testResult) ModifyWeights();
            }
            actEfficency = CheckNOTQuality();
            if(actEfficency >= minEfficency) break;
        }

        n = prevN;
        return i+1;
    }

    public void LearningOR() {
        int learningPhases = 10;

        double[] results = new double[learningPhases + 1];

        CreateFreshman();
        CreateEntries(2);

        results[0] = CheckORQuality();

        for (int i = 0; i < learningPhases; i++) {
            for(int j=0;j<4;j++) {
                GenerateORTest(j);
                TestNeuron();
                if (neuronResult != testResult) ModifyWeights();
            }
            results[i + 1] = CheckORQuality();
        }
        for (int i = 0; i < learningPhases; i++) {
            System.out.println((i+1) + ". " + results[i]);
        }
        return;
    }

    public void LearningNOT()
    {
        int learningPhases = 10;

        double[] results = new double[learningPhases + 1];

        CreateFreshman();
        CreateEntries(1);

        results[0] = CheckNOTQuality();

        for (int i = 0; i < learningPhases; i++) {
            for(int j=0;j<4;j++) {
                GenerateNOTTest(j);
                TestNeuron();
                if (neuronResult != testResult) ModifyWeights();
            }
            results[i + 1] = CheckNOTQuality();
        }
        for (int i = 0; i < learningPhases; i++) {
            System.out.println((i+1) + ". " + results[i]);
        }
        return;
    }

    public void LearningRED()
    {
        int learningPhases = 100;
        int testPhases = 100;

        double[] results = new double[learningPhases + 1];

        CreateFreshman();
        CreateEntries(3);

        results[0] = CheckREDQuality(testPhases);

        for (int i = 0; i < learningPhases; i++) {

                GenerateRedPointTest();
                TestNeuron();
                if (neuronResult != testResult) ModifyWeights();
            results[i + 1] = CheckREDQuality(testPhases);
        }
        for (int i = 0; i < learningPhases; i++) {
            System.out.println((i+1) + ". " + results[i]);
        }
        return;
    }

    public double CheckANDQuality()
    {
        int correct = 0;
            for(int j=0;j<4;j++) {
                GenerateANDTest(j);
                TestNeuron();
                if (!correctResult) System.out.printf("zla ilosc argumentow!");
                if (neuronResult == testResult) correct++;
            }
        return ((double)(correct) / (double)4);
    }

    public double CheckNOTQuality()
    {
        int correct = 0;
        for(int j=0;j<2;j++) {
            GenerateNOTTest(j);
            TestNeuron();
            if (!correctResult) System.out.printf("zla ilosc argumentow!");
            if (neuronResult == testResult) correct++;
        }
        return ((double)(correct) / (double)2);
    }
    public double CheckORQuality()
    {
        int correct = 0;
        for(int j=0;j<4;j++) {
            GenerateORTest(j);
            TestNeuron();
            if (!correctResult) System.out.printf("zla ilosc argumentow!");
            if (neuronResult == testResult) correct++;
        }
        return ((double)(correct) / (double)4);
    }

    public double CheckREDQuality(int testQUantity)
    {
        int correct = 0;
        for(int j=0;j<testQUantity;j++) {
            GenerateRedPointTest();
            TestNeuron();
            if (!correctResult) System.out.printf("zla ilosc argumentow!");
            if (neuronResult == testResult) correct++;
        }
        return ((double)(correct) / (double)testQUantity);
    }

    public void PrintTest()
    {
        System.out.println(testEntries[0]+ " " +testEntries[1] + " " + testResult);
    }
    public void TestANDTest()
    {
        for(int i=0;i<100;i++)
        {
            //GenerateANDTest();
            PrintTest();
        }
    }

    public static void main(String[] entry)
    {
        NeuronTeacher temp = new NeuronTeacher();
        //System.out.println(temp.StepsToLearnAND(1));

        int timesTest =100000;
        double startN = 0.1;
        double endN = 1;
        double changeN = 0.1;
        int resultSize = (int)((endN-startN)/changeN);
        double[] ANDresult=new double[resultSize],ORResult=new double[resultSize],NOTResult=new double[resultSize];

        for(int i=0;i<resultSize;i++)
        {
            double nVal = startN + (changeN*i);

            ANDresult[i] = 0;
            ORResult[i] = 0;
            NOTResult[i] = 0;

            for(int j=0;j<timesTest;j++) {
                ANDresult[i] += temp.StepsToLearnAND(1, nVal);
                ORResult[i] += temp.StepsToLearnOR(1, nVal);
                NOTResult[i] += temp.StepsToLearnNOT(1, nVal);
            }

            ANDresult[i] /= timesTest;
            ORResult[i] /= timesTest;
            NOTResult[i] /= timesTest;

        }

        System.out.println("AND: ");
        for(int i=0;i<resultSize;i++)
        {
            System.out.println(ANDresult[i]);
        }



        System.out.println("OR: ");
        for(int i=0;i<resultSize;i++)
        {
            System.out.println(ORResult[i]);
        }

        System.out.println("NOT: ");
        for(int i=0;i<resultSize;i++)
        {
            System.out.println(NOTResult[i]);
        }



        return;
    }

}
