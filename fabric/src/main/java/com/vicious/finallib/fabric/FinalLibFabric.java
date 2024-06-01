package com.vicious.finallib.fabric;

import com.vicious.finallib.FinalLib;
import net.fabricmc.api.ModInitializer;

public class FinalLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FinalLib.init();
    }
}