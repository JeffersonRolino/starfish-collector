package com.github.jeffersonrolino.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BaseActor extends Actor {
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;


    public BaseActor(float x, float y, Stage stage) {
        // Call the constructor from Actor Class
        super();

        animation = null;
        elapsedTime = 0;
        animationPaused = false;

        // Perform additional initialization tasks
        setPosition(x, y);
        stage.addActor(this);
    }


    public void setAnimation(Animation<TextureRegion> animation){
        this.animation = animation;
        TextureRegion textureRegion = this.animation.getKeyFrame(0);
        float w = textureRegion.getRegionWidth();
        float h = textureRegion.getRegionHeight();
        setSize(w, h);
        setOrigin(w/2, h/2);
    }


    public void setAnimationPaused(boolean pause){
        animationPaused = pause;
    }


    public void act(float dt){
        super.act(dt);

        if(!animationPaused){
            elapsedTime += dt;
        }
    }


    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        // Apply color tint effect
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);

        if(animation != null && isVisible()){
            batch.draw(animation.getKeyFrame(elapsedTime), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation()
            );
        }
    }


    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop){
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int n = 0; n < fileCount; n++) {
            String fileName = fileNames[n];
            Texture texture = new Texture(fileName);
            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        }
        else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if(this.animation == null){
            setAnimation(anim);
        }

        return anim;
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop){
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        int frameWidth = texture.getWidth();
        int frameHeight = texture.getHeight();

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                textureArray.add(temp[r][c]);
            }
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        }
        else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if(this.animation == null){
            setAnimation(anim);
        }

        return anim;
    }


    public Animation<TextureRegion> loadTexture(String fileName){
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }


    public boolean isAnimationFinished(){
        return animation.isAnimationFinished(elapsedTime);
    }
}
