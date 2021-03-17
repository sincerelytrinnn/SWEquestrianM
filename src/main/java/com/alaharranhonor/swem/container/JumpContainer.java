package com.alaharranhonor.swem.container;

import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.jumps.SDataSendPacket;
import com.alaharranhonor.swem.tileentity.JumpTE;
import com.alaharranhonor.swem.tileentity.LockerTE;
import com.alaharranhonor.swem.util.initialization.SWEMContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JumpContainer extends Container {

	public final JumpTE controller;
	public ServerPlayerEntity player;

	public int layerAmount;
	public Map<Integer, JumpLayer> layerTypes = new HashMap<>();
	public Map<Integer, Integer> layerColors = new HashMap<>();
	public StandardLayer currentStandard;


	public JumpContainer(final int id, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(id, playerInventory, getTileEntity(playerInventory, data));
	}

	public JumpContainer(int id, final PlayerInventory playerInventory, final JumpTE controller) {
		super(SWEMContainers.JUMP_CONTAINER.get(), id);
		this.controller = controller;
		if (!playerInventory.player.world.isRemote) {
			this.player = (ServerPlayerEntity) playerInventory.player;
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

	private static JumpTE getTileEntity(final PlayerInventory inventory, final PacketBuffer data) {
		if (inventory.player.world.isRemote) return null;
		Objects.requireNonNull(inventory, "Inventory cannot be null");
		Objects.requireNonNull(data, "Packet Data cannot be null");
		TileEntity tileAtPos = inventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof JumpTE) {
			return (JumpTE) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct." + tileAtPos);
	}

	@Override
	public void detectAndSendChanges() {
		if (player == null) return;
		if (player.world.isRemote) return;

		if (layerAmount != controller.getLayerAmount()) {
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> this.player), new SDataSendPacket(controller.getPos(), controller.layerAmount, controller.layerTypes, controller.layerColors, controller.currentStandard));
			layerAmount = controller.layerAmount;
		}

		for (Map.Entry<Integer, JumpLayer> entry : controller.layerTypes.entrySet()) {
			if (!layerTypes.entrySet().contains(entry)) {
				SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> this.player), new SDataSendPacket(controller.getPos(), controller.layerAmount, controller.layerTypes, controller.layerColors, controller.currentStandard));
				layerTypes.put(entry.getKey(), entry.getValue());
			}
		}

		for (Map.Entry<Integer, Integer> entry : controller.layerColors.entrySet()) {
			if (!layerColors.entrySet().contains(entry)) {
				SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> this.player), new SDataSendPacket(controller.getPos(), controller.layerAmount, controller.layerTypes, controller.layerColors, controller.currentStandard));
				layerColors.put(entry.getKey(), entry.getValue());
			}
		}

		if (currentStandard != controller.getCurrentStandard()) {
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> this.player), new SDataSendPacket(controller.getPos(), controller.layerAmount, controller.layerTypes, controller.layerColors, controller.currentStandard));
			currentStandard = controller.getCurrentStandard();
		}
	}
}
