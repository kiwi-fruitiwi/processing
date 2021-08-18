package particlesystem;

import processing.core.PApplet;

public class Main extends PApplet {
	public static void main(String[] args) {
		PApplet.main(new String[] {Main.class.getName()});
	}

	@Override
	public void settings() {
		size(700, 600);
	}

	@Override
	public void setup() {
		rectMode(RADIUS);
		colorMode(HSB, 360f, 100f, 100f, 100f);
	}

	@Override
	public void draw() {
		background(210, 100, 30, 100);
		rect(mouseX, mouseY, 20, 20);
	}

	@Override
	public void mousePressed() {
		System.out.println(mouseX);
	}
}