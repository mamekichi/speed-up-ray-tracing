package Method2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polygon {
	
	Polygon(){
		
	}
	
	public void create(Octree oct, Database database) {
		try {
			File file = new File("src/Jmap/map.csv");
	
			if(file.exists()) {
				FileReader nowFilereader = new FileReader(file);
				BufferedReader nowbr = new BufferedReader(nowFilereader);
				
				String nowLine;
				
				Coordinate coordinate1 = new Coordinate();
				Coordinate coordinate2 = new Coordinate();
				
				Box box = new Box();
				
				int countnum = 0;
				String[] sCoordination = new String[3];
				while((nowLine = nowbr.readLine()) != null) {
					//read file each line
					sCoordination = nowLine.split(",", 0);
					
					coordinate1.x = Double.parseDouble(sCoordination[0]);
					coordinate1.y = Double.parseDouble(sCoordination[1]);
					coordinate1.z = Double.parseDouble(sCoordination[2]);
					coordinate2.x = coordinate1.x+0.1;
					coordinate2.y = coordinate1.y+0.1;
					coordinate2.z = coordinate1.z+0.1;
					
					System.out.println("---" + countnum + "---");
					countnum++;
						
					box.createBox(coordinate1, coordinate2);
						
					oct.insertDB(database, box);
					
				}	
				nowFilereader.close();
			}else {
				System.out.println("No file");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

