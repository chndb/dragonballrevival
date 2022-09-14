package com.chrispy.dragonballrevival;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;

public class StatScreenHandler extends ScreenHandler {

    //region Vitality
    private int HandlerVitalityXP;
    private int HandlerVitality;
    private int HandlerVitalityNextLevelXP;
    public int getHandlerVitalityXP() {
        return HandlerVitalityXP;
    }

    public int getHandlerVitality() {
        return HandlerVitality;
    }
    public int getHandlerVitalityNextLevelXP() {
        return HandlerVitalityNextLevelXP;
    }
    //endregion
    //region Tenacity
    private int HandlerTenacityXP;
    private int HandlerTenacity;
    private int HandlerTenacityNextLevelXP;
    public int getHandlerTenacityXP() {
        return HandlerTenacityXP;
    }

    public int getHandlerTenacity() {
        return HandlerTenacity;
    }
    public int getHandlerTenacityNextLevelXP() {
        return HandlerTenacityNextLevelXP;
    }
    //endregion
    //region Resilience
    private int HandlerResilienceXP;
    private int HandlerResilience;
    private int HandlerResilienceNextLevelXP;
    public int getHandlerResilienceXP() {
        return HandlerResilienceXP;
    }

    public int getHandlerResilience() {
        return HandlerResilience;
    }
    public int getHandlerResilienceNextLevelXP() {
        return HandlerResilienceNextLevelXP;
    }
    //endregion
    //region Passion
    //endregion
    //region Determination
    //endregion

    public StatScreenHandler(int syncId, PacketByteBuf buf) {
        this(syncId);
        int[] StatsArray = buf.readIntArray();
        HandlerVitalityXP = StatsArray[0];
        HandlerVitality = StatsArray[1];
        HandlerVitalityNextLevelXP = StatsArray[2];
        HandlerTenacityXP = StatsArray[3];
        HandlerTenacity = StatsArray[4];
        HandlerTenacityNextLevelXP = StatsArray[5];
        HandlerResilienceXP = StatsArray[6];
        HandlerResilience = StatsArray[7];
        HandlerResilienceNextLevelXP = StatsArray[8];
    }
    public StatScreenHandler(int syncId) {
        super(MainModule.STAT_SCREEN_HANDLER, syncId);
    }

    public StatScreenHandler(int i, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        this(i, packetByteBuf);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }

}
