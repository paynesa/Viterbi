import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ViterbiTest {
	static HMM testingHMM;
	
	//Method to get an HMM to execute an example test 
	private static void getTestingHMM() {
        double[][] a = {
                {0.7, 0.3},
                {0.25, 0.75}
        };
        double[][] b = {
                {0.8, 0.2},
                {0.3, 0.7}
        };
        double[] pi = {0.45, 0.55};
        testingHMM = new HMM(a, b, pi);
    }

	
	@Test
	public void test_1112() {
		getTestingHMM();
		int[] testingArray = {1,1,1,2};
		ViterbiSequence viterbiAnswer = testingHMM.viterbi(testingArray);
		int[] viterbiPath = viterbiAnswer.getPath();
		Double probability = viterbiAnswer.getProbability();
		assertArrayEquals(testingArray, viterbiPath);
		assertEquals(0.0237, probability, 0.00001);
	}

}
