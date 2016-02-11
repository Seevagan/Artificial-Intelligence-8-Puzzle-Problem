import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemAI {

	static Integer width = 3;
	static Integer total = 9;
	static List<Integer> path = new ArrayList<Integer>();

	static int init[][] = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
	static int findChild = 0;

	static Map<Integer, String> moves = new HashMap<Integer, String>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "C:/Summer/Proj/file.txt";
		List<Integer> list = getInputFromFile(fileName);
		if (!isStateSolvable(list)) {
			System.out.println("Unsolvable!");
			return;
		}

		Node count = new Node();
		count.setList(list);
		count.setG(0);

		count.setH(getHeuristic(list));
		count.setF(count.getH() + count.getG());
		int x = getIndexOf0(list) / width;
		int y = getIndexOf0(list) % width;
		count.setx(x);
		count.sety(y);
		count.setPre(5);

		Result result = new Result();
		result.setF(0);
		result.setNode(count);
		
		moves.put(0, "UP");
		moves.put(1, "LEFT");
		moves.put(2, "DOWN");
		moves.put(3, "RIGHT");

		ProblemAIP(count, Integer.MAX_VALUE, result);

	}

	private static Result ProblemAIP(Node count, int f, Result result) {

		Result rs = new Result();
		path.add(count.getG(), count.getPre());

		ArrayList<Node> create = new ArrayList<Node>();

		if (count.getH() == 0) {
			rs.setF(f);
			rs.setNode(count);
			printPath(count);
			return rs;
		}

		for (int i = 0; i < 4; i++) {
			if (Math.abs(i - count.getPre()) == 2)
				continue;
			Node stat = createVal(count, init[i][0], init[i][1], i);
			if (findChild == 0)
				continue;
			create.add(stat);
		}

		if (create.size() == 0) {
			rs.setF(count.getF());
			rs.setNode(null);
			return rs;
		}

		while (true) {
			int next = 0;

			Collections.sort(create , new CompareHeuristic());
			Node bestNode = create.get(0);
			if (bestNode.getF() > f) {
				rs.setF(bestNode.getF());
				rs.setNode(null);
				return rs;
			}
			if (create.size() > 1)
				next = create.get(1).getF();
			else
				next= Integer.MAX_VALUE;

			f = f < next ? f : next;
			rs = ProblemAIP(bestNode, f, result);
			create.get(0).setF(rs.getF());
			f = rs.getF();
			if (rs.getNode() != null)
				return rs;

		}
	}

	private static void printPath(Node count) {
		int shuffle = 0;
		System.out.println("Postions to be moved from current state");
		for (int i = 1; i <= count.getG(); i++){
			System.out.println(moves.get(path.get(i)));
			shuffle++;
		}
		System.out.println("Total No of shuffles to obtain Result:"+shuffle);
		
	}

	private static Node createVal(Node parentNode, int x, int y, int pre) {

		int newX = parentNode.getx();
		int newY = parentNode.gety();

		Node child = new Node();

		if ((newX + x) < 0 || (newY + y) < 0 || (newX + x) > (width - 1)
				|| (newY + y) > (width - 1)) {
			findChild = 0;
			return child;

		}

		int tile = (newX + x) * width + (newY + y);

		List<Integer> list = new ArrayList<Integer>(parentNode.getList());
		int temp = list.get(tile);
		list.set(tile, 0);
		list.set(width * newX + newY, temp);

		child.setList(list);
		child.setx(newX + x);
		child.sety(newY + y);
		child.setG(parentNode.getG() + 1);
		child.setH(getHeuristic(list));
		child.setPre(pre);

		int maxfValue = (parentNode.getF()) > (child.getG() + child.getH()) ? parentNode.getF() : (child
				.getG() + child.getH());
		child.setF(maxfValue);
		findChild = 1;
		return child;
	}

	private static Integer getIndexOf0(List<Integer> last) {
		for (int i = 0; i < last.size(); i++) {
			if (last.get(i) == 0) {
				return i;
			}
		}
		return 0;
	}

	private static Integer getHeuristic(List<Integer> list) {
		int manD = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				int pos = i * width + j;
				int val = list.get(pos);
				if (val == 0)
					continue;
				manD = manD + Math.abs((val / width) - i)
						+ Math.abs((val % width) - j);
			}
		}
		return manD;
	}

	private static Boolean isStateSolvable(List<Integer> last) {
		Boolean tr = true;
		int chn = 0;
		for (int i = 0; i < last.size(); i++) {
			for (int j = i; j < last.size(); j++) {
				if ((last.get(j) != 0) && last.get(i) > last.get(j)) {
					chn++;
				}
			}
		}
		if (chn % 2 != 0)
			tr = false;
		return tr;
	}

	private static List<Integer> getInputFromFile(String fileName) {
		
		List<Integer> list = new ArrayList<Integer>();
		try {
			FileReader readFile=new FileReader("C:/Summer/Proj/file.txt");
			BufferedReader read = new BufferedReader(readFile);
			String line=null;
			while((line=read.readLine())!=null)
			{
				String[] parts = line.split("\\t");
				for(String part : parts)
				{
					int i=Integer.valueOf(part);
					list.add(i);
				}
		} 
		}catch (IOException e) {
			e.printStackTrace();
		} 
		return list;
	}

}

class Node {

	private int f, g, h;
	private int x, y;
	private int pre;
	private List<Integer> list;

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getx() {
		return x;
	}

	public void setx(int x) {
		this.x = x;
	}

	public int gety() {
		return y;
	}

	public void sety(int y) {
		this.y = y;
	}

	public int getPre() {
		return pre;
	}

	public void setPre(int pre) {
		this.pre = pre;
	}

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

}

class Result {

	private int f;
	private Node count;

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public Node getNode() {
		return count;
	}

	public void setNode(Node count) {
		this.count = count;
	}

}

class CompareHeuristic implements Comparator<Node> {

	
	public int compare(Node count1, Node count2) {
		return count1.getF() - count2.getF();
	}

}
