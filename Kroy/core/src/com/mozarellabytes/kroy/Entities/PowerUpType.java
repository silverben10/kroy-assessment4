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
    Mirror("mirror", 15.0f, 30, 30);

    /** fileName to retrieve the relevant sprite for that powerup */
    private final String fileName;

    /** timer to state how long a powerup may last*/
    private final float timer;

    /** x position to show where the powerup will spawn*/
    private final int spawnX;

    /** y position to show where the powerup will spawn*/
    private final int spawnY;

    /** the sprite for the particular powerup */
    private final Texture powerUpIcon;


    PowerUpType(String fileName, float timer, int spawnX, int spawnY){
        this.fileName = fileName;
        this.timer = timer;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.powerUpIcon = new Texture(Gdx.files.internal("sprites/powerup/"+fileName+".png"));
    }

    //Hovering animation method

    //Encounter method

    //Case statement to show all actions for each type of powerup
}
