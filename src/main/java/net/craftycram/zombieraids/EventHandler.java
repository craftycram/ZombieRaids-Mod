package net.craftycram.zombieraids;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import scala.collection.parallel.ParIterableLike;

import java.awt.*;
import java.lang.ref.Reference;
import java.util.logging.Level;

import static net.craftycram.zombieraids.Main.*;

public class EventHandler {

    @SubscribeEvent
    public void tickEvent (TickEvent.WorldTickEvent event) {
        //if (Minecraft.getMinecraft().theWorld != null) {
            if ((ticks > timings[timingState] + eventStartTick) && spawnLightnings) {
                System.out.println("test");
                double posX = genCoords(bedPosX, 10);
                double posZ = genCoords(bedPosZ, 10);
                int topBlock = event.world.getTopSolidOrLiquidBlock((int)posX, (int)posZ);
                EntityLightningBolt lighting = new EntityLightningBolt(world, posX, topBlock + 1, posZ);
                //world.playSound(posX, topBlock, posZ, "entity.lightning.impact", 1, 1 ,true);
                Minecraft.getMinecraft().theWorld.spawnEntityInWorld(lighting);
                //world.spawnEntityInWorld(lighting);
                //world.addEntity(lighting);
                timingState++;
            }
            if (timingState > timings.length - 1) {
                player.setPositionAndRotation(playerPosX, playerPosY + 5, playerPosZ, -130, 0);
                timingState = 0;
            }
            if (ticks > eventStartTick + 100 && spawnLightnings) {
                // remove bed block
                world.func_147480_a(bedPosX, bedPosY, bedPosZ, true);
                Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
                player.setPositionAndRotation(playerPosX, playerPosY, playerPosZ, player.getRotationYawHead(), 0);
                spawnLightnings = false;
                spawnZombies = true;
            }

            if(ticks > eventStartTick + 1000 && spawnZombies) {
                for (int i = 0; i < 100; i++) {
                    EntityZombie mob = new EntityZombie(world);
                    double posX = genCoords(bedPosX, 20);
                    double posZ = genCoords(bedPosZ, 20);
                    double posY = event.world.getTopSolidOrLiquidBlock((int)posX, (int)posZ);
                    // double posY = playerPos.getY() + (Math.random() * 1);
                    mob.setPosition(posX, playerPosY, posZ);
                    mob.setCustomNameTag("Kevin");
                    player.worldObj.spawnEntityInWorld(mob);
                    //world.spawnEntityInWorld(mob);
                }
                spawnZombies = false;
            }

            ticks++;
        //}
    }

    @SubscribeEvent
    public void testEvent(PlayerEvent.ItemPickupEvent event) {
        System.out.println("event pickup");
        Minecraft.getMinecraft().thePlayer.playSound("note.pling", 1, 1);
    }


    @SubscribeEvent
    public void playerSleepInBed(PlayerSleepInBedEvent event) {
        eventStartTick = ticks;
        bedPosX = event.x;
        bedPosY = event.y;
        bedPosZ = event.z;
        player = event.entityPlayer;
        playerPosX = Minecraft.getMinecraft().thePlayer.posX;
        playerPosY = Minecraft.getMinecraft().thePlayer.posY;
        playerPosZ = Minecraft.getMinecraft().thePlayer.posZ;
        world = event.entityPlayer.worldObj;
        //if (world.getWorldTime() > 1000) {
        //double rand = Math.random();
        //if (rand < 0.5) {
        spawnLightnings = true;
        player.addChatMessage(new ChatComponentText("Eine Horde Zombies überfällt dich!"));
        Minecraft.getMinecraft().thePlayer.playSound("ambient.cave.cave", 100, 0);
        Minecraft.getMinecraft().thePlayer.playSound("ambient.cave.cave", 100, 0);
        Minecraft.getMinecraft().thePlayer.playSound("ambient.cave.cave", 100, 0);
        Minecraft.getMinecraft().thePlayer.playSound("ambient.cave.cave", 100, 0);
        Minecraft.getMinecraft().thePlayer.playSound("ambient.cave.cave", 100, 0);
        Minecraft.getMinecraft().thePlayer.playSound("ambient.cave.cave", 100, 0);
        Minecraft.getMinecraft().thePlayer.playSound("ambient.cave.cave", 100, 0);
        //world.playSound(null, playerPos.getX(), playerPos.getY(), playerPos.getZ(), SoundEvents.AMBIENT_CAVE, SoundCategory.MASTER, 100, 0);
        //world.playSound(null, playerPos.getX(), playerPos.getY(), playerPos.getZ(), SoundEvents.AMBIENT_CAVE, SoundCategory.MASTER, 100, 0);
        //world.playSound(null, playerPos.getX(), playerPos.getY(), playerPos.getZ(), SoundEvents.EVENT_RAID_HORN, SoundCategory.MASTER, 80, 0);
        ////player.playSound(SoundEvents.AMBIENT_CAVE, 100, 0);
        ////player.playSound(SoundEvents.AMBIENT_CAVE, 100, 0);
        /////player.playSound(SoundEvents.EVENT_RAID_HORN, 80, 0);
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 2;
        //player.setPositionAndRotation(playerPos.getX(), playerPos.getY() + 5, playerPos.getZ(), player.getRotationYawHead(), 90);
        EntityItem item = new EntityItem(world, playerPosX, playerPosY, playerPosZ, new ItemStack(Blocks.stone));
        EntityZombie mob = new EntityZombie(world);
        mob.setPosition(playerPosX, playerPosY, playerPosZ);
        //world.addEntity(mob);
        world.spawnEntityInWorld(item);
        //}
        //}
    }

    public double genCoords(int p, int r) {
        double pos;
        if (Math.random() > 0.5) {
            pos = p + (Math.random() * r);
        } else {
            pos = p - (Math.random() * r);
        }
        return pos;
    }

}
