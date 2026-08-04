package ironfurnaces;

import ironfurnaces.init.ClientSetup;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraftforge.fml.ModBusHolder;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/** Client entrypoint: registers screen handlers once the Minecraft client is ready (Forge-equivalent timing). */
public final class IronFurnacesClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    FMLJavaModLoadingContext.get()
        .getModEventBus()
        .addListener(FMLClientSetupEvent.class, ClientSetup::init);
    ClientLifecycleEvents.CLIENT_STARTED.register(
        client -> ModBusHolder.BUS.post(new FMLClientSetupEvent()));
  }
}
