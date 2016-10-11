import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class BayesNet {
	ArrayList<BayesNode> nodes;

	public BayesNet(){
		this.nodes = new ArrayList<BayesNode>();
	}

	public void createNet(String filename) throws FileNotFoundException{	
		File inputFile = new File(filename);
		Scanner input = new Scanner(inputFile);

		ArrayList<String> nodeParents = new ArrayList<String>();
		ArrayList<String> nodeCpts = new ArrayList<String>();
		ArrayList<BayesNode> tempNodes = new ArrayList<BayesNode>();

		while(input.hasNextLine()){
			String temp = input.nextLine();
			System.out.println("Looping");
			String name = temp.substring(0, temp.indexOf(":"));
			String parents = temp.substring(temp.indexOf("[") + 1, temp.indexOf("]"));
			String cpt = temp.substring(temp.lastIndexOf("[") + 1, temp.lastIndexOf("]"));

			nodeParents.add(parents);
			nodeCpts.add(cpt);
			BayesNode node = new BayesNode(name, null);
			tempNodes.add(node);
		}
		for(int i = 0; i < tempNodes.size(); i++){
			//System.out.println(tempNodes.get(i).getName());
			ArrayList<BayesNode> tempPar = new ArrayList<BayesNode>();
			String[] parentNodesToi = nodeParents.get(i).split(" ");
			for(int j = 0; j < parentNodesToi.length; j++){
				for(int k = 0; k < tempNodes.size(); k++){
					//System.out.println(nodeParents.get(j));
					//System.out.println(tempNodes.get(k).getName() + " " + parentNodesToi[j] + "  " + Boolean.toString(tempNodes.get(k).getName().equals(parentNodesToi[j])));
					if(tempNodes.get(k).getName().equals(parentNodesToi[j])){
						//System.out.println(tempNodes.get(i).getName() + " " + parentNodesToi[j]);
						BayesNode p1 = tempNodes.get(k);
						tempPar.add(p1);
					}
				}
			}
			BayesNode t = tempNodes.get(i);
			t.setParents(tempPar);	
			nodes.add(t);
			String[] cptsTok = nodeCpts.get(i).split(" ");
			for(int p = 0; p < cptsTok.length; p++){
				nodes.get(i).modifyCptTable(p, Double.parseDouble(cptsTok[p]));
			}
			System.out.println(nodes.get(i).getName() + " " + nodes.get(i).getParents() + " " + Arrays.toString(nodes.get(i).cptTable));
		}
		return;
	}

	public void cptTableMethodA(float[] vals){
		return;
	}

	public void cptTableMethodB(float[] vals){
		return;
	}

	public void assignStatus(char[] values){
		return;
	}

	public void rejectionSampling(int numSamples){
		return;
	}

	public void likelihoodWeighting(int numSamples){
		return;
	}
}
