/**
 * Created by Lach on 2016-10-16.
 */
public class XORErrorCounter extends NeuronErrorCounter {

    @Override
    public double CountError() {

        double xorTest,xorNetwork;

        return properResult [0] - networkResult[0];

    }
}
