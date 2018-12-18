package Method2;

public class Coordinate {
	double longitude;
	double latitude;
	double elevation;
	double x;
	double y;
	double z;

	Coordinate(){
	}

	Coordinate(Coordinate temp){
		longitude = temp.longitude;
		latitude= temp.latitude;
		elevation = temp.elevation;
		x = temp.x;
		y = temp.y;
		z = temp.z;
	}
}
