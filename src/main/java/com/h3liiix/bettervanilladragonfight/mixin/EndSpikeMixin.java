package com.h3liiix.bettervanilladragonfight.mixin;

import com.h3liiix.bettervanilladragonfight.ConfigManager;
import net.minecraft.world.level.levelgen.feature.EndSpikeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndSpikeFeature.EndSpike.class)
public abstract class EndSpikeMixin {

    @Inject(method = "isGuarded", at = @At("HEAD"), cancellable = true)
    private void bettervanilladragonfight_onIsGuarded(CallbackInfoReturnable<Boolean> cir) {
        if (ConfigManager.INSTANCE.getCageAllEndCrystals()) {
            cir.setReturnValue(true);
        }
    }

    @org.spongepowered.asm.mixin.injection.ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    private static int bettervanilladragonfight_modifyRadius(int originalRadius) {
        if (ConfigManager.INSTANCE.getDisableSmallTowers() && originalRadius <= 2) {
            return 3;
        }
        return originalRadius;
    }

    @org.spongepowered.asm.mixin.injection.ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 3, argsOnly = true)
    private static int bettervanilladragonfight_modifyHeight(int originalHeight) {
        if (ConfigManager.INSTANCE.getDisableSmallTowers() && originalHeight <= 82) {
            return originalHeight + 9;
        }
        return originalHeight;
    }
}

