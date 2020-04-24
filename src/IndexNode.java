public class IndexNode {
	
	private Place[] positions;
	private String fileName;
	private int fileSize;
	
	public IndexNode(Place[] positions, String fileName, int fileSize) {
		this.positions = positions;
		this.fileName = fileName;
		this.fileSize = fileSize;
	}
	
	public Place[] getpositions() {
		return positions;
	}
	
	public String getName() {
		return fileName;
	}
	
	public int fileSize() {
		return fileSize;
	}
	
	public String toStr() {
		String s = "";
		for (int i = 0; i < positions.length; i++) {
			s += i+" : I " + positions[i].I + " J " + positions[i].J
					+" name: "+ fileName +" size: "+ fileSize + "\n";
		}
		return s;
	}
}
