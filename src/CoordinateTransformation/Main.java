package CoordinateTransformation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		
		int dirloop = 0;
		int fileloop = 0;
		
		Coordinate coordinate = new Coordinate();
		Transformation trans = new Transformation();
		
		String dirname = "src/Jmap/";
		/*
		 * 1.read left top latitude and longitude
		 * 2.transform latitude and longitude to decimal
		 * loop (perhaps 200 loop)
		 *  3.read elevation group each line
		 *  loop (200 loop)
		 *   4.pick up elevation
		 *   5.transform latitude and longitude and elevation to Geo
		 *  pool
		 * pool
		 */
		try {
			FileWriter outputFW = new FileWriter("src/Jmap/map.csv");
			PrintWriter outputPW = new PrintWriter(new BufferedWriter(outputFW));
			
			File dirlist = new File(dirname);
			String dirnamelist[] = dirlist.list();
			for(dirloop = 0; dirloop < dirnamelist.length; dirloop++) {
				if(dirnamelist[dirloop].length() == 4) {
					File filelist = new File(dirname + dirnamelist[dirloop]);
					String filenamelist[] = filelist.list();
					File file = null;
				
					for(fileloop = 0; fileloop < filenamelist.length; fileloop++) {
						System.out.println(filenamelist[fileloop]);
						if(!filenamelist[fileloop].equals("654400.MEM")&&filenamelist[fileloop].length() == 10) {
							file = new File(dirname + dirnamelist[dirloop] + "/" + filenamelist[fileloop]);
					
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
						
								coordinate.latitude = trans.LLtoDecimal(coordinate.latitude);
								coordinate.longitude = trans.LLtoDecimal(coordinate.longitude);
						
								String nowElevationpart;
								Matcher nowElevation;
					
								trans.toGeocentric(coordinate);
					
								double elevation[] = new double[200];
					
								while((nowLine = nowbr.readLine()) != null) {
									
									//read file each line
									nowElevationpart = nowLine.substring(9, 1009);
									nowElevation = Pattern.compile("[\\s\\S]{1,5}").matcher(nowElevationpart);
						
									//read elevation from file 
									int j = 0;
									while(nowElevation.find()) {
										elevation[j] = Integer.parseInt(nowElevation.group());
										j++;
									}
							
									//tranform coordination and write new csv file
									System.out.println(filenamelist[fileloop]);
									for(j = 0; j < 199; j++) {
								
										//transform coordination
										coordinate.elevation = elevation[j];
										trans.toGeocentric(coordinate);
								
										//write new file
										/*
										System.out.println("---");
										System.out.println(coordinate.x);
										System.out.println(coordinate.y);
										System.out.println(coordinate.z);
										System.out.println(coordinate.latitude);
										System.out.println(coordinate.longitude);
										System.out.println(coordinate.elevation);
										*/
										outputPW.println(coordinate.x + "," + coordinate.y +  "," + coordinate.z);
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
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}

