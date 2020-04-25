package com.mozarellabytes.kroy.Entities;

import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.GameScreen;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import static org.junit.Assert.*;
@RunWith(GdxTestRunner.class)
public class BlasterParticleTest {

    @Mock
    GameScreen gameScreenMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void isHit() {
        Patrol aPatrol = new Patrol(gameScreenMock, PatrolType.Blue);
        FireStation aTarget = new FireStation(1,5);
        BlasterParticle testParticle = new BlasterParticle(aPatrol, aTarget);
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