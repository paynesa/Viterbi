/*Author: Sarah Brogden Payne
 * a JUnit testing file that tests both the non-logarithmic Viterbi and 
 * logarithmic Viterbi algorithms. Test cases are taken from Dr. Jean Gallier's
 * CIS 262 notes available at https://www.cis.upenn.edu/~cis262/cis262-notes-20.html*/


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ViterbiTest {
	
	//static HMM used throughout the tests
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
	//Test the basic implementation without logarithmic Viterbi
	public void test_1112() {
		getTestingHMM();
		int[] testingArray = {1,1,1,2};
		ViterbiSequence viterbiAnswer = testingHMM.viterbi(testingArray);
		int[] viterbiPath = viterbiAnswer.getPath();
		Double probability = viterbiAnswer.getProbability();
		assertArrayEquals(testingArray, viterbiPath);
		assertEquals(0.0237, probability, 0.00001);
	}
	
	@Test
	//Test a longer input with logarithmic Viterbi
	public void logTest_1() {
		getTestingHMM();
		int[] longInput = new int[2000];
		for (int i = 0; i < 2000; i ++) {
			if (i < 500 || (i >= 1000 && i < 1500)) {
				longInput[i] = 1;
			}
			else {
				longInput[i] = 2;
			}
		}
		ViterbiSequence viterbiAnswer = testingHMM.viterbiLog(longInput);
		assertEquals(viterbiAnswer.getProbability(), -1227.47, 0.01);
	}
	
	@Test
	//test another longer input with logarithmic Viterbi
	public void logTest_2() {
		getTestingHMM();
		int[] longInput = new int[2004];
		for (int i = 0; i < 2004; i ++) {
			if (i < 500 || (i >= 1000 && i < 1500) || i > 1999) {
				longInput[i] = 1;
			}
			else {
				longInput[i] = 2;
			}
		}
		longInput[2003] = 2;
		ViterbiSequence viterbiAnswer = testingHMM.viterbiLog(longInput);
		assertEquals(viterbiAnswer.getProbability(), -1231.809, 0.001);
	}

}
