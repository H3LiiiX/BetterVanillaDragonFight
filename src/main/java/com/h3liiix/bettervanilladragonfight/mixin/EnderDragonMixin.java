package com.h3liiix.bettervanilladragonfight.mixin;

import com.h3liiix.bettervanilladragonfight.ConfigManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin {

    @ModifyArg(
            method = "checkCrystals",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;setHealth(F)V"),
            index = 0
    )
    private float bettervanilladragonfight_modifyHealAmount(float originalNewHealth) {
        EnderDragon dragon = (EnderDragon) (Object) this;
        float currentHealth = dragon.getHealth();
        float baseHeal = originalNewHealth - currentHealth; // Should be 1.0f in vanilla
        return currentHealth + (baseHeal * ConfigManager.INSTANCE.getHealDragonMultiplier());
    }

    @org.spongepowered.asm.mixin.Unique
    private int bettervanilladragonfight_calculateXp() {
        EnderDragon dragon = (EnderDragon) (Object) this;
        if (!(dragon.level() instanceof ServerLevel)) return 0;
        ServerLevel serverLevel = (ServerLevel) dragon.level();
        
        int scaleEligiblePlayerCount = (int) serverLevel.players().stream().filter(p -> ConfigManager.INSTANCE.getCountCreativeModePlayers() || !p.isCreative()).count();
        boolean scaleWithOne = ConfigManager.INSTANCE.getScaleWithOnePlayer();
        int playersContributing = 0;
        if (scaleEligiblePlayerCount > 0) {
            if (!scaleWithOne && scaleEligiblePlayerCount <= 1) {
                playersContributing = 0;
            } else if (scaleWithOne) {
                playersContributing = scaleEligiblePlayerCount;
            } else {
                playersContributing = scaleEligiblePlayerCount - 1;
            }
        }
        
        boolean isFirstKill = this.bettervanilladragonfight_isFirstKill != null ? this.bettervanilladragonfight_isFirstKill : (dragon.getDragonFight() != null && !dragon.getDragonFight().hasPreviouslyKilledDragon());
        
        return isFirstKill ? ConfigManager.INSTANCE.getBaseDragonXP() + (ConfigManager.INSTANCE.getAdditionalXPPerPlayer() * playersContributing)
                           : ConfigManager.INSTANCE.getRespawnDragonXP() + (ConfigManager.INSTANCE.getAdditionalRespawnXPPerPlayer() * playersContributing);
    }

    @org.spongepowered.asm.mixin.injection.Redirect(
            method = "tickDeath",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V")
    )
    private void bettervanilladragonfight_redirectAwardXp(ServerLevel level, net.minecraft.world.phys.Vec3 pos, int amount) {
        if (!ConfigManager.INSTANCE.getSplitXP()) {
            EnderDragon dragon = (EnderDragon) (Object) this;
            boolean isFirstKill = this.bettervanilladragonfight_isFirstKill != null ? this.bettervanilladragonfight_isFirstKill : (dragon.getDragonFight() != null && !dragon.getDragonFight().hasPreviouslyKilledDragon());
            int originalXpCount = isFirstKill ? 12000 : 500;
            int ourXpCount = bettervanilladragonfight_calculateXp();
            double ratio = (double) ourXpCount / (double) originalXpCount;
            int newAmount = net.minecraft.util.Mth.floor(amount * ratio);
            
            if (newAmount > 0) {
                net.minecraft.world.entity.ExperienceOrb.award(level, pos, newAmount);
            }
        }
    }

    @org.spongepowered.asm.mixin.Unique
    private Boolean bettervanilladragonfight_isFirstKill = null;

    @org.spongepowered.asm.mixin.injection.Inject(method = "tickDeath", at = @At("HEAD"))
    private void bettervanilladragonfight_cacheFirstKill(org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        if (this.bettervanilladragonfight_isFirstKill == null) {
            EnderDragon dragon = (EnderDragon) (Object) this;
            this.bettervanilladragonfight_isFirstKill = dragon.getDragonFight() != null && !dragon.getDragonFight().hasPreviouslyKilledDragon();
        }
    }

    @org.spongepowered.asm.mixin.Unique
    private void bettervanilladragonfight_giveSplitXP() {
        if (ConfigManager.INSTANCE.getSplitXP()) {
            EnderDragon dragon = (EnderDragon) (Object) this;
            if (dragon.level() instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel) dragon.level();
                if (serverLevel.getGameRules().get(net.minecraft.world.level.gamerules.GameRules.MOB_DROPS)) {
                    int totalXp = bettervanilladragonfight_calculateXp();
                    java.util.List<net.minecraft.server.level.ServerPlayer> players = serverLevel.players().stream()
                            .filter(p -> ConfigManager.INSTANCE.getCountCreativeModePlayers() || !p.isCreative())
                            .toList();
                    if (!players.isEmpty()) {
                        int xpPerPlayer = totalXp / players.size();
                        for (net.minecraft.server.level.ServerPlayer p : players) {
                            p.giveExperiencePoints(xpPerPlayer);
                        }
                    }
                }
            }
        }
    }

    @org.spongepowered.asm.mixin.injection.Inject(
            method = "tickDeath",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;remove(Lnet/minecraft/world/entity/Entity$RemovalReason;)V")
    )
    private void bettervanilladragonfight_onDragonDeathEnd(org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        bettervanilladragonfight_giveSplitXP();
        this.bettervanilladragonfight_isFirstKill = null;
    }


    @org.spongepowered.asm.mixin.Unique
    private net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase<?> bettervanilladragonfight_lastPhase = null;

    @org.spongepowered.asm.mixin.Unique
    private int bettervanilladragonfight_perchCrystalsToSpawn = -1;
    @org.spongepowered.asm.mixin.Unique
    private int bettervanilladragonfight_perchRemainderCrystals = 0;

    @org.spongepowered.asm.mixin.Unique
    private int bettervanilladragonfight_respawnTimer = 0;
    @org.spongepowered.asm.mixin.Unique
    private java.util.List<net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike> bettervanilladragonfight_pendingSpikes = new java.util.ArrayList<>();
    @org.spongepowered.asm.mixin.Unique
    private java.util.List<net.minecraft.world.entity.boss.enderdragon.EndCrystal> bettervanilladragonfight_dummyCrystals = new java.util.ArrayList<>();

    @org.spongepowered.asm.mixin.injection.Inject(method = "aiStep", at = @At("HEAD"))
    private void bettervanilladragonfight_onAiStep(org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        EnderDragon dragon = (EnderDragon) (Object) this;
        if (dragon.level().isClientSide()) return;
        
        if (this.bettervanilladragonfight_perchCrystalsToSpawn == -1) {
            this.bettervanilladragonfight_perchCrystalsToSpawn = ConfigManager.INSTANCE.getEndCrystalsRespawned();
        }

        net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase<?> currentPhase = dragon.getPhaseManager().getCurrentPhase().getPhase();
        if (this.bettervanilladragonfight_lastPhase != currentPhase) {
            this.bettervanilladragonfight_lastPhase = currentPhase;
            
            if (currentPhase == net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.SITTING_SCANNING && ConfigManager.INSTANCE.getPerchRespawnsEndCrystals()) {
                ServerLevel level = (ServerLevel) dragon.level();
                
                java.util.List<net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike> allSpikes = net.minecraft.world.level.levelgen.feature.EndSpikeFeature.getSpikesForLevel(level);
                java.util.List<net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike> emptySpikes = new java.util.ArrayList<>();
                
                for (net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike spike : allSpikes) {
                    net.minecraft.world.phys.AABB searchBox = new net.minecraft.world.phys.AABB(
                            spike.getCenterX() - 2,
                            spike.getHeight() - 2,
                            spike.getCenterZ() - 2,
                            spike.getCenterX() + 2,
                            spike.getHeight() + 4,
                            spike.getCenterZ() + 2
                    );
                    java.util.List<net.minecraft.world.entity.boss.enderdragon.EndCrystal> crystals = level.getEntitiesOfClass(net.minecraft.world.entity.boss.enderdragon.EndCrystal.class, searchBox);
                    if (crystals.isEmpty()) {
                        emptySpikes.add(spike);
                    }
                }
                
                int totalToSpawn = this.bettervanilladragonfight_perchCrystalsToSpawn + this.bettervanilladragonfight_perchRemainderCrystals;
                if (totalToSpawn > 0) {
                    java.util.Collections.shuffle(emptySpikes);
                    int spawnCount = Math.min(totalToSpawn, emptySpikes.size());
                    
                    net.minecraft.core.BlockPos spawnPos = level.getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new net.minecraft.core.BlockPos(0, 0, 0));
                    
                    for (int i = 0; i < spawnCount; i++) {
                        net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike spike = emptySpikes.get(i);
                        this.bettervanilladragonfight_pendingSpikes.add(spike);
                        
                        // Spawn dummy crystal for the beam
                        net.minecraft.world.entity.boss.enderdragon.EndCrystal dummy = net.minecraft.world.entity.EntityTypes.END_CRYSTAL.create(level, net.minecraft.world.entity.EntitySpawnReason.EVENT);
                        if (dummy != null) {
                            dummy.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
                            dummy.setShowBottom(false);
                            dummy.setInvulnerable(true);
                            dummy.setBeamTarget(new net.minecraft.core.BlockPos(spike.getCenterX(), spike.getHeight(), spike.getCenterZ()));
                            level.addFreshEntity(dummy);
                            this.bettervanilladragonfight_dummyCrystals.add(dummy);
                        }
                    }
                    
                    this.bettervanilladragonfight_perchRemainderCrystals = totalToSpawn - spawnCount;
                    if (ConfigManager.INSTANCE.getHalfNextEndCrystalsRespawned()) {
                        this.bettervanilladragonfight_perchCrystalsToSpawn /= 2;
                    }
                    
                    if (spawnCount > 0) {
                        this.bettervanilladragonfight_respawnTimer = 60; // 3 seconds animation
                    }
                }
            }
        }

        
        // Handle animation tick
        if (this.bettervanilladragonfight_respawnTimer > 0) {
            this.bettervanilladragonfight_respawnTimer--;
            if (this.bettervanilladragonfight_respawnTimer == 0) {
                ServerLevel level = (ServerLevel) dragon.level();
                
                // Cleanup dummy crystals
                for (net.minecraft.world.entity.boss.enderdragon.EndCrystal dummy : this.bettervanilladragonfight_dummyCrystals) {
                    dummy.discard();
                }
                this.bettervanilladragonfight_dummyCrystals.clear();
                
                // Spawn real crystals
                for (net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike spike : this.bettervanilladragonfight_pendingSpikes) {
                    level.explode(null, spike.getCenterX() + 0.5, spike.getHeight(), spike.getCenterZ() + 0.5, 5.0f, net.minecraft.world.level.Level.ExplosionInteraction.BLOCK);
                    
                    if (ConfigManager.INSTANCE.getPerchRegeneratesCages()) {
                        com.h3liiix.bettervanilladragonfight.PerchContext.isPerchRegen.set(true);
                        net.minecraft.world.level.levelgen.feature.configurations.EndSpikeConfiguration configuration = new net.minecraft.world.level.levelgen.feature.configurations.EndSpikeConfiguration(false, java.util.List.of(spike), (net.minecraft.core.BlockPos) null);
                        net.minecraft.world.level.levelgen.feature.Feature.END_SPIKE.place(configuration, level, level.getChunkSource().getGenerator(), level.getRandom(), new net.minecraft.core.BlockPos(spike.getCenterX(), 45, spike.getCenterZ()));
                        com.h3liiix.bettervanilladragonfight.PerchContext.isPerchRegen.set(false);
                    } else {
                        net.minecraft.core.BlockPos bedrockPos = new net.minecraft.core.BlockPos(spike.getCenterX(), spike.getHeight(), spike.getCenterZ());
                        level.setBlock(bedrockPos, net.minecraft.world.level.block.Blocks.BEDROCK.defaultBlockState(), 2);
                        level.setBlock(bedrockPos.above(), net.minecraft.world.level.block.Blocks.FIRE.defaultBlockState(), 2);
                        
                        net.minecraft.world.entity.boss.enderdragon.EndCrystal crystal = net.minecraft.world.entity.EntityTypes.END_CRYSTAL.create(level, net.minecraft.world.entity.EntitySpawnReason.EVENT);
                        if (crystal != null) {
                            crystal.setPos(spike.getCenterX() + 0.5, spike.getHeight() + 1.0, spike.getCenterZ() + 0.5);
                            ((com.h3liiix.bettervanilladragonfight.NaturalCrystalAccessor) crystal).betterVanillaDragonFight$setNatural(true);
                            level.addFreshEntity(crystal);
                        }
                    }
                    
                    level.levelEvent(3001, new net.minecraft.core.BlockPos(spike.getCenterX(), spike.getHeight(), spike.getCenterZ()), 0);
                }
                this.bettervanilladragonfight_pendingSpikes.clear();
            }
        }
    }
}



