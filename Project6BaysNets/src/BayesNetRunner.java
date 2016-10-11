import java.io.FileNotFoundException;
import java.util.Arrays;


public class BayesNetRunner {

	public static void main(String args[]) throws FileNotFoundException{
		BayesNet bn = new BayesNet();
		bn.createNet(args[0]);
		bn.assignStatus(args[1]);
		//double[] probsA = new double[10];
		//double[] probsB = new double[10];
		//for(int i = 0; i < 10; i++){
		//    probsA[i]=bn.rejectionSampling(Integer.parseInt(args[2]));
		//    probsB[i]=bn.likelihoodWeighting(Integer.parseInt(args[2]));
		//}
		//System.out.println(Arrays.toString(probsA));
		//System.out.println(Arrays.toString(probsB));
		double rejectionProb=bn.rejectionSampling(Integer.parseInt(args[2]));
		double likelihoodProb=bn.likelihoodWeighting(Integer.parseInt(args[2]));
		System.out.println("Probability from rejection sampling: "+rejectionProb);
		System.out.println("Probability from likelihood sampling: "+likelihoodProb);
	}
}
