package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
        FireTruck fireTruck1 = new FireTruck(new Vector2(9,10), Ruby, collisionsMock);
        FireTruck fireTruck2 = new FireTruck(new Vector2(10,10), Sapphire, collisionsMock);
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        FireStation fireStation = new FireStation(8, 10);
        fireStation.spawn(fireTruck1);
        fireStation.spawn(fireTruck2);
        for (int i=0; i<2000; i++) {
            fireTruck1.attack(fortress);
            fireTruck1.updateSpray();
            fireTruck2.attack(fortress);
            fireTruck2.updateSpray();
        }
        float fireTruck1ReserveEmpty = fireTruck1.getReserve();
        float fireTruck2ReserveEmpty = fireTruck2.getReserve();

        for (int i=0; i<200; i++) {
            fireStation.restoreTrucks();
        }

        boolean checkEmptyReservesAreSame = fireTruck1ReserveEmpty == fireTruck2ReserveEmpty;
        System.out.println(checkEmptyReservesAreSame);
        boolean checkSpeedTruckIsFull = fireTruck1.getReserve() == Ruby.getMaxReserve();
        System.out.println(checkSpeedTruckIsFull);
        boolean checkOceanTruckIsNotFull = fireTruck2.getReserve() !=  Sapphire.getMaxReserve();
        System.out.println(checkOceanTruckIsNotFull);

        assertTrue(checkEmptyReservesAreSame && checkSpeedTruckIsFull && checkOceanTruckIsNotFull);

    }

    @Test
    public void checkTrucksRepairToDifferentLevels() {
        FireTruck fireTruck1 = new FireTruck(new Vector2(9,10), Ruby, collisionsMock);
        FireTruck fireTruck2 = new FireTruck(new Vector2(10,10), Sapphire, collisionsMock);
        FireStation fireStation = new FireStation(8, 10);
        fireStation.spawn(fireTruck1);
        fireStation.spawn(fireTruck2);
        fireTruck1.repair(Ruby.getMaxHP()*-1);
        fireTruck2.repair(Sapphire.getMaxHP()*-1);
        float fireTruck1Health0 = fireTruck1.getHP();
        float fireTruck2Health0 = fireTruck2.getHP();

        for (int i=0; i<300; i++) {
            fireStation.restoreTrucks();
        }

        boolean checkHealth0IsSame = fireTruck1Health0 == fireTruck2Health0;
        boolean checkOceanTruckIsFullyRepaired = fireTruck2.getHP() == Sapphire.getMaxHP();
        boolean checkSpeedTruckIsNotFullyRepaired = fireTruck1.getHP() !=  Ruby.getMaxHP();

        assertTrue(checkHealth0IsSame && checkOceanTruckIsFullyRepaired && checkSpeedTruckIsNotFullyRepaired);

    }

    @Test
    public void checkDifferentRangeTest() {
        FireTruck fireTruck1 = new FireTruck(new Vector2(10, 15), Ruby, collisionsMock);
        FireTruck fireTruck2 = new FireTruck(new Vector2(10, 15), Sapphire, collisionsMock);
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Clifford);
        fireTruck1.fortressInRange(fortress.getPosition());
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
    public void damageFortressWithSpeedByDamageTest() {
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
    public void damageFortressWithSpeedByReserveTest() {
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
    public void damageFortressWithOceanByDamageTest() {
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
    public void damageFortressWithOceanByReserveTest() {
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