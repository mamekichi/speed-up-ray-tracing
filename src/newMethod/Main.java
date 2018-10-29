package newMethod;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//file
import java.io.File;
import java.io.FileReader;

public class Main {
	public static void main(String[] args) {
		System.out.println("hello");
		
		try {
			File file = new File("src/Jmap/6544/654450.MEM");
			
			if(file.exists()) {
				FileReader filereader = new FileReader(file);
				
				int data;
				char ctemp[] = new char[6];
				while((data = filereader.read(ctemp)) != -1) {
					System.out.println(ctemp);
				}
				filereader.close();
			}else {
				System.out.print("no file");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
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

