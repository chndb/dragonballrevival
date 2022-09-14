package com.chrispy.dragonballrevival.mixin;

import com.chrispy.dragonballrevival.common.util.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.player.PlayerEntity;
import com.chrispy.dragonballrevival.PlayerEntityExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTracker.class)
public abstract class DamageTrackerMixin {
    @Shadow
    public native LivingEntity getEntity();

    @Inject(method = "onDamage", at = @At("HEAD"))
    public void onDamage(DamageSource damageSource, float originalHealth, float damage, CallbackInfo ci) {
        Entity attacker = damageSource.getAttacker();
        if(attacker instanceof PlayerEntity && !(getEntity() instanceof PlayerEntity)) {
            PlayerEntityExt attackerExt = (PlayerEntityExt) attacker;
            if(originalHealth > damage)
                attackerExt.addTenacityXP((int)(Math.ceil(damage * attackerExt.getTrainingMult())));
            else
                attackerExt.addTenacityXP((int)(Math.ceil(originalHealth * attackerExt.getTrainingMult())));
        }

    }
}