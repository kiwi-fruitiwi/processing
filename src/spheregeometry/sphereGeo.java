package spheregeometry;

import processing.core.PApplet;
import processing.core.PVector;

/*
 * // Spherical Geometry, adopted from Daniel Shiffman's Coding Train
 * // https://thecodingtrain.com/CodingChallenges/025-spheregeometry.html
 */

public class sphereGeo extends PApplet {
	PVector[][] globe;
	int r;
	final int total = 25;
	float angleX = 0;
	float angleY = 0;

	public static void main(String[] args) {
		PApplet.main(new String[]{sphereGeo.class.getName()});
	}

	@Override
	public void settings() {
		size(500, 500, P3D);
	}

	@Override
	public void setup() {
		colorMode(HSB, 360f, 100f, 100f, 100f);
		frameRate(144);
		strokeWeight(2);
		stroke(0, 0, 80);

		r = 200;
		globe = new PVector[total+1][total+1];
	}

	@Override
	public void draw() {
		background(210, 80, 40);
		noStroke();

		translate(width/2f, height/2f);
		rotateX(angleX);
		rotateY(angleY);
		lights();

		for (int i = 0; i < total + 1; i++) {
			float lat = map(i, 0, total, 0, PI);
			float lon;
			float x, y, z;
			for (int j = 0; j < total + 1; j++) {
				lon = map(j, 0, total, 0, TWO_PI);
				x = r * sin(lat) * cos(lon);
				y = r * sin(lat) * sin(lon);
				z = r * cos(lat);
				globe[i][j] = new PVector(x, y, z);
			}
		}

		for (int i = 0; i < total; i++) {
			beginShape(TRIANGLES);
			for (int j = 0; j < total + 1; j++) {
				PVector v1 = globe[i][j];
				vertex(v1.x, v1.y, v1.z);
				PVector v2 = globe[i + 1][j];
        		vertex(v2.x, v2.y, v2.z);
			}
			endShape();
		}

		angleX += 0.005;
		angleY += 0.006;
	}
}
