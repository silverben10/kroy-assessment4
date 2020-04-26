
package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.GameScreen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.mozarellabytes.kroy.Entities.FortressType.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FortressTest {

    @Mock
    GameScreen gameScreenMock;

    @Mock
    TiledMapTileLayer collisionsMock;

    private boolean allDifferentValues(Object... args) {
        Set<Object> set = new HashSet<Object>(Arrays.asList(args));

        return set.size() == args.length;
    }

    @Test
    public void differentRangeTest() {

        assertTrue(allDifferentValues(
                Clifford.getRange(),
                Revs.getRange(),
                Walmgate.getRange(),
                Shambles.getRange(),
                Minster.getRange(),
                Railway.getRange()
        ));
    }

    @Test
    public void differentMaxHPTest() {
        assertTrue(allDifferentValues(
                Clifford.getMaxHP(0),
                Revs.getMaxHP(0),
                Walmgate.getMaxHP(0),
                Shambles.getMaxHP(0),
                Minster.getMaxHP(0),
                Railway.getMaxHP(0)
        ));
    }

    @Test
    public void differentFireRateTest() {
        assertTrue(allDifferentValues(
                Clifford.getDelay(),
                Revs.getDelay(),
                Walmgate.getDelay(),
                Shambles.getDelay(),
                Minster.getDelay(),
                Railway.getDelay()
        ));
    }

    @Test
    public void differentAPTest() {
        assertTrue(allDifferentValues(
                Clifford.getAP(),
                Revs.getAP(),
                Walmgate.getAP(),
                Shambles.getAP(),
                Minster.getAP(),
                Railway.getAP()
        ));
    }

    @Test
    public void attackTruckFromWalmgateFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), FireTruckType.Ruby, collisionsMock);
        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        fortress.attack(fireTruck, false, 1);
        fortress.updateBombs();
        assertEquals(150, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromCliffordFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Clifford);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), FireTruckType.Ruby, collisionsMock);
        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        fortress.attack(fireTruck, false, 1);
        fortress.updateBombs();
        assertEquals(150.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromRevolutionFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Revs);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), FireTruckType.Ruby, collisionsMock);
        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        fortress.attack(fireTruck, false, 1);
        fortress.updateBombs();
        assertEquals(150.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromWalmgateFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate); // range = 6
        FireTruck fireTruck = new FireTruck(new Vector2(15, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromWalmgateFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate); // range = 6
        FireTruck fireTruck = new FireTruck(new Vector2(16, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromWalmgateFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate); // range = 6
        FireTruck fireTruck = new FireTruck(new Vector2(17, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(new Vector2(13, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(new Vector2(14, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(new Vector2(15, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Revs); // range = 7
        FireTruck fireTruck = new FireTruck(new Vector2(16, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Revs); // range = 7
        FireTruck fireTruck = new FireTruck(new Vector2(17, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Revs); // range = 7
        FireTruck fireTruck = new FireTruck(new Vector2(18, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressBeforeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, Shambles); // range = 8
        FireTruck fireTruck = new FireTruck(new Vector2(17, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressOnBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, Shambles); // range = 8
        FireTruck fireTruck = new FireTruck(new Vector2(18, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressAfterBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, Shambles); // range = 8
        FireTruck fireTruck = new FireTruck(new Vector2(19, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressBeforeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, Minster); // range = 9
        FireTruck fireTruck = new FireTruck(new Vector2(18, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressOnBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, Minster); // range = 9
        FireTruck fireTruck = new FireTruck(new Vector2(19, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressAfterBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, Minster); // range = 9
        FireTruck fireTruck = new FireTruck(new Vector2(20, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromRailwayFortressBeforeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, Railway); // range = 5
        FireTruck fireTruck = new FireTruck(new Vector2(14, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRailwayFortressOnBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, Railway); // range = 5
        FireTruck fireTruck = new FireTruck(new Vector2(15, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRailwayFortressAfterBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 0, Railway); // range = 5
        FireTruck fireTruck = new FireTruck(new Vector2(16, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }
}
