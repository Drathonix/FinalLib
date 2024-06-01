package com.vicious.finallib.client;

import com.vicious.finallib.common.ServerNetwork;
import com.vicious.finallib.common.bridge.IPlayerMixin;
import com.vicious.finallib.common.guisync.InteractionMouseButton;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class ClientNetwork {
    @SuppressWarnings("all")
    public static void init(){
        NetworkManager.registerReceiver(NetworkManager.Side.S2C,ServerNetwork.SYNC_SLOT_ID,(buf, ctx)->{
            Player plr = ctx.getPlayer();
            if(plr instanceof IPlayerMixin mixin){
                if(mixin.hasGUISynchronizer()) {
                    mixin.finalLib$getGUISynchronizer().updateSlot(buf.readInt(),buf.readInt(),buf.readItem());
                }
            }
        });
        NetworkManager.registerReceiver(NetworkManager.Side.S2C,ServerNetwork.UNSYNC_ID,(buf, ctx) -> {
            Player plr = ctx.getPlayer();
            if(plr instanceof IPlayerMixin mixin) {
                mixin.finalLib$setGUISynchronizer(null);
                Minecraft.getInstance().setScreen(null);
            }
        });
    }

    public static void sendInvInteraction(int inv, int slot, InteractionMouseButton button, boolean[] keypresses){
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(inv);
        buf.writeInt(slot);
        buf.writeInt(button.ordinal());
        for (boolean keypress : keypresses) {
            buf.writeBoolean(keypress);
        }
        NetworkManager.sendToServer(ServerNetwork.INVENTORY_INTERACT_ID,buf);
    }

    public static void unsyncGUI() {

    }
}
