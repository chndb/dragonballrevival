package com.chrispy.dragonballrevival.mixin;

import com.chrispy.dragonballrevival.common.util.Constants;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import com.chrispy.dragonballrevival.PlayerEntityExt;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityExt, ExtendedScreenHandlerFactory {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }


    //region Training Multipliers
    double TrainingMult = 1.0;

    public double getTrainingMult() {
        return TrainingMult;
    }

    //endregion

    //region Vitality
    private int VitalityXP;
    private int Vitality;
    public void addVitalityXP(int amount) {
        VitalityXP += amount;
        updateVitality();
    }
    public void setVitalityXP(int amount) {
        VitalityXP = amount;
        updateVitality();
    }

    public int getVitalityXP() {
        return VitalityXP;
    }
    public void addVitality(int amount) {
        Vitality += amount;
        updateVitality();
    }
    public void setVitality(int amount) {
        Vitality = amount;
        updateVitality();
    }
    public int getVitality() {
        return Vitality;
    }

    public int getVitalityNextLevelXP() {
        if (Vitality < 16) {
            return 4*Vitality + 14;
        } else if (Vitality < 31) {
            return 25*Vitality - 86;
        } else {
            return 81*Vitality- 316;
        }
    }
    public void updateVitality() {
        while(VitalityXP >= getVitalityNextLevelXP()) {
            VitalityXP = VitalityXP - getVitalityNextLevelXP();
            Vitality += 1;
        }
        while(VitalityXP < 0) {
            Vitality -= 1;
            VitalityXP = VitalityXP + getTenacityNextLevelXP();
        }
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((int) Math.floor(getVitality() * Constants.Multipliers.VitalityFactor) + 20);
    }
    //endregion
    //region Tenacity
    private int TenacityXP;
    private int Tenacity;
    public void addTenacityXP(int amount) {
        TenacityXP += amount;
        updateTenacity();
    }
    public void setTenacityXP(int amount) {
        TenacityXP = amount;
        updateTenacity();
    }

    public int getTenacityXP() {
        return TenacityXP;
    }
    public void addTenacity(int amount) {
        Tenacity += amount;
        updateTenacity();
    }
    public void setTenacity(int amount) {
        Tenacity = amount;
        updateTenacity();
    }
    public int getTenacity() {
        return Tenacity;
    }

    public int getTenacityNextLevelXP() {
        if (Tenacity < 16) {
            return 4*Tenacity + 14;
        } else if (Tenacity < 31) {
            return 25*Tenacity - 86;
        } else {
            return 81*Tenacity- 316;
        }
    }
    public void updateTenacity() {
        while(TenacityXP >= getTenacityNextLevelXP()) {
            TenacityXP = TenacityXP - getTenacityNextLevelXP();
            Tenacity += 1;
        }
        while(TenacityXP < 0) {
            Tenacity -= 1;
            TenacityXP = TenacityXP + getTenacityNextLevelXP();
        }
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((int) Math.floor(getTenacity() * Constants.Multipliers.TenacityFactor) + 1);
    }
    //endregion
    //region Resilience
    private int ResilienceXP;
    private int Resilience;
    public void addResilienceXP(int amount) {
        ResilienceXP += amount;
        updateResilience();
    }
    public void setResilienceXP(int amount) {
        ResilienceXP = amount;
        updateResilience();
    }

    public int getResilienceXP() {
        return ResilienceXP;
    }
    public void addResilience(int amount) {
        Resilience += amount;
        updateResilience();
    }
    public void setResilience(int amount) {
        Resilience = amount;
        updateResilience();
    }
    public int getResilience() {
        return Resilience;
    }

    public int getResilienceNextLevelXP() {
        if (Resilience < 16) {
            return 4*Resilience + 14;
        } else if (Resilience < 31) {
            return 25*Resilience - 86;
        } else {
            return 81*Resilience- 316;
        }
    }
    public void updateResilience() {
        if(ResilienceXP >= getResilienceNextLevelXP()) {
            ResilienceXP = ResilienceXP - getResilienceNextLevelXP();
            Resilience += 1;
        }
        else if(ResilienceXP < 0) {
            Resilience -= 1;
            ResilienceXP = ResilienceXP + getResilienceNextLevelXP();
        }
    }
    //endregion
    //region Passion

    //endregion
    //region Determination

    //endregion

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void writeCustomDataToNbt(NbtCompound tag, CallbackInfo ci) {
        tag.putInt("TenacityXP", this.TenacityXP);
        tag.putInt("Tenacity", this.Tenacity);
        tag.putInt("VitalityXP", this.VitalityXP);
        tag.putInt("Vitality", this.Vitality);
        tag.putInt("ResilienceXP", this.ResilienceXP);
        tag.putInt("Resilience", this.Resilience);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    public void readCustomDataFromNbt(NbtCompound tag, CallbackInfo ci) {
        TenacityXP = tag.getInt("TenacityXP");
        Tenacity = tag.getInt("Tenacity");
        updateTenacity();
        VitalityXP = tag.getInt("VitalityXP");
        Vitality = tag.getInt("Vitality");
        updateVitality();
        ResilienceXP = tag.getInt("ResilienceXP");
        Resilience = tag.getInt("Resilience");
    }

    @ModifyVariable(method = "applyDamage", at = @At("HEAD"), argsOnly = true)
    protected float applyDamage(float amount, DamageSource source) {
        Entity attacker = source.getAttacker();
        boolean applyResilience = !source.bypassesArmor();
        float health = this.getHealth();
        int resilienceReduction = (int) Math.floor(this.getResilience() * Constants.Multipliers.ResilienceFactor);
        double PVPBonus = 1.0;
        if(attacker instanceof PlayerEntity){
            PlayerEntityExt attackerExt = (PlayerEntityExt) attacker;
            PVPBonus = Constants.Multipliers.PvpTrainingBonus;
            if(health > amount || (applyResilience && (health + resilienceReduction)  > amount))
                attackerExt.addTenacityXP((int)(Math.ceil(amount * PVPBonus * attackerExt.getTrainingMult())));
            else if (applyResilience){
                attackerExt.addTenacityXP((int) Math.ceil((health + resilienceReduction) * PVPBonus * attackerExt.getTrainingMult()));
            }
            else {
                attackerExt.addTenacityXP((int) Math.ceil(health * PVPBonus * attackerExt.getTrainingMult()));
            }
        }

        if (applyResilience) {
            this.addResilienceXP((int) Math.ceil(Math.min(amount,health+resilienceReduction) * PVPBonus * this.getTrainingMult()));
            return amount = amount - (float) Math.max(resilienceReduction, 0);
        }
        this.addResilienceXP((int) Math.ceil(Math.min(amount,health) * PVPBonus * this.getTrainingMult()));
        return amount = amount;
    }


}
