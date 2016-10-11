import java.util.ArrayList;


public class BayesNode {

    String name;
    ArrayList<BayesNode> parents;
    char type;  // '?'=query,  't'=evidence true,  'f'=evidence false,  '-'=unknown
    double[][] cptTable; // row0 = probability of true   Column i is the value for the i'th row of the cpt table (i is a bit representation of the truth table).
    int sampleValue; // 0=false 1=true -1=not-sampled

    public BayesNode(String name, ArrayList<BayesNode> parents) {
        super();
        this.name = name;
        this.parents = parents;
        this.type = '-';
        if(parents==null){
            this.cptTable = new double[2][1];
        }
        else{
            this.cptTable = new double[2][(int) Math.pow(2, parents.size())];
        }
        this.sampleValue = -1;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<BayesNode> getParents() {
        return parents;
    }
    public void setParents(ArrayList<BayesNode> parents) {
        this.parents = parents;
        if(parents==null){
            this.cptTable = new double[2][1];
        }
        else{
            this.cptTable = new double[2][(int) Math.pow(2, parents.size())];
        }
    }
    public char getType() {
        return type;
    }
    public void setType(char type) {
        this.type = type;
    }
    public double getCptValue(int r, int c) {
        return cptTable[r][c];
    }
    public void modifyCptTable(int r, int c, double value) {
        this.cptTable[r][c] = value;
    }
    
    /*public double getProb(){
        double p = 0;
        if(this.parents==null || this.parents.size()==0){
            return this.cptTable[0][0];
        }
        else{
            double parentsP = 1;
            int[] parentVals = new int[this.parents.size()];
            for(int i = 0; i < this.cptTable[0].length; i++){ // Loop for each row of cpt table
                parentsP = 1;
                for(int j = 0; j < parentVals.length; j++){ // Get probability of parents set as given by ith row of cpt table.
                    parentVals[j] = i & (int) Math.pow(2, j);
                    if(parentVals[j]==1){
                        parentsP *= this.parents.get(j).getProb(); // Multiply conditionally independent parents.
                    }
                    else{
                        parentsP *= (1-this.parents.get(j).getProb());
                    }
                }
                p += this.cptTable[0][i]*parentsP; // P(X | Parents)*P(Parents)
            }
        }
        return p;
    }*/

    public int getSample(){
        return sampleValue;
    }

    public void setSample(){ // Uses prior sampling method
        this.sampleValue = 0; // Something went wrong.
    }

    public void setLikelihoodSample(){ // Uses likelihood sampling
        if(this.sampleValue != -1){ // Don't sample a node that is already set.
            return;
        }
        else if(this.type=='t'){ // True evidence
            this.sampleValue = 1;
        }
        else if(this.type=='f'){ // False evidence
            this.sampleValue = 0;
        }
        else{ // Either query or unknown
            double val = Math.random();
            if(this.parents==null || this.parents.size()==0){ // No parents
                if(val<this.cptTable[0][0]){
                    this.sampleValue = 1;
                }
                else{
                    this.sampleValue = 0;
                }
            }
            else{ // Has parents; Sample parents first.
                int index = 0;
                for(int i = 0; i < this.parents.size(); i++){ // Find index in cpt table for parents.
                    this.parents.get(i).setLikelihoodSample(); // Set sample for parent (if not set)
                    index = index | (this.parents.get(i).getSample() << i); // Bit representation of cpt truth table
                }
                if(val<this.cptTable[0][index]){
                    this.sampleValue = 1;
                }
                else{
                    this.sampleValue = 0;
                }
            }
        }
        this.sampleValue = 0; // Something went wrong.
    }

    public void dropSample(){
        this.sampleValue = -1;
    }
}
