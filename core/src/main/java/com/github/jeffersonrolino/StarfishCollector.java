package com.github.jeffersonrolino;

import com.github.jeffersonrolino.entities.BaseActor;
import com.github.jeffersonrolino.entities.Starfish;
import com.github.jeffersonrolino.entities.Turtle;

public class StarfishCollector extends GameBeta {
    private Turtle turtle;
    private Starfish starfish;
    private BaseActor ocean;

    @Override
    public void initialize() {
        ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);

        starfish = new Starfish(380, 380, mainStage);

        turtle = new Turtle(20, 20, mainStage);
    }

    @Override
    public void update(float dt) {

    }
}
