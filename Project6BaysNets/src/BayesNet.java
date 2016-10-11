import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
			//System.out.println(nodes.get(i) + " " + nodes.get(i).getParents() + " " + Arrays.toString(nodes.get(i).cptTable));
		}
		input.close();
		return;
	}

	public void assignStatus(String filename) throws FileNotFoundException{//char[] values){
		File inputFile = new File(filename);
		Scanner input = new Scanner(inputFile);
		ArrayList<String> string = new ArrayList<String>();
		
		//String[] charArr = null;
		while(input.hasNextLine()){
			String temp = input.nextLine();
			string.add(temp);
			//System.out.println(temp);
		}
		for(int i = 0; i < string.get(0).length(); i++){
			char[] charArr = new char[string.get(0).length()];
			char c = string.get(i).charAt(i);
			charArr[i] = c;
			System.out.println(charArr[i]);
		}
		/*String[] str = string.get(0).split(",");
		System.out.println(str.length);
		String chArr = Arrays.toString(str);
		System.out.println(chArr);*/
		/*for(int i = 0; i < str.length; i++){
			chArr[i] = str[i].toChar();
			System.out.println(chArr[i]);
		}*/

		//System.out.println(string);
		String[] tempS = string.toArray(new String[]{});
		//System.out.println(tempS);
		char[] cArr = new char[tempS.length];
		//System.out.println(tempS.length);
		for(int o = 0; o < tempS.length; o++){
			cArr[o] = tempS[o].charAt(o);
			//System.out.println(cArr[o]);
		}
		//String[] tempArr = temps.split(",");
		//String t = Arrays.toString(tempS);
		//System.out.println(t);
		//char[] charArr = t.toCharArray();
		//for(char output: charArr){
		
		//System.out.println(output);
		//}
		//for(int k = 0; k < nodes.size(); k++){
			//nodes.get(k).setType(cArr[k]);

			//System.out.println(charArr[k]);
			//System.out.println(nodes.get(k).getType());
	//	}
	input.close();
}
	
	public double rejectionSampling(int numSamples){
		int notDiscardedSample = 0;
		int totalNumOfNotDiscarded = numSamples;
		
		for(int i = 0; i < nodes.size(); i++){
			for(int j = 0; j < numSamples; j++){
				nodes.get(i).setSample();
				if(nodes.get(i).getSample() == nodes.get(i).getType()){
					notDiscardedSample++; 
				}
				else {
					nodes.get(i).dropSample();
					totalNumOfNotDiscarded--;
				}
			}
		}
		return notDiscardedSample/totalNumOfNotDiscarded;
	}

	public double likelihoodWeighting(int numSamples){
		double weightProb = 0;
		for(int i = 0; i < nodes.size(); i++){
			for(int j = 0; j < numSamples; j++){
				nodes.get(i).setLikelihoodSample();
			}
		}
		return weightProb;
	}
}
