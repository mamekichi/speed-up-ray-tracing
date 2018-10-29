package newMethod;

public class Coordinate {
	double longitude;
	double latitude;
	double elevation;

	Coordinate(){
	}

	Coordinate(Coordinate temp){
		longitude = temp.longitude;
		latitude= temp.latitude;
		elevation = temp.elevation;
	}
}
