package rekteiru.hotshirtlessmen.mixin;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ChatComponentText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import com.mojang.authlib.GameProfile;

import rekteiru.hotshirtlessmen.Bow;
import rekteiru.hotshirtlessmen.main;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer {

    @Shadow
    private ItemStack itemInUse;

    @Shadow
    public InventoryPlayer inventory;

    @Final
    @Shadow
    private GameProfile gameProfile;

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void bowFixStuff(CallbackInfo ci){
        try {
            if (itemInUse != null && inventory != null && gameProfile != null && main.mc.thePlayer != null){
                if (gameProfile == main.mc.thePlayer.getGameProfile()) {

                    ItemStack itemstack = inventory.getCurrentItem();

                    if (Bow.BowFix(itemInUse, itemstack)) {
                        itemInUse = itemstack;
                    }
                }
            }
        } catch (NullPointerException npe) {
            try {
                // main.mc.thePlayer.addChatMessage(new ChatComponentText("NPE prevented: "+npe));
                System.out.println(npe);
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
