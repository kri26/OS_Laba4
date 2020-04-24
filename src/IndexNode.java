public class IndexNode {
	
	private Place[] positions;
	private String fileName;
	private int fileSize;
	private IndexNode node;
	
	public IndexNode(Place[] positions, String fileName, int fileSize) {
		this.positions = positions;
		this.fileName = fileName;
		this.fileSize = fileSize;
		
	}
	
	public void indexNode(Place[] ps) {
		node = new IndexNode(positions,"name", -1);
	}
	
	public Place[] getpositions() {
		return positions;
	}
	
	public String getName() {
		return fileName;
	}
	
	public int fileSize() {
		if(node.fileSize == -1)
			return fileSize;
		return fileSize + node.fileSize;
	}
	
	public String toStr() {
		String s = "";
		for (int i = 0; i < positions.length; i++) {
			s += i+" : I " + positions[i].I + " J " + positions[i].J
					+" name: "+ fileName +" size: "+ fileSize + "\n";
		}
		return s;
	}
	
	public void setNode(Place[] ps, String name, int size) {
		 node = new IndexNode(ps,name, size);
	}
	
	public IndexNode nextNode() {
		return node;
	}
}
