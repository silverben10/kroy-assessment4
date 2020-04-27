package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Utilities.MenuInputHandler;
import com.mozarellabytes.kroy.Utilities.SoundFX;

/** This screen is shown after the splash screen and is
 * where the player can choose to start the game or view
 * the control screen */
public class MenuScreen implements Screen {

    /**
     * The game
     */
    private final Kroy game;
    public final OrthographicCamera camera;

    /**
     * The menu screen image - see ui/menuscreen_blank_2
     */
    private final Texture backgroundImage;

    /**
     * Rectangle containing the position of the play button
     */
    private final Rectangle startButton;

    /**
     * Texture of the start button when it has not been clicked
     */
    private final Texture startIdleTexture;

    /**
     * Texture of the start button when has been clicked
     */
    private final Texture startClickedTexture;

    /**
     * Contains the current state of the start button:
     * startIdleTexture if the start button is not being pressed,
     * startClickedTexture if the start button has been pressed
     */
    private Texture currentStartTexture;

    /**
     * Rectangle containing the position of the easy difficulty button
     */
    private final Rectangle easyButton;

    /**
     * Texture of the easy difficulty button when it has not been clicked
     */
    private final Texture easyIdleTexture;

    /**
     * Texture of the easy difficulty button when has been clicked
     */
    private final Texture easyClickedTexture;

    /**
     * Contains the current state of the easy difficulty button:
     * easyIdleTexture if the easy difficulty button is not being pressed,
     * easyClickedTexture if the easy difficulty button has been pressed
     */
    private Texture currentEasyTexture;

    /**
     * Rectangle containing the position of the medium difficulty button
     */
    private final Rectangle mediumButton;

    /**
     * Texture of the medium difficulty button when it has not been clicked
     */
    private final Texture mediumIdleTexture;

    /**
     * Texture of the medium difficulty button when has been clicked
     */
    private final Texture mediumClickedTexture;

    /**
     * Contains the current state of the medium difficulty button:
     * easyIdleTexture if the medium difficulty button is not being pressed,
     * easyClickedTexture if the medium difficulty button has been pressed
     */
    private Texture currentMediumTexture;

    /**
     * Rectangle containing the position of the hard difficulty button
     */
    private final Rectangle hardButton;

    /**
     * Texture of the hard difficulty button when it has not been clicked
     */
    private final Texture hardIdleTexture;

    /**
     * Texture of the hard difficulty button when has been clicked
     */
    private final Texture hardClickedTexture;

    /**
     * Contains the current state of the hard difficulty button:
     * easyIdleTexture if the hard difficulty button is not being pressed,
     * easyClickedTexture if the hard difficulty button has been pressed
     */
    private Texture currentHardTexture;

    private final Rectangle loadButton;

    /**
     * Tetxture for the load button when it hasn't been clicked
     */
    private final Texture loadIdleTexture;

    /**
     * Texture for the load button when it has been clicked
     */
    private final Texture loadClickedTexture;

    /**
     * Contains the current state of the load button:
     * loadIdleTexture if the load button isn't being pressed,
     * loadClickedTexture if the load button is being pressed
     */
    private Texture currentLoadTexture;

    /**
     * Rectangle containing the position of the control button
     */
    private final Rectangle controlsButton;

    /**
     * Texture of the control button when it has not been clicked
     */
    private final Texture controlsIdleTexture;

    /**
     * Texture of the control button when has been clicked
     */
    private final Texture controlsClickedTexture;

    /**
     * Contains the current state of the control button:
     * controlsIdleTexture if the control button is not being pressed,
     * controlsClickedTexture if the control button has been pressed
     */
    private Texture currentControlsTexture;

    /**
     * Rectangle containing the position of the sound button
     */
    private final Rectangle soundButton;

    /**
     * Texture of the sound on button when it has not been clicked
     */
    private final Texture soundOnIdleTexture;

    /**
     * Texture of the sound off button when it has not been clicked
     */
    private final Texture soundOffIdleTexture;

    /**
     * Texture of the sound on button when it has been clicked
     */
    private final Texture soundOnClickedTexture;

