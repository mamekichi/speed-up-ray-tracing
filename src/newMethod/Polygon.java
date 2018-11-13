package newMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polygon {
	
	Polygon(){
		
	}
	
	public void create(Octree oct, Database database, Box rangeBox) {
		try {
			File file = new File("src/Jmap/6544/654450.MEM");
	
			if(file.exists()) {
				FileReader nowFilereader = new FileReader(file);
				FileReader nextFilereader = new FileReader(file);
				BufferedReader nowbr = new BufferedReader(nowFilereader);
				BufferedReader nextbr = new BufferedReader(nextFilereader);
				
				int i;
				
				String nowLine;
				String nextLine;
				Map nowMap = new Map();
				Map nextMap = new Map();
				
				String nowElevationpart;
				String nextElevationpart;
				Matcher nowElevation;
				Matcher nextElevation;
				
				Coordinate coordinate1 = new Coordinate();
				Coordinate coordinate2 = new Coordinate();
				
				Box box = new Box();
				
				//except header and set nextLine
				nowbr.readLine();
				nextbr.readLine();
				nextbr.readLine();
				int countnum = 0;
				while(((nowLine = nowbr.readLine()) != null) && ((nextLine = nextbr.readLine()) != null)) {
					//read file each line
					System.out.println("---" + countnum + "---");
					countnum++;
					nowMap.meshcode = Integer.parseInt(nowLine.substring(0, 6));
					nextMap.meshcode = Integer.parseInt(nextLine.substring(0, 6));
					nowMap.recordnum = Integer.parseInt(nowLine.substring(6,9));
					nextMap.recordnum = Integer.parseInt(nextLine.substring(6, 9));
					nowElevationpart = nowLine.substring(9, 1009);
					nextElevationpart = nextLine.substring(9, 1009);
					nowElevation = Pattern.compile("[\\s\\S]{1,5}").matcher(nowElevationpart);
					nextElevation = Pattern.compile("[\\s\\S]{1,5}").matcher(nextElevationpart);
					
					i = 0;
					while(nowElevation.find() && nextElevation.find()) {
						nowMap.elevation[i] = Integer.parseInt(nowElevation.group());
						nextMap.elevation[i] = Integer.parseInt(nextElevation.group());
						i++;
					}
					
					for(i = 0; i < 199; i++) {
						coordinate1.longitude = i*50;
						coordinate1.latitude = nowMap.recordnum*50;
						coordinate1.elevation = nowMap.elevation[i];
						coordinate2.longitude = (i+1)*50;
						coordinate2.latitude = nextMap.recordnum*50;
						coordinate2.elevation = nextMap.elevation[i+1];
						
						box.createBox(coordinate1, coordinate2);
						
						//ver.1
						//oct.insertDB(database, box);
						
						//ver.2
						oct.create(database, rangeBox, box, 4);
						
						//System.out.println(box.top.elevation);
						//System.out.println(box.bottom.elevation);
					}
					
				}	
				nowFilereader.close();
				nextFilereader.close();
			}else {
				System.out.println("No file");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

