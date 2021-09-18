package gravitation;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

/*
version comments
    single attractor in the center with many planets orbiting
        background trail
    mutual attraction
    many attractors. add one more with each click
    move to 3D

 */
public class Main extends PApplet {
    List<Planet> planets;
    Planet attractor;
    PVector gravity;

    public static void main(String[] args) {
        PApplet.main(new String[]{gravitation.Main.class.getName()});
    }

    @Override
    public void settings() {
        size(640, 360);
    }

    @Override
    public void setup() {
        frameRate(144);
        rectMode(RADIUS);
        colorMode(HSB, 360f, 100f, 100f, 100f);
        stroke(0, 0, 100, 50);

        attractor = new Planet(this, width/2, height/2, 500);
        attractor.setVel(this, new PVector(0, 0));

//        gravity = new PVector(0f, 0.01f);
        planets = new ArrayList<>();
        for (int i=0; i < 100; i++) {
            int x = width/2;
            int y = height/2;

            while (dist(x, y, width/2, height/2) < 100) {
                x = (int) random(width);
                y = (int) random(height);
            }

            planets.add(new Planet(this, x, y, (int) random(20)+1));
        }
    }

    @Override
    public void draw() {
        background(210, 100, 30, 100);

        attractor.show(this);
        attractor.update(this);

        for (Planet p : planets) {
            p.update(this);
            p.show(this);
            attractor.attract(this, p);
//            p.apply_force(this, PVector.mult(gravity, p.mass));
        }
    }

    @Override
    public void mousePressed() {
        System.out.println(frameRate);
    }
}