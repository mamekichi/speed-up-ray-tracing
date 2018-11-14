package CoordinateTransformation;

import java.lang.Math;

public class Constant {
	double powE;
	double a;
	
	public Constant() {
		
		double f84 = 1/298.257223563;
		this.powE = f84 * (2 - f84);
		this.a = 6378137.000;
	}

}
