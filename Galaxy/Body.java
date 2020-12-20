public class Body{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	/** constructor */
	public Body(double xP, double yP, double xV,
              double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	/** constructor */
	public Body(Body b){
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	/** calculate the distance between this planet and the given planet */
	public double calcDistance(Body b){
		double distance = Math.sqrt((this.xxPos - b.xxPos) * (this.xxPos - b.xxPos)
		+(this.yyPos - b.yyPos) * (this.yyPos - b.yyPos));
		return distance;
	}

	/** calculate the force exerted by given planet */
	public double calcForceExertedBy(Body b){
		double G = 6.67 * Math.pow(10,-11);
		double F = G * this.mass * b.mass / Math.pow(this.calcDistance(b), 2);
		if (this.equals(b)){
			return 0;
		}
		return F; 
	}

	/** calculate the force exerted by b along x direction */
	public double calcForceExertedByX(Body b){
		double Fx = this.calcForceExertedBy(b) * (b.xxPos - this.xxPos) / this.calcDistance(b);
		return Fx;
	}

	/** calculate the force exerted by b along y direction */
	public double calcForceExertedByY(Body b){
		double Fy = this.calcForceExertedBy(b) * (b.yyPos - this.yyPos) / this.calcDistance(b);
		return Fy;
	}

	/** calclate the force exerted by all other planets along x direction */
	public double calcNetForceExertedByX(Body[] allBodys){ 
		double NFx = 0;
		for(Body b : allBodys){
			if (this.equals(b)){
				continue;
			}
			NFx = NFx + this.calcForceExertedByX(b);
		}
		return NFx;
	}

	/** calculate the force exerted by all other planets long y direction */
	public double calcNetForceExertedByY(Body[] allBodys){ 
		double NFy = 0;
		for(Body b : allBodys){
			if (this.equals(b)){
				continue;
			}
			NFy = NFy + this.calcForceExertedByY(b);
		}
		return NFy;
	}

	/** udpadet the planet position after moving (affected by the gravity*/
	public void update(double dt, double fX, double fY){
		double ax = fX/mass;
		double ay = fY/mass;
		xxVel = xxVel + dt * ax;
		yyVel = yyVel + dt * ay;
		xxPos = xxPos + dt * xxVel;
		yyPos = yyPos + dt * yyVel;
	}

	/** draw all of the planets */
	public void draw() {
		StdDraw.enableDoubleBuffering();
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
		StdDraw.show();
	}
}

