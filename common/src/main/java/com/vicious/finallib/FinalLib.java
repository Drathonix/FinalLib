package com.vicious.finallib;

import com.llamalad7.mixinextras.utils.MixinExtrasLogger;
import com.vicious.finallib.common.ServerNetwork;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FinalLib
{
	public static final String MOD_ID = "finallib";
    public static Logger logger = LogManager.getLogger(MOD_ID);

    public static void init() {
        ServerNetwork.init();
	}
}
