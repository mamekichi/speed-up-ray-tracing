package CoordinateTransformation;

import java.lang.Math;

public class Transformation {
	
	public void toGeocentric(Coordinate coordinate) {
		
		Constant constant = new Constant();
		double radLat = Math.toRadians(coordinate.latitude);
		double radLon = Math.toRadians(coordinate.longitude);
		double radEle = Math.toRadians(coordinate.elevation);
		
		double N = constant.a / Math.sqrt(1-constant.powE*Math.pow(Math.sin(radLat), 2));
		
		coordinate.x = (N + coordinate.elevation) * Math.cos(radLat) * Math.cos(radLon);
		coordinate.y = (N + coordinate.elevation) * Math.cos(radLat) * Math.sin(radLon);
		coordinate.z = (N * (1 - constant.powE) + coordinate.elevation) * Math.sin(radLat);
		
	}
	
	public double LLtoDecimal(double l) {
		int dgree = 0;
		int minute = 0;
		double second = 0.0;
		
		dgree = (int)(l / 10000.0);
		minute = (int)((l - dgree*10000.0)/100.0);
		second = (l - dgree*10000.0 - minute*100.0);
		l = dgree + (minute*60 + second)/3600;
		
		return l;
	}

}
