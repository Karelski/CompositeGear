/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import java.util.List;

import com.google.common.collect.Lists;

import ic2.api.item.IC2Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import techreborn.api.TechRebornAPI;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.common.compat.IC2Compat;
import tws.zcaliptium.compositegear.common.compat.TRCompat;
import tws.zcaliptium.compositegear.common.crafting.RecipesDyingArmor;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ConfigurationCG;

public class ItemsCG
{
	private static final String COMPOSITE_NAME = "composite";

	// Weapons
	public static Item compositeBow;

    public static List<Item> COLORABLE_REGISTRY = Lists.<Item>newArrayList();

	public static EnumRarity CG_UNCOMMON = EnumHelper.addRarity("CG_UNCOMMON", TextFormatting.GREEN, "CgUncommon");
	public static EnumRarity CG_RARE = EnumHelper.addRarity("CG_RARE", TextFormatting.BLUE, "CgRare");
	public static Item.ToolMaterial GENERIC_TOOL_MATERIAL = EnumHelper.addToolMaterial("CG_GENERIC", 0, 1, 0.0F, 0.0F, 0);

	public static void load()
	{
		compositeBow = new ItemCGBow("composite_bow", 2000, 15);

		if (CompositeGear.proxy.isClient()) {
			registerItemModels();
		}

		if (Loader.isModLoaded(Compats.TR))
		{
			TRCompat.load();
		}

		if (Loader.isModLoaded(Compats.IC2))
		{
			IC2Compat.load();
		}
	}

	private static void registerItemModels()
	{
		registerItemModel(compositeBow, "tool/composite_bow");
	}

	public static ItemStack getStackNoMeta(Item prototype)
	{
		ItemStack result = new ItemStack(prototype);
		Items.APPLE.setDamage(result, 32767);
		return result;
	}

	private static void registerRecipe(IRecipe recipe)
	{
		recipe.setRegistryName(new ResourceLocation(ModInfo.MODID, "100"));

		ForgeRegistries.RECIPES.register(recipe);
	}

	public static void loadRecipes()
	{
		registerRecipe(new RecipesDyingArmor());
		
		/*if (!Loader.isModLoaded(Compats.IC2) && !Loader.isModLoaded(Compats.TR)) {
			GameRegistry.addSmelting(Items.SLIME_BALL, new ItemStack(rubberBall), 0);
		}*/
	}

	public static Item registerItem(Item item, ResourceLocation rl) {
		item.setRegistryName(rl);
		return registerItem(item);
	}

	public static Item registerItem(Item item) {
		ForgeRegistries.ITEMS.register(item);
		return item;
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item, String name) {
		registerItemModel(item, 0, name);
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item, int meta, String name) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ModInfo.MODID + ":" + name, "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerMultiItem(Item item, String name, String path) {
		ResourceLocation loc = new ResourceLocation(ModInfo.MODID, path);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(loc, "type=" + name));
	}
}
