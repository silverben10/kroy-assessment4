package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Entities.*;
import com.mozarellabytes.kroy.GameState;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Utilities.*;


import java.util.ArrayList;

/**
 * The Screen that our game is played in.
 * Accessed from MenuScreen when the user
 * clicks the Start button, and exits when
 * the player wins or loses the game
 */
public class GameScreen implements Screen {

    /** Instance of our game that allows us the change screens */
    private final Kroy game;

    /** Renders our tiled map */
    private final OrthogonalTiledMapRenderer mapRenderer;

    /** Camera to set the projection for the screen */
    private final OrthographicCamera camera;

    /** Renders shapes such as the health/reserve
     * stat bars above entities */
    private final ShapeRenderer shapeMapRenderer;

    /** Stores the layers of our tiled map */
    private final MapLayers mapLayers;

    /** Stores the structures layers, stores the background layer */
    private final int[] structureLayersIndices, backgroundLayerIndex;

    /** Batch that has dimensions in line with the 40x25 map */
    private final Batch mapBatch;

    /** Used for shaking the camera when a bomb hits a truck */
    private final CameraShake camShake;

    /** Stores whether the game is running or is paused */
    private PlayState state;

    /**
     * Deals with all the user interface on the screen
     * that does not want to be inline with the map
     * coordinates, e.g. big stat bars, buttons, pause
     * screen
     */
    private final GUI gui;

    /**
     * Stores the progress through the game. It keeps
     * track of trucks/fortresses and will end the game
     * once an end game condition has been met
     */
    public final GameState gameState;

    /** List of Fortresses currently active on the map */
    private final ArrayList<Fortress> fortresses;

    private final ArrayList<Patrol> patrols;

    /** Where the FireEngines' spawn, refill and repair */
    private final FireStation station;

    /** The FireTruck that the user is currently drawing a path for */
    public FireTruck selectedTruck;

    /** The entity that the user has clicked on to show
     * the large stats in the top left corner */
    public Object selectedEntity;

    /** A class keeping track of the current difficulty and the time to the next change */
    private DifficultyControl difficultyControl;

    /** An arraylist of all the entities that have been destroyed */
    private ArrayList<DestroyedEntity> deadEntities;

    public FPSLogger fpsCounter;

    public boolean wasPaused = false;
    /** Play when the game is being played
     * Pause when the pause button is clicked */
    public enum PlayState {
        PLAY, PAUSE
    }

    /**
     * Constructor which has the game passed in
     *
     * @param game LibGdx game
     */
    public GameScreen(Kroy game) {
        this.game = game;
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
        backgroundLayerIndex = new int[]{
                mapLayers.getIndex("background")
        };

        structureLayersIndices = new int[] {
                mapLayers.getIndex("structures"),
                mapLayers.getIndex("structures2"),
                mapLayers.getIndex("structures3"),
                mapLayers.getIndex("transparentStructures")};

        station = new FireStation(3, 7);

        spawn(FireTruckType.Emerald);
        spawn(FireTruckType.Amethyst);
        spawn(FireTruckType.Sapphire);
        spawn(FireTruckType.Ruby);

        fortresses = new ArrayList<Fortress>();
        fortresses.add(new Fortress(12, 23.5f, FortressType.Revs));
        fortresses.add(new Fortress(30.5f, 22.5f, FortressType.Walmgate));
        fortresses.add(new Fortress(16.5f, 3.5f, FortressType.Railway));
        fortresses.add(new Fortress(32f, 1.5f, FortressType.Clifford));
        fortresses.add(new Fortress(41.95f, 23.5f, FortressType.Museum));
        fortresses.add(new Fortress(44f, 11f, FortressType.CentralHall));

        patrols = new ArrayList<Patrol>();
        patrols.add(new Patrol(this,PatrolType.Blue));
        patrols.add(new Patrol(this,PatrolType.Green));
        patrols.add(new Patrol(this,PatrolType.Peach));
        patrols.add(new Patrol(this,PatrolType.Violet));
        patrols.add(new Patrol(this,PatrolType.Yellow));
        patrols.add(new Patrol(this,PatrolType.Station));

        deadEntities = new ArrayList<>(7                           );


        // sets the origin point to which all of the polygon's local vertices are relative to.
        for (FireTruck truck : station.getTrucks()) {
            truck.setOrigin(Constants.TILE_WxH / 2, Constants.TILE_WxH / 2);
        }

        mapBatch = mapRenderer.getBatch();

        if (SoundFX.music_enabled) {
            SoundFX.sfx_soundtrack.setVolume(.5f);
            SoundFX.sfx_soundtrack.play();
        }
    }

