public class NBody{

	/** get the radius of the planets in a given file */
	public static double readRadius(String args) {
		In in = new In(args);

		int firstItemInFile = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	/** get the body(mass, xPos, yPos) in a given file */
	public static Body[] readBodies(String args) {
		In in = new In(args);

		int firstItemInfile = in.readInt();
		double secondItemInfile = in.readDouble();
		Body[] bodies = new Body[firstItemInfile];
		int i = 0;

		while (firstItemInfile > 0) {
			double x = in.readDouble();
			double y = in.readDouble();
			double v1 = in.readDouble();
			double v2 = in.readDouble();
			double mass = in.readDouble();
			String image = in.readString();

			Body a = new Body(x, y, v1, v2, mass, image);
			bodies[i] = a;
			i = i + 1;
			firstItemInfile = firstItemInfile - 1;
		}
		return bodies;
	}

	/** must be given 0: runtime, 1: speed of planets 2: file*/
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double r = readRadius(filename);
		Body[] bodies = readBodies(filename);
		int number = bodies.length;

		/** print the universe */
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-r, r);
		StdDraw.clear();
		StdDraw.picture(0, 75, "images/starfield.jpg");
		for (Body b: bodies){
			b.draw();
		}
		StdDraw.show();
		StdDraw.pause(2000);

		double time = 0;

		/** procedure of updating the universe */
		while (time < T){
			double[] xForces = new double[number];
			double[] yForces = new double[number];
			for (int i=0;i<number; i++){
				xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}
			for (int j=0;j<number; j++){
				bodies[j].update(dt, xForces[j], yForces[j]);
			}
			StdDraw.picture(0, 75, "images/starfield.jpg");
			for (Body b: bodies){
				b.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			time = time + dt;  // add "T = T + dt * 2" in the next line 

		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", r);

		/** when the universe stop, show the position of all planets */
		for (int i = 0; i < bodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
        	bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}
}


