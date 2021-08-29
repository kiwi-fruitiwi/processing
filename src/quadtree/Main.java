package quadtree;

import processing.core.PApplet;

public class Main extends PApplet {
	// done: vehicle, target


	
	public static void main(String[] args) {
		PApplet.main(new String[] {Main.class.getName()});
	}

	@Override
	public void settings() {
		size(1000, 800);
	}

	@Override
	public void setup() {
		colorMode(HSB, 360f, 100f, 100f, 100f);
		frameRate(144);


	}

	@Override
	public void draw() {
		background(210, 80, 40);


	}
}