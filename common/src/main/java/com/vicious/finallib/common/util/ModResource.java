package com.vicious.finallib.common.util;

import com.vicious.finallib.FinalLib;
import net.minecraft.resources.ResourceLocation;

public class ModResource extends ResourceLocation {
    public ModResource(String key) {
        super(FinalLib.MOD_ID,key);
    }
}
