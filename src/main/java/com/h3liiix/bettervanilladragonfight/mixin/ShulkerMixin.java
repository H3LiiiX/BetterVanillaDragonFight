package com.h3liiix.bettervanilladragonfight.mixin;

import com.h3liiix.bettervanilladragonfight.TowerShulker;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Shulker.class)
public abstract class ShulkerMixin implements TowerShulker {

    @Unique
    private boolean betterVanillaDragonFight$isTowerShulker = false;

    @Override
    public boolean betterVanillaDragonFight$isTowerShulker() {
        return this.betterVanillaDragonFight$isTowerShulker;
    }

    @Override
    public void betterVanillaDragonFight$setTowerShulker(boolean isTowerShulker) {
        this.betterVanillaDragonFight$isTowerShulker = isTowerShulker;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void bettervanilladragonfight_onAddAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
        output.putBoolean("BetterVanillaDragonFight_IsTowerShulker", this.betterVanillaDragonFight$isTowerShulker);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void bettervanilladragonfight_onReadAdditionalSaveData(ValueInput input, CallbackInfo ci) {
        this.betterVanillaDragonFight$isTowerShulker = input.getBooleanOr("BetterVanillaDragonFight_IsTowerShulker", false);
    }

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void bettervanilladragonfight_onHurtServer(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.betterVanillaDragonFight$isTowerShulker) {
            if (source.getEntity() instanceof EnderDragon || source.is(DamageTypes.DRAGON_BREATH)) {
                cir.setReturnValue(false);
            }
        }
    }
}
