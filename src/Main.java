import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		System.out.println("hello");

		try {
			Class.forName("org.sqlite.JDBC");

			Connection con = DriverManager.getConnection("jdbc:sqlite:../database.db");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}

		//
		Octree oct = new Octree();

		Coordinate top = new Coordinate();
		Coordinate bottom = new Coordinate();

		top.x = 0;
		top.y = 1024;
		top.z = 0;
		bottom.x = 1024;
		bottom.y = 0;
		bottom.z = 1024;

		oct.createTree(4, top, bottom);
		System.out.println(top.x);
		System.out.println(oct.top.x);
		System.out.println(oct.child2.top.x);
		System.out.println(oct.child2.child2.top.x);
		System.out.println(oct.child2.child2.child2.top.x);
		System.out.println(oct.child2.child2.child2.child2.top.x);


		return;
	}
}
