package com.terriblefriends.booktrolling.mixins.tooltips;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BannerItem.class)
public class BannerItemMixin extends Item {
    public BannerItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(at=@At("HEAD"),method="appendTooltip")
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        super.appendTooltip(stack, world, tooltip, context);
    }
}
