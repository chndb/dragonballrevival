package com.chrispy.dragonballrevival.mixin;

import com.chrispy.dragonballrevival.PlayerEntityExt;
import com.chrispy.dragonballrevival.common.util.Constants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin{
    @Inject(method = "heal", at = @At("RETURN"))
    private void heal(float amount, CallbackInfo ci) {
        double PVPBonus = (false) ? Constants.Multipliers.PvpTrainingBonus : 1.0;
        LivingEntity cast = (LivingEntity) (Object) this;
        if(cast instanceof PlayerEntity) {
            PlayerEntityExt playerExt = (PlayerEntityExt) this;
            if (((int) (playerExt.getVitality() * Constants.Multipliers.VitalityFactor) + 20)!=((int) cast.getHealth())) {
                if ((((int) cast.getMaxHealth()) - ((int) cast.getHealth())) > amount) {
                    playerExt.addVitalityXP((int) (Math.ceil(amount * PVPBonus * playerExt.getTrainingMult())));
                } else {
                    playerExt.addVitalityXP((int) Math.ceil((((playerExt.getVitality() * Constants.Multipliers.VitalityFactor) + 20) - ((int) cast.getHealth())) * PVPBonus * playerExt.getTrainingMult()));
                }
            }
        }
    }
}
