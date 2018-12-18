package Method2;

import java.sql.ResultSet;
import java.util.List;


public class Octree {
	Coordinate top;
	Coordinate bottom;
	
	int divisionNum;
	int layerNum;
	double xLength;
	double yLength;
	double zLength;
	
	public Octree(Box box) {
		top = new Coordinate();
		bottom = new Coordinate();
		
		this.top.x = box.top.x;
		this.top.y = box.top.y;
		this.top.z = box.top.z;
		this.bottom.x = box.bottom.x;
		this.bottom.y = box.bottom.y;
		this.bottom.z = box.bottom.z;
		
		this.layerNum = 8;
		this.divisionNum = (int)Math.pow(2, layerNum);
		
		this.xLength = bottom.x - top.x;
		this.yLength = bottom.y - top.y;
		this.zLength = top.z - bottom.z;
	}
	
	public ResultSet getBox(Database database, String dbname, Box box) {
		ResultSet resultset = null;
		try {
			String value ="";
			
			value += "top_latitude > " + String.valueOf(box.top.y) + " AND " +
					"top_longitude > " + String.valueOf(box.top.x) + " AND " +
					"top_elevation < " + String.valueOf(box.top.z) + " AND " + 
					"bottom_latitude < " + String.valueOf(box.bottom.y) + " AND " +
					"bottom_longitude < " + String.valueOf(box.bottom.x) + " AND " +
					"bottom_elevation > " + String.valueOf(box.bottom.z); 
			
				resultset = database.select(dbname, value);
		}catch(Exception e) {
			System.out.println(e);
		}
		return resultset;
	}
	
	private int bitshift(int n) {
		
		n = (n | n << 8) & 0x00ff00ff;
		n = (n | n << 4) & 0x0f0f0f0f;
		n = (n | n << 2) & 0x33333333;
		n = (n | n << 1) & 0x55555555;
		
		return n;
	}

	public void insertDB(Database database, Box box) {
		//insert using only top coordination
		int xArea = (int)(box.top.x/(xLength/divisionNum));
		int yArea = (int)(box.top.y/(yLength/divisionNum));
		int zArea = (int)(box.top.z/(zLength/divisionNum));
		
		xArea = this.bitshift(xArea);
		yArea = this.bitshift(yArea);
		zArea = this.bitshift(zArea);
		
		int Area = xArea | (yArea << 1) | (zArea << 2);
		System.out.println(Area);
		String dbName = "db" + String.valueOf(Area);
		
		try {
			
			String value = "id,bottom_longitude,top_longitude,bottom_latitude,top_latitude,bottom_elevation,top_elevation";
			database.createTable(dbName, value);
		}catch (Exception e) {
		}
		try {	
			String queValue = String.valueOf(database.id) + ","+String.valueOf(box.top.x) + ","+
					String.valueOf(box.bottom.x) + ","+
					String.valueOf(box.top.y) + ","+
					String.valueOf(box.bottom.y) + ","+
					String.valueOf(box.bottom.z) + ","+
					String.valueOf(box.top.z);
			database.insert(dbName, queValue);
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}

	public void search(List<String> list, Box box) {
		int topxArea = (int)(box.top.x/(xLength/divisionNum));
		int topyArea = (int)(box.top.y/(yLength/divisionNum));
		int topzArea = (int)(box.top.z/(zLength/divisionNum));
		int bottomxArea = (int)(box.bottom.x/(xLength/divisionNum));
		int bottomyArea = (int)(box.bottom.y/(yLength/divisionNum));
		int bottomzArea = (int)(box.bottom.z/(zLength/divisionNum));
		
		topxArea = this.bitshift(topxArea);
		topyArea = this.bitshift(topyArea);
		topzArea = this.bitshift(topzArea);
		bottomxArea = this.bitshift(bottomxArea);
		bottomyArea = this.bitshift(bottomyArea);
		bottomzArea = this.bitshift(bottomzArea);
		
		int Area;
		int i,j,k;
		for(i = topxArea; i <= bottomxArea; i++) {
			for(j = topyArea; j <= bottomyArea; j++) {
				for(k = bottomzArea; k <= topzArea; k++) {
					Area = i | (j << 1) | (k << 2);
					list.add("db" + String.valueOf(Area));
				}
			}
		}
		return;
	}
}
