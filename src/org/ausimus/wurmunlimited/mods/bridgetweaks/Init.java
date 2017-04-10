package org.ausimus.wurmunlimited.mods.bridgetweaks;
import org.gotti.wurmunlimited.modloader.interfaces.*;

import java.util.Properties;

public class Init implements WurmServerMod, PreInitable, Configurable, Initable {

    static int RopeMaxWidth;
    static int WoodMaxWidth;
    static int StoneMaxWidth;
    static int MarbleMaxWidth;

    @Override
    public void preInit() {
        //ModActions.init();
    }

    @Override
    public void init() {
        new PBQRewrite();
    }


    @Override
    public void configure(Properties properties) {
        RopeMaxWidth = Integer.parseInt(properties.getProperty("RopeMaxWidth", Integer.toString(RopeMaxWidth)));
        WoodMaxWidth = Integer.parseInt(properties.getProperty("WoodMaxWidth", Integer.toString(WoodMaxWidth)));
        StoneMaxWidth = Integer.parseInt(properties.getProperty("StoneMaxWidth", Integer.toString(StoneMaxWidth)));
        MarbleMaxWidth = Integer.parseInt(properties.getProperty("MarbleMaxWidth", Integer.toString(MarbleMaxWidth)));
    }
}