    /**
     * Texture of the sound off button when it has been clicked
     */
    private final Texture soundOffClickedTexture;
    private Texture currentSoundTexture;

    /**
     * Determines if the difficulty selection should be displayed or not
     */
    private boolean difficultySelect;

    /**
     * Constructs the MenuScreen
     *
     * @param game LibGdx game
     */
    public MenuScreen(final Kroy game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);

        backgroundImage = new Texture(Gdx.files.internal("menuscreen_blank_2.png"), true);
        backgroundImage.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        startIdleTexture = new Texture(Gdx.files.internal("ui/start_idle.png"), true);
        startIdleTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        startClickedTexture = new Texture(Gdx.files.internal("ui/start_clicked.png"), true);
        startClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        easyIdleTexture = new Texture(Gdx.files.internal("ui/easy_idle.png"), true);
        easyIdleTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        easyClickedTexture = new Texture(Gdx.files.internal("ui/easy_clicked.png"), true);
        easyClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        mediumIdleTexture = new Texture(Gdx.files.internal("ui/normal_idle.png"), true);
        mediumIdleTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        mediumClickedTexture = new Texture(Gdx.files.internal("ui/normal_clicked.png"), true);
        mediumClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        hardIdleTexture = new Texture(Gdx.files.internal("ui/hard_idle.png"), true);
        hardIdleTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        hardClickedTexture = new Texture(Gdx.files.internal("ui/hard_clicked.png"), true);
        hardClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        loadIdleTexture = new Texture(Gdx.files.internal("ui/load_idle.png"), true);
        loadIdleTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        loadClickedTexture = new Texture(Gdx.files.internal("ui/load_clicked.png"), true);
        loadClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        controlsIdleTexture = new Texture(Gdx.files.internal("ui/controls_idle.png"), true);
        controlsIdleTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        controlsClickedTexture = new Texture(Gdx.files.internal("ui/controls_clicked.png"), true);
        controlsClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        soundOnIdleTexture = new Texture(Gdx.files.internal("ui/sound_on_idle.png"), true);
        soundOnIdleTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        soundOffIdleTexture = new Texture(Gdx.files.internal("ui/sound_off_idle.png"), true);
        soundOffIdleTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        soundOnClickedTexture = new Texture(Gdx.files.internal("ui/sound_on_clicked.png"), true);
        soundOnClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        soundOffClickedTexture = new Texture(Gdx.files.internal("ui/sound_off_clicked.png"), true);
        soundOffClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        MenuInputHandler ih = new MenuInputHandler(this);

        difficultySelect = false;

        if (SoundFX.music_enabled) {
            SoundFX.sfx_menu.setLooping(true);
            SoundFX.sfx_menu.play();
            currentSoundTexture = soundOnIdleTexture;
        } else {
            currentSoundTexture = soundOffIdleTexture;
        }

        currentStartTexture = startIdleTexture;
        currentLoadTexture = loadIdleTexture;
        currentControlsTexture = controlsIdleTexture;
        currentEasyTexture = easyIdleTexture;
        currentMediumTexture = mediumIdleTexture;
        currentHardTexture = hardIdleTexture;

        startButton = new Rectangle();
        startButton.width = (float) (currentStartTexture.getWidth() * 0.75);
        startButton.height = (float) (currentStartTexture.getHeight() * 0.75);
        startButton.x = (int) (camera.viewportWidth / 2 - startButton.width / 2);
        startButton.y = (int) ((camera.viewportHeight / 2 - startButton.height / 2) * 0.8);

        loadButton = new Rectangle();
        loadButton.width = (float) (currentLoadTexture.getWidth() * 0.75);
        loadButton.height = (float) (currentLoadTexture.getHeight() * 0.75);
        loadButton.x = (int) (camera.viewportWidth / 2 - loadButton.width / 2);
        loadButton.y = (int) ((camera.viewportHeight / 2 - loadButton.height / 2) * 0.55);

