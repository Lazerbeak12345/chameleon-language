package ParseTools;

import TokeniserTools.ChamlcToken;

/**
 * A tree or leaf node
 */
public abstract class ParseNode{
	int row,col,start_r,start_c;
	public static String[] nodes={
		"_leaf_",
		"ROOT"
	};
	public ParseNode(String name,int row,int col,int start_r,int start_c) {
		this.row=row;
		this.col=col;
		this.start_r=start_r;
		this.start_c=start_c;
		this.number=nameToInt(name);
	}
	private int number;//,tokNumber=0;
	public int getNumber() {
		return number;
	}
	/**
	 * Convert a given name into the respective int.
	 * 
	 * NOTE: It's actually really slow, as it's using a linear search.
	 * 
	 * @param name
	 * @return
	 */
	public static int nameToInt(String name) {
		for (int i=0;i<nodes.length;++i) {
			if (name.equals(nodes[i])) return i+ChamlcToken.tokens.length;
		}
		//return -17;
		return ChamlcToken.nameToInt(name);
	}
	/**
	 * Get the name
	 */
	public String getName() {
		if (number<0) {
			return "ERROR";//A negative is an error code
		}else if(number<ChamlcToken.tokens.length){
			//System.out.print("<!--??-->");
			return ChamlcToken.tokens[number];
		}else return nodes[number-ChamlcToken.tokens.length];
	}
	/**
	 * Print this ParseNode as XML
	 */
	abstract public void printAsXML();
}
