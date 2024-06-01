package com.vicious.finallib.forge;

import com.vicious.finallib.FinalLibClient;
import dev.architectury.platform.forge.EventBuses;
import com.vicious.finallib.FinalLib;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FinalLib.MOD_ID)
public class FinalLibForge {
    public FinalLibForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(FinalLib.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        FinalLib.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid=FinalLib.MOD_ID,bus=Mod.EventBusSubscriber.Bus.MOD,value= Dist.CLIENT)
    public static class CMEs {
        @SubscribeEvent
        public static void clientInit(FMLClientSetupEvent event) {
            FinalLibClient.init();
        }
    }

}