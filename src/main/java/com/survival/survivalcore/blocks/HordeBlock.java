package com.survival.survivalcore.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class HordeBlock extends Block{
    public HordeBlock(String name, Material material) {
    	super(material);
    	this.setRegistryName(name);
    	this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.MISC);

    }
}
