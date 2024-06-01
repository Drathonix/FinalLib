package com.vicious.finallib.common;

import com.vicious.finallib.FinalLib;
import com.vicious.finallib.common.bridge.IPlayerMixin;
import com.vicious.finallib.common.guisync.InteractionKeyPress;
import com.vicious.finallib.common.guisync.InteractionMouseButton;
import com.vicious.finallib.common.util.ModResource;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ServerNetwork {
    public static final ModResource INVENTORY_INTERACT_ID = new ModResource("inv_int");
    public static final ModResource SYNC_SLOT_ID = new ModResource("sync_slot");
    public static final ModResource UNSYNC_ID = new ModResource("unsync");

    @SuppressWarnings("all")
    public static void init(){
        NetworkManager.registerReceiver(NetworkManager.Side.C2S,INVENTORY_INTERACT_ID,(buf,ctx)->{
            Player plr = ctx.getPlayer();
            if(plr instanceof IPlayerMixin mixin){
                if(mixin.hasGUISynchronizer()) {
                    int inv = buf.readInt();
                    int slot = buf.readInt();
                    InteractionMouseButton button = InteractionMouseButton.values()[buf.readInt()];
                    boolean[] keyPresses = new boolean[InteractionKeyPress.values().length];
                    for (InteractionKeyPress value : InteractionKeyPress.values()) {
                        keyPresses[value.ordinal()] = buf.readBoolean();
                    }
                    mixin.finalLib$getGUISynchronizer().handleInteractionPacket(plr,buf.readInt(), buf.readInt(), InteractionMouseButton.values()[buf.readInt()], keyPresses);
                }
                else{
                    FinalLib.logger.warn(plr.getName().getString() + " attempted to interact without having an active GUISynchronizer.");
                }
            }
        });
        NetworkManager.registerReceiver(NetworkManager.Side.C2S,UNSYNC_ID,(buf, ctx) -> {
            Player plr = ctx.getPlayer();
            if(plr instanceof IPlayerMixin mixin) {
                mixin.finalLib$setGUISynchronizer(null);
            }
        });
    }

    public static void sendSlotUpdate(Player plr, int inventory, int slot, ItemStack itemStack) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(inventory);
        buf.writeInt(slot);
        buf.writeItem(itemStack);
        NetworkManager.sendToPlayer((ServerPlayer) plr,SYNC_SLOT_ID,buf);
    }
}
