package newMethod;

import java.sql.ResultSet;
import java.util.List;

public class Octree {

	Coordinate top;
	Coordinate bottom;

	Octree child1;
	Octree child2;
	Octree child3;
	Octree child4;
	Octree child5;
	Octree child6;
	Octree child7;
	Octree child8;

	String dbname;
	int unum;
	
	public void getBox(Database database, String dbname, Box box) {
		try {
			int i;
			ResultSet results = null;
			String value ="";
			
			value += "top_latitude > " + String.valueOf(box.top.latitude) + " AND " +
					"top_longitude > " + String.valueOf(box.top.longitude) + " AND " +
					"top_elevation < " + String.valueOf(box.top.elevation) + " AND " + 
					"bottom_latitude < " + String.valueOf(box.bottom.latitude) + " AND " +
					"bottom_longitude < " + String.valueOf(box.bottom.longitude) + " AND " +
					"bottom_elevation > " + String.valueOf(box.bottom.elevation); 
			
				//System.out.println(list.get(i));
				//results = statement.executeQuery("select * from "+list.get(i)+";");
				results = database.select(dbname, value);
				if(results != null) {
					while(results.next()) {
						//System.out.println(results.getString("top_latitude"));
					}
				}
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	public void insertDB(Database database, Box box) {
		try {
			if( (box.top.longitude > this.top.longitude && box.top.longitude < this.bottom.longitude) &&
				(box.top.latitude > this.top.latitude && box.top.latitude < this.bottom.latitude) &&
				(box.top.elevation < this.top.elevation && box.top.elevation > this.bottom.elevation)) {
				this.child1.insertDB(database, box);
				this.child2.insertDB(database, box);
				this.child3.insertDB(database, box);
				this.child4.insertDB(database, box);
				this.child5.insertDB(database, box);
				this.child6.insertDB(database, box);
				this.child7.insertDB(database, box);
				this.child8.insertDB(database, box);
			}
		}catch(Exception e) {
			
			try {
				
				String queValue = String.valueOf(database.id) + ","+String.valueOf(box.top.longitude) + ","+
						String.valueOf(box.bottom.longitude) + ","+
						String.valueOf(box.top.latitude) + ","+
						String.valueOf(box.bottom.latitude) + ","+
						String.valueOf(box.bottom.elevation) + ","+
						String.valueOf(box.top.elevation);				
				//database.insert(this.dbname, queValue);
			}catch(Exception ex) {
				System.out.println(ex);
			
			}
			
			 //System.out.println(this.dbname);
		}
	}

	public void search(List<String> list, Coordinate t, Coordinate b) {
		try {
			if( (this.top.longitude <= b.longitude && t.longitude <= this.bottom.longitude) &&
				(this.top.latitude <= b.latitude && t.latitude <= this.bottom.latitude) &&
				(this.top.elevation >= b.elevation && t.elevation >= this.bottom.elevation) ) {
				this.child1.search(list, t, b);
				this.child2.search(list, t, b);
				this.child3.search(list, t, b);
				this.child4.search(list, t, b);
				this.child5.search(list, t, b);
				this.child6.search(list, t, b);
				this.child7.search(list, t, b);
				this.child8.search(list, t, b);
			}
		}catch(Exception e){
			list.add(this.dbname);
		}
		return;

	}

	public void createTree(Database database, int level, Coordinate t, Coordinate b) {
		this.top = new Coordinate(t);
		this.bottom = new Coordinate(b);

		if(level == 0) {
			this.dbname = "db" + String.valueOf((int)this.top.longitude) + String.valueOf((int)this.top.latitude) + String.valueOf((int)this.top.elevation);
			
			try {
				String value = "id,bottom_longitude,top_longitude,bottom_latitude,top_latitude,bottom_elevation,top_elevation";
				database.createTable(this.dbname, value);
			}catch(Exception e) {
				//System.out.println(e);
			}
			
			this.unum = (int)this.top.longitude;
			return;
		}

		child1 = new Octree();
		child2 = new Octree();
		child3 = new Octree();
		child4 = new Octree();
		child5 = new Octree();
		child6 = new Octree();
		child7 = new Octree();
		child8 = new Octree();

		level--;

		Coordinate topTemp;
		Coordinate bottomTemp;

		//child1
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child1.createTree(database, level, topTemp, bottomTemp);

		//child2
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child2.createTree(database, level, topTemp, bottomTemp);

		//child3
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child3.createTree(database, level, topTemp, bottomTemp);

		//child4
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child4.createTree(database, level,topTemp, bottomTemp);

		//child5
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child5.createTree(database, level, topTemp, bottomTemp);

		//child6
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child6.createTree(database, level, topTemp, bottomTemp);

		//child7
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child7.createTree(database, level, topTemp, bottomTemp);

		//child8
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child8.createTree(database, level, topTemp, bottomTemp);
	}
}
