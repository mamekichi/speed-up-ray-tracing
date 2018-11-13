package newMethod;


import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		System.out.println("start");
		
		//create Octree
		Octree oct = new Octree();

		Coordinate top = new Coordinate();
		Coordinate bottom = new Coordinate();

		top.longitude = 0;
		top.latitude = 0;
		top.elevation = 2000;
		bottom.longitude = 10000;
		bottom.latitude = 10000;
		bottom.elevation = 0;
		
		Polygon polygon = new Polygon();
		Database database = new Database();
		try {
			oct.createTree(database, 4, top, bottom);

			//create DB
			//read file test
			polygon.create(oct, database);

			//search
			top.longitude = 9500;
			top.latitude = 9500;
			top.elevation = 1600;
			bottom.longitude = 10000;
			bottom.latitude = 10000;
			bottom.elevation = 1500;
			List<String> list = new ArrayList<>();
			oct.search(list, top, bottom);
			System.out.println(list);
			
			Box searchBox = new Box();
			searchBox.createBox(top, bottom);
			int i;
			String dbname = "";
			for(i = 0; i < list.size();i++) {
				dbname += list.get(i) + ",";	
			}
			dbname = dbname.substring(0, dbname.length()-1);
			double ave = 0.0;
			int temp2;
			for(temp2 = 0; temp2 < 1; temp2++) {
				long startTime = System.currentTimeMillis();
				int temp;
					for(temp = 0; temp < 100; temp++) {
						oct.getBox(database, dbname, searchBox);
					}
				long endTime = System.currentTimeMillis();
				ave += endTime-startTime;
			}
			ave /= 1;
			System.out.println(ave);
			
			/*
			ResultSet results; 
			//statement.executeQuery("create virtual table demo_index USING rtree(id,minX,maxX,minY,maxY);");
			statement.executeUpdate("INSERT INTO demo_index VALUES(5,-80.7749,-80.7747,35.3776,35.3778);");
			results = statement.executeQuery("SELECT * FROM demo_index WHERE id = 1;");
			while(results.next()) {
				System.out.println(results.getString("minX"));
			}
			*/
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		return;
	}
}

