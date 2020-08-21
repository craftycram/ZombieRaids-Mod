package net.craftycram.zombieraids;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {

    public static final String MODID = "zombieraids";
    public static final String MODNAME = "Zombie Raids";
    public static final String VERSION = "1.0.0";


    public static int ticks;
    public static int timingState = 0;
    //public static int[] timings = {10, 20, 30, 40};
    public static int[] timings = {10, 20, 30, 40};
    public static int eventStartTick = ticks;
    public static double playerPosX;
    public static double playerPosY;
    public static double playerPosZ;
    public static World world = null;
    public static boolean spawnLightnings = false;
    public static boolean spawnZombies = false;
    public static EntityPlayer player = null;
    public static int bedPosX;
    public static int bedPosY;
    public static int bedPosZ;

    @Instance
    public static Main instance = new Main();


    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {

    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        FMLCommonHandler.instance().bus().register(new net.craftycram.zombieraids.EventHandler());
        MinecraftForge.EVENT_BUS.register(new net.craftycram.zombieraids.EventHandler());

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {

    }



}
