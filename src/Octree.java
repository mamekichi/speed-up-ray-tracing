import java.util.ArrayList;

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

	int unum;

	//範囲内にあるboxをlistにいれて返す
	public void search(ArrayList<Integer> list, Coordinate t, Coordinate b) {
		try {
			if( (this.top.x < b.x && t.x < this.bottom.x) ||
				(this.top.y < b.y && t.y < this.bottom.y) ||
				(this.top.z < b.z && t.z < this.bottom.z) ) {
				this.child1.search(list, t, b);
				this.child2.search(list, t, b);
				this.child3.search(list, t, b);
				this.child4.search(list, t, b);
				this.child5.search(list, t, b);
				this.child6.search(list, t, b);
				this.child7.search(list, t, b);
				this.child8.search(list, t, b);
			}
		}catch(Exception e){
			//add list
		}
		return;

	}

	public void createTree(int level, Coordinate t, Coordinate b) {
		this.top = new Coordinate(t);
		this.bottom = new Coordinate(b);

		if(level == 0) {
			this.unum = (int)this.top.x;
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
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		bottomTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		bottomTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		bottomTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child1.createTree(level, topTemp, bottomTemp);

		//child2
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		bottomTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		bottomTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child2.createTree(level, topTemp, bottomTemp);

		//child3
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		bottomTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		bottomTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child3.createTree(level, topTemp, bottomTemp);

		//child4
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		topTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		bottomTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child4.createTree(level,topTemp, bottomTemp);

		//child5
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		bottomTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		bottomTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		topTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child5.createTree(level, topTemp, bottomTemp);

		//child6
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		bottomTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		topTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child6.createTree(level, topTemp, bottomTemp);

		//child7
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		bottomTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		topTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		topTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child7.createTree(level, topTemp, bottomTemp);

		//child8
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.x = (topTemp.x + bottomTemp.x)/2.0;
		topTemp.y = (topTemp.y + bottomTemp.y)/2.0;
		topTemp.z = (topTemp.z + bottomTemp.z)/2.0;
		child8.createTree(level, topTemp, bottomTemp);
	}
}
