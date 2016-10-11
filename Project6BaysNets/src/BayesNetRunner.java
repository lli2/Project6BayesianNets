import java.io.FileNotFoundException;


public class BayesNetRunner {

	public static void main(String args[]) throws FileNotFoundException{
		BayesNet bn = new BayesNet();
		bn.createNet(args[0]);
	}
}
