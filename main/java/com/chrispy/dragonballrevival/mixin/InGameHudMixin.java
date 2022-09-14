package com.chrispy.dragonballrevival.mixin;

import com.chrispy.dragonballrevival.InGameHudExt;
import com.chrispy.dragonballrevival.common.util.Constants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper implements InGameHudExt {
    private static final Identifier HEALTH_BAR = new Identifier(Constants.MOD_ID, "textures/gui/healthbar.png");

    private int vitality;

    @Shadow
    private int scaledHeight;
    @Shadow
    public native TextRenderer getTextRenderer();

    @ModifyVariable(method = "renderStatusBars", at = @At("STORE"), name = "s")
    private int modifyS(int s) {
        return s = this.scaledHeight-56;
    }

    @Overwrite
    public void renderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
        int vTemp = requestVitality();
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((int)(vTemp* Constants.Multipliers.VitalityFactor) + 20);
        int currentHealth = (int) Math.ceil(player.getHealth());
        int maximumHealth = (int) Math.floor(vTemp*Constants.Multipliers.VitalityFactor) + 20;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, HEALTH_BAR);
        drawTexture(matrices, x, y-7, 0, 0, 81, 16,81, 32);
        int percent = 4 + (72*currentHealth/ maximumHealth);
        drawTexture(matrices, x+1, y-7, 0, 16, percent, 16,81, 32);
        this.getTextRenderer().draw(matrices, currentHealth + "/" + maximumHealth, x+24+6*(Integer.toString(maximumHealth).length() - Integer.toString(currentHealth).length()), y-2, -1);
        RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
    }

    public int requestVitality() {
        ClientPlayNetworking.send(Constants.VITALITY_REQUEST, PacketByteBufs.empty());
        return vitality;
    }

    public void requestSetVitality(int i) {
        vitality = i;
    }
}
