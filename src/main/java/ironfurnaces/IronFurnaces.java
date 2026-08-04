/*
 * Copyright 2025 Astryxion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ironfurnaces;

import com.mojang.logging.LogUtils;
import ironfurnaces.init.AtmRecipeSupport;
import ironfurnaces.init.ClientSetup;
import ironfurnaces.init.ModSetup;
import ironfurnaces.init.Registration;
import ironfurnaces.network.Messages;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import ironfurnaces.util.EventHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(IronFurnaces.MOD_ID)
public class IronFurnaces {

    public static final String MOD_ID = "ironfurnaces";

    public static final Logger LOGGER = LogUtils.getLogger();
    private static final Identifier SOUL_LAVA_ID = Identifier.fromNamespaceAndPath("allthemodium", "soul_lava");
    private static final Identifier SOUL_LAVA_BUCKET_ID = Identifier.fromNamespaceAndPath("allthemodium", "soul_lava_bucket");

    public IronFurnaces(IEventBus modEventBus, ModContainer modContainer) {

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        modEventBus.addListener(ModSetup::init);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(ClientSetup::init);
        modEventBus.register(Messages.class);

        ironfurnaces.init.Registration.init(modEventBus);

        NeoForge.EVENT_BUS.addListener(EventHandler::explosionEvent);
        NeoForge.EVENT_BUS.addListener(AtmRecipeSupport::onAddServerReloadListeners);
        NeoForge.EVENT_BUS.addListener(AtmRecipeSupport::onServerStarted);
        NeoForge.EVENT_BUS.addListener(AtmRecipeSupport::onServerStopping);
        NeoForge.EVENT_BUS.addListener(this::onRightClickBlock);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.HEATER_TILE.get(), WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Energy.BLOCK, ironfurnaces.init.Registration.HEATER_TILE.get(),
                (be, side) -> ((BlockWirelessEnergyHeaterTile) be).energyStorage);

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.IRON_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.IRON_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.GOLD_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.GOLD_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.DIAMOND_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.DIAMOND_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.EMERALD_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.EMERALD_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.OBSIDIAN_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.OBSIDIAN_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.CRYSTAL_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.CRYSTAL_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.NETHERITE_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.NETHERITE_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.COPPER_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.COPPER_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.SILVER_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.SILVER_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.ALLTHEMODIUM_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.ALLTHEMODIUM_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.VIBRANIUM_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.VIBRANIUM_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.UNOBTAINIUM_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.UNOBTAINIUM_FURNACE_TILE.get());

        event.registerBlockEntity(Capabilities.Item.BLOCK, ironfurnaces.init.Registration.MILLION_FURNACE_TILE.get(), WorldlyContainerWrapper::new);
        registerFurnaceCapabilities(event, Registration.MILLION_FURNACE_TILE.get());
    }

    private static void registerFurnaceCapabilities(RegisterCapabilitiesEvent event, net.minecraft.world.level.block.entity.BlockEntityType<?> type) {
        event.registerBlockEntity(Capabilities.Energy.BLOCK, type, (be, side) -> ((BlockIronFurnaceTileBase) be).energyStorage);
        event.registerBlockEntity(Capabilities.Fluid.BLOCK, type, (be, side) -> {
            BlockIronFurnaceTileBase furnace = (BlockIronFurnaceTileBase) be;
            return furnace.isGenerator() ? furnace.getFluidStorage() : null;
        });
    }

    private void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getLevel().getBlockEntity(event.getPos()) instanceof BlockIronFurnaceTileBase furnace)) {
            return;
        }
        if (!furnace.isGenerator()) {
            return;
        }

        ItemStack held = event.getItemStack();
        if (held.isEmpty()) {
            return;
        }

        boolean handled = false;
        if (held.is(Items.LAVA_BUCKET)) {
            handled = tryInsertBucket(furnace, FluidResource.of(Fluids.LAVA));
            if (handled && !event.getLevel().isClientSide()) {
                consumeAndGive(event.getEntity(), event.getHand(), new ItemStack(Items.BUCKET));
            }
        } else {
            var soulLava = BuiltInRegistries.FLUID.getOptional(SOUL_LAVA_ID).orElse(null);
            Item soulLavaBucket = BuiltInRegistries.ITEM.getOptional(SOUL_LAVA_BUCKET_ID).orElse(Items.AIR);
            if (soulLava != null && soulLavaBucket != Items.AIR && held.is(soulLavaBucket)) {
                handled = tryInsertBucket(furnace, FluidResource.of(soulLava));
                if (handled && !event.getLevel().isClientSide()) {
                    consumeAndGive(event.getEntity(), event.getHand(), new ItemStack(Items.BUCKET));
                }
            } else if (held.is(Items.BUCKET)) {
                handled = tryExtractBucket(furnace, event.getEntity(), event.getHand());
            }
        }

        if (handled) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    private static boolean tryInsertBucket(BlockIronFurnaceTileBase furnace, FluidResource fluid) {
        if (!furnace.isAllowedGeneratorFluid(fluid)) {
            return false;
        }
        try (Transaction tx = Transaction.openRoot()) {
            int inserted = furnace.getFluidStorage().insert(fluid, FluidType.BUCKET_VOLUME, tx);
            if (inserted == FluidType.BUCKET_VOLUME) {
                tx.commit();
                return true;
            }
        }
        return false;
    }

    private static boolean tryExtractBucket(BlockIronFurnaceTileBase furnace, Player player, InteractionHand hand) {
        FluidResource resource = furnace.getFluidStorage().getResource(0);
        if (resource.isEmpty()) {
            return false;
        }

        ItemStack resultBucket = ItemStack.EMPTY;
        if (resource.equals(FluidResource.of(Fluids.LAVA))) {
            resultBucket = new ItemStack(Items.LAVA_BUCKET);
        } else {
            var soulLava = BuiltInRegistries.FLUID.getOptional(SOUL_LAVA_ID).orElse(null);
            Item soulLavaBucket = BuiltInRegistries.ITEM.getOptional(SOUL_LAVA_BUCKET_ID).orElse(Items.AIR);
            if (soulLava != null && soulLavaBucket != Items.AIR && resource.equals(FluidResource.of(soulLava))) {
                resultBucket = new ItemStack(soulLavaBucket);
            }
        }
        if (resultBucket.isEmpty()) {
            return false;
        }

        try (Transaction tx = Transaction.openRoot()) {
            int extracted = furnace.getFluidStorage().extract(resource, FluidType.BUCKET_VOLUME, tx);
            if (extracted != FluidType.BUCKET_VOLUME) {
                return false;
            }
            tx.commit();
        }

        if (!player.level().isClientSide()) {
            consumeAndGive(player, hand, resultBucket);
        }
        return true;
    }

    private static void consumeAndGive(Player player, InteractionHand hand, ItemStack result) {
        if (player.getAbilities().instabuild) {
            return;
        }
        ItemStack inHand = player.getItemInHand(hand);
        if (inHand.getCount() == 1) {
            player.setItemInHand(hand, result);
            return;
        }
        inHand.shrink(1);
        if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }
    }
}
