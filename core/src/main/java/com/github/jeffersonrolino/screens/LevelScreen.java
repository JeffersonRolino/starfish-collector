package com.github.jeffersonrolino.screens;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.github.jeffersonrolino.entities.*;

import java.util.ArrayList;
import java.util.Random;

public class LevelScreen extends BaseScreen {
    private Turtle turtle;
    private boolean win;
    int amountOfStarfishesCollected = 0;


    ArrayList<Starfish> starfishes;
    ArrayList<Rock> rocks;


    @Override
    public void initialize() {
        starfishes = new ArrayList<Starfish>();
        rocks = new ArrayList<Rock>();

        Random random = new Random();

        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);

        BaseActor.setWorldBounds(ocean);

        Starfish starfish1 = new Starfish(400, 400, mainStage);
        starfishes.add(starfish1);
        Starfish starfish2 = new Starfish(500, 100, mainStage);
        starfishes.add(starfish2);
        Starfish starfish3 = new Starfish(100, 450, mainStage);
        starfishes.add(starfish3);
        Starfish starfish4 = new Starfish(200, 250, mainStage);
        starfishes.add(starfish4);

        Rock rock1 = new Rock(200, 150, mainStage);
        rocks.add(rock1);
        Rock rock2 = new Rock(100, 300, mainStage);
        rocks.add(rock2);
        Rock rock3 = new Rock(300, 350, mainStage);
        rocks.add(rock3);
        Rock rock4 = new Rock(450, 200, mainStage);
        rocks.add(rock4);

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
                BaseActor youWinMessage = new BaseActor(0, 0, uiStage);
                youWinMessage.loadTexture("you-win.png");
                youWinMessage.centerAtPosition(400, 300);
                youWinMessage.setOpacity(0);
                youWinMessage.addAction(Actions.delay(1));
                youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
            }
        }
    }
}
