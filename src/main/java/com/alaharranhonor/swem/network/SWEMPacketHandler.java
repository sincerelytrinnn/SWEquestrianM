package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.network.jumps.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class SWEMPacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(SWEM.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void init() {
		INSTANCE.registerMessage(0, AddJumpXPMessage.class, AddJumpXPMessage::encode, AddJumpXPMessage::decode, AddJumpXPMessage::handle);
		INSTANCE.registerMessage(1, UpdateHorseInventoryMessage.class, UpdateHorseInventoryMessage::encode, UpdateHorseInventoryMessage::decode, UpdateHorseInventoryMessage::handle);
		INSTANCE.registerMessage(2, SyncEntityIdToClient.class, SyncEntityIdToClient::encode, SyncEntityIdToClient::decode, SyncEntityIdToClient::handle);
		INSTANCE.registerMessage(3, SendHorseSpeedChange.class, SendHorseSpeedChange::encode, SendHorseSpeedChange::decode, SendHorseSpeedChange::handle);
		INSTANCE.registerMessage(4, ClientStatusMessagePacket.class, ClientStatusMessagePacket::encode, ClientStatusMessagePacket::decode, ClientStatusMessagePacket::handle);
		INSTANCE.registerMessage(6, HorseStateChange.class, HorseStateChange::encode, HorseStateChange::decode, HorseStateChange::handle);
		INSTANCE.registerMessage(7, HorseHungerChange.class, HorseHungerChange::encode, HorseHungerChange::decode, HorseHungerChange::handle);
		INSTANCE.registerMessage(8, RenameItemPacket.class, RenameItemPacket::encode, RenameItemPacket::decode, RenameItemPacket::handle);
		INSTANCE.registerMessage(9, HorseFlyingMessage.class, HorseFlyingMessage::encode, HorseFlyingMessage::decode, HorseFlyingMessage::handle);
		INSTANCE.registerMessage(13, CAddLayerPacket.class, CAddLayerPacket::encode, CAddLayerPacket::decode, CAddLayerPacket::handle);
		INSTANCE.registerMessage(14, CChangeColorPacket.class, CChangeColorPacket::encode, CChangeColorPacket::decode, CChangeColorPacket::handle);
		INSTANCE.registerMessage(15, CChangeLayerPacket.class, CChangeLayerPacket::encode, CChangeLayerPacket::decode, CChangeLayerPacket::handle);
		INSTANCE.registerMessage(16, CChangeStandardPacket.class, CChangeStandardPacket::encode, CChangeStandardPacket::decode, CChangeStandardPacket::handle);
		INSTANCE.registerMessage(17, CDestroyPacket.class, CDestroyPacket::encode, CDestroyPacket::decode, CDestroyPacket::handle);
		INSTANCE.registerMessage(18, CRemoveLayerPacket.class, CRemoveLayerPacket::encode, CRemoveLayerPacket::decode, CRemoveLayerPacket::handle);
		INSTANCE.registerMessage(19, SDataSendPacket.class, SDataSendPacket::encode, SDataSendPacket::decode, SDataSendPacket::handle);
		INSTANCE.registerMessage(20, CMountEntityPacket.class, CMountEntityPacket::encode, CMountEntityPacket::decode, CMountEntityPacket::handle);
		INSTANCE.registerMessage(21, SHorseFriendPacket.class, SHorseFriendPacket::encode, SHorseFriendPacket::decode, SHorseFriendPacket::handle);
		INSTANCE.registerMessage(22, CHorseJumpPacket.class, CHorseJumpPacket::encode, CHorseJumpPacket::decode, CHorseJumpPacket::handle);
		INSTANCE.registerMessage(23, SHorseJumpPacket.class, SHorseJumpPacket::encode, SHorseJumpPacket::decode, SHorseJumpPacket::handle);
	}
}
