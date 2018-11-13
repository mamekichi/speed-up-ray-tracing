package newMethod;


import java.sql.ResultSet;
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
		
		//create oct tree
		oct.createTree(database, 4, top, bottom);
		
		//create polygon and insert polygon into database
		polygon.create(oct, database);
		
		//search
		try {
			//set search range
			Box searchBox = new Box();
			top.longitude = 9500;
			top.latitude = 9500;
			top.elevation = 1600;
			bottom.longitude = 10000;
			bottom.latitude = 10000;
			bottom.elevation = 1500;
			searchBox.createBox(top, bottom);
			
			//search database table in oct tree
			List<String> list = new ArrayList<>();
			oct.search(list, searchBox);
			System.out.println(list);	//oct list
			
			//link database name
			int i;
			String dbname = "";
			for(i = 0; i < list.size();i++) {
				dbname += list.get(i) + ",";	
			}
			dbname = dbname.substring(0, dbname.length()-1);
			
			//search polygon in database table
			double ave = 0.0;
			int temp2;
			ResultSet resultset = null;
			for(temp2 = 0; temp2 < 1; temp2++) {
				long startTime = System.currentTimeMillis();
				int temp;
					for(temp = 0; temp < 100; temp++) {
						resultset = oct.getBox(database, dbname, searchBox);
						if(resultset != null) {
							while(resultset.next()) {
								//System.out.println(resultset.getString("top_latitude"));
							}
						}
					}
				long endTime = System.currentTimeMillis();
				ave += endTime-startTime;
			}
			ave /= 1;
			System.out.println(ave);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		return;
	}
}

