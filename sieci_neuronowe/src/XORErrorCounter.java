/**
 * Created by Lach on 2016-10-16.
 */
public class XORErrorCounter extends NeuronErrorCounter {

    @Override
    public double CountError() {

        double xorTest,xorNetwork;

        if(properResult[0]==1 && properResult[1]==0)
        {
            xorTest = 1;
        }
        else if(properResult[0]==0 && properResult[1]==1)
        {
            xorTest = 1;
        }
        else if(properResult[0] == properResult[1] && (properResult[0]==0 ||properResult[0]==1))
        {
            xorTest = 0;
        }
        else xorTest = -1;

        if(networkResult[0]==1 && networkResult[1]==0)
        {
            xorNetwork = 1;
        }
        else if(networkResult[0]==0 && networkResult[1]==1)
        {
            xorNetwork = 1;
        }
        else if(networkResult[0] == networkResult[1] && (networkResult[0]==0 ||networkResult[0]==1))
        {
            xorNetwork = 0;
        }
        else xorNetwork = -1;

        return xorTest - xorNetwork;

    }
}
