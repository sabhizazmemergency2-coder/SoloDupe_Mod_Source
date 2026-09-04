package fr.solodupe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SoloDupeMod implements ModInitializer {
    public static final String MOD_ID = "solodupe";
    public static final Identifier DUPE_PACKET_ID = new Identifier(MOD_ID, "dupe_request");

    @Override
    public void onInitialize() {
        // Enregistrement de la réception du packet sur le serveur intégré
        ServerPlayNetworking.registerGlobalReceiver(DUPE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            int amount = buf.readInt();
            
            server.execute(() -> {
                // Logique de duplication (sécurisée côté serveur)
                if (player.currentScreenHandler instanceof DupeScreenHandler) {
                    DupeScreenHandler dupeHandler = (DupeScreenHandler) player.currentScreenHandler;
                    ItemStack inputStack = dupeHandler.getSlot(0).getStack();
                    
                    if (!inputStack.isEmpty()) {
                        // Exemple de coût : 1 niveau d'XP par item
                        int cost = amount; 
                        
                        if (player.experienceLevel >= cost || player.isCreative()) {
                            if (!player.isCreative()) {
                                player.addExperienceLevels(-cost);
                            }
                            
                            ItemStack outputStack = inputStack.copy();
                            outputStack.setCount(amount);
                            dupeHandler.getSlot(1).setStack(outputStack);
                        }
                    }
                }
            });
        });
    }
}
