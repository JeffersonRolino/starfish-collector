package com.github.jeffersonrolino.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Turtle extends BaseActor {
    public Turtle(float x, float y, Stage stage) {
        super(x, y, stage);

        String[] fileNames = {
            "turtle-1.png", "turtle-2.png", "turtle-3.png", "turtle-4.png", "turtle-5.png", "turtle-6.png"
        };

        loadAnimationFromFiles(fileNames, 0.1f, true);
    }

    public void act(float dt)
    {
        super.act(dt);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            this.moveBy(-1, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            this.moveBy(1, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            this.moveBy(0, 1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            this.moveBy(0, -1);
        }
    }
}
