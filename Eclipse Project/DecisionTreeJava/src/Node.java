import java.util.ArrayList;

/**
 * @author Vishal Doshi
 *
 */
public class Node {
	private ArrayList<Record> data;
	private double entropy;
	private	ArrayList<Node> children;
	private String name;
	private boolean[] attributesUsed= {false, false, false, false, false};
	private int decision;
	private String childType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	Node(ArrayList<Record> data){
		this.data = data;
		this.entropy = 0.0;
		this.children = new ArrayList<Node>();
		this.decision = -1;
		this.childType = "none";
	}
	public String getChildType() {
		return childType;
	}
	public void setChildType(String childType) {
		this.childType = childType;
	}
	public int getDecision() {
		return decision;
	}
	public void setDecision(int decision) {
		this.decision = decision;
	}
	public void setAttributeUsed(boolean[] b){
		for (int i = 0; i < b.length; i++) {
			attributesUsed[i] = b[i];
		}
	}
	public void setAttributeUsed(int i){
			attributesUsed[i] = true;	
	}
	public boolean[] getAttributeUsed(){
		return attributesUsed;
	}
	
	public void insertChild(Node child){
		children.add(child);
		System.out.println("Child inserted!");
	}
	public ArrayList<Record> getData() {
		return data;
	}

	public void setData(ArrayList<Record> data) {
		this.data = data;
	}

	public double getEntropy() {
		return entropy;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public String toString(){
		String childrenNames = "";
		String attUsed = "";
		for(Node n: children){
			childrenNames+= "\t"+n.getName()+",\n";
		}
		for(int u = 0 ; u< attributesUsed.length;u++)
			attUsed+=("\n"+Attribute.names.values()[u]+": "+attributesUsed[u]);
		return ("******\nNode Name:"+name+"\nList of Children:\n"+childrenNames+"\nEntropy of \""+name+"\"="+entropy+"\nAttributes used:"+attUsed+"\nDecision: "+decision+"\nChild Type:"+childType+"\n******");
	}
	
	//Prints the tree
	public void printTree(String s){
		System.out.print(s+name+"\n");
		if(children.size()==0){
			String d = decision == 0?"no":"yes";
			System.out.println(s+"\t=>"+d);
		}
		for(Node n: children){
			n.printTree(s+"|\t");
		}
	}
	
}
