package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Utilities.*;


//#Assessment 4 Allows for loading of saved games
public class LoadScreen implements Screen {

    private final Kroy game;

    private final Texture backgroundImage;

    private final OrthographicCamera camera;

    private final Rectangle exitButton;

    private final Screen parent;

    /**
     * Width of the screen
     */
    private final float screenWidth;

    /**
     * Height of the screen
     */
    private final float screenHeight;

    private GlyphLayout layout = new GlyphLayout();

    private final Rectangle loadButton1;

    private final Rectangle loadButton2;

    private final Rectangle loadButton3;

    /**
     * Constructor for the Save scree.
     *
     * @param game   Reference to the Kroy LibGDX game
     * @param parent the screen that called the Save screen
     */
    public LoadScreen(Kroy game, Screen parent) {
        this.game = game;
        this.parent = parent;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        screenWidth = camera.viewportWidth;
        screenHeight = camera.viewportHeight;

        Gdx.input.setInputProcessor(new LoadScreenInputHandler(this));

        backgroundImage = new Texture(Gdx.files.internal("images/YorkMapEdit.png"), true);
        backgroundImage.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        exitButton = new Rectangle();
        exitButton.x = (int) (screenWidth / 1.08f);
        exitButton.y = (int) (screenHeight / 1.126f);
        exitButton.width = 30;
        exitButton.height = 30;

        loadButton1 = new Rectangle();
        loadButton1.width = 500;
        loadButton1.height = 50;
        loadButton1.x = screenWidth/2 - loadButton1.width/2;
        loadButton1.y = screenHeight - 400;

        loadButton2 = new Rectangle();
        loadButton2.width = 500;
        loadButton2.height = 50;
        loadButton2.x = screenWidth/2 - loadButton2.width/2;
        loadButton2.y = screenHeight - 500;

        loadButton3 = new Rectangle();
        loadButton3.width = 500;
        loadButton3.height = 50;
        loadButton3.x = screenWidth/2 - loadButton3.width/2;
        loadButton3.y = screenHeight - 600;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        drawBackgroundImage();
        drawFilledBackgroundBox();

        game.batch.begin();

        layout.setText(game.font50, "Load Game");
        game.font50.draw(game.batch, layout, screenWidth / 2 - layout.width / 2, screenHeight - 100);

        layout.setText(game.font25, "Select one of the slots below to load a previous game.");
        game.font25.draw(game.batch, layout, screenWidth / 2 - layout.width / 2, screenHeight - 175);
        game.batch.end();

        renderLoadButton(1, loadButton1.x, loadButton1.y, (int) loadButton1.width, (int) loadButton1.height);
        renderLoadButton(2, loadButton2.x, loadButton2.y, (int) loadButton2.width, (int) loadButton2.height);
        renderLoadButton(3, loadButton3.x,loadButton3.y, (int) loadButton3.width, (int) loadButton3.height);

        renderExitButton();
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

    public void closeScreen() {
        Gdx.input.setInputProcessor(new MenuInputHandler((MenuScreen) parent));
        this.game.setScreen(parent);
    }

    public void toGameScreen(int gameDifficulty, int loadSlot) {
        SoundFX.stopMusic();
        SoundFX.playGameMusic();
        this.game.setScreen(new GameScreen(game, gameDifficulty, loadSlot));
    }

    /**
     * Draws the image being shown behind the controls panel
     */
    private void drawBackgroundImage() {
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);
        game.batch.end();
    }

    /**
     * Draws the black rectangle over which the controls are shown
     */
    private void drawFilledBackgroundBox() {
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.rect(screenWidth / 25.6f, screenHeight / 16, screenWidth / 1.085f, screenHeight / 1.14f, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
        game.shapeRenderer.end();
    }

    /**
     * Renders the exit button
     */
    private void renderExitButton() {
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.rect(screenWidth / 1.08f, screenHeight / 1.126f, 30, 30, Color.FIREBRICK, Color.FIREBRICK,
            Color.FIREBRICK, Color.FIREBRICK);
        game.shapeRenderer.rect(screenWidth / 1.078f, screenHeight / 1.123f, 26, 26, Color.RED, Color.RED, Color.RED,
            Color.RED);
        game.shapeRenderer.end();

        game.batch.begin();
        game.font33Red.draw(game.batch, "X", screenWidth / 1.075f, screenHeight / 1.103f);
        game.batch.end();
    }

    private void renderLoadButton(int loadSlot, float x, float y, int buttonWidth, int buttonHeight) {
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.rect(x, y, buttonWidth, buttonHeight, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
            Color.LIGHT_GRAY, Color.LIGHT_GRAY);
        game.shapeRenderer.end();

        game.batch.begin();
        layout.setText(game.font33Red, "Slot "+ loadSlot);
        game.font33Red.draw(game.batch, layout, x+(buttonWidth/2)-(layout.width/2), y+buttonHeight-(layout.height/2));
        game.batch.end();
    }

    public Rectangle getExitButton() {
        return this.exitButton;
    }

    public Rectangle getLoadButton1() {
        return loadButton1;
    }

    public Rectangle getLoadButton2() {
        return loadButton2;
    }

    public Rectangle getLoadButton3() {
        return loadButton3;
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    public GameScreen getParent() {
        return (GameScreen) this.parent;
    }

    @Override
    public void dispose() {

    }
}
