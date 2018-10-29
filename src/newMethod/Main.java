package newMethod;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
		System.out.println("hello");
		
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
				
				double latitude;
				double longitude;
				
				Coordinate coordinate1 = new Coordinate();
				Coordinate coordinate2 = new Coordinate();
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
					System.out.println(nowMap.meshcode);
					System.out.println(nowMap.recordnum);
					System.out.println(nextMap.meshcode);
					System.out.println(nextMap.recordnum);
					for(j = 0; j < 200; j++) {
						System.out.println(nowMap.elevation[j]);
						System.out.println(nextMap.elevation[j]);
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

		top.longitude = 0;
		top.latitude = 0;
		top.elevation = 0;
		bottom.longitude = 1024;
		bottom.latitude = 1024;
		bottom.elevation = 1024;

		oct.createTree(3, top, bottom);

		//create DB
		oct.insertDB(top,bottom);

		//search
		top.longitude = 0;
		top.latitude = 0;
		top.elevation = 0;
		bottom.longitude = 600;
		bottom.latitude = 600;
		bottom.elevation = 0;
		List<Integer> list = new ArrayList<>();
		oct.search(list, top, bottom);
		System.out.println(list);


		return;
	}
}

