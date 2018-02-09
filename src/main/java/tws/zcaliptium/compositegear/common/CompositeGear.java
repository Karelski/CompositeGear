/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import tws.zcaliptium.compositegear.common.items.ItemsCG;

import org.apache.logging.log4j.Logger;

import ic2.api.recipe.Recipes;

@Mod(modid = ModInfo.MODID, name = ModInfo.MODNAME, dependencies="after:ic2;after:techreborn;after:hzdslib", version = ModInfo.VERSION)
public class CompositeGear
{
    @Instance(ModInfo.MODID)
    public static CompositeGear instance;
    
    @SidedProxy(clientSide = "tws.zcaliptium.compositegear.client.ClientProxy", serverSide = "tws.zcaliptium.compositegear.common.CommonProxy")
	public static CommonProxy proxy;
    
    public static final Side side = FMLCommonHandler.instance().getEffectiveSide();
    
    public static CreativeTabs ic2Tab;
	public static Logger modLog;

	public static void getIC2Tab()
	{
		for (int i = 0; i < CreativeTabs.CREATIVE_TAB_ARRAY.length; i++) {
			if (CreativeTabs.CREATIVE_TAB_ARRAY[i].getTabLabel().equalsIgnoreCase("IC2")) {
				ic2Tab = CreativeTabs.CREATIVE_TAB_ARRAY[i];
			}
		}
	}
    
    @EventHandler
    public void load(FMLPreInitializationEvent event)
    {
    	modLog = event.getModLog();
    	ConfigurationCG.init(event.getSuggestedConfigurationFile());

    	if (proxy.isClient()) {
        	getIC2Tab();
    	}

    	ItemsCG.load();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		proxy.registerEventHandlers();
    }
    
    @EventHandler
    public void afterModsLoaded(FMLPostInitializationEvent event)
    {
        ItemsCG.loadRecipes();
        
        // Item icon coloring won't work without it.
        if (proxy.isClient())
        {
    		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
            {
                public int colorMultiplier(ItemStack stack, int tintIndex)
                {
                    return tintIndex > 0 ? -1 : ((ItemArmor)stack.getItem()).getColor(stack);
                }
            }, ItemsCG.compositeHelmet, ItemsCG.compositeChestplate, ItemsCG.compositeLeggings, ItemsCG.compositeBoots,
            		ItemsCG.ushankaHat, ItemsCG.balaclavaMask, ItemsCG.shemaghMask, ItemsCG.rubberGasmask, ItemsCG.respiratorMask,
            		ItemsCG.compositeLightHelmet, ItemsCG.compositeLightVest, ItemsCG.compositeLightLeggings, ItemsCG.compositeLightBoots);
        }
    }
}
