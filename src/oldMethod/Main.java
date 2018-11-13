package oldMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		
		//Connect DB
		try {
			Class.forName("org.sqlite.JDBC");
			
			connection = DriverManager.getConnection("jdbc:sqlite:src/DB/oldMethod.db");
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//create table
		try {
			statement.executeUpdate("create virtual table map USING rtree(id,bottom_longitude,top_longitude,bottom_latitude,top_latitude,bottom_elevation,top_elevation);");
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//openFile
		File file;
		try {
			file = new File("src/Jmap/6544/654450.MEM");
			if(file.exists()) {
				FileReader nowFilereader = new FileReader(file);
				FileReader nextFilereader = new FileReader(file);
				BufferedReader nowbr = new BufferedReader(nowFilereader);
				BufferedReader nextbr = new BufferedReader(nextFilereader);
			
				int i,j;
				String line;
				String nowLine;
				String nextLine;
				Map nowMap = new Map();
				Map nextMap = new Map();
				String nowElevationpart;
				String nextElevationpart;
				Matcher nowElevation;
				Matcher nextElevation;
				double latitude = 0;
				double longitude = 0;
				
				Coordinate coordinate1 = new Coordinate();
				Coordinate coordinate2 = new Coordinate();
				Box box = new Box();
				//except header and set nextLine
				nowbr.readLine();
				nextbr.readLine();
				nextbr.readLine();
				int id = 1;
				while(((nowLine = nowbr.readLine()) != null) && ((nextLine = nextbr.readLine()) != null)) {
					System.out.println("---");
					nowMap.meshcode = Integer.parseInt(nowLine.substring(0, 6));
					nextMap.meshcode = Integer.parseInt(nextLine.substring(0, 6));
					nowMap.recordnum = Integer.parseInt(nowLine.substring(6,9));
					nextMap.recordnum = Integer.parseInt(nextLine.substring(6, 9));
					nowElevationpart = nowLine.substring(9, 1009);
					nextElevationpart = nextLine.substring(9, 1009);
					nowElevation = Pattern.compile("[\\s\\S]{1,5}").matcher(nowElevationpart);
					nextElevation = Pattern.compile("[\\s\\S]{1,5}").matcher(nextElevationpart);
					j = 0;
					while(nowElevation.find() && nextElevation.find()) {
						nowMap.elevation[j] = Integer.parseInt(nowElevation.group());
						nextMap.elevation[j] = Integer.parseInt(nextElevation.group());
						j++;
					}	
				
					for(j = 0; j < 199; j++) {
						coordinate1.longitude = j*50;
						coordinate1.latitude = nowMap.recordnum*50;
						coordinate1.elevation = nowMap.elevation[j];
						coordinate2.longitude = (j+1)*50;
						coordinate2.latitude = nextMap.recordnum*50;
						coordinate2.elevation = nextMap.elevation[j+1];
						box.createBox(coordinate1, coordinate2);
						String queValue = String.valueOf(id) + ","+String.valueOf(box.top.longitude) + ","+
								String.valueOf(box.bottom.longitude) + ","+
								String.valueOf(box.top.latitude) + ","+
								String.valueOf(box.bottom.latitude) + ","+
								String.valueOf(box.bottom.elevation) + ","+
								String.valueOf(box.top.elevation);
						//insert into table
						try {
							//statement.executeUpdate("INSERT INTO map VALUES("+queValue+");");
						}catch(Exception e) {
							//System.out.println(e);
						}
						id++;
						//System.out.println(box.top.elevation);
						//System.out.println(box.bottom.elevation);
					}
				}
				try {
					nowFilereader.close();
					nextFilereader.close();
				}catch(Exception e) {
					System.out.println(e);
				}
			}else {
				System.out.print("no file");
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//select
		try {
			int temp;
			Coordinate top = new Coordinate();
			Coordinate bottom = new Coordinate();
			top.longitude = 9500;
			top.latitude = 9500;
			top.elevation = 1600;
			bottom.longitude = 10000;
			bottom.latitude = 10000;
			bottom.elevation = 1500;
			
			Box box = new Box();
			box.createBox(top, bottom);
			int temp2;
			double ave = 0.0;
			for(temp2 = 0; temp2 < 1; temp2++) {
				long startTime = System.currentTimeMillis();
				for(temp = 0;temp<100;temp++) {
					String value ="";
				
					value += "top_latitude > " + String.valueOf(box.top.latitude) + " AND " +
							"top_longitude > " + String.valueOf(box.top.longitude) + " AND " +
							"top_elevation < " + String.valueOf(box.top.elevation) + " AND " + 
							"bottom_latitude < " + String.valueOf(box.bottom.latitude) + " AND " +
							"bottom_longitude < " + String.valueOf(box.bottom.longitude) + " AND " +
							"bottom_elevation > " + String.valueOf(box.bottom.elevation); 
					resultset = statement.executeQuery("SELECT * FROM map where "+value+";");
					if(resultset != null) {
						while(resultset.next()) {
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
			System.out.println(e);
		}
	}
}
