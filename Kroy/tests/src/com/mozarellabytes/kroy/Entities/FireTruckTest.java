package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.GdxTestRunner;
import com.mozarellabytes.kroy.Screens.GameScreen;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.mozarellabytes.kroy.Entities.FireTruckType.*;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class FireTruckTest {

    @Mock
    GameScreen gameScreenMock;

    @Mock (answer = Answers.RETURNS_DEEP_STUBS)
    TiledMapTileLayer collisionsMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private boolean allDifferentValues(Object... args) {
        Set<Object> set = new HashSet<Object>(Arrays.asList(args));

        return set.size() == args.length;
    }

    @Test
    public void differentSpeedTest() {
        assertTrue(allDifferentValues(
                Ruby.getSpeed(),
                Sapphire.getSpeed(),
                Amethyst.getSpeed(),
                Emerald.getSpeed()
        ));
    }

    @Test
    public void rubyTruckShouldMove3TilesIn25FramesTest() {
        FireTruck fireTruck = new FireTruck(new Vector2(10,10), Ruby, collisionsMock);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);

        Mockito.when(collisionsMock.getCell(10, 10).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(10, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(11, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);

        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        fireTruck.addTileToPath(new Vector2(11,11));
        for (int i=0; i<25; i++) {
            fireTruck.move();
        }
        Vector2 expectedPosition = new Vector2(11, 11);
        assertEquals(expectedPosition, fireTruck.getPosition());
    }

    @Test
    public void sapphireTruckShouldNotMove3TilesIn25FramesTest() {
        FireTruck fireTruck = new FireTruck(new Vector2(10,10), Sapphire, collisionsMock);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);

        Mockito.when(collisionsMock.getCell(10, 10).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(10, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(11, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);

        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        fireTruck.addTileToPath(new Vector2(11,11));
        for (int i=0; i<25; i++) {
            fireTruck.move();
        }
        Vector2 expectedPosition = new Vector2(11, 11);
        assertNotEquals(expectedPosition, fireTruck.getPosition());
    }

    @Test
    public void sapphireTruckShouldMove3TilesIn50FramesTest() {
        FireTruck fireTruck = new FireTruck(new Vector2(10,10), Sapphire, collisionsMock);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);

        Mockito.when(collisionsMock.getCell(10, 10).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(10, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(11, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);

        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        fireTruck.addTileToPath(new Vector2(11,11));
        for (int i=0; i<50; i++) {
            fireTruck.move();
        }
        Vector2 expectedPosition = new Vector2(11, 11);
        assertEquals(expectedPosition, fireTruck.getPosition());
    }

    @Test
    public void amethystTruckShouldMove3TilesIn60FramesTest() {
        FireTruck fireTruck = new FireTruck(new Vector2(10,10), Amethyst, collisionsMock);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);

        Mockito.when(collisionsMock.getCell(10, 10).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(10, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(11, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);

        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        fireTruck.addTileToPath(new Vector2(11,11));

        for (int i=0; i<60; i++) {
            fireTruck.move();
        }

        Vector2 expectedPosition = new Vector2(11, 11);
        assertEquals(expectedPosition, fireTruck.getPosition());
    }

    @Test
    public void emeraldTruckShouldMove3TilesIn25FramesTest() {
        FireTruck fireTruck = new FireTruck(new Vector2(10,10), Emerald, collisionsMock);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);

        Mockito.when(collisionsMock.getCell(10, 10).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(10, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(11, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);

        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        fireTruck.addTileToPath(new Vector2(11,11));

        for (int i=0; i<25; i++) {
            fireTruck.move();
        }

        Vector2 expectedPosition = new Vector2(11, 11);
        assertEquals(expectedPosition, fireTruck.getPosition());
    }

    @Test
    public void differentMaxVolumeTest() {
        assertTrue(allDifferentValues(
                Ruby.getMaxReserve(),
                Sapphire.getMaxReserve(),
                Amethyst.getMaxReserve(),
                Emerald.getMaxReserve()
        ));
    }

    @Test
    public void differentAPTest() {
        assertTrue(allDifferentValues(
                Ruby.getAP(),
                Sapphire.getAP(),
                Amethyst.getAP(),
                Emerald.getAP()
        ));
    }

    public void differentRangeTest() {
        assertTrue(allDifferentValues(
                Ruby.getRange(),
                Sapphire.getRange(),
                Amethyst.getRange(),
                Emerald.getRange()
        ));
    }

    @Test
    public void differentMaxHPTest() {
        assertTrue(allDifferentValues(
                Ruby.getMaxHP(),
                Sapphire.getMaxHP(),
                Amethyst.getMaxHP(),
                Emerald.getMaxHP()
        ));
    }

    @Test
    public void checkTrucksFillToDifferentLevels() {
        FireTruck rubyTruck = new FireTruck(new Vector2(9,10), Ruby, collisionsMock);
        FireTruck sapphireTruck = new FireTruck(new Vector2(9,10), Sapphire, collisionsMock);
        FireTruck amethystTruck = new FireTruck(new Vector2(9,10), Amethyst, collisionsMock);
        FireTruck emeraldTruck = new FireTruck(new Vector2(9,10), Emerald, collisionsMock);
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        FireStation fireStation = new FireStation(8, 10);
        fireStation.spawn(rubyTruck);
        fireStation.spawn(sapphireTruck);
        fireStation.spawn(amethystTruck);
        fireStation.spawn(emeraldTruck);
        for (int i=0; i<2000; i++) {
            rubyTruck.attack(fortress);
            rubyTruck.updateSpray();
            sapphireTruck.attack(fortress);
            sapphireTruck.updateSpray();
            amethystTruck.attack(fortress);
            amethystTruck.updateSpray();
            emeraldTruck.attack(fortress);
            emeraldTruck.updateSpray();
        }

        for (int i=0; i<450; i++) {
            fireStation.restoreTrucks();
        }

        assertTrue(
                rubyTruck.getReserve() < emeraldTruck.getReserve() &&
                        emeraldTruck.getReserve() < sapphireTruck.getReserve() &&
                        sapphireTruck.getReserve() < amethystTruck.getReserve()
        );

    }

    @Test
    public void checkTrucksRepairToDifferentLevels() {
        FireTruck rubyTruck = new FireTruck(new Vector2(9,10), Ruby, collisionsMock);
        FireTruck sapphireTruck = new FireTruck(new Vector2(10,10), Sapphire, collisionsMock);
        FireTruck amethystTruck = new FireTruck(new Vector2(9,10), Amethyst, collisionsMock);
        FireTruck emeraldTruck = new FireTruck(new Vector2(10,10), Emerald, collisionsMock);
        FireStation fireStation = new FireStation(8, 10);
        fireStation.spawn(rubyTruck);
        fireStation.spawn(sapphireTruck);
        fireStation.spawn(amethystTruck);
        fireStation.spawn(emeraldTruck);
        rubyTruck.repair(Ruby.getMaxHP()*-1);
        sapphireTruck.repair(Sapphire.getMaxHP()*-1);
        amethystTruck.repair(Amethyst.getMaxHP()*-1);
        emeraldTruck.repair(Sapphire.getMaxHP()*-1);

        for (int i=0; i<500; i++) {
            fireStation.restoreTrucks();
        }

        assertTrue(
                emeraldTruck.getHP() < sapphireTruck.getHP() &&
                        sapphireTruck.getHP() < rubyTruck.getHP() &&
                        rubyTruck.getHP() < amethystTruck.getHP()
        );

    }

    @Test
    public void checkDifferentRangeTest() {
        FireTruck fireTruck1 = new FireTruck(new Vector2(10, 15), Ruby, collisionsMock);
        FireTruck fireTruck2 = new FireTruck(new Vector2(10, 15), Sapphire, collisionsMock);
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Clifford);

        assertNotEquals(fireTruck1.fortressInRange(fortress.getPosition()), fireTruck2.fortressInRange(fortress.getPosition()));
    }

    @Test
    public void truckShouldDecreaseHealthOfFortress() {
        FireTruck fireTruck = new FireTruck( new Vector2(10, 10), Ruby, collisionsMock);
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        float healthBefore = fortress.getHP();
        fireTruck.attack(fortress);
        for (int i=0; i<200; i++) {
            fireTruck.updateSpray();
        }
        float healthAfter = fortress.getHP();
        assertTrue(healthBefore > healthAfter);
    }

    @Test
    public void truckShouldDecreaseReserveWhenAttackingFortress() {
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), Ruby, collisionsMock);
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        float reserveBefore = fireTruck.getReserve();
        fireTruck.attack(fortress);
        for (int i=0; i<100; i++) {
            fireTruck.updateSpray();
        }
        float reserveAfter = fireTruck.getReserve();
        assertTrue(reserveBefore > reserveAfter);
    }

    @Test
    public void damageFortressWithRubyByDamageTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), Ruby, collisionsMock);
        fireTruck.attack(fortress);
        for (int i=0; i<200; i++) {
            fireTruck.updateSpray();
        }
        float fortressHealthAfter = fortress.getHP();
        assertEquals(FortressType.Walmgate.getMaxHP(0) - Ruby.getAP(), fortressHealthAfter, 0.0);
    }

    @Test
    public void damageFortressWithRubyByReserveTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), Ruby, collisionsMock);
        fireTruck.attack(fortress);
        for (int i=0; i<100; i++) {
            fireTruck.updateSpray();
        }
        float fireTruckReserveAfter = fireTruck.getReserve();
        assertEquals(Ruby.getMaxReserve() - Ruby.getAP(), fireTruckReserveAfter, 0.0);
    }

    @Test
    public void damageFortressWithSapphireByDamageTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), Sapphire, collisionsMock);
        fireTruck.attack(fortress);
        for (int i=0; i<200; i++) {
            fireTruck.updateSpray();
        }
        float fortressHealthAfter = fortress.getHP();
        assertEquals(FortressType.Walmgate.getMaxHP(0) - Sapphire.getAP(), fortressHealthAfter, 0.0);
    }

    @Test
    public void damageFortressWithSapphireByReserveTest() {
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        FireTruck fireTruck = new FireTruck(new Vector2(10, 10), Sapphire, collisionsMock);
        fireTruck.attack(fortress);
        for (int i=0; i<100; i++) {
            fireTruck.updateSpray();
        }
        float fireTruckReserveAfter = fireTruck.getReserve();
        assertEquals(Sapphire.getMaxReserve() - Sapphire.getAP(), fireTruckReserveAfter, 0.0);
    }

    @Test
    public void moveTest() {
        FireTruck fireTruck = new FireTruck(new Vector2(10,10), Ruby, collisionsMock);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,10);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(10,11);

        Mockito.when(collisionsMock.getCell(10, 10).getTile().getProperties().get("road").equals("true")).thenReturn(true);
        Mockito.when(collisionsMock.getCell(10, 11).getTile().getProperties().get("road").equals("true")).thenReturn(true);

        fireTruck.setMoving(true);
        fireTruck.addTileToPath(new Vector2(10,10));
        fireTruck.addTileToPath(new Vector2(10,11));
        for (int i=0; i<50; i++) {
            fireTruck.move();
        }
        Vector2 expectedPosition = new Vector2(10, 11);
        assertEquals(expectedPosition, fireTruck.getPosition());
    }

}