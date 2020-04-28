package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Screens.GameScreen;

//#Assessment 4 added to implement powerups
public class PowerUp extends Sprite {


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

    /** The firetruck that the powerup is affecting */
    private FireTruck fireTruck;

    /** Float showing time left till a powerup is destroyed if it is not collected */
    private float timeTillDeletion = 10;

    /** Determines if a powerup is active or not */
    private boolean active = false;

    /** x position to show where the powerup will spawn*/
    private final int spawnX;

    /** y position to show where the powerup will spawn*/
    private final int spawnY;


    public PowerUp(PowerUpType type, int spawnX, int spawnY){
        super(type.getPowerUpIcon());

        this.type = type;
        this.spawnY = spawnY;
        this.spawnX = spawnX;
        this.position = new Vector2(spawnX,spawnY);
        this.texture = type.getPowerUpIcon();
        this.name =  type.getName();
        this.timer = type.getTimer();
    }

    /**
     * Draws the powerup onto the game map
     * @param mapBatch mapbatch that handles rendering
     */
    public void drawSprite(Batch mapBatch){
        if(!active) {
            mapBatch.draw(this, this.position.x, this.position.y, 1, 1);
        }
        else if(this.name.equals("immunity")){
            mapBatch.draw(this,fireTruck.getPosition().x,fireTruck.getPosition().y,1,1);
        }
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

    /**
     * Handles behaviours for the powerups that should be updated every frame
     */
    public void update(){
        if(!active){
            timeTillDeletion -= Gdx.graphics.getDeltaTime();
        }
        else{
            switch (this.name) {
                case "mirror":
                    if(timer > 0){
                        timer -= Gdx.graphics.getDeltaTime();
                    }
                    else {
                        timeTillDeletion = 0;
                    }
                    break;
                case "immunity":
                    if(timer > 0){
                        timer -= Gdx.graphics.getDeltaTime();
                    }
                    else {
                        fireTruck.setImmune(false);
                        timeTillDeletion = 0;
                    }
                    break;
                case "speed":
                    if(timer > 0){
                        timer -= Gdx.graphics.getDeltaTime();
                    }
                    else {
                        fireTruck.setSpeed(fireTruck.getSpeed()/2);
                        timeTillDeletion = 0;
                    }
                    break;
                case "damage":
                    if(timer > 0){
                        timer -= Gdx.graphics.getDeltaTime();
                    }
                    else {
                        fireTruck.setAP(fireTruck.getAP()/2);
                        timeTillDeletion = 0;
                    }
                    break;
                default:
                    //else code
                    break;
            }
        }
    }

    /**
     * Handles changes to be made immediately after an effects activation
     */
    //Case statement to show all actions for each type of powerup
    public void effect() {
        //effect is called if a powerup is reached
        switch (this.name) {
            case "immunity":
                fireTruck.setImmune(true);
                break;
            case "repair":
                fireTruck.setHP((int) fireTruck.type.getMaxHP());
                fireTruck.setReserve(fireTruck.type.getMaxReserve());
                timeTillDeletion = 0;
                break;
            case "speed":
                fireTruck.setSpeed(fireTruck.getSpeed()*2);
                break;
            case "damage":
                fireTruck.setAP(fireTruck.getAP()*2);
                break;
            default:
                //else code
                break;
        }
    }

    public Vector2 getPosition() { return position; }
    public int getSpawnX(){return this.spawnX;}
    public int getSpawnY(){return this.spawnY;}
    public float getTimeTillDeletion(){return this.timeTillDeletion;}

    /**
     * Sets the powerup to active then triggers its effect
     */
    public void setActive(){
        active = true;
        effect();
    }

    public boolean isActive() {
        return active;
    }

    public FireTruck getFireTruck() {
        return fireTruck;
    }

    public void setFireTruck(FireTruck fireTruck){this.fireTruck = fireTruck;}
}
