package com.chrispy.dragonballrevival.mixin;

import com.chrispy.dragonballrevival.PlayerEntityExt;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin{

    @Inject(method = "copyFrom", at = @At("RETURN"))
    private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        PlayerEntityExt oldExt = (PlayerEntityExt) oldPlayer;
        PlayerEntityExt thisExt = (PlayerEntityExt) this;
        thisExt.setVitalityXP(oldExt.getVitalityXP());
        thisExt.setVitality(oldExt.getVitality());
        thisExt.updateVitality();
        thisExt.setTenacityXP(oldExt.getTenacityXP());
        thisExt.setTenacity(oldExt.getTenacity());
        thisExt.updateTenacity();
        thisExt.setResilienceXP(oldExt.getResilienceXP());
        thisExt.setResilience(oldExt.getResilience());
        thisExt.updateResilience();

    }
}
