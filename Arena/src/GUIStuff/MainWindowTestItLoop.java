package GUIStuff;

/**
 * Created by Lach on 2016-12-29.
 */
public class MainWindowTestItLoop implements Runnable{

    public MainWindow sauce;

    MainWindowTestItLoop(MainWindow sauce)
    {
        this.sauce = sauce;
    }

    @Override
    public void run() {

        //sauce.TestujButton();
        //System.out.println(sauce.GetBestGrade());

        while(!sauce.koniecPetli)
        {

            sauce.TestujButton();

            while(!sauce.koniecTest)
            {
                synchronized (this){
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println(sauce.GetBestGrade());
            if(!sauce.koniecPetli) sauce.NextGenerationButton();
        }


    }


}
