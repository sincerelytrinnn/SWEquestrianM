package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
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
	}
}
