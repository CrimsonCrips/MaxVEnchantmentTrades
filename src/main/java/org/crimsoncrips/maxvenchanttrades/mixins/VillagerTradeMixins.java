package org.crimsoncrips.maxvenchanttrades.mixins;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Collectors;


@Mixin(targets = "net.minecraft.world.entity.npc.VillagerTrades$EnchantBookForEmeralds")

public class VillagerTradeMixins implements VillagerTrades.ItemListing {

    @Shadow @Final private int villagerXp;


    @Nullable
    @Override
    public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
        List<Enchantment> $$2 = (List) BuiltInRegistries.ENCHANTMENT.stream().filter(Enchantment::isTradeable).collect(Collectors.toList());
        Enchantment $$3 = (Enchantment)$$2.get(randomSource.nextInt($$2.size()));
        int $$4 = $$3.getMaxLevel();
        ItemStack $$5 = EnchantedBookItem.createForEnchantment(new EnchantmentInstance($$3, $$4));
        int $$6 = 2 + randomSource.nextInt(5 + $$4 * 10) + 3 * $$4;
        if ($$3.isTreasureOnly()) {
            $$6 *= 2;
        }

        if ($$6 > 64) {
            $$6 = 64;
        }

        return new MerchantOffer(new ItemStack(Items.EMERALD, $$6), new ItemStack(Items.BOOK), $$5, 12, this.villagerXp, 0.2F);

    }
}
