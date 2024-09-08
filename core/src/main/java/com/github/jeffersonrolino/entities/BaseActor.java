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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class BaseActor extends Actor {
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;
    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;


    public BaseActor(float x, float y, Stage stage) {
        // Call the constructor from Actor Class
        super();

        this.animation = null;
        this.elapsedTime = 0;
        this.animationPaused = false;

        this.velocityVec = new Vector2(0, 0);
        this.accelerationVec = new Vector2(0, 0);
        this.acceleration = 0;
        this.maxSpeed = 1000;
        this.deceleration = 0;

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

    public void setSpeed(float speed){
        // if lenght is zero, then assume motion angle is zero degrees
        if(velocityVec.len() == 0){
            velocityVec.set(speed, 0);
        }
        else {
            velocityVec.setLength(speed);
        }
    }

    public float getSpeed(){
        return velocityVec.len();
    }

    public void setMotionAngle(float angle){
        velocityVec.setAngleDeg(angle);
    }

    public float getMotionAngle(){
        return velocityVec.angleDeg();
    }

    public void setAcceleration(float acceleration){
        this.acceleration = acceleration;
    }

    public void accelerateAtAngle(float angle){
        this.accelerationVec.add(new Vector2(acceleration, 0).setAngleDeg(angle));
    }

    public void accelerateForward(){
        this.accelerateAtAngle(getRotation());
    }

    public void setMaxSpeed(float maxSpeed){
        this.maxSpeed = maxSpeed;
    }

    public void setDeceleration(float deceleration){
        this.deceleration = deceleration;
    }

    public void applyPhysics(float dt){
        //apply acceleration
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        float speed = getSpeed();

        // decrease speed (decelerate) when not accelerating
        if(accelerationVec.len() == 0){
            speed -= deceleration * dt;
        }

        // keep speed within set bounds
        speed = MathUtils.clamp(speed, 0, maxSpeed);

        // update velocity
        setSpeed(speed);

        // apply velocity
        moveBy(velocityVec.x * dt, velocityVec.y * dt);

        // reset acceleration
        accelerationVec.set(0, 0);
    }


    public void act(float dt){
        super.act(dt);

        if(!animationPaused){
            elapsedTime += dt;
        }
    }

    public boolean isMoving(){
        return (getSpeed() > 0);
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
