package CoordinateTransformation;

public class Main {
	public static void main(String[] args) {
		
		Coordinate coordinate = new Coordinate();
		Transformation trans = new Transformation();
		
		coordinate.latitude = 36.103774791666666;
		coordinate.longitude = 140.08785504166664;
		coordinate.elevation = 25.72;
		
		trans.toGeocentric(coordinate);
		
		System.out.println(coordinate.x);
		System.out.println(coordinate.y);
		System.out.println(coordinate.z);
		
		
	}
}

