package com.teamtea.eclipticseasons.common.network.message;


import com.teamtea.eclipticseasons.EclipticSeasons;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

public class ChunkUpdateMessage implements CustomPacketPayload {
    public final byte[] snowyArea;
    public final int x;
    public final int z;
    public final List<Integer> y;

    public ChunkUpdateMessage(byte[] snowyArea, int x, int z, List<Integer> y) {
        this.snowyArea = snowyArea;
        this.x = x;
        this.z = z;
        this.y = y;
    }

    public static final Type<ChunkUpdateMessage> TYPE = new Type<>(EclipticSeasons.rl("chunk_snow"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, ChunkUpdateMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.byteArray(256),
            chunkUpdateMessage -> chunkUpdateMessage.snowyArea,
            ByteBufCodecs.VAR_INT,
            chunkUpdateMessage -> chunkUpdateMessage.x,
            ByteBufCodecs.VAR_INT,
            chunkUpdateMessage -> chunkUpdateMessage.z,
            MessageCodec.intlistStreamCodec,
            chunkUpdateMessage -> chunkUpdateMessage.y,
            ChunkUpdateMessage::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
