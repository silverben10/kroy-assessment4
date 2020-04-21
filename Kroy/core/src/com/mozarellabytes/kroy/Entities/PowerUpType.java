package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * PowerUpType is an enum that defines unique attributes
 * will display different sprites based on type of powerup
 * contains hovering animation method
 * contains method for handling interaction with fireTruck
 */
public enum PowerUpType {
    Mirror("mirror", 15.0f, 30, 30),
    Immunity("immunity", 15.0f,10,10),
    Repair("repair", 0f, 20,20),
    Speed("speed", 15.0f,40,40),
    Damage("damage", 15.0f, 50,50);

    /** fileName to retrieve the relevant sprite for that powerup */
    private final String fileName;

    /** timer to state how long a powerup may last*/
    private final float timer;

    /** x position to show where the powerup will spawn*/
    private final int spawnX;

    /** y position to show where the powerup will spawn*/
    private final int spawnY;

    /** powerup position*/
    private Vector2 position;

    /** the sprite for the particular powerup */
    private final Texture powerUpIcon;

    PowerUpType(String fileName, float timer, int spawnX, int spawnY){
        this.fileName = fileName;
        this.timer = timer;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.position = new Vector2(spawnX,spawnY);
        this.powerUpIcon = new Texture(Gdx.files.internal("sprites/powerup/"+fileName+".png"));
    }

    public String getName(){return this.fileName;}
    public float getTimer(){return this.timer;}
    public int getSpawnX(){return this.spawnX;}
    public int getSpawnY(){return this.spawnY;}
    public Vector2 getPosition(){return this.position;}
    public Texture getPowerUpIcon(){return this.powerUpIcon;}


    //Encounter method

}
