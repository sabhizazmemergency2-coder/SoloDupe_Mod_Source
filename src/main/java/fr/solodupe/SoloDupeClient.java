package fr.solodupe;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class SoloDupeClient implements ClientModInitializer {
    private static KeyBinding dupeKeyBinding;

    @Override
    public void onInitializeClient() {
        dupeKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.solodupe.open", 
            InputUtil.Type.KEYSYM, 
            GLFW.GLFW_KEY_F7, 
            "category.solodupe.utils"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (dupeKeyBinding.wasPressed()) {
                if (client.player != null) {
                    if (client.isInSingleplayer()) {
                        // Ouvre le GUI (Ceci nécessitera l'implémentation de la factory du Screen)
                        client.player.sendMessage(Text.literal("Ouverture du GUI (F7)"), true);
                        // client.setScreen(new DupeScreen(new DupeScreenHandler(client.player.currentScreenHandler.syncId, client.player.getInventory())));
                    } else {
                        client.player.sendMessage(Text.literal("§cCe mod ne fonctionne qu'en Solo !"), false);
                    }
                }
            }
        });
    }
}
