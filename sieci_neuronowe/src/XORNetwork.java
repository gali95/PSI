/**
 * Created by Lach on 2016-10-16.
 */
public class XORNetwork extends NeuronNetworkTeacher{

    int testDataSeed;
    public tescik eloha;
    public int itersToLearn;

    public double dopBlad;

    public XORNetwork()
    {
        setType(new NeuronBetter());
        SetErrorCounter(new XORErrorCounter());
    }

    public int getTestDataSeed() {
        return testDataSeed;
    }

    public void setTestDataSeed(int testDataSeed) {
        this.testDataSeed = testDataSeed%4;
    }

    public void SetTestData()
    {
        testEntries = new double[2];
        testEntries[0] = testDataSeed%2;
        testEntries[1] = testDataSeed/2;

        testResult = new double[1];

        if(testEntries[0]==1 && testEntries[1]==0)
        {
            testResult[0] = 1;
        }
        else if(testEntries[0]==0 && testEntries[1]==1)
        {
            testResult[0] = 1;
        }
        else if((testEntries[0] == testEntries[1]) && (testEntries[0]==0 ||testEntries[0]==1))
        {
            testResult[0] = 0;
        }
        else testResult[0] = -1;
    }

    public double GetNetworkQuality(int testNum, int whichOne)
    {
        double qualitySum=0;
        double[] wyniki = new double[4];

            setTestDataSeed(testNum);
            RunRoutine();
            wyniki[0] = networkResult[0];
            double rozn = testResult[0] - networkResult[0];
            if(rozn <0) rozn *= -1;
            if(rozn <1-dopBlad) qualitySum++;
        /*
        if(whichOne%100 == 0) {
            //eloha.SetActualWynikLabels(wyniki);
            String wypisz = "";
            for (int i = 0; i < 4; i++) {
                wypisz += wyniki[i] + " ";
            }
            System.out.println(wypisz);
        }
        */
        return qualitySum;
    }

    public double GetNetworkQuality( int whichOne)
    {
        double qualitySum=0;
        double[] wyniki = new double[4];
        for(int i=0;i<4;i++)
        {
            setTestDataSeed(i);
            RunRoutine();
            wyniki[i] = networkResult[0];
            double rozn = testResult[0] - networkResult[0];
            if(rozn <0) rozn *= -1;
            if(rozn <1-dopBlad) qualitySum++;
        }
        if(whichOne%100 == 0) {
            //eloha.SetActualWynikLabels(wyniki);
            String wypisz = "";
            for (int i = 0; i < 4; i++) {
                wypisz += wyniki[i] + " ";
            }
            System.out.println(wypisz);
        }
        return qualitySum/4;
    }

    public void LearnUntil(double diff)
    {
        itersToLearn = 0;
        dopBlad = 1-diff;
        for(int i=0;i<4;i++)
        {
            while(GetNetworkQuality(i,itersToLearn)<1)
            {
                setTestDataSeed(i);
                LearningRoutine();
                itersToLearn++;
                if(itersToLearn%100==0) GetNetworkQuality(itersToLearn);
            }

        }


    }

}
