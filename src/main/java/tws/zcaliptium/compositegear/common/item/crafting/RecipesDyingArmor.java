/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.item.crafting;

import java.util.ArrayList;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tws.zcaliptium.compositegear.common.items.ItemCompositeArmor;

public class RecipesDyingArmor implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inventory, World worldObj)
    {
        ItemStack itemstack = null;
        ArrayList arraylist = new ArrayList();

        for (int i = 0; i < inventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = inventory.getStackInSlot(i);

            if (itemstack1 != null)
            {
                if (itemstack1.getItem() instanceof ItemArmor)
                {
                    ItemArmor itemarmor = (ItemArmor)itemstack1.getItem();

                    if ((!(itemarmor instanceof ItemCompositeArmor)) || itemstack != null) {
                        return false;
                    }

                    itemstack = itemstack1;
                } else {
                    if (itemstack1.getItem() != Items.DYE)
                    {
                        return false;
                    }

                    arraylist.add(itemstack1);
                }
            }
        }

        return itemstack != null && !arraylist.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inventory)
    {
        ItemStack itemstack = null;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemArmor itemarmor = null;
        int k;
        int l;
        float f;
        float f1;
        int l1;

        for (k = 0; k < inventory.getSizeInventory(); ++k)
        {
            ItemStack itemstack1 = inventory.getStackInSlot(k);

            if (itemstack1 != null)
            {
                if (itemstack1.getItem() instanceof ItemArmor)
                {
                    itemarmor = (ItemArmor)itemstack1.getItem();

                    if ((!(itemarmor instanceof ItemCompositeArmor)) || itemstack != null)
                    {
                        return null;
                    }

                    itemstack = itemstack1.copy();
                    itemstack.setCount(1);

                    if (itemarmor.hasColor(itemstack1))
                    {
                        l = itemarmor.getColor(itemstack);
                        f = (float)(l >> 16 & 255) / 255.0F;
                        f1 = (float)(l >> 8 & 255) / 255.0F;
                        float f2 = (float)(l & 255) / 255.0F;
                        i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                        aint[0] = (int)((float)aint[0] + f * 255.0F);
                        aint[1] = (int)((float)aint[1] + f1 * 255.0F);
                        aint[2] = (int)((float)aint[2] + f2 * 255.0F);
                        ++j;
                    }
                }
                else
                {
                    if (itemstack1.getItem() != Items.dye) {
                        return null;
                    }

                    float[] afloat = EntitySheep.fleeceColorTable[BlockColored.func_150032_b(itemstack1.getItemDamage())];
                    int j1 = (int)(afloat[0] * 255.0F);
                    int k1 = (int)(afloat[1] * 255.0F);
                    l1 = (int)(afloat[2] * 255.0F);
                    i += Math.max(j1, Math.max(k1, l1));
                    aint[0] += j1;
                    aint[1] += k1;
                    aint[2] += l1;
                    ++j;
                }
            }
        }

        if (itemarmor == null) {
            return null;
        } else {
            k = aint[0] / j;
            int i1 = aint[1] / j;
            l = aint[2] / j;
            f = (float)i / (float)j;
            f1 = (float)Math.max(k, Math.max(i1, l));
            k = (int)((float)k * f / f1);
            i1 = (int)((float)i1 * f / f1);
            l = (int)((float)l * f / f1);
            l1 = (k << 8) + i1;
            l1 = (l1 << 8) + l;
            itemarmor.func_82813_b(itemstack, l1);
            return itemstack;
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 10;
    }

    public ItemStack getRecipeOutput()
    {
        return null;
    }

	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLocation getRegistryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFit(int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
}