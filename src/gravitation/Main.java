package gravitation;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Thanks to our hero Abe Pazos at https://vimeo.com/channels/p5idea, who
 * teaches us how to use Processing inside IDEA
 */
public class Main extends PApplet {
    List<Planet> planets;
    PVector gravity;

    public static void main(String[] args) {
        PApplet.main(new String[]{gravitation.Main.class.getName()});
    }

    @Override
    public void settings() {
        size(700, 600);
    }

    @Override
    public void setup() {
        rectMode(RADIUS);
        colorMode(HSB, 360f, 100f, 100f, 100f);

        gravity = new PVector(0, 0.1f);
        planets = new ArrayList<>();
        for (int i=0; i < 1000; i++) {
            planets.add(new Planet(this, (int) random(width),
                    (int) random(height),
                    (int) random(20)+1));
        }
    }

    @Override
    public void draw() {
        background(210, 100, 30, 100);

        for (Planet p : planets) {
            p.update(this);
            p.show(this);
            p.apply_force(this, gravity);
        }
    }

    @Override
    public void mousePressed() {
        System.out.println(mouseX);
    }
}