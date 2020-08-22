/**Author: Sarah Brogden Payne
 * A class created to store information returned from the Viterbi algorithm:
 * a sequence of states corresponding to the highest-probability and the
 * probability corresponding to this.**/

public class ViterbiSequence {
    //inner states
    private double probability;
    private int[] path;
    //constructor takes in a probability and path
    public ViterbiSequence(double probability, int[] path) {
        this.probability = probability;
        this.path = path;
    }
    //returns the probability
    public double getProbability() {
        return probability;
    }
    //returns the path
    public int[] getPath() {
        return path;
    }
}
