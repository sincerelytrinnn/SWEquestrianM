package com.alaharranhonor.swem;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class SWLIG extends ItemGroup {
    @Nonnull
    private final Supplier<ItemStack> iconSupplier;

    public SWLIG(@Nonnull final String name, @Nonnull final Supplier<ItemStack> iconSupplier) {
        super(name);
        this.iconSupplier = iconSupplier;
    }

    @Override
    @Nonnull
    public ItemStack makeIcon() {
        return iconSupplier.get();
    }
}
