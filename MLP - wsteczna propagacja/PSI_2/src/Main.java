import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Smaug on 2016-10-05.
 */
public class Main {

    public static void main(String[] args) {

        //NIE KAZDA NAUKA JEST POPRAWNA !!! - zbadac wagi, moze one problemem

        /*
        double[][] dataAND = {{0, 0, 0}, {0, 1, 0}, {1, 0, 0}, {1, 1, 1}};
        double[][] dataOR = {{0, 0, 0}, {0, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        double[][] dataNOT = {{0, 1}, {1, 0}};
        double[][] dataXOR = {{0, 0, 0}, {0, 1, 1}, {1, 0, 1}, {1, 1, 0}};
        */

        double[][] data = {{0,0},{0,1},{1,0},{1,1}};
        double[][] correctData ={{0},{1},{1},{0}};

        Network testNet = new Network(2,2,1,5); // (ilosc wejsc,ilosc neuronow na warstwe ukryta,ilosc wyjsc oczekiwanych,liczba warstw ukrytych)
        //testNet.RandomWeights(-0.45,0.45);
        double n=0.2;

        Timee[] testy = new Timee[5];
        testy[0] = new Timee(2,2,1000);
        testy[1] = new Timee(5,2,1000);
        testy[2] = new Timee(20,100,1000);
        testy[3] = new Timee(80,80,1000);
        testy[4] = new Timee(100,20,1000);

        for(int i=4;i<5;i++)
        {
            TeachAndTextMultiple(data,correctData,n,testy[i]);
            SaveResults(testy[i],"wyniki"+(i+1)+".txt");
        }

        //TeachAndTextMultiple(testNet,data,correctData,n,new Timee(),1);



    }

    public static void SaveResults(Timee results, String fileloc)
    {
        try {
            PrintWriter out = new PrintWriter(fileloc);


                out.println(results.printableResult());

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    static Boolean TeachAndTest(Network net, double[][] data, double[][] correctData, double n,Timee tmR){


        int i=0;
        boolean isDone=false;

        Timee tm = new Timee();

        double acceptableError=0.01;

        long tiem = System.nanoTime();
        long sum = 0;

        while(!isDone ){
            long runlearnTime = System.nanoTime();
            i++;
            isDone=true;

            net.Teach(correctData[0], data[0], n);
            net.Teach(correctData[1], data[1], n);
            net.Teach(correctData[2], data[2], n);
            net.Teach(correctData[3], data[3], n);

            runlearnTime = System.nanoTime() - runlearnTime;
            sum+= runlearnTime;

            if(i%100==0){
                System.out.println("TEST: "+i);
                System.out.println("00="+net.CountOutput(data[0])[0]+" 01= "+net.CountOutput(data[1])[0]+" 10= "+net.CountOutput(data[2])[0]+" 11= "+net.CountOutput(data[3])[0]);
            }

            if((Math.abs(correctData[0][0]-net.CountOutput(data[0])[0])>acceptableError)||(Math.abs(correctData[1][0]-net.CountOutput(data[1])[0])>acceptableError)||(Math.abs(correctData[2][0]-net.CountOutput(data[2])[0])>acceptableError)||(Math.abs(correctData[3][0]-net.CountOutput(data[3])[0])>acceptableError)){
                isDone=false;
            }
            if(i> 100000)
            {
                return false;
            }
        }
        tiem = System.nanoTime() - tiem;
        tm.time += (double)tiem/1000000000;
        //System.out.println("Czas: "+tm.time);
        tm.sredniTime += (((double)sum/1000000000)/(4*i));
        tm.iterations+=i;
        //System.out.println("sredni czas: "+tm.sredniTime + " ( "+tm.iterations+" )");

        tmR.time += tm.time;
        tmR.sredniTime += tm.sredniTime;
        tmR.iterations += tm.iterations;

        return true;

        //System.out.println("LAST:"+i);
        //System.out.println("00="+net.CountOutput(data[0])[0]+" 01= "+net.CountOutput(data[1])[0]+" 10= "+net.CountOutput(data[2])[0]+" 11= "+net.CountOutput(data[3])[0]);

    }
    static void TeachAndTextMultiple(double[][] data, double[][] correctData, double n,Timee tm)
    {
        int ile_zle = 0;
        Network net = new Network(2,tm.neuronPerHiddenLayer,1,tm.hiddenLayers);
        for(int i=0;i<tm.iloscProbekStat;i++) {
            net.RandomWeights(-0.45,0.45);
            if(TeachAndTest(net,data,correctData,n,tm) == false)
            {
                i--;
                ile_zle++;
                System.out.println("cos nie pyklo ("+ (i+1) + "/"+ tm.iloscProbekStat + ")");
            }
        }
        tm.Divide();
        System.out.println("koniec probki ( " + ile_zle + " )");

        //System.out.println("Czas: "+tm.time);
        //System.out.println("sredni czas: "+tm.sredniTime + " ( "+tm.iterations+" )");

    }
}