package sbfac;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import static java.lang.Math.sqrt;


public class Vehicle extends PApplet {
	private PVector pos;
	private PVector vel;
	private PVector acc;
	private float max_speed;
	private float max_force;
	private int r;
	private int ACC_VECTOR_SCALE;


	public Vehicle(int x, int y) {
		this.pos = new PVector(x, y);
		this.vel = new PVector(random(-1, 1), random(-1, 1));
		this.acc = new PVector(0, 0);

		this.max_speed = random(3f, 5f);
		this.max_force = random(0.02f, 0.03f);
		this.r = 20;
		this.ACC_VECTOR_SCALE = 2000;
	}

	// assume self.mass is 1, so we don't need to worry about mass
	public void apply_force(PVector force) {
		// F = ma.Since m = 1, F = a
		force.limit(this.max_force);
		this.acc.add(force);
	}

	// how do the position and velocity change with each frame?
	// don't forget to limit the velocity!
	public void update(PApplet self) {
		this.pos.add(this.vel);
		this.vel.add(this.acc);
		this.vel.limit(this.max_speed);
		this.acc = new PVector(0, 0);
	}

	public void show(PApplet self) {
		self.pushMatrix();
		self.translate(this.pos.x, this.pos.y);

		self.rotate(this.vel.heading());

		// this is where we draw our object. we're going to try for a 9S Hackbot
        // https://puu.sh/I3E19/9d32002c25.png
		r = this.r;

		float T = 0.4f; // how far away is the tip away from the origin?
		float C = 0.2f; // what is the radius of the inner circle?
		float B = 0.3f; // how far away is the butt away from the origin?

		self.fill(0, 0, 100, 75);
		self.stroke(0, 0, 0, 100);
		self.strokeWeight(1);
		self.beginShape();
		self.vertex(r, 0); // front tip
		self.vertex(0, r*T); // top
		self.vertex(-r*T, 0); // butt
		self.vertex(0, -r*T); // bottom
		self.vertex(r, 0); // front tip
		self.endShape();

		self.fill(0, 0, 0, 90);
		self.circle(0, 0, r*C);
		self.stroke(0, 0, 0, 100);
		self.strokeWeight(1);
		self.line(0, 0, -r*T, 0); // line to the butt

		float x = (float) ((r*T)/(sqrt(3)+T));
		self.line(0, 0, x, (float) sqrt(3)*x); // line to the top 120 degrees
		self.line(0, 0, x, (float) -sqrt(3)*x); // line to the bottom 120 degrees

        // two little squares in the back
		self.rectMode(PConstants.CENTER);
		self.fill(0, 0, 100, 50);
		self.strokeWeight(1);
		self.square(r*-B, r*T, r*0.2f);
		self.square(r*-B, -r*T, r*0.2f);

		self.popMatrix();
	}

	public void edge_wrap(PApplet self) {
		if (this.pos.x + this.r > self.width)
			this.pos.x = this.r;
		if (this.pos.x - this.r < 0)
			this.pos.x = self.width - this.r;

		if (this.pos.y + this.r > self.height)
			this.pos.y = this.r;
		if (this.pos.y - this.r < 0)
			this.pos.y = self.height - this.r;
	}
}