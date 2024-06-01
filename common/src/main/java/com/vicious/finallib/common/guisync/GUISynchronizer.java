package com.vicious.finallib.common.guisync;

import com.vicious.finallib.common.ServerNetwork;
import com.vicious.finallib.common.inventory.InventoryWrapper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class GUISynchronizer {
    private final List<InventoryWrapper> inventories = new ArrayList<>();
    private ItemStack heldItemStack = ItemStack.EMPTY;

    public void registerInventoryWrapper(Function<Integer, InventoryWrapper> factory){
        inventories.add(factory.apply(inventories.size()));
    }

    public void handleInteractionPacket(Player plr, int interactedInventory, int slotIndex, InteractionMouseButton mouseButton, boolean[] keyPresses){
        if(interactedInventory < inventories.size() && interactedInventory > 0) {
            InventoryWrapper interacted = inventories.get(interactedInventory);
            inventories.stream().filter(inv -> inv.getInventoryNumber() != interactedInventory).forEach(inv -> {
                InteractionHandler handler = new InteractionHandler(inv, interacted, heldItemStack, mouseButton, keyPresses, slotIndex);
                if (handler.shouldAllow()) {
                    Set<SlotIndex> toUpdate = handler.handle();
                    heldItemStack = handler.getHeldStack();
                    for (SlotIndex index : toUpdate) {
                        if(index.slotIndex() != -1){
                            ServerNetwork.sendSlotUpdate(plr,index.inventory(),index.slotIndex(),inventories.get(index.inventory()).getItemStack(index.slotIndex()));
                        }
                        else{
                            InventoryWrapper i = inventories.get(index.inventory());
                            for (int j = 0; j < i.getSize(); j++) {
                                ServerNetwork.sendSlotUpdate(plr,index.inventory(),j,i.getItemStack(j));
                            }
                        }
                    }
                }
            });
        }
    }

    public void updateSlot(int inventory, int slotIndex, ItemStack newState){
        inventories.get(inventory).setItem(slotIndex,newState);
    }
}
