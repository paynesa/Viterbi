/**Author: Sarah Payne
 * A class representing a HMM, taking as input A, B, and pi.
 * This class also includes methods to implement the Viterbi algorithm
 * regularly or using logarithms. Both methods return a ViterbiSequence,
 * which is a class storing the most likely path and its probability**/

public class HMM {
    //the HMM's inner states
    private double[][] a;
    private double[][] b;
    private double[] pi;
    //constructor, which takes in A, B, and pi
    public HMM(double[][] a, double[][] b, double[] pi) {
        this.a = a;
        this.b = b;
        this.pi = pi;
    }
    
    /**An implementation of the viterbi algorithm given a sequence of state
     * indexes, which correspond to some states**/
    public ViterbiSequence viterbi(int[] omega) {
        //convert the states from being 1-indexed to 0-indexed for ease 
        int[] w = new int[omega.length];
        for (int i = 0; i < omega.length; i++) {
            w[i] = omega[i] - 1;
        }
        int start = w[0];
        //initalize an array to store the scores
        double[] scores = new double[a.length];
        //initialize scores
        for (int j = 0; j < a.length; j++) {
            double tscore = pi[j] * b[j][start];
            scores[j] = tscore;
        }
        //an array to store the predicted state at each iteration
        int[][] predarray = new int[a.length][w.length];
        
        //main forward loop
        for (int t = 1; t < w.length; t++) {
            //a temporary array to store the scores
            double[] tmpscores = new double[scores.length];
            for (int j = 0; j < a.length; j++) {
                //keep track of the max probability + state encountered
                double max = 0;
                int maxpred = 0;
                for (int k = 0; k < a.length; k++) {
                    //calculate tscore(k) and update the max if needed
                    double tscorek = scores[k] * a[k][j] * b[j][w[t]];
                    if (tscorek > max) {
                        max = tscorek;
                        maxpred = k;
                    }
                }
                //update the temp scores and prediction array as needed
                tmpscores[j] = max; 
                predarray[j][t] = maxpred;
            }
            //update the scores to be ready for the next iteration
            scores = tmpscores;
        } 
        
        //the main back loop
        int[] path = new int[w.length];
        //find the max probability at T and corresponding state I(t)
        int it = 0;
        double currmax = 0;
        for (int i = 0; i < scores.length; i ++) {
            if (scores[i] > currmax) {
                currmax = scores[i];
                it = i;
            }
        }
        //set the last value in the path array to the predicted index
        path[path.length - 1] = it + 1;
        //iterate backwards through the state array to find the most likely path
        for (int i = w.length - 1; i > 0; i--) {
            int curr = predarray[it][i];
            path[i - 1] = curr + 1;
            it = curr;
        }
        //initialize and return a viterbisequence with the path and probability
        ViterbiSequence answer = new ViterbiSequence(currmax, path);
        return answer;
    }
    
    
    
    /**A method to run the underflow-limiting version of the Viterbi algorithm
     * that utilized logs**/
    public ViterbiSequence viterbiLog(int[] omega) {
        //convert to 0-indexing for ease in later steps
        int[] w = new int[omega.length];
        for (int i = 0; i < omega.length; i++) {
            w[i] = omega[i] - 1;
        }
        int start = w[0];
        //initialize an array to store the scores
        double[] scores = new double[a.length];
        //initialize scores
        for (int j = 0; j < a.length; j++) {
            double tscore = Math.log(pi[j]) + Math.log(b[j][start]);
            scores[j] = tscore;
        }
        //initialize an array to store the predicting state indexes
        int[][] predarray = new int[a.length][w.length];
        
        //main forward loop
        for (int t = 1; t < w.length; t++) {
            double[] tmpscores = new double[scores.length];
            for (int j = 0; j < a.length; j++) {
                //find the maximal tscore and corresponding index
                double max = (-1) * Double.MAX_VALUE;
                int maxpred = 0;
                for (int k = 0; k < a.length; k++) {
                    //calculate the tscore and update max if needed
                    double tscorek = scores[k] + Math.log(a[k][j])
                        + Math.log(b[j][w[t]]);
                    if (tscorek > max) {
                        max = tscorek;
                        maxpred = k;
                    }
                }
                //update temp scores and index array
                tmpscores[j] = max; 
                predarray[j][t] = maxpred;
            }
            //replace scores for the next iteration 
            scores = tmpscores;
        } 
        
        //main back loop
        int[] path = new int[w.length];
        //calculate I(t), the index predicting the max end probability
        int it = 0;
        double currmax = (-1) * Double.MAX_VALUE;
        for (int i = 0; i < scores.length; i ++) {
            if (scores[i] > currmax) {
                currmax = scores[i];
                it = i;
            }
        }
        //set I(t) to be the last state in the path and iterate back
        path[path.length - 1] = it + 1;
        for (int i = w.length - 1; i > 0; i--) {
            int curr = predarray[it][i];
            path[i - 1] = curr + 1;
            it = curr;
        }
        //initialize and return a viterbisequence storing path and probability
        ViterbiSequence answer = new ViterbiSequence(currmax, path);
        return answer;
    }
}

