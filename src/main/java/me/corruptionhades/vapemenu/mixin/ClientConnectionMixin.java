package me.corruptionhades.vapemenu.mixins;

import me.corruptionhades.vapemenu.event.impl.PacketSendEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void onSend(Packet<?> packet, CallbackInfo ci) {
        PacketSendEvent pse = new PacketSendEvent(packet);
        if (pse.isCancelled()) ci.cancel();
    }
}
