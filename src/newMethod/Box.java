package newMethod;

public class Box {
	Coordinate top;
	Coordinate bottom;
	
	Box(){
		top = new Coordinate();
		bottom = new Coordinate();
	}
	
	public void createBox(Coordinate coordinate1, Coordinate coordinate2){
		
		//smaller x is top x
		if(coordinate1.longitude <= coordinate2.longitude) {
			top.longitude = coordinate1.longitude;
			bottom.longitude = coordinate2.longitude;
		}else {
			top.longitude = coordinate2.longitude;
			bottom.longitude = coordinate1.longitude;
		}
		
		//smaller y is top y
		if(coordinate1.latitude <= coordinate2.latitude) {
			top.latitude = coordinate1.latitude;
			bottom.latitude = coordinate2.latitude;
		}else {
			top.latitude = coordinate2.latitude;
			bottom.latitude = coordinate1.latitude;
		}
		
		//bigger z is top z
		if(coordinate1.elevation > coordinate2.elevation) {
			top.elevation = coordinate1.elevation;
			bottom.elevation = coordinate2.elevation;
		}else {
			top.elevation = coordinate2.elevation;
			bottom.elevation = coordinate1.elevation;
		}
		
	}

}
