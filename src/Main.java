
public class Main {
	public static void main(String[] args) {
		System.out.println("hello");
		
		Octree oct = new Octree();
		
		Coordinate top = new Coordinate();
		Coordinate bottom = new Coordinate();
		
		top.x = 0;
		top.y = 1024;
		top.z = 0;
		bottom.x = 1024;
		bottom.y = 0;
		bottom.z = 1024;
		
		oct.createTree(3, top, bottom);
		
		System.out.println(oct.child2.top.x);
		
		return;
	}
}
