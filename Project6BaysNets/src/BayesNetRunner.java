import java.io.FileNotFoundException;


public class BayesNetRunner {

	public static void main(String args[]) throws FileNotFoundException{
		BayesNet bn = new BayesNet();
		bn.createNet(args[0]);
		bn.assignStatus(args[1]);
		double rejectionProb=bn.rejectionSampling(Integer.parseInt(args[2]));
		double likelihoodProb=bn.likelihoodWeighting(Integer.parseInt(args[2]));
		System.out.println("Probability from rejection sampling: "+rejectionProb);
		System.out.println("Probability from likelihood sampling: "+likelihoodProb);
	}
}
