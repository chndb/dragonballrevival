package com.chrispy.dragonballrevival;

import com.chrispy.dragonballrevival.common.util.Constants;
import com.chrispy.dragonballrevival.mixin.InGameHudMixin;
import com.chrispy.dragonballrevival.mixin.MinecraftClientAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static com.chrispy.dragonballrevival.MainModule.STAT_SCREEN_HANDLER;

public class DragonballrevivalClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(STAT_SCREEN_HANDLER, StatScreen::new);
        KeyBinding MeditationKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.dragonballrevival.meditation", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_P, // The keycode of the key
                "key.category.dragonballrevival" // The translation key of the keybinding's category.
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (MeditationKeybind.wasPressed()) {
                ClientPlayNetworking.send(Constants.STAT_PACKET_ID, PacketByteBufs.empty());
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(Constants.VITALITY_REQUEST, (client, handler, buf, responseSender) -> {
            int bufPass = buf.readInt();
            client.execute(() -> {
                ((InGameHudExt) (((MinecraftClientAccessor) client).getInGameHud())).requestSetVitality(bufPass);
            });
        });
    }
}
