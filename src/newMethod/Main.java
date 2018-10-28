package newMethod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

		//create Octree
		Octree oct = new Octree();

		Coordinate top = new Coordinate();
		Coordinate bottom = new Coordinate();

		top.x = 0;
		top.y = 0;
		top.z = 0;
		bottom.x = 1024;
		bottom.y = 1024;
		bottom.z = 1024;

		oct.createTree(3, top, bottom);

		//create DB
		oct.insertDB(top,bottom);

		//search
		top.x = 0;
		top.y = 0;
		top.z = 0;
		bottom.x = 600;
		bottom.y = 600;
		bottom.z = 0;
		List<Integer> list = new ArrayList<>();
		oct.search(list, top, bottom);
		System.out.println(list);


		return;
	}
}

