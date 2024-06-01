package com.vicious.finallib.common.inventory;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface InventoryWrapper {
    int getInventoryNumber();

    int getSize();

    default boolean isEmpty(){
        for (int i = 0; i < getSize(); i++) {
            if(!getItemStack(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    ItemStack getItemStack(int i);

    ItemStack removeItem(int i, int j);

    void setItem(int i, ItemStack itemStack);

    void setChanged();

    default boolean slotAcceptsItem(int slot, ItemStack stack){
        return true;
    }

    default @NotNull ItemStack insertItem(@NotNull ItemStack stack){
        return insertItem(stack,false);
    }

    default @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate){
        if(stack.isEmpty()){
            return ItemStack.EMPTY;
        }
        ItemStack result = stack.copy();
        for (int i = 0; i < getSize() && result.getCount() > 0; i++) {
            ItemStack current = getItemStack(i);
            if(current.isEmpty()){
                if(slotAcceptsItem(i,stack)) {
                    ItemStack inserted = result.copy();
                    inserted.setCount(inserted.getMaxStackSize());
                    result.shrink(inserted.getCount());
                    if (!simulate) {
                        setItem(i, inserted);
                    }
                }
            }
            else if(current.sameItem(result)){
                int change = Math.min(result.getCount(),current.getMaxStackSize()-current.getCount());
                result.shrink(change);
                if(!simulate) {
                    current.grow(change);
                    setChanged();
                }
            }
        }
        return result;
    }

    default @NotNull ItemStack extractItem(@NotNull ItemStack requested){
        return extractItem(requested,false);
    }

    default @NotNull ItemStack extractItem(@NotNull ItemStack requested, boolean simulate){
        if(requested.isEmpty()){
            return ItemStack.EMPTY;
        }
        ItemStack remaining = requested.copy();
        for (int i = 0; i < getSize() && remaining.getCount() > 0; i++) {
            ItemStack current = getItemStack(i);
            if(current.sameItem(requested)){
                int change = Math.min(remaining.getCount(),current.getCount());
                if(!simulate) {
                    current.shrink(change);
                    setChanged();
                }
            }
        }
        ItemStack result = requested.copy();
        result.setCount(requested.getCount()- remaining.getCount());
        return result;
    }

    default void transferMax(ItemStack stack, InventoryWrapper targetInventory){
        if(stack.isEmpty()){
            return;
        }
        for (int i = 0; i < getSize(); i++) {
            ItemStack current = getItemStack(i);
            if(current.sameItem(stack)){
                ItemStack result = targetInventory.insertItem(current);
                current.setCount(result.getCount());
                setChanged();
                if(result.isEmpty()){
                    return;
                }
            }
        }
    }

}
