package com.chrispy.dragonballrevival.common.util;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class Constants {
        public static final String MOD_ID = "dragonballrevival";
        public static final Identifier STAT_PACKET_ID = new Identifier("dragonballrevival", "stat_packet_id");
        public static final Identifier VITALITY_REQUEST = new Identifier("dragonballrevival", "vitality_request");

        public static class Multipliers {
                public static final double PvpTrainingBonus = 2.0;
                public static final double VitalityFactor = 1.0;
                public static final double TenacityFactor = 1.0;
                public static final double ResilienceFactor = 0.25;
                public static final double PassionFactor = 1.0;
                public static final double DeterminationFactor = 1.0;

        }

}