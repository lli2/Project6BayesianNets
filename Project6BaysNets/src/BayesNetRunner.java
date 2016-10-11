import java.io.FileNotFoundException;


public class BayesNetRunner {

	public static void main(String args[]) throws FileNotFoundException{
		BayesNet bn = new BayesNet();
		bn.createNet(args[0]);
		bn.assignStatus(args[1]);
		bn.rejectionSampling(Integer.parseInt(args[2]));
		bn.likelihoodWeighting(Integer.parseInt(args[2]));
	}
}
