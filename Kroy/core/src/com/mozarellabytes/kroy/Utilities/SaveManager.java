package com.mozarellabytes.kroy.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.google.gson.Gson;
import com.mozarellabytes.kroy.Entities.FireTruck;
import com.mozarellabytes.kroy.GameState;
import com.mozarellabytes.kroy.Screens.GameScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class SaveManager {

    public ArrayList<FireTruck> fireTruckArrayList = new ArrayList<>();

    public SaveManager() {
    }

    public static void saveGameData(GameScreen gameScreen, GameState gameState) {
        Preferences gameData = Gdx.app.getPreferences("gameData.xml");

        /* *
        GameState.java
        - activeFireTrucks
        - fortressesDestroyed
        - stationDestroyed
        - hasShownDanceTutorial

        GameScreen.java
        - Fortress ArrayList
        - FireTruck ArrayList
        - deadEntities ArrayList
        - difficultyControl
        * */

        // 1. Save GameState data.
        gameData.putInteger("activeFireTrucks", gameState.getActiveFireTrucks());
        gameData.putInteger("fortressesDestroyed", gameState.getFortressesDestroyed());
        gameData.putBoolean("stationDestroyed", gameState.isStationDestroyed());
        gameData.putBoolean("hasShownDanceTutorial", gameState.hasDanceTutorialShown());

        // 2. Save GameScreen data.
        Gson gson = new Gson();
        gameData.putString("fortressesList", gson.toJson(gameScreen.getFortresses())); // Was causing errors but fixed by removing unnecessary texture attribute from Patrol class.
        gameData.putString("fireTruckList", gson.toJson(new Queue<Vector2>())); // Was causing errors because of fire engines containing gamescreen fixed by only giving it needed information
        gameData.putString("deadEntitiesList", gson.toJson(gameScreen.getDeadEntities()));
        gameData.putString("difficultyMultiplier", gson.toJson(gameScreen.getDifficultyControl().getDifficultyMultiplier()));

        // 3. Write the data to .JSON file.
        try {
            gameData.flush();
            System.out.println("Saved game state.");
        }
        catch (Exception e) {
            System.out.println("There was an error...");
            System.out.println(e.toString());
        }
    }

    public static Preferences loadGameData() {
        return Gdx.app.getPreferences("gameData");
    }
}
