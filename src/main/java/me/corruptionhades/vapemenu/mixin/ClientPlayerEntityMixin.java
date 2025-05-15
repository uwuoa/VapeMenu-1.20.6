package me.corruptionhades.vapemenu.mixins;

import com.mojang.authlib.GameProfile;
import me.corruptionhades.vapemenu.VapeMenu;
import me.corruptionhades.vapemenu.command.Command;
import me.corruptionhades.vapemenu.command.CommandManager;
import me.corruptionhades.vapemenu.event.impl.ChatMessageSentEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends net.minecraft.client.network.AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    public void sendChatMessage(String msg, CallbackInfo ci) {
        if (msg.startsWith(VapeMenu.getInstance().getCommandPrefix())) {
            String[] args = msg.substring(1).split(" ");
            for (Command command : CommandManager.INSTANCE.getCmds()) {
                if (args[0].equalsIgnoreCase(command.getName())) {
                    command.onCmd(msg, args);
                    ci.cancel();
                    return;
                }
            }
        }

        ChatMessageSentEvent chatMessageSentEvent = new ChatMessageSentEvent(msg);
        if (chatMessageSentEvent.isCancelled()) {
            ci.cancel();
        }
    }
}
