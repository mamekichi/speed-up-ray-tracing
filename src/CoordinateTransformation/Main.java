package CoordinateTransformation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		
		int i;
		
		Coordinate coordinate = new Coordinate();
		Transformation trans = new Transformation();
		
		String dirname = "src/Jmap/6544/";
		
		try {
			File filelist = new File(dirname);
			String filenamelist[] = filelist.list();
			File file = null;
			for(i = 0; i < filenamelist.length; i++) {
				if(!filenamelist[i].equals("._.DS_Store") && !filenamelist[i].equals(".DS_Store")) {
					
					if(filenamelist[i].length() != 10){ 
						filenamelist[i] = filenamelist[i].substring(2,filenamelist[i].length());
					}
					file = new File(dirname + filenamelist[i]);
	
					if(file.exists()) {
						FileReader nowFilereader = new FileReader(file);
						BufferedReader nowbr = new BufferedReader(nowFilereader);
					
						String nowLine;
				
						//except header and set nextLine
						nowLine = nowbr.readLine();
						nowLine = nowbr.readLine();
						System.out.println(filenamelist[i]);
						System.out.println(nowLine.substring(887, 894));
						coordinate.latitude = Double.parseDouble(nowLine.substring(887, 894));
						coordinate.longitude = Double.parseDouble(nowLine.substring(895, 903));
						coordinate.elevation = 0.0;
					
						String nowElevationpart;
						Matcher nowElevation;
					
						trans.toGeocentric(coordinate);
					
						double elevation[] = new double[200];
					
						while((nowLine = nowbr.readLine()) != null) {
							//read file each line
							//nowMap.meshcode = Integer.parseInt(nowLine.substring(0, 6));
							//nowMap.recordnum = Integer.parseInt(nowLine.substring(6,9));
							nowElevationpart = nowLine.substring(9, 1009);
							nowElevation = Pattern.compile("[\\s\\S]{1,5}").matcher(nowElevationpart);
						
							int j = 0;
							while(nowElevation.find()) {
								elevation[j] = Integer.parseInt(nowElevation.group());
								j++;
							}	
					
							for(j = 0; j < 199; j++) {
								coordinate.elevation = elevation[j];
								trans.toGeocentric(coordinate);
							
								//System.out.println(coordinate.x);
								//System.out.println(coordinate.y);
								//System.out.println(coordinate.z);
								//System.out.println("");
							
								coordinate.longitude += 0.25;
							}
							coordinate.latitude += 0.5;
						}		
						nowFilereader.close();
					}else {
						System.out.println("No file");
					}
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("haittayo");
		}
		
		//////////////////////////////////////////////////
		/*
		coordinate.latitude = 36.103774791666666;
		coordinate.longitude = 140.08785504166664;
		coordinate.elevation = 25.72;
		
		trans.toGeocentric(coordinate);
		
		System.out.println(coordinate.x);
		System.out.println(coordinate.y);
		System.out.println(coordinate.z);
		
		*/
	}
}

