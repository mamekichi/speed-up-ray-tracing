package newMethod;

import java.util.List;

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

	public void insertDB(Coordinate t, Coordinate b) {
		try {
			if( (t.longitude > this.top.longitude && t.longitude < this.bottom.longitude) &&
				(t.latitude > this.top.latitude && t.latitude < this.bottom.latitude) &&
				(t.elevation > this.top.elevation && t.elevation < this.bottom.elevation)) {
				this.child1.insertDB(t, b);
				this.child2.insertDB(t, b);
				this.child3.insertDB(t, b);
				this.child4.insertDB(t, b);
				this.child5.insertDB(t, b);
				this.child6.insertDB(t, b);
				this.child7.insertDB(t, b);
				this.child8.insertDB(t, b);
			}
		}catch(Exception e) {
			//add DB
		}
	}

	//範囲内にあるboxをlistにいれて返す
	public void search(List<Integer> list, Coordinate t, Coordinate b) {
		try {
			if( (this.top.longitude <= b.longitude && t.longitude <= this.bottom.longitude) &&
				(this.top.latitude <= b.latitude && t.latitude <= this.bottom.latitude) &&
				(this.top.elevation <= b.elevation && t.elevation <= this.bottom.elevation) ) {
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
			list.add(this.unum);
		}
		return;

	}

	public void createTree(int level, Coordinate t, Coordinate b) {
		this.top = t;
		this.bottom = b;

		if(level == 0) {
			this.unum = (int)this.top.longitude;
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
		bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child1.createTree(level, topTemp, bottomTemp);

		//child2
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child2.createTree(level, topTemp, bottomTemp);

		//child3
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child3.createTree(level, topTemp, bottomTemp);

		//child4
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		bottomTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child4.createTree(level,topTemp, bottomTemp);

		//child5
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child5.createTree(level, topTemp, bottomTemp);

		//child6
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		bottomTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child6.createTree(level, topTemp, bottomTemp);

		//child7
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		bottomTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child7.createTree(level, topTemp, bottomTemp);

		//child8
		topTemp = new Coordinate(t);
		bottomTemp = new Coordinate(b);
		topTemp.longitude = (topTemp.longitude + bottomTemp.longitude)/2.0;
		topTemp.latitude = (topTemp.latitude + bottomTemp.latitude)/2.0;
		topTemp.elevation = (topTemp.elevation + bottomTemp.elevation)/2.0;
		child8.createTree(level, topTemp, bottomTemp);
	}
}
