package me.corruptionhades.vapemenu.mixins;

import me.corruptionhades.vapemenu.event.impl.CommandSuggestEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onCommandSuggestions", at = @At("TAIL"))
    public void onCmdSuggest(CommandSuggestionsS2CPacket packet, CallbackInfo ci) {
        new CommandSuggestEvent(packet).call();
    }
}
