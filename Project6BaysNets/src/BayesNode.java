import java.util.ArrayList;


public class BayesNode {

    String name;
    ArrayList<BayesNode> parents;
    int type;  // 0=query,  1=evidence,  2=unknown
    float[][] cptTable;

    public BayesNode(String name, ArrayList<BayesNode> parents, int type) {
        super();
        this.name = name;
        this.parents = parents;
        this.type = type;
        this.cptTable = new float[2][parents.size()*2];
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
    public void addParent(BayesNode p) {
        this.parents.add(p);
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public float getCptValue(int r, int c) {
        return cptTable[r][c];
    }
    public void modifyCptTable(int r, int c, float value) {
        this.cptTable[r][c] = value;
    }

}
