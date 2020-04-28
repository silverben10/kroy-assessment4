package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.reflect.TypeToken;
import com.mozarellabytes.kroy.Entities.*;
import com.mozarellabytes.kroy.GameState;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Utilities.*;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Random;

/**
 * The Screen that our game is played in.
 * Accessed from MenuScreen when the user
 * clicks the Start button, and exits when
 * the player wins or loses the game
 */
public class GameScreen implements Screen {

    /**
     * Instance of our game that allows us the change screens
     */
    private final Kroy game;


    //#Assessment 4 allows for difficulty selection
    /**
     * The difficulty level of the game
     */
    private int fixedGameDifficulty;


    /*
     * Renders our tiled map
     */
    private final OrthogonalTiledMapRenderer mapRenderer;

    /**
     * Camera to set the projection for the screen
     */
    private final OrthographicCamera camera;

    /**
     * Renders shapes such as the health/reserve
     * stat bars above entities
     */
    private final ShapeRenderer shapeMapRenderer;

    /**
     * Stores the layers of our tiled map
     */
    private final MapLayers mapLayers;

    /**
     * Stores the structures layers, stores the background layer
     */
    private final int[] structureLayersIndices, backgroundLayerIndex;

    /**
     * Batch that has dimensions in line with the 40x25 map
     */
    private final Batch mapBatch;

    /**
     * Used for shaking the camera when a bomb hits a truck
     */
    private final CameraShake camShake;

    /**
     * Stores whether the game is running or is paused
     */
    private PlayState state;

    /**
     * Deals with all the user interface on the screen
     * that does not want to be inline with the map
     * coordinates, e.g. big stat bars, buttons, pause
     * screen
     */
    private GUI gui;

    /**
     * Stores the progress through the game. It keeps
     * track of trucks/fortresses and will end the game
     * once an end game condition has been met
     */
    public final GameState gameState;

    /**
     * List of Fortresses currently active on the map
     */
    private final ArrayList<Fortress> fortresses;

    private final ArrayList<Patrol> patrols;


    //#Assessment 4 Added powerups
    /**
     * List of active PowerUps on the map
     */
    private final ArrayList<PowerUp> powerUps;

    /** List of PowerUps to remove from the map */
    private final ArrayList<PowerUp> powerUpsToRemove;



    /**
     * Where the FireEngines' spawn, refill and repair
     */
    private final FireStation station;

    private ArrayList<FireTruck> trucksToDestroy;

    private ArrayList<FireTruck> mirrorTrucks;

    /**
     * The FireTruck that the user is currently drawing a path for
     */
    public FireTruck selectedTruck;

    /**
     * The entity that the user has clicked on to show
     * the large stats in the top left corner
     */
    public Object selectedEntity;

    /**
     * A class keeping track of the current difficulty and the time to the next change
     */
    private DifficultyControl difficultyControl;

    /**
     * An arraylist of all the entities that have been destroyed
     */
    private ArrayList<DestroyedEntity> deadEntities;

    public FPSLogger fpsCounter;

    private Preferences savedData;

    public boolean wasPaused = false;

    /**
     * Play when the game is being played
     * Pause when the pause button is clicked
     */
    public enum PlayState {
        PLAY, PAUSE
    }

    //#Assessment 4 Explosion animations
    /**
     * The list of all active explosions
     */
    private ArrayList<Explosion> explosions;
    /**
     * The list of all explosions to be removed
     */
    private ArrayList<Explosion> explosionsToRemove;



    /**
     * Constructor which has the game passed in
     *
     * @param game LibGdx game
     */
    public GameScreen(Kroy game, int loadSlot, int fixedGamedifficulty) {
        this.game = game;
        this.fixedGameDifficulty = fixedGamedifficulty;
        fpsCounter = new FPSLogger();

        difficultyControl = new DifficultyControl();

        state = PlayState.PLAY;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);

