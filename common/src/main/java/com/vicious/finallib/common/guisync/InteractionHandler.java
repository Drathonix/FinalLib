package com.vicious.finallib.common.guisync;

import com.vicious.finallib.common.inventory.InventoryWrapper;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class InteractionHandler {
    private final InventoryWrapper otherInventory;
    private final InventoryWrapper interactedInventory;

    private ItemStack heldStack;
    private final InteractionMouseButton mouseButton;
    private final boolean[] keyPresses;
    private final int slotIndex;

    public InteractionHandler(InventoryWrapper otherInventory, InventoryWrapper interactedInventory, ItemStack heldStack, InteractionMouseButton mouseButton, boolean[] keyPresses, int slotIndex) {
        this.otherInventory = otherInventory;
        this.interactedInventory = interactedInventory;
        this.heldStack = heldStack.copy();
        this.mouseButton = mouseButton;
        this.keyPresses = keyPresses;
        this.slotIndex = slotIndex;
    }

    public boolean shouldAllow(){
        return true;
    }

    public boolean isQuick(){
        return keyPresses[InteractionKeyPress.SHIFT.ordinal()];
    }
    public boolean isDrop(){
        return keyPresses[InteractionKeyPress.DROP.ordinal()];
    }

    public Set<SlotIndex> handle(){
        if(slotIndex < 0 || slotIndex >= interactedInventory.getSize()){
            return new HashSet<>();
        }
        ItemStack slotStack = interactedInventory.getItemStack(slotIndex);
        Set<SlotIndex> results = new HashSet<>();
        // When holding shift.
        if(isQuick()){
            // If the interaction is a double click, transfer all items matching the slot stack.
            if(mouseButton == InteractionMouseButton.DOUBLE_RIGHT_CLICK){
                interactedInventory.transferMax(slotStack, otherInventory);
                results.add(new SlotIndex(otherInventory.getInventoryNumber(),-1));
                results.add(new SlotIndex(interactedInventory.getInventoryNumber(),-1));
            }
            // Transfer as much of the item as possible to the other inventory.
            else{
                ItemStack stack = otherInventory.insertItem(slotStack);
                interactedInventory.setItem(slotIndex,stack);
                results.add(new SlotIndex(otherInventory.getInventoryNumber(),-1));
                results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
            }
        }
        else {
            if (mouseButton == InteractionMouseButton.RIGHT_CLICK) {
                // When right-clicking an empty slot while holding an item, place one item and shrink the original stack.
                if (slotStack.isEmpty()) {
                    if (!heldStack.isEmpty()) {
                        ItemStack result = heldStack.copy();
                        result.setCount(1);
                        interactedInventory.setItem(slotIndex,result);
                        results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
                        heldStack.shrink(0);
                    }
                }
                // When right-clicking a full slot...
                else {
                    // while not holding an item, split the stack in half, take the rounded up amount.
                    if (heldStack.isEmpty()) {
                        int split = (int) Math.ceil(slotStack.getCount() / 2.0);
                        heldStack = slotStack.copy();
                        heldStack.setCount(split);
                        slotStack.shrink(split);
                        interactedInventory.setItem(slotIndex,slotStack);
                        results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
                    }
                    // while holding an item...
                    else {
                        // place one item if same and space is available.
                        if (slotStack.sameItem(heldStack)) {
                            int change = Math.min(slotStack.getCount() - slotStack.getMaxStackSize(), 1);
                            if (change > 0) {
                                slotStack.grow(change);
                                heldStack.shrink(change);
                                interactedInventory.setItem(slotIndex,slotStack);
                                results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
                            }
                        }
                        // swap items if not the same
                        else {
                            ItemStack temp = heldStack;
                            heldStack = slotStack;
                            interactedInventory.setItem(slotIndex,temp);
                            results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
                        }
                    }
                }

            }
            if (mouseButton == InteractionMouseButton.LEFT_CLICK) {
                // when not holding anything take the entire stack.
                if(heldStack.isEmpty()){
                    heldStack = slotStack;
                    interactedInventory.setItem(slotIndex,ItemStack.EMPTY);
                    results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
                }
                // when holding a stack...
                else {
                    // place the entire stack
                    if(slotStack.isEmpty()){
                        interactedInventory.setItem(slotIndex,heldStack);
                        heldStack = ItemStack.EMPTY;
                        results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
                    }
                    // if the slot is full
                    else{
                        // place as much as possible.
                        if(slotStack.sameItem(heldStack)){
                            int change = Math.min(slotStack.getMaxStackSize()-slotStack.getCount(),heldStack.getCount());
                            if (change > 0) {
                                slotStack.grow(change);
                                heldStack.shrink(change);
                                interactedInventory.setItem(slotIndex,slotStack);
                                results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
                            }
                        }
                        // swap the stacks
                        else {
                            ItemStack temp = heldStack;
                            heldStack = slotStack;
                            interactedInventory.setItem(slotIndex,temp);
                            results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
                        }
                    }
                }
            }
            // Extract as much of the slotStack as possible.
            if(mouseButton == InteractionMouseButton.DOUBLE_RIGHT_CLICK && !slotStack.isEmpty()){
                heldStack = slotStack;
                ItemStack request = slotStack.copy();
                request.setCount(slotStack.getMaxStackSize()-slotStack.getCount());
                heldStack.grow(interactedInventory.extractItem(request).getCount());
                results.add(new SlotIndex(interactedInventory.getInventoryNumber(),-1));
            }
            // Pull 1 item from the other inventory and place in slotStack per scroll action.
            if(mouseButton == InteractionMouseButton.SCROLL_UP && !slotStack.isEmpty()){
                ItemStack request = slotStack.copy();
                request.setCount(1);
                slotStack.grow(otherInventory.extractItem(request).getCount());
                results.add(new SlotIndex(otherInventory.getInventoryNumber(),-1));
                interactedInventory.setItem(slotIndex,slotStack);
                results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
            }
            // Push 1 item to the other inventory per scroll action.
            if(mouseButton == InteractionMouseButton.SCROLL_UP && !slotStack.isEmpty()){
                ItemStack request = slotStack.copy();
                request.setCount(1);
                if(otherInventory.insertItem(request).isEmpty()) {
                    slotStack.shrink(1);
                }
                results.add(new SlotIndex(otherInventory.getInventoryNumber(),-1));
                interactedInventory.setItem(slotIndex,slotStack);
                results.add(new SlotIndex(interactedInventory.getInventoryNumber(),slotIndex));
            }
        }
        return results;
    }

    public ItemStack getHeldStack(){
        return heldStack;
    }
}
