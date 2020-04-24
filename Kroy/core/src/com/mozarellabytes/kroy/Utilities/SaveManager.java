package com.mozarellabytes.kroy.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mozarellabytes.kroy.Entities.FireTruck;
import com.mozarellabytes.kroy.Entities.Fortress;
import com.mozarellabytes.kroy.Screens.GameScreen;

import java.util.ArrayList;

public class SaveManager {

    public SaveManager() {
    }

    public static void saveGameData(GameScreen gameScreen, int saveSlot) {
        Preferences gameData = Gdx.app.getPreferences("save"+saveSlot+".xml");

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
        gameData.putInteger("activeFireTrucks", gameScreen.gameState.getActiveFireTrucks());
        gameData.putInteger("fortressesDestroyed", gameScreen.gameState.getFortressesDestroyed());
        gameData.putBoolean("stationDestroyed", gameScreen.gameState.isStationDestroyed());
        gameData.putBoolean("hasShownDanceTutorial", gameScreen.gameState.hasDanceTutorialShown());

        // 2. Save GameScreen data.
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        // Was causing errors but fixed by removing unnecessary texture attribute from Patrol class.
        gameData.putString("deadEntitiesList", gson.toJson(gameScreen.getDeadEntities()));
        gameData.putString("currentDifficulty", gson.toJson(gameScreen.getDifficultyControl().getCurrentDifficulty()));
        gameData.putString("fortressesList", createFortressArray(gameScreen.getFortresses()).toString());
        gameData.putString("fireTruckList", createFireTruckArray(gameScreen.getStation().getTrucks()).toString());
        gameData.putFloat("fireStationHP", gameScreen.getStation().getHP());
        gameData.putInteger("gameDifficulty", gameScreen.getFixedGameDifficulty());

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

    public static Preferences loadGameData(int loadSlot) {
        Preferences prefs = Gdx.app.getPreferences("save"+loadSlot+".xml");
        System.out.println(prefs.get());
        return prefs;
    }

    /**
     * Manually constructs and returns a JsonArray of all the fire trucks in the game. This was needed because the Gson
     * library didn't appear capable of handling the Vector2 positions of the fire trucks (they weren't being serialised).
     * @param truckList The arrayList of FireTrucks from the GameScreen.
     * @return JsonArray of FireTruck objects.
     */
    public static JsonArray createFireTruckArray(ArrayList<FireTruck> truckList) {
        JsonArray JsonTruckArray = new JsonArray();
        for (FireTruck truck : truckList) {
            JsonObject JsonTruck = new JsonObject();
            JsonTruck.addProperty("type", truck.getType().toString());
            JsonTruck.addProperty("HP", truck.getHP());
            JsonTruck.addProperty("reserve", truck.getReserve());

            // Creates a new JsonObject for the truck's position, since it's composed of x and y coordinates.
            JsonObject JsonTruckPos = new JsonObject();
            JsonTruckPos.addProperty("x", Math.round(truck.getPosition().x));
            JsonTruckPos.addProperty("y", Math.round(truck.getPosition().y));

            JsonTruck.add("position", JsonTruckPos);

            JsonTruckArray.add(JsonTruck);
        }
        return JsonTruckArray;
    }

    public static JsonArray createFortressArray(ArrayList<Fortress> fortressList) {
        JsonArray JsonFortressArray = new JsonArray();
        for (Fortress fortress : fortressList) {
            JsonObject JsonFortress = new JsonObject();
            JsonFortress.addProperty("fortressType", fortress.getFortressType().toString());
            JsonFortress.addProperty("HP", fortress.getHP());

            JsonObject JsonFortressPos = new JsonObject();
            JsonFortressPos.addProperty("x", fortress.getPosition().x);
            JsonFortressPos.addProperty("y", fortress.getPosition().y);

            JsonFortress.add("position", JsonFortressPos);

            JsonFortressArray.add(JsonFortress);
        }
        return JsonFortressArray;
    }
}
