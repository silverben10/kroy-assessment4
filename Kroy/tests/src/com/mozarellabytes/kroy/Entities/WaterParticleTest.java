package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.GameScreen;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class WaterParticleTest {

    @Mock
    TiledMapTileLayer collisionsMock;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void isHit() {
        Fortress testFortress = new Fortress(5, 5, 0, FortressType.Minster);
        Vector2 X = new Vector2(1, 1);
        FireTruck testTruck = new FireTruck(X, FireTruckType.Ruby, collisionsMock);
        WaterParticle testParticle = new WaterParticle(testTruck, testFortress);
        testParticle.setPositionX(1);
        testParticle.setPositionY(5);
        testParticle.setTargetPositionX(1);
        testParticle.setTargetPositionY(5);
        assertTrue(testParticle.isHit());

        testParticle.setPositionX(2);
        testParticle.setPositionY(8);
        assertFalse(testParticle.isHit());

    }
}