    @Override
    public void show() {
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

//        Draw station if it hasn't been destroyed.
       if(!gameState.hasStationDestoyed()) {
            station.draw(mapBatch);
       }

//       Draw all fortresses.
        for (Fortress fortress : this.fortresses) {
            fortress.draw(mapBatch);
        }

//        Draw all dead fortresses.
        for (DestroyedEntity deadFortress : deadEntities){
            deadFortress.draw(mapBatch);
        }

        mapBatch.end();

        mapRenderer.render(structureLayersIndices);

        mapBatch.begin();
        for (Patrol patrol : this.patrols) {
            if(patrol.getType().equals(PatrolType.Station)){
                if(gameState.firstFortressDestroyed()){
                    patrol.drawSprite(mapBatch);
                }
            }
            else{
                patrol.drawSprite(mapBatch);
            }
        }
        mapBatch.end();

        shapeMapRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (FireTruck truck : station.getTrucks()) {
            truck.drawStats(shapeMapRenderer);
        }

        for (Patrol patrol : this.patrols) {
            if(patrol.getType().equals(PatrolType.Station)){
                if(gameState.firstFortressDestroyed()){
                    patrol.drawStats(shapeMapRenderer);
                }
            }
            else{
                patrol.drawStats(shapeMapRenderer);
            }
        }

        if(station.getHP()>0){
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

        for (int i = 0; i < station.getTrucks().size(); i++) {
            FireTruck truck = station.getTruck(i);

             if(!truck.path.isEmpty() && wasPaused) {
                 truck.setMoving(true);
             }

            if(i == station.getTrucks().size()-1) {
                wasPaused = false;
            }


            truck.move();
            truck.updateSpray();

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

//            If a patrol reaches the same tile as a fireTruck, begin the minigame.
            for (Patrol patrol : this.patrols) {
                Vector2 patrolPos = new Vector2(Math.round(patrol.position.x), Math.round(patrol.position.y));
                if (patrolPos.equals(truck.getTilePosition())) {
                    doDanceOff(truck, patrol);

                }
            }

            // check if truck is destroyed
            if (truck.getHP() <= 0) {
                gameState.removeFireTruck();
                station.destroyTruck(truck);
                if (truck.equals(this.selectedTruck)) {
                    this.selectedTruck = null;
                }
            }
        }

        if (station.getHP() <= 0) {
            if(!(gameState.hasStationDestoyed())){
                gameState.setStationDestoyed();
                deadEntities.add(station.getDestroyedStation());
            }
            patrols.remove(PatrolType.Station);
        }

        for (int i=0;i<this.patrols.size();i++) {
            Patrol patrol=this.patrols.get(i);

            patrol.updateSpray();

            if(patrol.getType().equals(PatrolType.Station)) {
                if((gameState.firstFortressDestroyed())) {
                    if((patrol.getPosition().equals(PatrolType.Station.getPoint4()))) {
                        patrol.attack(station);
                    }
                    else {
                        patrol.move();
                    }
                }
                else{
                    if(gameState.hasStationDestoyed()) {
                        patrols.remove(patrol);

                        //patrol.move();
                        /*if((patrol.getPosition().equals(PatrolType.Station.getPoint1()))){
                            patrols.remove(patrol);
                        }*/
                    }
                }
            }

            else {
                patrol.move();
            }
            if (patrol.getHP() <= 0) {
                patrols.remove(patrol);
                if((patrol.getType().equals(PatrolType.Station))&&(!gameState.hasStationDestoyed())) {
                    patrols.add(new Patrol(this,PatrolType.Station));
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
                gameState.addFortress();
                deadEntities.add(fortress.createDestroyedFortress());
                this.fortresses.remove(fortress);
                if (SoundFX.music_enabled) {
                    SoundFX.sfx_fortress_destroyed.play();
                }
            }
        }

        if (gameState.getTrucksInAttackRange() > 0 && SoundFX.music_enabled){
            SoundFX.playTruckAttack();
        } else {
            SoundFX.stopTruckAttack();
        }

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
     * @param position  coordinates of where the user clicked
     * @return          <code>true</code> if player clicks on a truck
     *                  <code>false</code> otherwise
     */
    public boolean checkClick(Vector2 position) {
        for (int i = this.station.getTrucks().size() - 1; i >= 0; i--) {
            FireTruck selectedTruck = this.station.getTruck(i);
            Vector2 truckTile = getTile(selectedTruck.getPosition());
            if (position.equals(truckTile) &&!selectedTruck.getMoving()) {
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
     * @param position  coordinates of truck
     * @return          coordinates of closest tile
     */
    private Vector2 getTile(Vector2 position) {
        return new Vector2((float) Math.round((position.x)), (float) Math.round(position.y));
    }

    /**
     * Checks whether the user has clicked on a the last tile in a
     * truck's trail path and selects the truck as active truck and
     * entity
     *
     * @param position  the coordinates where the user clicked
     * @return          <code>true</code> if player clicks on the
     *                  last tile in a truck's path
     *                  <code>false</code> otherwise
     */
    public boolean checkTrailClick(Vector2 position) {
        for (int i=this.station.getTrucks().size()-1; i>=0; i--) {
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
     *  truck's path is on the road. This uses the custom "road"
     * boolean property in the collisions layer within the tiled map
     *
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @return  <code>true</code> if the tile is a road
     *          <code>false</code> otherwise
     */
    public boolean isRoad(int x, int y) {
        return ((TiledMapTileLayer) mapLayers.get("collisions")).getCell(x, y).getTile().getProperties().get("road").equals(true);
    }

    /**
     * Changes from GameScreen to Control screen, passing "game" so that when
     * the player exits the control screen, it knows to return to the Game
     */
    public void toControlScreen() {
        game.setScreen(new ControlsScreen(game, this, "game"));
    }

    /** Exits the main game screen and goes to the menu, called when the home
     * button is clicked */
    public void toHomeScreen() {
        game.setScreen(new MenuScreen(game));
        SoundFX.sfx_soundtrack.dispose();
    }

    /**
     * Starts a dance-off between the given firetruck and the given ET
     * @param firetruck
     * @param et
     */
    public void doDanceOff(FireTruck firetruck, Patrol et) {
        SoundFX.stopMusic();
        game.setScreen(new DanceScreen(game, this, firetruck, et));
    }

    /**
     * Creates a new FireEngine, plays a sound and adds it gameState to track
     * @param type Type of truck to be spawned (Ocean, Speed)
     */
    private void spawn(FireTruckType type) {
        SoundFX.sfx_truck_spawn.play();
        station.spawn(new FireTruck(this, new Vector2(6,7), type));
        gameState.addFireTruck();
    }

    /** Toggles between Play and Pause state when the Pause button is clicked */
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

}

