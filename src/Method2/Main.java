package Method2;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		System.out.println("start");

		Coordinate top = new Coordinate();
		Coordinate bottom = new Coordinate();

		//set octree range
		top.x = -4200000;
		top.y = 4100000;
		top.z = 3000000;
		bottom.x = -3500000;
		bottom.y = 4700000;
		bottom.z = 2200000;
		
		Polygon polygon = new Polygon();
		Database database = new Database();
		
		Box rangeBox = new Box();
		rangeBox.createBox(top, bottom);
		
		Octree oct = new Octree(rangeBox);
		
		//create polygon and insert polygon into database
		//polygon.create(oct, database);
		
		//search
		try {
			//set search range
			Box searchBox = new Box();
			top.x = -4292211.3;
			top.y = 4145306;
			top.z = 2216614;
			bottom.x = -4292210;
			bottom.y = 4145307;
			bottom.z = 2216613;
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
								System.out.println("top_elevation");
								System.out.println(resultset.getString("top_elevation"));
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

