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
	
	public Octree() {
		this.dbname = "";
		this.child1 = null;
		this.child2 = null;
		this.child3 = null;
		this.child4 = null;
		this.child5 = null;
		this.child6 = null;
		this.child7 = null;
		this.child8 = null;
	}
	
	public void create(Database database, Box rangeBox, Box polygonBox, int level) {
		
		this.top = new Coordinate(rangeBox.top);
		this.bottom = new Coordinate(rangeBox.bottom);

		//if this is leaf
		if(level == 0) {
			//if table don't exist
			if(this.dbname.equals("")) {
				this.dbname = "db" + String.valueOf((int)this.top.longitude) + String.valueOf((int)this.top.latitude) + String.valueOf((int)this.top.elevation);
			
				try {
					String value = "id,bottom_longitude,top_longitude,bottom_latitude,top_latitude,bottom_elevation,top_elevation";
					database.createTable(this.dbname, value);
				}catch(Exception e) {
					//System.out.println(e);
				}
			}
			
			//insert
			try {	
				String queValue = String.valueOf(database.id) + ","+String.valueOf(polygonBox.top.longitude) + ","+
						String.valueOf(polygonBox.bottom.longitude) + ","+
						String.valueOf(polygonBox.top.latitude) + ","+
						String.valueOf(polygonBox.bottom.latitude) + ","+
						String.valueOf(polygonBox.bottom.elevation) + ","+
						String.valueOf(polygonBox.top.elevation);				
				database.insert(this.dbname, queValue);
			}catch(Exception e) {
				System.out.println(e);
			}
			
			
			this.unum = (int)this.top.longitude;
			return;
		}
		//if not leaf
		level--;
		Coordinate topTemp;
		Coordinate bottomTemp;
		Box box = new Box();
		topTemp = new Coordinate(rangeBox.top);
		bottomTemp = new Coordinate(rangeBox.bottom);
		
		//distribute polygon to box that top join in
		if(polygonBox.top.longitude > this.top.longitude && polygonBox.top.longitude <= this.bottom.longitude/2.0) {
			if(polygonBox.top.latitude > this.top.latitude && polygonBox.top.latitude <= this.bottom.latitude/2.0) {
				if(polygonBox.top.elevation <= this.top.elevation && polygonBox.top.elevation > this.top.elevation/2.0) {
					//child1
					if(child1 == null) {
						child1 = new Octree();
					}
					bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
					bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
					bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
					box.createBox(topTemp, bottomTemp);
					child1.create(database, box, polygonBox, level);
					
				}else if(polygonBox.top.elevation <= this.top.elevation/2.0 && polygonBox.top.elevation > this.bottom.elevation) {
					//child5
					if(child5 == null) {
						child5 = new Octree();
					}
					bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
					bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
					topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
					box.createBox(topTemp, bottomTemp);
					child5.create(database, box, polygonBox, level);
				}
			}else if(polygonBox.top.latitude > this.bottom.latitude/2.0 && polygonBox.top.latitude <= this.bottom.latitude) {
				if(polygonBox.top.elevation <= this.top.elevation && polygonBox.top.elevation > this.top.elevation/2.0) {
					//child3	
					if(child3 == null) {
						child3 = new Octree();
					}
					topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
					bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
					bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
					box.createBox(topTemp, bottomTemp);
					child3.create(database, box, polygonBox, level);
					
				}else if(polygonBox.top.elevation <= this.top.elevation/2.0 && polygonBox.top.elevation > this.bottom.elevation) {
					//child7
					if(child7 == null) {
						child7 = new Octree();
					}
					bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
					topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
					topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
					box.createBox(topTemp, bottomTemp);
					child7.create(database, box, polygonBox, level);
				}
			}
		}else if(polygonBox.top.longitude > this.bottom.longitude/2.0 && polygonBox.top.longitude <= this.bottom.longitude) {
			if(polygonBox.top.latitude > this.top.latitude && polygonBox.top.latitude <= this.bottom.latitude/2.0) {
				if(polygonBox.top.elevation <= this.top.elevation && polygonBox.top.elevation > this.top.elevation/2.0) {
					//child2
					if(child2 == null) {
						child2 = new Octree();
					}
					topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
					bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
					bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
					box.createBox(topTemp, bottomTemp);
					child2.create(database, box, polygonBox, level);
				}else if(polygonBox.top.elevation <= this.top.elevation/2.0 && polygonBox.top.elevation > this.bottom.elevation) {
					//child6
					if(child6 == null) {
						child6 = new Octree();
					}
					topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
					bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
					topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
					box.createBox(topTemp, bottomTemp);
					child6.create(database, box, polygonBox, level);
				}
			}else if(polygonBox.top.latitude > this.bottom.latitude/2.0 && polygonBox.top.latitude <= this.bottom.latitude) {
				if(polygonBox.top.elevation <= this.top.elevation && polygonBox.top.elevation > this.top.elevation/2.0) {
					//child4
					if(child4 == null) {
						child4 = new Octree();
					}
					topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
					topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
					bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
					box.createBox(topTemp, bottomTemp);
					child4.create(database, box, polygonBox, level);
				}else if(polygonBox.top.elevation <= this.top.elevation/2.0 && polygonBox.top.elevation > this.bottom.elevation) {
					//child8
					if(child8 == null) {
						child8 = new Octree();
					}
					topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
					topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
					topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
					box.createBox(topTemp, bottomTemp);
					child8.create(database, box, polygonBox, level);
				}
			}
		}
	}
	
	public ResultSet getBox(Database database, String dbname, Box box) {
		ResultSet resultset = null;
		try {
			String value ="";
			
			value += "top_latitude > " + String.valueOf(box.top.latitude) + " AND " +
					"top_longitude > " + String.valueOf(box.top.longitude) + " AND " +
					"top_elevation < " + String.valueOf(box.top.elevation) + " AND " + 
					"bottom_latitude < " + String.valueOf(box.bottom.latitude) + " AND " +
					"bottom_longitude < " + String.valueOf(box.bottom.longitude) + " AND " +
					"bottom_elevation > " + String.valueOf(box.bottom.elevation); 
			
				resultset = database.select(dbname, value);
		}catch(Exception e) {
			System.out.println(e);
		}
		return resultset;
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

	public void search(List<String> list, Box polygonBox) {
		try {
			//distribute polygon to box that top join in
			if(polygonBox.top.longitude > this.top.longitude && polygonBox.top.longitude <= this.bottom.longitude/2.0) {
				if(polygonBox.top.latitude > this.top.latitude && polygonBox.top.latitude <= this.bottom.latitude/2.0) {
					if(polygonBox.top.elevation <= this.top.elevation && polygonBox.top.elevation > this.top.elevation/2.0) {
						//child1
						child1.search(list ,polygonBox);
						
					}else if(polygonBox.top.elevation <= this.top.elevation/2.0 && polygonBox.top.elevation > this.bottom.elevation) {
						//child5
						child5.search(list ,polygonBox);
					}
				}else if(polygonBox.top.latitude > this.bottom.latitude/2.0 && polygonBox.top.latitude <= this.bottom.latitude) {
					if(polygonBox.top.elevation <= this.top.elevation && polygonBox.top.elevation > this.top.elevation/2.0) {
						//child3	
						child3.search(list ,polygonBox);
					}else if(polygonBox.top.elevation <= this.top.elevation/2.0 && polygonBox.top.elevation > this.bottom.elevation) {
						//child7
						child7.search(list ,polygonBox);
					}
				}
			}else if(polygonBox.top.longitude > this.bottom.longitude/2.0 && polygonBox.top.longitude <= this.bottom.longitude) {
				if(polygonBox.top.latitude > this.top.latitude && polygonBox.top.latitude <= this.bottom.latitude/2.0) {
					if(polygonBox.top.elevation <= this.top.elevation && polygonBox.top.elevation > this.top.elevation/2.0) {
						//child2
						child2.search(list ,polygonBox);
					}else if(polygonBox.top.elevation <= this.top.elevation/2.0 && polygonBox.top.elevation > this.bottom.elevation) {
						//child6
						child6.search(list ,polygonBox);
					}
				}else if(polygonBox.top.latitude > this.bottom.latitude/2.0 && polygonBox.top.latitude <= this.bottom.latitude) {
					if(polygonBox.top.elevation <= this.top.elevation && polygonBox.top.elevation > this.top.elevation/2.0) {
						//child4
						child4.search(list ,polygonBox);
					}else if(polygonBox.top.elevation <= this.top.elevation/2.0 && polygonBox.top.elevation > this.bottom.elevation) {
						//child8
						child8.search(list ,polygonBox);
					}
				}
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
