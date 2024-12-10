package com.github.jeffersonrolino;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.github.jeffersonrolino.entities.*;

import java.util.ArrayList;

public class StarfishCollector extends GameBeta {
    private Turtle turtle;
    private boolean win;
    ArrayList<Starfish> starfishes = new ArrayList<>();
    ArrayList<Rock> rocks = new ArrayList<>();
    int amountOfStarfishesCollected = 0;


    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);

        starfishes.add(new Starfish(400, 400, mainStage));
        starfishes.add(new Starfish(500, 100, mainStage));
        starfishes.add(new Starfish(100, 450, mainStage));
        starfishes.add(new Starfish(200, 250, mainStage));

        rocks.add(new Rock(200, 150, mainStage));
        rocks.add(new Rock(100, 300, mainStage));
        rocks.add(new Rock(300, 350, mainStage));
        rocks.add(new Rock(450, 200, mainStage));

        turtle = new Turtle(20, 20, mainStage);

        win = false;

    }

    @Override
    public void update(float dt) {
        for(Rock rock : rocks){
            turtle.preventOverlap(rock);
        }

        for(Starfish starfish : starfishes){
            if(turtle.overlaps(starfish) && !starfish.collected){
                starfish.collected = true;
                amountOfStarfishesCollected++;
                starfish.clearActions();
                starfish.addAction(Actions.fadeOut(1));
                starfish.addAction(Actions.after(Actions.removeActor()));

                Whirlpool whirlpool = new Whirlpool(0, 0, mainStage);
                whirlpool.centerAtActor(starfish);
                whirlpool.setOpacity(0.25f);
            }

            if(amountOfStarfishesCollected == starfishes.size() && !win){
                win = true;
                BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
                youWinMessage.loadTexture("you-win.png");
                youWinMessage.centerAtPosition(400, 300);
                youWinMessage.setOpacity(0);
                youWinMessage.addAction(Actions.delay(1));
                youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
            }
        }
    }
}
