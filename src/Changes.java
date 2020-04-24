import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Changes extends JPanel{
	
	private static int[][] memory;
	private static int countElement;
	private static int countFree;
	private static ArrayList<File> files;
	
	public Changes(int[][] memory, int countElement, int countFree, ArrayList<File> files) {
		this.memory = memory;
		this.countElement = countElement;
		this.countFree = countFree;
		this.files = files;
	}
	
	public static boolean addFile(String name, int size) {
		if (files != null) {
			for (int i = 0; i < files.size(); i++) {
				if (files.get(i).getNode().getName().equals(name)) {
					JOptionPane.showMessageDialog(null, "¬ведите другое им€");
					return false;
				}
			}
		}
		if (size / 4 > countFree) {
			JOptionPane.showMessageDialog(null, "¬ведите меньший размер");
			return false;
		}
		countFree -= size / 4;
		countElement = size / 4;
		Place[] ps = new Place[countElement];
		int ps_id = 0;
		for (int i = 0; i < memory.length && countElement > 0; i++) {
			for (int j = 0; j < memory[i].length && countElement > 0; j++) {
				if (memory[i][j] == 1) {
					countElement--;
					memory[i][j] = 2;
					ps[ps_id++] = new Place(i, j);
				}
			}
		}
		IndexNode knot = new IndexNode(ps,name, size);
		File file = new File(knot);
		files.add(file);
		return true;
	}
	
	public static void Delete(String s) {
		Place[] ps = getfile(s).getNode().getpositions();
		if (ps != null) {
			for (int i = 0; i < ps.length; i++) {
				memory[ps[i].I][ps[i].J] = 1;
			}
		}
		countFree += getfile(s).getNode().fileSize() / 2;
		files.remove(getfile(s));
	}
	
	public static File getfile(String s) {
		if (files != null) {
			for (int i = 0; i < files.size(); i++) {
				if (files.get(i).getNode().getName().equals(s)) {
					return files.get(i);
				}
			}
		}
		return null;
	}
}
