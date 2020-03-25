package com.mozarellabytes.kroy.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.mozarellabytes.kroy.GameState;
import com.mozarellabytes.kroy.Screens.GameScreen;

public class SaveManager {

    public SaveManager() {
    }

    public void saveGameData(GameScreen gameScreen, GameState gameState) {
        Preferences gameData = Gdx.app.getPreferences("gameData");

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
        gameData.putString("fortressesList", gson.toJson(gameScreen.getFortresses()));
        gameData.putString("fireTruckList", gson.toJson(gameScreen.getStation().getTrucks()));
        gameData.putString("deadEntitiesList", gson.toJson(gameScreen.getDeadEntities()));
        gameData.putString("difficultyMultiplier", gson.toJson(gameScreen.getDifficultyControl().getDifficultyMultiplier()));
    }

    public Preferences loadGameData() {
        return Gdx.app.getPreferences("gameData");
    }
}