        controlsButton = new Rectangle();
        controlsButton.width = (float) (currentControlsTexture.getWidth() * 0.75);
        controlsButton.height = (float) (currentControlsTexture.getHeight() * 0.75);
        controlsButton.x = (int) (camera.viewportWidth / 2 - controlsButton.width / 2);
        controlsButton.y = (int) ((camera.viewportHeight / 2 - controlsButton.height / 2) * 0.35);

        easyButton = new Rectangle();
        easyButton.width = (float) (currentEasyTexture.getWidth());
        easyButton.height = (float) (currentEasyTexture.getHeight());
        easyButton.x = (int) (camera.viewportWidth / 2 - easyButton.width / 2);
        easyButton.y = (int) ((camera.viewportHeight / 2 - easyButton.height / 2) * 0.8);

        mediumButton = new Rectangle();
        mediumButton.width = (float) (currentMediumTexture.getWidth());
        mediumButton.height = (float) (currentMediumTexture.getHeight());
        mediumButton.x = (int) (camera.viewportWidth / 2 - mediumButton.width / 2);
        mediumButton.y = (int) ((camera.viewportHeight / 2 - mediumButton.height / 2) * 0.5);

        hardButton = new Rectangle();
        hardButton.width = (float) (currentHardTexture.getWidth());
        hardButton.height = (float) (currentHardTexture.getHeight());
        hardButton.x = (int) (camera.viewportWidth / 2 - hardButton.width / 2);
        hardButton.y = (int) ((camera.viewportHeight / 2 - hardButton.height / 2) * 0.2);

        soundButton = new Rectangle();
        soundButton.width = 50;
        soundButton.height = 50;
        soundButton.x = camera.viewportWidth - soundButton.getWidth() - 5;
        soundButton.y = camera.viewportHeight - soundButton.getHeight() - 5;

