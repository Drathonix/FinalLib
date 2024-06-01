package com.vicious.finallib.fabric;

import com.vicious.finallib.FinalLib;
import com.vicious.finallib.FinalLibClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class FinalLibFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FinalLibClient.init();
        CompoundTag
    }
}