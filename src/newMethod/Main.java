package newMethod;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//file
import java.io.*;
import java.io.File;
import java.io.FileReader;

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
		
		Database database = new Database();
		try {
			oct.createTree(database, 4, top, bottom);

			//create DB
			//read file test
			try {
				File file = new File("src/Jmap/6544/654450.MEM");
			
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
							oct.insertDB(database, box);
							//System.out.println(box.top.elevation);
							//System.out.println(box.bottom.elevation);
						}
					}
					nowFilereader.close();
					nextFilereader.close();
				}else {
					System.out.print("no file");
				}
			}catch (IOException e) {
				e.printStackTrace();
			}

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
			System.out.println(oct.bottom.elevation);
			System.out.println(oct.child4.bottom.elevation);
			
			Box searchBox = new Box();
			searchBox.createBox(top, bottom);
			int i;
			String dbname = "";
			for(i = 0; i < list.size();i++) {
				dbname += list.get(i) + ",";	
			}
			long startTime = System.currentTimeMillis();
			int temp;
			for(temp = 0; temp < 1000; temp++) {
				oct.getBox(database, dbname, searchBox);
			}
			long endTime = System.currentTimeMillis();
			System.out.println(endTime-startTime);
			
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

