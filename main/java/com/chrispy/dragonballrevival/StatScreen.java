package com.chrispy.dragonballrevival;

import com.chrispy.dragonballrevival.common.util.Constants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


@Environment(EnvType.CLIENT)
public class StatScreen extends HandledScreen<StatScreenHandler> {
    private static final Identifier BAR = new Identifier(Constants.MOD_ID, "textures/gui/bar.png");
    private static final Identifier TEXTURE = new Identifier(Constants.MOD_ID, "textures/gui/cloud.png");
    private static final Identifier BG = new Identifier(Constants.MOD_ID, "textures/gui/meditationbg.png");
    private ScreenHandler handler;
    int xInitial /*192*/ ;
    int yInitial /*88*/;

    //region Vitality
    private int VitalityXP;
    private int Vitality;
    private int VitalityNextLevelXP;
    //endregion
    //region Tenacity
    private int TenacityXP;
    private int Tenacity;
    private int TenacityNextLevelXP;
    //endregion
    //region Resilience
    private int ResilienceXP;
    private int Resilience;
    private int ResilienceNextLevelXP;
    //endregion
    //region Passion
    private int PassionXP = 0;
    private int Passion = 0;
    //endregion
    //region Determination
    private int DeterminationXP = 0;
    private int Determination = 0;
    private int DeterminationNextLevelXP;
    //endregion
    public StatScreen(StatScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, Text.of(""));
        this.handler = handler;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BG);
        drawTexture(matrices, 0, 0, 0, 0, MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight(),512,512);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (this.width - 384) / 2;
        int j = (this.height - 384) / 2;
        int topStat = Math.max(Vitality,Math.max(Tenacity,Math.max(Resilience,Math.max(Passion,Determination))));
        drawTexture(matrices, i, j, 0, 0, 384, 384,384, 384);
        textRenderer.draw(matrices, Vitality + " Vitality", xInitial + (Integer.toString(topStat).length() - Integer.toString(Vitality).length())*6, yInitial, 8);
        textRenderer.draw(matrices, Tenacity + " Tenacity", xInitial + (Integer.toString(topStat).length() - Integer.toString(Tenacity).length())*6, yInitial + 18, 8);
        textRenderer.draw(matrices, Resilience + " Resilience", xInitial + (Integer.toString(topStat).length() - Integer.toString(Resilience).length())*6, yInitial + 36, 8);
        textRenderer.draw(matrices, Passion + " Passion", xInitial+ (Integer.toString(topStat).length() - Integer.toString(Passion).length())*6, yInitial + 54, 8);
        textRenderer.draw(matrices, Determination + " Determination", xInitial + (Integer.toString(topStat).length() - Integer.toString(Determination).length())*6, yInitial + 72, 8);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 3.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BAR);
        drawTexture(matrices, xInitial, yInitial + 9, 0, 0, 80, 5,80, 10);
        drawTexture(matrices, xInitial, yInitial + 9, 0, 5, (int) Math.floor(80*VitalityXP/VitalityNextLevelXP), 5,80, 10);
        drawTexture(matrices, xInitial, yInitial + 18 + 9, 0, 0, 80, 5,80, 10);
        drawTexture(matrices, xInitial, yInitial + 18 + 9, 0, 5, (int) Math.floor(80*TenacityXP/TenacityNextLevelXP), 5,80, 10);
        drawTexture(matrices, xInitial, yInitial + 36 + 9, 0, 0, 80, 5,80, 10);
        drawTexture(matrices, xInitial, yInitial + 36 + 9, 0, 5, (int) Math.floor(80*ResilienceXP/ResilienceNextLevelXP), 5,80, 10);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        xInitial = MinecraftClient.getInstance().getWindow().getScaledWidth()/2 - 40;
        yInitial = MinecraftClient.getInstance().getWindow().getScaledHeight()/2 - 30;
        drawBackground(matrices, delta, mouseX, mouseY);
        if(xInitial + 80 > mouseX && mouseX > xInitial && yInitial + 9 + 5 > mouseY && mouseY > yInitial + 9) {renderTooltip(matrices, Text.of(VitalityXP + "/" + VitalityNextLevelXP), mouseX,mouseY);}
        if(xInitial + 80 > mouseX && mouseX > xInitial && yInitial + 18 + 9 + 5 > mouseY && mouseY > yInitial + 18 + 9) {renderTooltip(matrices, Text.of(TenacityXP + "/" + TenacityNextLevelXP), mouseX,mouseY);}
        if(xInitial + 80 > mouseX && mouseX > xInitial && yInitial + 36 + 9 + 5 > mouseY && mouseY > yInitial + 36 + 9) {renderTooltip(matrices, Text.of(ResilienceXP + "/" + ResilienceNextLevelXP), mouseX,mouseY);}
    }

    protected void init() {
        super.init();
        TenacityXP = ((StatScreenHandler) handler).getHandlerTenacityXP();
        Tenacity = ((StatScreenHandler) handler).getHandlerTenacity();
        TenacityNextLevelXP = ((StatScreenHandler) handler).getHandlerTenacityNextLevelXP();
        VitalityXP = ((StatScreenHandler) handler).getHandlerVitalityXP();
        Vitality = ((StatScreenHandler) handler).getHandlerVitality();
        VitalityNextLevelXP = ((StatScreenHandler) handler).getHandlerVitalityNextLevelXP();
        ResilienceXP = ((StatScreenHandler) handler).getHandlerResilienceXP();
        Resilience = ((StatScreenHandler) handler).getHandlerResilience();
        ResilienceNextLevelXP = ((StatScreenHandler) handler).getHandlerResilienceNextLevelXP();
    }
}
