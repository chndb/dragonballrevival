package com.chrispy.dragonballrevival;

import com.chrispy.dragonballrevival.common.util.Constants;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.chrispy.dragonballrevival.common.util.Constants.MOD_ID;

public class MainModule implements ModInitializer{
    public static final Logger LOGGER = LoggerFactory.getLogger("dragonballrevival");
    public static final ExtendedScreenHandlerType<StatScreenHandler> STAT_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(MOD_ID,"stat_screen"), new ExtendedScreenHandlerType(StatScreenHandler::new));

    public static final Block MEDICAL_POD = new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final Block MEDICAL_POD_TOP = new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f));


    @Override
    public void onInitialize() {
        System.out.println("dragonballrevival successfully loaded");
        ServerPlayNetworking.registerGlobalReceiver(Constants.STAT_PACKET_ID, (server, playerEntity, handler, buf, packetSender) -> {
            server.execute(() -> {
                PlayerEntityExt player = (PlayerEntityExt) playerEntity;
                PacketByteBuf bufPass = new PacketByteBuf(Unpooled.buffer());
                int[] bufferArray = new int[]{player.getVitalityXP(),player.getVitality(),player.getVitalityNextLevelXP(),player.getTenacityXP(), player.getTenacity(), player.getTenacityNextLevelXP(), player.getResilienceXP(), player.getResilience(), player.getResilienceNextLevelXP(), 0, 0, 0, 0, 0, 0};
                bufPass.writeIntArray(bufferArray);
                playerEntity.openHandledScreen(new ExtendedScreenHandlerFactory() {
                    @Override
                    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
                        packetByteBuf.writeIntArray(bufferArray);
                    }

                    @Override
                    public Text getDisplayName() {
                        return Text.empty();
                    }

                    @Nullable
                    @Override
                    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                        return new StatScreenHandler(syncId, bufPass);
                    }
                });
            });
        });
        ServerPlayNetworking.registerGlobalReceiver(Constants.VITALITY_REQUEST, (server, playerEntity, handler, buf, packetSender) -> {
            server.execute(() -> {
                PacketByteBuf bufPass = new PacketByteBuf(Unpooled.buffer());
                int buffer = ((PlayerEntityExt) playerEntity).getVitality();
                bufPass.writeInt(buffer);
                ServerPlayNetworking.send(playerEntity, Constants.VITALITY_REQUEST, bufPass);
            });
        });
        Registry.register(Registry.BLOCK, new Identifier("dragonballrevival", "medical_pod"), MEDICAL_POD);
        Registry.register(Registry.BLOCK, new Identifier("dragonballrevival", "medical_pod_top"), MEDICAL_POD_TOP);
        Registry.register(Registry.ITEM, new Identifier("dragonballrevival", "medical_pod"), new BlockItem(MEDICAL_POD, new FabricItemSettings().group(ItemGroup.MISC)));
    }
}

