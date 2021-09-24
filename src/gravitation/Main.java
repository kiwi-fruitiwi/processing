package gravitation;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

/*
version comments
    single attractor in the center with many planets orbiting
        background trail
    mutual attraction
    edge bounce or check?
    many attractors. add one more with each click
    move to 3D
    trails in 3D:
        entire sketch is a particle system with lifespans
            they must be drawn in order!
        or we can attempt trail object with varying transparency
    optional octree

 */
public class Main extends PApplet {
    List<Planet> planets;
    Planet attractor;
    PVector gravity;
    PeasyCam cam;

    public static void main(String[] args) {
        PApplet.main(new String[]{gravitation.Main.class.getName()});
    }

    @Override
    public void settings() {
        size(1280, 720, P3D);
    }

    @Override
    public void setup() {
        // if you don't specify the framerate, it defaults to 60
//        frameRate(144);
        rectMode(RADIUS);
        colorMode(HSB, 360f, 100f, 100f, 100f);
        stroke(0, 0, 100, 50);
        cam = new PeasyCam(this, width/2, height/2, 0, 500);

        // add many planets
        planets = new ArrayList<>();
        for (int i=0; i < 4; i++) {
            planets.add(
                    new Planet(this,
                            (int) random(width/4, width*3/4),
                            (int) random(height/4, height*3/4),
                            (int) random(200, 600),
                            (int) random(20, 100)));
        }
    }

    @Override
    public void draw() {
        background(210, 100, 30, 100);

        // have each planet apply its force to each other planet
        for (Planet p : planets) {
            // check all other planets
            for (Planet n : planets) {
                if (n != p) {
                    p.attract(this, n);
                }
            }
        }

        for (Planet p : planets) {
            p.update(this);
        }

        // show and update have to be separate because we don't want one
        // planet to rely on another planet's changes mid-loop
        for (Planet p : planets) {
            p.show(this);
        }
    }

    @Override
    public void mousePressed() {
        System.out.println(frameRate);
        lights();
//        planets.add(new Planet(this, mouseX, mouseY, 0, 100));
    }
}