        Gdx.input.setInputProcessor(ih);

    }

    @Override
    public void show() {

    }

    /**
     * Renders the menu screen consisting of the background and the start, controls and sound buttons.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(51 / 255f, 34 / 255f, 99 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);

        if (!difficultySelect) {
            game.batch.draw(currentStartTexture, startButton.x, startButton.y, startButton.width, startButton.height);
            game.batch.draw(currentControlsTexture, controlsButton.x, controlsButton.y, controlsButton.width, controlsButton.height);
            game.batch.draw(currentLoadTexture, loadButton.x, loadButton.y, loadButton.width, loadButton.height);
            game.batch.draw(currentSoundTexture, soundButton.x, soundButton.y, soundButton.width, soundButton.height);
        } else {
            game.batch.draw(currentEasyTexture, easyButton.x, easyButton.y, easyButton.width, easyButton.height);
            game.batch.draw(currentMediumTexture, mediumButton.x, mediumButton.y, mediumButton.width, mediumButton.height);
            game.batch.draw(currentHardTexture, hardButton.x, hardButton.y, hardButton.width, hardButton.height);
        }

        game.batch.end();
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

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        backgroundImage.dispose();
        currentStartTexture.dispose();
        startClickedTexture.dispose();
        startIdleTexture.dispose();
        currentControlsTexture.dispose();
        controlsClickedTexture.dispose();
        controlsIdleTexture.dispose();
        currentSoundTexture.dispose();
        soundOnIdleTexture.dispose();
        soundOnClickedTexture.dispose();
        soundOffIdleTexture.dispose();
        soundOffClickedTexture.dispose();
        SoundFX.sfx_menu.stop();
    }

    /**
     * Changes the screen from normal menu screen to the difficulty select screen
     */
    public void toDifficultySelect() {
        difficultySelect = true;
    }

    /**
     * Changes the screen from menu screen to game screen
     *
     * @param difficulty An integer representing the games difficulty setting
     */
    public void toGame(int difficulty) {
        SoundFX.sfx_menu.stop();
        game.setScreen(new GameScreen(game, 0, difficulty));
    }

    /**
     * Changes the screen from the menu screen to the control screen
     */
    public void toControlScreen() {
        game.setScreen(new ControlsScreen(game, this, "menu"));
    }

    public void toLoadScreen() {
        game.setScreen(new LoadScreen(game, this));
    }

    /**
     * Changes the texture of the start button when it has been clicked on
     */
    public void clickedStartButton() {
        if (SoundFX.music_enabled) {
            SoundFX.sfx_button_clicked.play();
        }
        currentStartTexture = startClickedTexture;
    }

    /**
     * Changes the texture of the controls button when it has been clicked on
     */
    public void clickedControlsButton() {
        if (SoundFX.music_enabled) {
            SoundFX.sfx_button_clicked.play();
        }
        currentControlsTexture = controlsClickedTexture;
    }

    /**
     * Changes the texture of the easy difficulty button when it has been clicked on
     */
    public void clickedEasyButton() {
        if (SoundFX.music_enabled) {
            SoundFX.sfx_button_clicked.play();
        }
        currentEasyTexture = easyClickedTexture;
    }

    /**
     * Changes the texture of the normal difficulty button when it has been clicked on
     */
    public void clickedMediumButton() {
        if (SoundFX.music_enabled) {
            SoundFX.sfx_button_clicked.play();
        }
        currentMediumTexture = mediumClickedTexture;
    }

    /**
     * Changes the texture of the hard difficulty button when it has been clicked on
     */
    public void clickedHardButton() {
        if (SoundFX.music_enabled) {
            SoundFX.sfx_button_clicked.play();
        }
        currentHardTexture = hardClickedTexture;
    }

    /**
     * Changes the texture of the sound button when it has been clicked on
     */
    public void clickedSoundButton() {
        if (SoundFX.music_enabled) {
            currentSoundTexture = soundOnClickedTexture;
        } else {
            currentSoundTexture = soundOffClickedTexture;
        }
    }

    public void clickedLoadButton() {
        if (SoundFX.music_enabled) {
            SoundFX.sfx_button_clicked.play();
        }
        currentLoadTexture = loadClickedTexture;
    }

    /**
     * Turns the sound on and off and changes the sound icon accordingly,
     * turns the sound off in the sound was on and turns the sound on if the
     * sound was off
     */
    public void changeSound() {
        if (SoundFX.music_enabled) {
            currentSoundTexture = soundOffIdleTexture;
            SoundFX.stopMusic();
        } else {
            currentSoundTexture = soundOnIdleTexture;
            SoundFX.playMenuMusic();
        }
    }

    /**
     * The texture of the start button when it has not been clicked on
     */
    public void idleStartButton() {
        currentStartTexture = startIdleTexture;
    }

    /**
     * The texture of the control button when it has not been clicked on
     */
    public void idleControlsButton() {
        currentControlsTexture = controlsIdleTexture;
    }

    /**
     * The texture of the easy difficulty button when it has not been clicked on
     */
    public void idleEasyButton() {
        currentEasyTexture = easyIdleTexture;
    }

    /**
     * The texture of the normal difficulty button when it has not been clicked on
     */
    public void idleMediumButton() {
        currentMediumTexture = mediumIdleTexture;
    }

    /**
     * The texture of the hard difficulty button when it has not been clicked on
     */
    public void idleHardButton() {
        currentHardTexture = hardIdleTexture;
    }


    /**
     * The texture of the sound button when it has not been clicked on
     */
    public void idleSoundButton() {
        if (SoundFX.music_enabled) {
            currentSoundTexture = soundOnIdleTexture;
        } else {
            currentSoundTexture = soundOffIdleTexture;
        }
    }

    public void idleLoadButton() {
        currentLoadTexture = loadIdleTexture;
    }

    public Rectangle getStartButton() {
        return startButton;
    }

    public Rectangle getControlsButton() {
        return controlsButton;
    }

    public Rectangle getEasyButton() {
        return easyButton;
    }

    public Rectangle getMediumButton() {
        return mediumButton;
    }

    public Rectangle getHardButton() {
        return hardButton;
    }

    public Rectangle getSoundButton() {
        return soundButton;
    }

    public boolean isDifficultySelect() {
        return difficultySelect;
    }

    public Rectangle getLoadButton() {
        return loadButton;
    }
}
