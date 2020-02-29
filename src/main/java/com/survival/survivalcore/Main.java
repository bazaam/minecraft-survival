package com.survival.survivalcore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.Logger;

import com.survival.survivalcore.blocks.HordeBlock;
import com.survival.survivalcore.init.ModBlocks;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main
{


    private static Logger logger;
    
    public static ToolMaterial testToolMaterial;
    public static Item myItem;
    public static HordeBlock hordeController;

   
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        ModBlocks.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

    }
    

    
    public static void SpawnWave(EntityPlayer player, BlockPos pos, int numEnemies) {
    	BlockPos playerPos = player.getPosition();
    	for (int i = 0; i < numEnemies; i++) {
    		EntityZombie zombie = new EntityZombie(player.world);
    		BlockPos target = playerPos;
    		target = target.add(-10,0,0);
    		IBlockState targetBlock = player.world.getBlockState(target);
    		
    		while (targetBlock.getMaterial() != Material.AIR) {
    			target = target.add(0,2,0);
    			targetBlock = player.world.getBlockState(target);
    		}
    		
    		zombie.setLocationAndAngles(target.getX(), target.getY(), target.getZ(), 0, 0);
    		if (!player.world.isRemote) {
    			player.world.spawnEntity(zombie);
    			
    		}
    	}
    }   
    
    @Mod.EventBusSubscriber
    public static class HordeBlockPlacedHandler {
    	@SubscribeEvent
    	public static void HordeBlockPlaced(BlockEvent.EntityPlaceEvent event) {
    		if (event.getEntity() instanceof EntityPlayer) {
    			EntityPlayer player = (EntityPlayer)event.getEntity();
    			if (event.getPlacedBlock().getBlock() instanceof HordeBlock ) {
    				int stackSize = player.getHeldItemMainhand().getCount();
    				SpawnWave(player, player.getPosition(), stackSize);
    				
    			}
    			
    			
    		}
    	}
    }
    
    @Mod.EventBusSubscriber
    public static class ZombieWandSpawnHandler {
    	@SubscribeEvent
    	public static void stickUsed(RightClickItem event) {
    		if (event.getItemStack().getItem() == Items.STICK) {
    			if(event.getEntity() instanceof EntityPlayer) { 					//added safety check to protect the cast below
            		EntityPlayer player = (EntityPlayer)event.getEntity();
            		BlockPos target = player.rayTrace(100, 1.0f).getBlockPos();
            		
                 	        		
            		EntityZombie zombie = new EntityZombie(player.world);
            		zombie.setLocationAndAngles(target.getX(), target.getY(), target.getZ(), 0, 0);
            		
            		if (!player.world.isRemote) {
            			player.world.spawnEntity(zombie);
            			
            		}
    			}

    		}
    	}
    }


}
