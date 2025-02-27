package com.terriblefriends.booktrolling.mixins;

import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtTagSizeTracker;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

@Mixin(PacketByteBuf.class)
public class PacketByteBufMixin {
    PacketByteBuf PBB_instance = (PacketByteBuf) (Object) this;

    @Inject(at=@At("HEAD"),method="readItemStack",cancellable = true)
    private void noNbtLimits(CallbackInfoReturnable<ItemStack> cir) {
        if (!PBB_instance.readBoolean()) {
            cir.setReturnValue(ItemStack.EMPTY);
        } else {
            ItemStack itemStack = new ItemStack(PBB_instance.readRegistryValue(Registry.ITEM), PBB_instance.readByte());
            ByteBufInputStream BBIS;
            int readerIndex = PBB_instance.readerIndex();
            byte b = PBB_instance.readByte();
            if (b != 0) {
                PBB_instance.readerIndex(readerIndex);
                try {
                    BBIS = new ByteBufInputStream(PBB_instance);
                    itemStack.setNbt(NbtIo.read(BBIS, NbtTagSizeTracker.EMPTY));

                } catch (IOException var5) {
                    throw new EncoderException(var5);
                }
            }

            cir.setReturnValue(itemStack);
        }
        if (cir.isCancellable()) cir.cancel();
    }
}
