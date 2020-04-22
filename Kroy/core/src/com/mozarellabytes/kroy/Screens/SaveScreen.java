package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Utilities.GUI;
import com.mozarellabytes.kroy.Utilities.GameInputHandler;
import com.mozarellabytes.kroy.Utilities.SaveScreenInputHandler;

public class SaveScreen implements Screen {

    private final Kroy game;

    private final Texture backgroundImage;

    private final OrthographicCamera camera;

    private final Rectangle exitButton;

    private final Screen parent;

    /** Width of the screen */
    private final float screenWidth;

    /** Height of the screen */
    private final float screenHeight;

    /** Constructor for the Save scree.
     * @param game Reference to the Kroy LibGDX game
     * @param parent the screen that called the Save screen
     */
    public SaveScreen(Kroy game, Screen parent) {
        this.game = game;
        this.parent = parent;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        screenWidth = camera.viewportWidth;
        screenHeight = camera.viewportHeight;

        Gdx.input.setInputProcessor(new SaveScreenInputHandler(this));

        backgroundImage = new Texture(Gdx.files.internal("images/YorkMapEdit.png"), true);
        backgroundImage.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        exitButton = new Rectangle();
        exitButton.x = (int)(screenWidth / 1.08f);
        exitButton.y = (int)(screenHeight / 1.126f);
        exitButton.width = 30;
        exitButton.height = 30;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        drawBackgroundImage();
        drawFilledBackgroundBox();

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
        GUI gui = new GUI(game, (GameScreen) parent);
        Gdx.input.setInputProcessor(new GameInputHandler((GameScreen) parent, gui));
        gui.idleInfoButton();
        this.game.setScreen(parent);
    }

    /** Draws the image being shown behind the controls panel */
    private void drawBackgroundImage(){
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);
        game.batch.end();
    }

    /** Draws the black rectangle over which the controls are shown */
    private void drawFilledBackgroundBox(){
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.rect(screenWidth / 25.6f,screenHeight / 16, screenWidth / 1.085f , screenHeight / 1.14f, Color.BLACK, Color.BLACK,Color.BLACK, Color.BLACK);
        game.shapeRenderer.end();
    }

    /** Renders the exit button */
    private void renderExitButton(){
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.rect(screenWidth / 1.08f,  screenHeight / 1.126f, 30, 30, Color.FIREBRICK, Color.FIREBRICK, Color.FIREBRICK, Color.FIREBRICK);
        game.shapeRenderer.rect(screenWidth / 1.078f,  screenHeight / 1.123f, 26, 26, Color.RED, Color.RED, Color.RED, Color.RED);
        game.shapeRenderer.end();

        game.batch.begin();
        game.font33Red.draw(game.batch, "X",screenWidth / 1.075f, screenHeight / 1.103f);
        game.batch.end();
    }

    public Rectangle getExitButton() {
        return this.exitButton;
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    @Override
    public void dispose() {

    }
}
