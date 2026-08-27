package com.h3liiix.bettervanilladragonfight.mixin;

import com.h3liiix.bettervanilladragonfight.ConfigManager;
import com.h3liiix.bettervanilladragonfight.NaturalCrystalAccessor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin implements NaturalCrystalAccessor {

    @Unique
    private boolean betterVanillaDragonFight$isNatural = false;

    @Override
    public boolean betterVanillaDragonFight$isNatural() {
        return this.betterVanillaDragonFight$isNatural;
    }

    @Override
    public void betterVanillaDragonFight$setNatural(boolean natural) {
        this.betterVanillaDragonFight$isNatural = natural;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void bettervanilladragonfight_onAddAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
        output.putBoolean("BetterVanillaDragonFight_IsNatural", this.betterVanillaDragonFight$isNatural);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void bettervanilladragonfight_onReadAdditionalSaveData(ValueInput input, CallbackInfo ci) {
        this.betterVanillaDragonFight$isNatural = input.getBooleanOr("BetterVanillaDragonFight_IsNatural", false);
    }


    @Inject(method = "tick", at = @At("TAIL"))
    private void bettervanilladragonfight_onTick(CallbackInfo ci) {
        EndCrystal crystal = (EndCrystal) (Object) this;
        
        if (!crystal.level().isClientSide() && this.betterVanillaDragonFight$isNatural && ConfigManager.INSTANCE.getEnableCageMiningFatigue()) {
            if (crystal.tickCount % 20 == 0) { // Check once per second
                double range = ConfigManager.INSTANCE.getMiningFatigueRange();
                AABB boundingBox = crystal.getBoundingBox().inflate(range);
                List<Player> players = crystal.level().getEntitiesOfClass(Player.class, boundingBox);
                
                if (!players.isEmpty()) {
                    int durationTicks = ConfigManager.INSTANCE.getMiningFatigueDuration() * 20;
                    int level = ConfigManager.INSTANCE.getMiningFatigueLevel();
                    int amplifier = Math.max(0, level - 1);
                    
                    for (Player player : players) {
                        player.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, durationTicks, amplifier, true, true));
                    }
                }
            }
        }
    }
}
