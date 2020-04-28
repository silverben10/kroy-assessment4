package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PowerUpTest {

    @Mock
    TiledMapTileLayer collisionsMock;

    @Test
    public void powerUpImmunityEffectTest() {
        PowerUp powerUp = new PowerUp(PowerUpType.Immunity, 0, 0);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 0), FireTruckType.Ruby, collisionsMock);

        assertFalse(fireTruck.isImmune());

        powerUp.setFireTruck(fireTruck);
        powerUp.setActive();

        assertTrue(fireTruck.isImmune());
    }

    @Test
    public void powerUpRepairEffectTest() {
        PowerUp powerUp = new PowerUp(PowerUpType.Repair, 0, 0);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 0), FireTruckType.Ruby, collisionsMock);

        fireTruck.setHP(1);
        fireTruck.setReserve(1);

        powerUp.setFireTruck(fireTruck);
        powerUp.setActive();

        assertEquals(fireTruck.getHP(), fireTruck.getType().getMaxHP(), 0.0);
        assertEquals(fireTruck.getReserve(), fireTruck.getType().getMaxReserve(), 0.0);
    }

    @Test
    public void powerUpSpeedEffectTest() {
        PowerUp powerUp = new PowerUp(PowerUpType.Speed, 0, 0);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 0), FireTruckType.Ruby, collisionsMock);

        float originalSpeed = fireTruck.getSpeed();

        powerUp.setFireTruck(fireTruck);
        powerUp.setActive();

        assertEquals(fireTruck.getSpeed(), originalSpeed * 2, 0.0);
    }

    @Test
    public void powerUpDamageEffectTest() {
        PowerUp powerUp = new PowerUp(PowerUpType.Damage, 0, 0);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 0), FireTruckType.Ruby, collisionsMock);

        float originalDamage = fireTruck.getAP();

        powerUp.setFireTruck(fireTruck);
        powerUp.setActive();

        assertEquals(fireTruck.getAP(), originalDamage * 2, 0.0);
    }

    @Test
    public void powerUpImmunityEndsTest() {
        PowerUp powerUp = new PowerUp(PowerUpType.Immunity, 0, 0);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 0), FireTruckType.Ruby, collisionsMock);

        powerUp.setFireTruck(fireTruck);
        powerUp.setActive();

        assertTrue(powerUp.isActive());

        powerUp.timer = 0;
        powerUp.update();

        assertFalse(fireTruck.isImmune());
    }

    @Test
    public void powerUpSpeedEndsTest() {
        PowerUp powerUp = new PowerUp(PowerUpType.Speed, 0, 0);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 0), FireTruckType.Ruby, collisionsMock);

        float originalSpeed = fireTruck.getSpeed();

        powerUp.setFireTruck(fireTruck);
        powerUp.setActive();

        assertTrue(powerUp.isActive());
        assertEquals(fireTruck.getSpeed(), originalSpeed * 2, 0.0);

        powerUp.timer = 0;
        powerUp.update();

        assertEquals(fireTruck.getSpeed(), originalSpeed, 0.0);
    }

    @Test
    public void powerUpDamageEndsTest() {
        PowerUp powerUp = new PowerUp(PowerUpType.Damage, 0, 0);
        FireTruck fireTruck = new FireTruck(new Vector2(0, 0), FireTruckType.Ruby, collisionsMock);

        float originalDamage = fireTruck.getAP();

        powerUp.setFireTruck(fireTruck);
        powerUp.setActive();

        assertTrue(powerUp.isActive());
        assertEquals(fireTruck.getAP(), originalDamage * 2, 0.0);

        powerUp.timer = 0;
        powerUp.update();

        assertEquals(fireTruck.getAP(), originalDamage, 0.0);
    }

}