        TiledMap map = new TmxMapLoader().load("maps/YorkMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.TILE_WxH);
        mapRenderer.setView(camera);

        shapeMapRenderer = new ShapeRenderer();
        shapeMapRenderer.setProjectionMatrix(camera.combined);

        gui = new GUI(game, this);

        Gdx.input.setInputProcessor(new GameInputHandler(this, gui));

        gameState = new GameState();
        camShake = new CameraShake();

        //Orders renderer to start rendering the background, then the player layer, then structures
        mapLayers = map.getLayers();
        backgroundLayerIndex = new int[]{mapLayers.getIndex("background")};

        structureLayersIndices = new int[]{mapLayers.getIndex("structures3"),
            mapLayers.getIndex("structures"),
            mapLayers.getIndex("structures2"),
            mapLayers.getIndex("transparentStructures")};

        //#Assessment 4 changed position to adjust for fullscreen map
        station = new FireStation(3, 8);

        if (loadSlot == 0) {
            spawn(FireTruckType.Emerald);
            spawn(FireTruckType.Amethyst);
            spawn(FireTruckType.Sapphire);
            spawn(FireTruckType.Ruby);

            //#Assessment 4 changed positions to adjust for fullscreen map and difficulty selection
            fortresses = new ArrayList<Fortress>();
            fortresses.add(new Fortress(12, 24.5f, fixedGamedifficulty, FortressType.Revs));
            fortresses.add(new Fortress(30.5f, 23.5f, fixedGamedifficulty, FortressType.Walmgate));
            fortresses.add(new Fortress(14.5f, 5, fixedGamedifficulty, FortressType.Railway));
            fortresses.add(new Fortress(34, 2.5f, fixedGamedifficulty, FortressType.Clifford));
            fortresses.add(new Fortress(41.95f, 25.5f, fixedGamedifficulty, FortressType.Minster));
            fortresses.add(new Fortress(44.5f, 10.5f, fixedGamedifficulty, FortressType.Shambles));

            patrols = new ArrayList<Patrol>();

            patrols.add(new Patrol(this, PatrolType.Blue));
            patrols.add(new Patrol(this, PatrolType.Green));
            patrols.add(new Patrol(this, PatrolType.Red));
            patrols.add(new Patrol(this, PatrolType.Violet));
            patrols.add(new Patrol(this, PatrolType.Yellow));
            patrols.add(new Patrol(this, PatrolType.Station));

            //Assessment 4 Added powerups
            powerUps = new ArrayList<PowerUp>();
            powerUpsToRemove = new ArrayList<PowerUp>();
            mirrorTrucks = new ArrayList<>();

            trucksToDestroy = new ArrayList<>();



            deadEntities = new ArrayList<>(7);

            //#Assessment 4 explosion animations
            explosions = new ArrayList<>();
            explosionsToRemove = new ArrayList<>();

            // sets the origin point to which all of the polygon's local vertices are relative to.
            for (FireTruck truck : station.getTrucks()) {
                truck.setOrigin(Constants.TILE_WxH / 2, Constants.TILE_WxH / 2);
            }
        } else {
            savedData = SaveManager.loadGameData(loadSlot);

            Gson gson = new Gson();

            this.fixedGameDifficulty = savedData.getInteger("gameDifficulty");

            /* Deserialise the fireTruckList from the saved data, spawning the fire trucks at their saved locations
               and health/water levels.
            */
            ArrayList<FireTruck> trucks = gson.fromJson(savedData.getString("fireTruckList"),
                new TypeToken<ArrayList<FireTruck>>() {
                }.getType());

            System.out.println(trucks);

            for (int i = 0; i < trucks.size(); i++) {
                FireTruck truck = trucks.get(i);
                Vector2 pos = truck.getPosition();
                spawn(truck.type, pos);

                station.getTruck(i).setHP((int) truck.getHP());
                station.getTruck(i).setReserve(truck.getReserve());
            }

            System.out.println(station.getTrucks());

            ArrayList<Fortress> fortressList = gson.fromJson(savedData.getString("fortressesList"),
                new TypeToken<ArrayList<Fortress>>() {
                }.getType());

            System.out.println(fortressList);

            fortresses = new ArrayList<Fortress>();

            for (int i = 0; i < fortressList.size(); i++) {
                Fortress fortress = fortressList.get(i);
                System.out.println(fortress);
                Vector2 pos = fortress.getPosition();
                System.out.println(pos);
                System.out.println(fortress.getFortressType());

                fortresses.add(new Fortress(pos.x, pos.y, fixedGameDifficulty, fortress.fortressType));
                System.out.println(fortresses);
                fortresses.get(i).setHP(fortress.getHP());
            }

            getDifficultyControl().setCurrentDifficulty(Integer.parseInt(savedData.getString("currentDifficulty")));

            if (savedData.getString("hasShownDanceTutorial").equals("true")) {
                gameState.setDanceTutorialShown();
            }

            float fireStationHP = savedData.getFloat("fireStationHP");
            station.setHP(fireStationHP);

            if (station.getHP() <= 0) {
                gameState.setStationDestroyed();
            }

            for (int i = 0; i < savedData.getInteger("fortressesDestroyed"); i++) {
                gameState.addFortress();
            }

            patrols = new ArrayList<Patrol>();
            patrols.add(new Patrol(this, PatrolType.Blue));
            patrols.add(new Patrol(this, PatrolType.Green));
            patrols.add(new Patrol(this, PatrolType.Violet));
            patrols.add(new Patrol(this, PatrolType.Yellow));
            patrols.add(new Patrol(this, PatrolType.Station));

            //Assessment 4 Added powerups
            powerUps = new ArrayList<PowerUp>();
            powerUpsToRemove = new ArrayList<PowerUp>();

            deadEntities = new ArrayList<>(7);

            // sets the origin point to which all of the polygon's local vertices are relative to.
            for (FireTruck truck : station.getTrucks()) {
                truck.setOrigin(Constants.TILE_WxH / 2, Constants.TILE_WxH / 2);
            }
        }


        mapBatch = mapRenderer.getBatch();

        if (SoundFX.music_enabled) {
            SoundFX.sfx_soundtrack.setVolume(.5f);
            SoundFX.sfx_soundtrack.play();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GameInputHandler(this, gui));
    }

