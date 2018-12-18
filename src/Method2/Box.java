package Method2;

public class Box {
	public Coordinate top;
	public Coordinate bottom;
	
	Box(){
		top = new Coordinate();
		bottom = new Coordinate();
	}
	
	public void createBox(Coordinate coordinate1, Coordinate coordinate2){
		
		//smaller x is top x
		if(coordinate1.x <= coordinate2.x) {
			top.x = coordinate1.x;
			bottom.x = coordinate2.x;
		}else {
			top.x = coordinate2.x;
			bottom.x = coordinate1.x;
		}
		
		//smaller y is top y
		if(coordinate1.y <= coordinate2.y) {
			top.y = coordinate1.y;
			bottom.y = coordinate2.y;
		}else {
			top.y = coordinate2.y;
			bottom.y = coordinate1.y;
		}
		
		//bigger z is top z
		if(coordinate1.z > coordinate2.z) {
			top.z = coordinate1.z;
			bottom.z = coordinate2.z;
		}else {
			top.z = coordinate2.z;
			bottom.z = coordinate1.z;
		}
		
	}

}
