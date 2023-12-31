package net.andrewyernau.tutorialmod;

import com.mojang.logging.LogUtils;
import net.andrewyernau.tutorialmod.block.ModBlocks;
import net.andrewyernau.tutorialmod.block.entity.ModBlockEntities;
import net.andrewyernau.tutorialmod.entity.ModEntityTypes;
import net.andrewyernau.tutorialmod.entity.client.GloomjawRenderer;
import net.andrewyernau.tutorialmod.fluid.ModFluids;
import net.andrewyernau.tutorialmod.fluid.ModFluidTypes;
import net.andrewyernau.tutorialmod.item.ModItems;
import net.andrewyernau.tutorialmod.networking.ModMessages;
import net.andrewyernau.tutorialmod.painting.ModPaintings;
import net.andrewyernau.tutorialmod.recipe.ModRecipes;
import net.andrewyernau.tutorialmod.screen.ModMenuTypes;
import net.andrewyernau.tutorialmod.screen.WizardTableMenu;
import net.andrewyernau.tutorialmod.screen.WizardTableScreen;
import net.andrewyernau.tutorialmod.villager.ModVillagers;
import net.andrewyernau.tutorialmod.world.feature.ModConfiguredFeatures;
import net.andrewyernau.tutorialmod.world.feature.ModPlacedFeatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(TutorialMod.MOD_ID)
public class TutorialMod {
    public static final String MOD_ID = "tutorialmod";

    private static final Logger LOGGER = LogUtils.getLogger();

    public TutorialMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModPaintings.register(modEventBus);
        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        GeckoLib.initialize();
        ModEntityTypes.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            ModMessages.register();
            ModVillagers.registerPOIs();
        });


    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_SOAP_WATER.get(),RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_SOAP_WATER.get(),RenderType.translucent());

            MenuScreens.register(ModMenuTypes.WIZARD_TABLE_MENU.get(), WizardTableScreen::new);
            EntityRenderers.register(ModEntityTypes.GLOOMJAW.get(), GloomjawRenderer::new);
        }
    }
}