    @Override
    public void render(float delta) {
        fpsCounter.log();

        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render(backgroundLayerIndex);

        mapBatch.begin();

        for (FireTruck truck : station.getTrucks()) {
            truck.drawPath(mapBatch);
            truck.drawSprite(mapBatch);
        }

        if (!gameState.isStationDestroyed()) {
            station.draw(mapBatch);
        }

        for (Fortress fortress : this.fortresses) {
            fortress.draw(mapBatch);
        }

        //#Assessment 4 draw powerup sprites on the map
        for (PowerUp powerUp : this.powerUps) {
            powerUp.drawSprite(mapBatch);
        }

        for (DestroyedEntity deadFortress : deadEntities) {

            deadFortress.draw(mapBatch);
        }

        mapBatch.end();

        mapRenderer.render(structureLayersIndices);

        mapBatch.begin();
        for (Patrol patrol : patrols) {
            if (patrol.getType().equals(PatrolType.Station)) {
                if (gameState.firstFortressDestroyed()) {
                    patrol.drawSprite(mapBatch);
                }
            } else {
                patrol.drawSprite(mapBatch);
            }
        }

        //#Assessment 4 sets explosion animations to be removed once they expire
        for (Explosion explosion : explosions) {
            if (explosion.drawExplosion(mapBatch)) {
                explosionsToRemove.add(explosion);
            }
        }
        explosions.removeAll(explosionsToRemove);

        mapBatch.end();

        shapeMapRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (FireTruck truck : station.getTrucks()) {
            truck.drawStats(shapeMapRenderer);
        }
        for (Patrol patrol : patrols) {
            if (patrol.getType().equals(PatrolType.Station)) {
                if (gameState.firstFortressDestroyed()) {
                    patrol.drawStats(shapeMapRenderer);
                }
            } else {
                patrol.drawStats(shapeMapRenderer);
            }
        }

        if (station.getHP() > 0) {
            station.drawStats(shapeMapRenderer);
        }


        for (Fortress fortress : fortresses) {
            fortress.drawStats(shapeMapRenderer);
            for (Bomb bomb : fortress.getBombs()) {
                bomb.drawBomb(shapeMapRenderer);
            }
        }

        shapeMapRenderer.end();
        gui.renderSelectedEntityRange(selectedEntity, shapeMapRenderer);

        gui.renderSelectedEntity(selectedEntity);

        switch (state) {
            case PLAY:
                this.update(delta);
                break;
            case PAUSE:

                // render dark background
                SoundFX.stopTruckAttack();

                Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);

                shapeMapRenderer.begin(ShapeRenderer.ShapeType.Filled);

                shapeMapRenderer.setColor(0, 0, 0, 0.1f);
                shapeMapRenderer.rect(0, 0, this.camera.viewportWidth, this.camera.viewportHeight);

                shapeMapRenderer.end();

                gui.renderPauseScreenText();
                wasPaused = true;

                break;

        }
        gui.renderButtons();


