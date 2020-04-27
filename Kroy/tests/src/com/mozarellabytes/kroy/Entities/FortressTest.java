
package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.mozarellabytes.kroy.Entities.FortressType.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FortressTest {

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
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), FireTruckType.Ruby, collisionsMock);
        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        fortress.attack(fireTruck, false, 1);
        fortress.updateBombs();
        assertEquals(142.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromCliffordFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Clifford);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), FireTruckType.Ruby, collisionsMock);
        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        fortress.attack(fireTruck, false, 1);
        fortress.updateBombs();
        assertEquals(146.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromRevolutionFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Revs);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), FireTruckType.Ruby, collisionsMock);
        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        fortress.attack(fireTruck, false, 1);
        fortress.updateBombs();
        assertEquals(145.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromShamblesFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Shambles);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), FireTruckType.Ruby, collisionsMock);
        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        fortress.attack(fireTruck, false, 1);
        fortress.updateBombs();
        assertEquals(144.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromMinsterFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Minster);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), FireTruckType.Ruby, collisionsMock);
        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        fortress.attack(fireTruck, false, 1);
        fortress.updateBombs();
        assertEquals(140.0, fireTruck.getHP(), 0.0);
    }

    @Test
    public void attackTruckFromRailwayFortressDamageTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Railway);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), FireTruckType.Ruby, collisionsMock);
        fireTruck.setTimeOfLastAttack(System.currentTimeMillis() - 5000);
        fortress.attack(fireTruck, false, 1);
        fortress.updateBombs();
        assertEquals(143.0, fireTruck.getHP(), 0.0);
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
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Walmgate); // range = 6
        FireTruck fireTruck = new FireTruck(new Vector2(16, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromWalmgateFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Walmgate); // range = 6
        FireTruck fireTruck = new FireTruck(new Vector2(17, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(new Vector2(13, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(new Vector2(14, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromCliffordFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Clifford); // range = 4
        FireTruck fireTruck = new FireTruck(new Vector2(15, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressBeforeRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Revs); // range = 7
        FireTruck fireTruck = new FireTruck(new Vector2(16, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressOnRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Revs); // range = 7
        FireTruck fireTruck = new FireTruck(new Vector2(17, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRevolutionFortressAfterRangeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, FortressType.Revs); // range = 7
        FireTruck fireTruck = new FireTruck(new Vector2(18, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressBeforeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, Shambles); // range = 8
        FireTruck fireTruck = new FireTruck(new Vector2(17, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressOnBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, Shambles); // range = 8
        FireTruck fireTruck = new FireTruck(new Vector2(18, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromShamblesFortressAfterBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, Shambles); // range = 8
        FireTruck fireTruck = new FireTruck(new Vector2(19, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressBeforeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, Minster); // range = 9
        FireTruck fireTruck = new FireTruck(new Vector2(18, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressOnBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, Minster); // range = 9
        FireTruck fireTruck = new FireTruck(new Vector2(19, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromMinsterFortressAfterBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, Minster); // range = 9
        FireTruck fireTruck = new FireTruck(new Vector2(20, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void attackTruckFromRailwayFortressBeforeBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, Railway); // range = 5
        FireTruck fireTruck = new FireTruck(new Vector2(14, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRailwayFortressOnBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, Railway); // range = 5
        FireTruck fireTruck = new FireTruck(new Vector2(15, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertTrue(withinRange);
    }

    @Test
    public void attackTruckFromRailwayFortressAfterBoundaryTest() {
        Fortress fortress = new Fortress(10, 10, 1, Railway); // range = 5
        FireTruck fireTruck = new FireTruck(new Vector2(16, 10), FireTruckType.Ruby, collisionsMock);
        boolean withinRange = fortress.withinRange(fireTruck.getPosition());
        assertFalse(withinRange);
    }

    @Test
    public void differentDamageFromRevsFortressForEachDifficulty() {
        Fortress fortress1 = new Fortress(0,0, 1, Revs);
        Fortress fortress2 = new Fortress(0,0, 2, Revs);
        Fortress fortress3 = new Fortress(0,0, 4, Revs);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 1), FireTruckType.Ruby, collisionsMock);

        fortress1.getBombs().add(new Bomb(fortress1, fireTruck, false, 1));
        fortress1.getBombs().get(0).damageTruck();

        float truckHP1 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP1); // Restore truck to original health.

        fortress2.getBombs().add(new Bomb(fortress1, fireTruck, false, 2));
        fortress2.getBombs().get(0).damageTruck();

        float truckHP2 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP2); // Restore truck to original health.

        fortress3.getBombs().add(new Bomb(fortress1, fireTruck, false, 4));
        fortress3.getBombs().get(0).damageTruck();

        float truckHP3 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP3); // Restore truck to original health.

        assertTrue(allDifferentValues(truckHP1, truckHP2, truckHP3));
    }

    @Test
    public void differentDamageFromWalmgateFortressForEachDifficulty() {
        Fortress fortress1 = new Fortress(0,0, 1, Walmgate);
        Fortress fortress2 = new Fortress(0,0, 2, Walmgate);
        Fortress fortress3 = new Fortress(0,0, 4, Walmgate);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 1), FireTruckType.Ruby, collisionsMock);

        fortress1.getBombs().add(new Bomb(fortress1, fireTruck, false, 1));
        fortress1.getBombs().get(0).damageTruck();

        float truckHP1 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP1); // Restore truck to original health.

        fortress2.getBombs().add(new Bomb(fortress1, fireTruck, false, 2));
        fortress2.getBombs().get(0).damageTruck();

        float truckHP2 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP2); // Restore truck to original health.

        fortress3.getBombs().add(new Bomb(fortress1, fireTruck, false, 4));
        fortress3.getBombs().get(0).damageTruck();

        float truckHP3 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP3); // Restore truck to original health.

        assertTrue(allDifferentValues(truckHP1, truckHP2, truckHP3));
    }

    @Test
    public void differentDamageFromCliffordFortressForEachDifficulty() {
        Fortress fortress1 = new Fortress(0,0, 1, Clifford);
        Fortress fortress2 = new Fortress(0,0, 2, Clifford);
        Fortress fortress3 = new Fortress(0,0, 4, Clifford);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 1), FireTruckType.Ruby, collisionsMock);

        fortress1.getBombs().add(new Bomb(fortress1, fireTruck, false, 1));
        fortress1.getBombs().get(0).damageTruck();

        float truckHP1 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP1); // Restore truck to original health.

        fortress2.getBombs().add(new Bomb(fortress1, fireTruck, false, 2));
        fortress2.getBombs().get(0).damageTruck();

        float truckHP2 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP2); // Restore truck to original health.

        fortress3.getBombs().add(new Bomb(fortress1, fireTruck, false, 4));
        fortress3.getBombs().get(0).damageTruck();

        float truckHP3 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP3); // Restore truck to original health.

        assertTrue(allDifferentValues(truckHP1, truckHP2, truckHP3));
    }

    @Test
    public void differentDamageFromShamblesFortressForEachDifficulty() {
        Fortress fortress1 = new Fortress(0,0, 1, Shambles);
        Fortress fortress2 = new Fortress(0,0, 2, Shambles);
        Fortress fortress3 = new Fortress(0,0, 4, Shambles);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 1), FireTruckType.Ruby, collisionsMock);

        fortress1.getBombs().add(new Bomb(fortress1, fireTruck, false, 1));
        fortress1.getBombs().get(0).damageTruck();

        float truckHP1 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP1); // Restore truck to original health.

        fortress2.getBombs().add(new Bomb(fortress1, fireTruck, false, 2));
        fortress2.getBombs().get(0).damageTruck();

        float truckHP2 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP2); // Restore truck to original health.

        fortress3.getBombs().add(new Bomb(fortress1, fireTruck, false, 4));
        fortress3.getBombs().get(0).damageTruck();

        float truckHP3 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP3); // Restore truck to original health.

        assertTrue(allDifferentValues(truckHP1, truckHP2, truckHP3));
    }

    @Test
    public void differentDamageFromMinsterFortressForEachDifficulty() {
        Fortress fortress1 = new Fortress(0,0, 1, Minster);
        Fortress fortress2 = new Fortress(0,0, 2, Minster);
        Fortress fortress3 = new Fortress(0,0, 4, Minster);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 1), FireTruckType.Ruby, collisionsMock);

        fortress1.getBombs().add(new Bomb(fortress1, fireTruck, false, 1));
        fortress1.getBombs().get(0).damageTruck();

        float truckHP1 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP1); // Restore truck to original health.

        fortress2.getBombs().add(new Bomb(fortress1, fireTruck, false, 2));
        fortress2.getBombs().get(0).damageTruck();

        float truckHP2 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP2); // Restore truck to original health.

        fortress3.getBombs().add(new Bomb(fortress1, fireTruck, false, 4));
        fortress3.getBombs().get(0).damageTruck();

        float truckHP3 = fireTruck.getHP();
        fireTruck.repair(FireTruckType.Ruby.getMaxHP() - truckHP3); // Restore truck to original health.

        assertTrue(allDifferentValues(truckHP1, truckHP2, truckHP3));
    }

    @Test
    public void differentDamageFromRailwayFortressForEachDifficulty() {
        Fortress fortress1 = new Fortress(0,0, 1, Railway);
        Fortress fortress2 = new Fortress(0,0, 2, Railway);
        Fortress fortress3 = new Fortress(0,0, 4, Railway);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 1), FireTruckType.Ruby, collisionsMock);

        fortress1.getBombs().add(new Bomb(fortress1, fireTruck, false, 1));
        fortress1.getBombs().get(0).damageTruck();

        float truckHP1 = fireTruck.getHP();
        fireTruck.setHP((int) fireTruck.type.getMaxHP());

        fortress2.getBombs().add(new Bomb(fortress1, fireTruck, false, 2));
        fortress2.getBombs().get(0).damageTruck();

        float truckHP2 = fireTruck.getHP();
        fireTruck.setHP((int) fireTruck.type.getMaxHP());

        fortress3.getBombs().add(new Bomb(fortress1, fireTruck, false, 4));
        fortress3.getBombs().get(0).damageTruck();

        float truckHP3 = fireTruck.getHP();
        fireTruck.setHP((int) fireTruck.type.getMaxHP());

        assertTrue(allDifferentValues(truckHP1, truckHP2, truckHP3));
    }
}
