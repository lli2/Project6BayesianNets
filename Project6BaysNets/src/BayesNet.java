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
			//System.out.println("Looping");
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
		String inputString = "";
		
		//String[] charArr = null;
		if(input.hasNextLine()){
			inputString = input.nextLine();
			//System.out.println(temp);
		}
		else{
		    System.exit(0);
		}
		for(int i = 0; i < this.nodes.size(); i++){
			char c = inputString.charAt(i*2);
			this.nodes.get(i).setType(c);
		}
	input.close();
	}

	public double rejectionSampling(int numSamples){
		int sumSampleValues = 0;
		int totalNumOfNotDiscarded = numSamples;
		
		for(int i = 0; i < numSamples; i++){
		    int sampleValue = -1;
			for(int j = 0; j < this.nodes.size(); j++){
				this.nodes.get(j).setSample();
			}
			for(int j = 0; j < this.nodes.size(); j++){
                if(this.nodes.get(j).getType()=='t'&&this.nodes.get(j).getSample()!=1){
                    totalNumOfNotDiscarded--;
                    sampleValue=-1;
                    break;
                }
                else if(this.nodes.get(j).getType()=='f'&&this.nodes.get(j).getSample()!=0){
                    totalNumOfNotDiscarded--;
                    sampleValue=-1;
                    break;
                }
                if(this.nodes.get(j).getType()=='?'){
                    sampleValue=this.nodes.get(j).getSample();
                }
            }
            for(int j = 0; j < this.nodes.size(); j++){
                this.nodes.get(j).dropSample();
            }
            if(sampleValue!=-1){
                sumSampleValues += sampleValue;
            }
		}
		return (double) sumSampleValues / (double) totalNumOfNotDiscarded;
	}

	public double likelihoodWeighting(int numSamples){
		double weightProb = 0;
		int sumSamples = 0;
		for(int i = 0; i < numSamples; i++){
	        for(int j = 0; j < this.nodes.size(); j++){
	            //System.out.println(this.nodes.get(j).getName()+" is: " + this.nodes.get(j).getSample());
	            nodes.get(j).setLikelihoodSample();
	        }
			for(int j = 0; j < this.nodes.size(); j++){
			    //System.out.println(this.nodes.get(j).getName()+" is: " +this.nodes.get(j).getType()+" with " + this.nodes.get(j).getSample());
			    if(this.nodes.get(j).getType()=='?'){
                    sumSamples += this.nodes.get(j).getSample();
                }
            }
			for(int j = 0; j < this.nodes.size(); j++){
                this.nodes.get(j).dropSample();
            }
		}
		weightProb = (double) sumSamples / (double) numSamples;
		return weightProb;
	}
}
