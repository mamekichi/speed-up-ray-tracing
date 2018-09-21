
public class Octree {
	
	Coordinate top;
	Coordinate bottom;
	
	Octree child1;
	Octree child2;
	Octree child3;
	Octree child4;
	Octree child5;
	Octree child6;
	Octree child7;
	Octree child8;
	
	public void createTree(int level, Coordinate t, Coordinate b) {
		this.top = t;
		this.bottom = b;
		
		if(level == 0) {
			return;
		}
		
		child1 = new Octree();
		child2 = new Octree();
		child3 = new Octree();
		child4 = new Octree();
		child5 = new Octree();
		child6 = new Octree();
		child7 = new Octree();
		child8 = new Octree();
		
		level--;
		
		Coordinate topTemp;
		Coordinate bottomTemp;
		
		//child1
		topTemp = t;
		bottomTemp = b;
		bottomTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		bottomTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		bottomTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child1.createTree(level, topTemp, bottomTemp);
		
		//child2
		topTemp = t;
		bottomTemp = b;
		topTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		bottomTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		bottomTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child2.createTree(level, topTemp, bottomTemp);
		
		//child3
		topTemp = t;
		bottomTemp = b;
		topTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		bottomTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		bottomTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child3.createTree(level, topTemp, bottomTemp);
		
		//child4
		topTemp = t;
		bottomTemp = b;
		topTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		topTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		bottomTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child4.createTree(level,topTemp, bottomTemp);
		
		//child5
		topTemp = t;
		bottomTemp = b;
		bottomTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		bottomTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		topTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child5.createTree(level, topTemp, bottomTemp);
		
		//child6
		topTemp = t;
		bottomTemp = b;
		topTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		bottomTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		topTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child6.createTree(level, topTemp, bottomTemp);
		
		//child7
		topTemp = t;
		bottomTemp = b;
		bottomTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		topTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		topTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child7.createTree(level, topTemp, bottomTemp);
		
		//child8
		topTemp = t;
		bottomTemp = b;
		topTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		topTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		topTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child8.createTree(level, topTemp, bottomTemp);
	}
}
