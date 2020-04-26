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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import com.mozarellabytes.kroy.Entities.FireTruckType.*;


import com.mozarellabytes.kroy.Entities.FireTruckType.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class FireStationTest {

    @Mock
    GameScreen gameScreenMock;

    @Mock
    TiledMapTileLayer collisionsMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void repairPassTest() {
        FireStation station = new FireStation(10, 10);
        station.spawn(new FireTruck(new Vector2(11, 10), FireTruckType.Ruby, collisionsMock));
        station.getTruck(0).fortressDamage(50);
        float HPBeforeRepair = station.getTruck(0).getHP();
        station.restoreTrucks();
        float HPAfterRepair = station.getTruck(0).getHP();
        assertTrue(HPAfterRepair > HPBeforeRepair);
    }

    @Test
    public void repairIncorrectPositionTest() {
        FireStation station = new FireStation(10, 10);
        station.spawn(new FireTruck(new Vector2(20, 10), FireTruckType.Ruby, collisionsMock));
        station.getTruck(0).fortressDamage(50);
        float HPBeforeRepair = station.getTruck(0).getHP();
        station.restoreTrucks();
        float HPAfterRepair = station.getTruck(0).getHP();
        assertFalse(HPAfterRepair > HPBeforeRepair);
    }

    @Test
    public void repairAlreadyFullyRepairedTest() {
        FireStation station = new FireStation(10, 10);
        station.spawn(new FireTruck(new Vector2(11, 10), FireTruckType.Ruby, collisionsMock));
        float HPBeforeRepair = station.getTruck(0).getHP();
        station.restoreTrucks();
        float HPAfterRepair = station.getTruck(0).getHP();
        assertFalse(HPAfterRepair > HPBeforeRepair);
    }

    @Test
    public void refillPassTest() {
        FireStation station = new FireStation(10, 10);
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        station.spawn(new FireTruck(new Vector2(11, 10), FireTruckType.Ruby, collisionsMock));
        station.getTruck(0).attack(fortress);
        float HPBeforeRefill = station.getTruck(0).getReserve();
        station.restoreTrucks();
        float HPAfterRefill = station.getTruck(0).getReserve();
        assertTrue(HPAfterRefill > HPBeforeRefill);
    }

    @Test
    public void refillIncorrectPositionTest() {
        FireStation station = new FireStation(10, 10);
        Fortress fortress = new Fortress(10, 10, 0, FortressType.Walmgate);
        station.spawn(new FireTruck(new Vector2(20, 10), FireTruckType.Ruby, collisionsMock));
        station.getTruck(0).attack(fortress);
        float HPBeforeRefill = station.getTruck(0).getReserve();
        station.restoreTrucks();
        float HPAfterRefill = station.getTruck(0).getReserve();
        assertFalse(HPAfterRefill > HPBeforeRefill);
    }

    @Test
    public void refillAlreadyFullTest() {
        FireStation station = new FireStation(10, 10);
        station.spawn(new FireTruck(new Vector2(11, 10), FireTruckType.Ruby, collisionsMock));
        float HPBeforeRefill = station.getTruck(0).getReserve();
        station.restoreTrucks();
        float HPAfterRefill = station.getTruck(0).getReserve();
        assertFalse(HPAfterRefill > HPBeforeRefill);
    }

    @Test
    public void trucksCannotOccupySameTileTest() {
        FireTruck fireTruck1 = new FireTruck(new Vector2(11, 13), FireTruckType.Ruby, collisionsMock);
        FireTruck fireTruck2 = new FireTruck(new Vector2(13, 13), FireTruckType.Sapphire, collisionsMock);

        FireStation station = new FireStation(0,0);
        station.spawn(fireTruck1);
        station.spawn(fireTruck2);

        fireTruck1.setMoving(true);
        fireTruck1.addTileToPath(new Vector2(11, 13));
        System.out.println(fireTruck1.getPath());
        fireTruck1.addTileToPath(new Vector2(12, 13));
        System.out.println(fireTruck1.getPath());
        fireTruck1.addTileToPath(new Vector2(13, 13));
        System.out.println(fireTruck1.getPath());
        for (int i=0; i<100; i++) {
            station.checkForCollisions();
            fireTruck1.move();
        }
        Vector2 expectedPosition = new Vector2(12, 13);
        assertEquals(expectedPosition, fireTruck1.getPosition());
    }

    @Test
    public void trucksShouldNotMovePastEachOtherTest() {
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,11);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,12);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,13);
        Mockito.doReturn(true).when(gameScreenMock).isRoad(11,14);

        FireTruck fireTruck1 = new FireTruck(new Vector2(11, 11), FireTruckType.Ruby, collisionsMock);
        FireTruck fireTruck2 = new FireTruck(new Vector2(11, 14), FireTruckType.Sapphire, collisionsMock);

        FireStation station = new FireStation(0,0);
        station.spawn(fireTruck1);
        station.spawn(fireTruck2);

        fireTruck1.setMoving(true);
        fireTruck1.addTileToPath(new Vector2(11, 11));
        fireTruck1.addTileToPath(new Vector2(11, 12));
        fireTruck1.addTileToPath(new Vector2(11, 13));
        fireTruck1.addTileToPath(new Vector2(11, 14));

        fireTruck2.setMoving(true);
        fireTruck2.addTileToPath(new Vector2(11, 14));
        fireTruck2.addTileToPath(new Vector2(11, 13));
        fireTruck2.addTileToPath(new Vector2(11, 12));
        fireTruck2.addTileToPath(new Vector2(11, 11));
        for (int i=0; i<100; i++) {
            station.checkForCollisions();
            fireTruck1.move();
            fireTruck2.move();
        }
        Vector2 fireTruck1TargetPosition = new Vector2(11, 14);
        Vector2 fireTruck2TargetPosition = new Vector2(11, 11);
        assertTrue(!fireTruck1.getPosition().equals(fireTruck1TargetPosition) && !fireTruck2.getPosition().equals(fireTruck2TargetPosition));
    }

}