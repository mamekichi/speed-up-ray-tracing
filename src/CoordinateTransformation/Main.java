package CoordinateTransformation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		
		int i;
		
		Coordinate coordinate = new Coordinate();
		Transformation trans = new Transformation();
		int dgree = 0;
		int minute = 0;
		double second = 0.0;
		
		String dirname = "src/Jmap/6544/";
		
		try {
			File filelist = new File(dirname);
			String filenamelist[] = filelist.list();
			File file = null;
			for(i = 0; i < filenamelist.length; i++) {
				if(!filenamelist[i].equals("654400.MEM")&&filenamelist[i].length() == 10) {
					
					file = new File(dirname + filenamelist[i]);
					
					if(file.exists()) {
						FileInputStream fis = new FileInputStream(file);
						InputStreamReader isr = new InputStreamReader(fis, "SJIS");
						BufferedReader nowbr = new BufferedReader(isr);
						String nowLine;
						
						nowLine = nowbr.readLine();
						
						coordinate.latitude = Double.parseDouble(nowLine.substring(710, 717));
						coordinate.longitude = Double.parseDouble(nowLine.substring(718, 725));
						
						//optimisation
						coordinate.latitude /= 10.0;
						coordinate.longitude += 10000000.0;
						coordinate.longitude /= 10.0;
						coordinate.elevation = 0.0;
						
						dgree = (int)(coordinate.latitude / 10000.0);
						minute = (int)((coordinate.latitude - dgree*10000.0)/100.0);
						second = (coordinate.latitude - dgree*10000.0 - minute*100.0);
						coordinate.latitude = dgree + (minute*60 + second)/3600;
						
						dgree = (int)(coordinate.longitude / 10000.0);
						minute = (int)((coordinate.longitude - dgree*10000.0)/100.0);
						second = (coordinate.longitude - dgree*10000.0 - minute*100.0);
						coordinate.longitude = dgree + (minute*60 + second)/3600;
						
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
						
							//read elevation from file 
							int j = 0;
							while(nowElevation.find()) {
								elevation[j] = Integer.parseInt(nowElevation.group());
								j++;
							}
							
							//tranform coordination and write new csv file
							System.out.println(filenamelist[i]);
							for(j = 0; j < 199; j++) {
								
								//transform coordination
								
								coordinate.elevation = elevation[j];
								trans.toGeocentric(coordinate);
								
								//write new file
								System.out.println("---");
								System.out.println(coordinate.x);
								System.out.println(coordinate.y);
								System.out.println(coordinate.z);
								System.out.println(coordinate.latitude);
								System.out.println(coordinate.longitude);
								System.out.println(coordinate.elevation);
								
							
								coordinate.longitude += 0.0000694;
							}
							coordinate.latitude += 0.000138;
						}		
						//nowFilereader.close();
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

