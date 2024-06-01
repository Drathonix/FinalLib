package com.vicious.finallib;

import com.vicious.finallib.client.ClientNetwork;
import com.vicious.finallib.common.ServerNetwork;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FinalLibClient
{
    public static void init() {
        ClientNetwork.init();
	}
}
