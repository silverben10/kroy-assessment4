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
    Mirror("mirror", 30f),
    Immunity("immunity", 10.0f),
    Repair("repair", 0f),
    Speed("speed", 20.0f),
    Damage("damage", 20.0f);

    /** fileName to retrieve the relevant sprite for that powerup */
    private final String fileName;

    /** timer to state how long a powerup may last*/
    private final float timer;


    /** the sprite for the particular powerup */
    private final Texture powerUpIcon;

    PowerUpType(String fileName, float timer){
        this.fileName = fileName;
        this.timer = timer;
        this.powerUpIcon = new Texture(Gdx.files.internal("sprites/powerup/"+fileName+".png"));
    }

    public String getName(){return this.fileName;}
    public float getTimer(){return this.timer;}
    public Texture getPowerUpIcon(){return this.powerUpIcon;}


    //Encounter method

}
