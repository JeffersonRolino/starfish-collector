package com.github.jeffersonrolino;

import com.badlogic.gdx.graphics.Texture;
import com.github.jeffersonrolino.entities.ActorBeta;
import com.github.jeffersonrolino.entities.Turtle;

public class StarfishCollectorBeta extends GameBeta {
    private Turtle turtle;
    private ActorBeta starfish;
    private ActorBeta ocean;
    private ActorBeta winMessage;

    private boolean win;

    public void initialize()
    {
        ocean = new ActorBeta();
        ocean.setTexture(new Texture("water.jpg"));
        mainStage.addActor(ocean);

        starfish = new ActorBeta();
        starfish.setTexture(new Texture("starfish.png"));
        starfish.setPosition(380, 380);
        mainStage.addActor(starfish);

        turtle = new Turtle();
        turtle.setTexture(new Texture("turtle-1.png"));
        turtle.setPosition(20, 20);
        mainStage.addActor(turtle);

        winMessage = new ActorBeta();
        winMessage.setTexture(new Texture("you-win.png"));
        winMessage.setPosition(180, 180);
        winMessage.setVisible(false);
        mainStage.addActor(winMessage);

        win = false;
    }

    public void update(float dt)
    {
        // check win condition: turtle must be overlapping starfish
        if(turtle.overlaps(starfish))
        {
            starfish.remove();
            winMessage.setVisible(true);
        }
    }
}
