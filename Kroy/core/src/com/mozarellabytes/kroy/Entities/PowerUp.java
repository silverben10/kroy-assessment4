package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Screens.GameScreen;

public class PowerUp extends Sprite {

    /** Enables access to functions in GameScreen */
    private final GameScreen gameScreen;


    /** Defines set of pre-defined attributes */
    public final PowerUpType type;

    /** Position of powerup in tiles */
    public Vector2 position;

    /** Texture for powerup */
    public Texture texture;

    /** Name for the powerup */
    public String name;

    /** Float showing time left of powerup effect */
    public float timer;

    public PowerUp(GameScreen gameScreen, PowerUpType type){
        super(type.getPowerUpIcon());

        this.type = type;
        this.gameScreen = gameScreen;
        this.position = type.getPosition();
        this.texture = type.getPowerUpIcon();
        this.name =  type.getName();
        this.timer = type.getTimer();
    }

    public void drawSprite(Batch mapBatch){
        mapBatch.draw(this, this.position.x, this.position.y, 1, 1);
    }

    //Hovering animation method
    public void hover(){
        for(int i = 0; i<= 3; i++) {
            for (float j = 0f; j <= 1f; j = j + 0.1f) {
                if((i == 0) || (i == 3)) {
                    position.y += j;
                }else if((i == 1) || (i==2)){
                    position.y -= j;
                }
            }
        }
    }

    //Case statement to show all actions for each type of powerup
    public void effect() {
        //effect is called if a powerup is reached
        switch (this.name) {
            case "mirror":
                // code for mirror
                //begin timer
                //spawn mirror fire truck (must be done in gamescreen class)
                //when timer ends, destroy
                break;
            case "immunity":
                //code for immunity
                //begin timer
                //add modifier of 0 to prevent enemies injuring fire truck
                //when timer ends, change modifier to 1
            case "repair":
                //code for repair and refill
                //set firetruck's HP and water to max
            case "speed":
                //code for speed boost
                //begin timer
                //increase firetruck's speed by some static modifier
                //when timer ends decrease firetruck's speed by same modifier
            case "damage":
                //code for damage boost
                //begin timer
                //increase firetruck's AP by some modifier
                //when timer ends decrease AP by same modifier
            default:
                //else code
        }
    }

    public Vector2 getPosition() { return position; }
}