        gui.renderDifficultyCounter(difficultyControl);
    }

    /**
     * Manages all of the updates/checks during the game
     *
     * @param delta The time in seconds since the last render
     */
    private void update(float delta) {
        gameState.hasGameEnded(game);
        gameState.firstFortressDestroyed();
        camShake.update(delta, camera, new Vector2(camera.viewportWidth / 2f, camera.viewportHeight / 2f));

        station.restoreTrucks();
        station.checkForCollisions();

        gameState.setTrucksInAttackRange(0);

        //#Assessment 4 Code to calculate what powerup to spawn and when//where to spawn it
        int powerUpX = new Random().nextInt(50);
        int powerUpY = new Random().nextInt(30);
        if(new Random().nextInt(20) == 1 && isRoad(powerUpX, powerUpY) && !tileHasPowerup(powerUpX,powerUpY)){
            int randomPowerup = new Random().nextInt(5);
            switch (randomPowerup) {
                case 0:
                    powerUps.add(new PowerUp(PowerUpType.Mirror, powerUpX, powerUpY));
                    break;
                case 1:
                    powerUps.add(new PowerUp(PowerUpType.Immunity, powerUpX, powerUpY));
                    break;
                case 2:
                    powerUps.add(new PowerUp(PowerUpType.Repair, powerUpX, powerUpY));
                    break;
                case 3:
                    powerUps.add(new PowerUp(PowerUpType.Speed, powerUpX, powerUpY));
                    break;
                case 4:
                    powerUps.add(new PowerUp(PowerUpType.Damage, powerUpX, powerUpY));
                    break;
            }
        }

        //#Assessment 4 code for the destruction and activation of powerups
        for(PowerUp powerUp: powerUps){
            powerUp.update();
            if(powerUp.getTimeTillDeletion() <= 0){
                powerUpsToRemove.add(powerUp);
                if(powerUp.type == PowerUpType.Mirror){
                    station.destroyTruck(powerUp.getFireTruck());
                    mirrorTrucks.remove(powerUp.getFireTruck());
                }
            }
            for(FireTruck truck: station.getTrucks()){
                if(truck.getTilePosition().equals(powerUp.getPosition()) && !powerUp.isActive() && truck.type != FireTruckType.Mirror){
                    powerUp.setFireTruck(truck);
                    powerUp.setActive();
                    if(powerUp.type == PowerUpType.Mirror){
                        FireTruck mirrorTruck = new FireTruck(truck.getTilePosition(),FireTruckType.Mirror,  (TiledMapTileLayer) mapLayers.get("collisions"));
                        mirrorTruck.setAP(truck.getAP());
                        mirrorTruck.setSpeed(truck.getSpeed());
                        mirrorTruck.setHP((int) truck.getHP());
                        mirrorTruck.setInitialHP(mirrorTruck.getHP());
                        mirrorTruck.setReserve(truck.getReserve());
                        mirrorTruck.setInitialReserve(mirrorTruck.getReserve());
                        mirrorTrucks.add(mirrorTruck);
                        powerUp.setFireTruck(mirrorTruck);
                    }
                }
            }
        }
        powerUps.removeAll(powerUpsToRemove);

        //Assessment 4 code to spawn mirror trucks
        for(FireTruck mirrorTruck: mirrorTrucks){
            if(!station.getTrucks().contains(mirrorTruck) && mirrorTruck.getHP() > 0){
                station.spawn(mirrorTruck);
            }
        }



        for (int i = 0; i < station.getTrucks().size(); i++) {
            FireTruck truck = station.getTruck(i);

            if (!truck.path.isEmpty() && wasPaused) {
                truck.setMoving(true);
            }

            if (i == station.getTrucks().size() - 1) {
                wasPaused = false;
            }


            truck.move();
            truck.updateSpray();

            //truck.move();

            // manages attacks between trucks and fortresses
            for (Fortress fortress : this.fortresses) {
                if (fortress.withinRange(truck.getVisualPosition())) {
                    fortress.attack(truck, true, difficultyControl.getDifficultyMultiplier());
                }
                if (truck.fortressInRange(fortress.getPosition())) {
                    gameState.incrementTrucksInAttackRange();
                    truck.attack(fortress);
                    break;
                }
            }

            for (Patrol patrol : this.patrols) {
                Vector2 patrolPos = new Vector2(Math.round(patrol.position.x), Math.round(patrol.position.y));
                if (patrolPos.equals(truck.getTilePosition())&& truck.type != FireTruckType.Mirror) {
                    doDanceOff(truck, patrol);

                }
            }

            //handles encounters between firetrucks and powerups
            //for number of powerups, check if a firetruck position matches powerup position
            //call effect in event of collision and remove powerup from list

            // check if truck is destroyed
            if (truck.getHP() <= 0) {

                //#Assessment 4 Adds explosion animation once firetruck is destroyed
                explosions.add(new Explosion(4, 4, (int) truck.getPosition().x - 1, (int) truck.getPosition().y - 1, 0.025f));

                gameState.removeFireTruck();
                station.destroyTruck(truck);
                if (truck.equals(this.selectedTruck)) {
                    this.selectedTruck = null;
                }
            }
        }

        //#Assessment4 made some changes to the destruction behaviour and added explosion animation
        if (station.getHP() <= 0) {
            if (!(gameState.isStationDestroyed())) {
                explosions.add(new Explosion(12, 10, (int) station.getPosition().x - 3, (int) station.getPosition().y - 4, 0.1f));
                for(FireTruck truck: station.getTrucks()){
                    if(truck.getTilePosition().y == station.getPosition().y && truck.getTilePosition().x > station.getPosition().x && truck.getTilePosition().x < station.getPosition().x+4){
                        trucksToDestroy.add(truck);
                    }
                }
                for(FireTruck truck: trucksToDestroy){
                    station.destroyTruck(truck);
                    gameState.removeFireTruck();
                }
                gameState.setStationDestroyed();
                deadEntities.add(station.getDestroyedStation());
                patrols.remove(PatrolType.Station);
            }
        }

        for (int i = 0; i < this.patrols.size(); i++) {
            Patrol patrol = this.patrols.get(i);
            System.out.println("Currently using patrol " + patrol);
            patrol.updateSpray();

            if (patrol.getType().equals(PatrolType.Station)) {
                if ((gameState.firstFortressDestroyed())) {
                    if ((patrol.getPosition().equals(PatrolType.Station.getPoint4()))) {
                        patrol.attack(station);
                    } else {
                        patrol.move();
                    }
                } else {
                    if (gameState.isStationDestroyed()) {
                        patrols.remove(patrol);

                        //patrol.move();
                    /*if((patrol.getPosition().equals(PatrolType.Station.getPoint1()))){
                        patrols.remove(patrol);
                    }*/
                    }
                }
            } else {
                System.out.println("Moving patrols!");
                patrol.move();
            }
            if (patrol.getHP() <= 0) {
                patrols.remove(patrol);
                if ((patrol.getType().equals(PatrolType.Station)) && (!gameState.isStationDestroyed())) {
                    patrols.add(new Patrol(this, PatrolType.Station));
                }
            }
        }

        for (int i = 0; i < this.fortresses.size(); i++) {
            Fortress fortress = this.fortresses.get(i);

            boolean hitTruck = fortress.updateBombs();
            if (hitTruck) {
                camShake.shakeIt(.2f);
            }

            // check if fortress is destroyed
            if (fortress.getHP() <= 0) {

                //#Assessment 4 added explosion animation upon fortress destruction
                explosions.add(new Explosion(12, 10, (int) fortress.getPosition().x - 5, (int) fortress.getPosition().y - 5, 0.1f));

                gameState.addFortress();
                deadEntities.add(fortress.createDestroyedFortress());
                this.fortresses.remove(fortress);
                if (SoundFX.music_enabled) {
                    SoundFX.sfx_fortress_destroyed.play();
                }
            }

        }

        if (gameState.getTrucksInAttackRange() > 0 && SoundFX.music_enabled) {
            SoundFX.playTruckAttack();
        } else {
            SoundFX.stopTruckAttack();
        }

        //System.out.println(SoundFX.isPlaying);

        shapeMapRenderer.end();
        shapeMapRenderer.setColor(Color.WHITE);

        gui.renderSelectedEntity(selectedEntity);

        difficultyControl.incrementCurrentTime(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        shapeMapRenderer.dispose();
        mapBatch.dispose();
        SoundFX.sfx_soundtrack.stop();
    }

    /**
     * Checks whether the player has clicked on a truck and sets that
     * truck to selected truck and entity
     *
     * @param position coordinates of where the user clicked
     * @return <code>true</code> if player clicks on a truck
     * <code>false</code> otherwise
     */
    public boolean checkClick(Vector2 position) {
        for (int i = this.station.getTrucks().size() - 1; i >= 0; i--) {
            FireTruck selectedTruck = this.station.getTruck(i);
            Vector2 truckTile = getTile(selectedTruck.getPosition());
            if (position.equals(truckTile) && !selectedTruck.getMoving()) {
                this.selectedTruck = this.station.getTruck(i);
                this.selectedEntity = this.station.getTruck(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the coordinates of the tile that the truck is closest to
     *
     * @param position coordinates of truck
     * @return coordinates of closest tile
     */
    private Vector2 getTile(Vector2 position) {
        return new Vector2((float) Math.round((position.x)), (float) Math.round(position.y));
    }

    /**
     * Checks whether the user has clicked on a the last tile in a
     * truck's trail path and selects the truck as active truck and
     * entity
     *
     * @param position the coordinates where the user clicked
     * @return <code>true</code> if player clicks on the
     * last tile in a truck's path
     * <code>false</code> otherwise
     */
    public boolean checkTrailClick(Vector2 position) {
        for (int i = this.station.getTrucks().size() - 1; i >= 0; i--) {
            if (!this.station.getTruck(i).path.isEmpty()) {
                if (position.equals(this.station.getTruck(i).path.last())) {
                    this.selectedTruck = this.station.getTruck(i);
                    this.selectedEntity = this.station.getTruck(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the tile that the user is trying to add to the
     * truck's path is on the road. This uses the custom "road"
     * boolean property in the collisions layer within the tiled map
     *
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @return <code>true</code> if the tile is a road
     * <code>false</code> otherwise
     */
    public boolean isRoad(int x, int y) {
        return ((TiledMapTileLayer) mapLayers.get("collisions")).getCell(x, y).getTile().getProperties().get("road").equals(true);
    }

    //#Assessment 4 powerup code
    /**
     * Determines if a tile contains a powerup
     * @param tileX X position of the tile
     * @param tileY Y position of the tile
     * @return True if the tile has a powerup
     */
    public boolean tileHasPowerup(int tileX, int tileY){
        for(PowerUp powerUp: powerUps){
            if(powerUp.getSpawnX() == tileX && powerUp.getSpawnY() == tileY){
                return true;
            }
        }
        return false;
    }

    /**
     * Changes from GameScreen to Control screen, passing "game" so that when
     * the player exits the control screen, it knows to return to the Game
     */
    public void toControlScreen() {
        game.setScreen(new ControlsScreen(game, this, "game"));
    }

    /**
     * Exits the main game screen and goes to the menu, called when the home
     * button is clicked.
     */
    public void toHomeScreen() {
        game.setScreen(new MenuScreen(game));
        SoundFX.stopTruckAttack();
        SoundFX.sfx_soundtrack.dispose();
    }

    /**
     * Switches from Game to Save screen. Passes a reference to itself so the Save screen
     * knows where to return to when its exit button is clicked.
     */
    public void toSaveScreen() {
        game.setScreen(new SaveScreen(game, this));
    }


    /**
     * Starts a dance-off between the given firetruck and the given ET
     *
     * @param firetruck
     * @param et
     */
    //#Assessment 4 changed to allow persistent sound settings
    public void doDanceOff(FireTruck firetruck, Patrol et) {
        if (SoundFX.music_enabled) {
            SoundFX.stopMusic();
            SoundFX.playDanceoffMusic();
        }
        game.setScreen(new DanceScreen(game, this, firetruck, et));
    }

    /**
     * Creates a new FireEngine, plays a sound and adds it gameState to track
     *
     * @param type Type of truck to be spawned (Ocean, Speed)
     */
    private void spawn(FireTruckType type) {
        if(SoundFX.music_enabled)
            SoundFX.sfx_truck_spawn.play();
        station.spawn(new FireTruck(new Vector2(6, 8), type, (TiledMapTileLayer) mapLayers.get("collisions")));
        gameState.addFireTruck();
    }

    /**
     * Creates a new FireEngine at the specified location, plays a sound and adds it to the gameState to track.
     *
     * @param type        Type of truck to be spawned (Ocean, Speed)
     * @param spawnCoords Co-ordinates to spawn the truck at.
     */
    private void spawn(FireTruckType type, Vector2 spawnCoords) {
        if(SoundFX.music_enabled)
            SoundFX.sfx_truck_spawn.play();
        station.spawn(new FireTruck(spawnCoords, type, (TiledMapTileLayer) mapLayers.get("collisions")));
        gameState.addFireTruck();
    }

    /**
     * Toggles between Play and Pause state when the Pause button is clicked
     */
    public void changeState() {
        if (this.state.equals(PlayState.PLAY)) {
            this.state = PlayState.PAUSE;
        } else {
            this.state = PlayState.PLAY;
        }
    }

    public FireStation getStation() {
        return this.station;
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    public ArrayList<Fortress> getFortresses() {
        return this.fortresses;
    }

    public PlayState getState() {
        return this.state;
    }

    //#Assessment 4 allows for difficulty selection
    public int getFixedGameDifficulty() {
        return this.fixedGameDifficulty;
    }


    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public ArrayList<DestroyedEntity> getDeadEntities() {
        return this.deadEntities;
    }

    public DifficultyControl getDifficultyControl() {
        return difficultyControl;
    }
}

