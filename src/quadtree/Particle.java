package quadtree;

import processing.core.PApplet;

/**
 * A particle has (x,y) coordinates and can jitter around the canvas with its
 * own show / render method.
 */
public class Particle {
	float x, y;
	int r;

	public Particle(PApplet self, float x, float y) {
		this.x = x;
		this.y = y;

		this.r = 4;
	}

	public void move(PApplet self) {
		x += self.random(-1, 1);
		y += self.random(-1, 1);
	}

	public void render(PApplet self) {
		self.noStroke();
		self.fill(0, 0, 100);
		self.circle(x, y, r*2);
	}
}
