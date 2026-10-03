package com.h3liiix.bettervanilladragonfight.mixin;

import com.h3liiix.bettervanilladragonfight.NaturalCrystalAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.feature.EndSpikeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndSpikeFeature.class)
public abstract class EndSpikeFeatureMixin {

    @Redirect(method = "placeSpike", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ServerLevelAccessor;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean bettervanilladragonfight_onAddFreshEntity(ServerLevelAccessor level, Entity entity) {
        if (entity instanceof EndCrystal crystal) {
            ((NaturalCrystalAccessor) crystal).betterVanillaDragonFight$setNatural(true);
        }
        return level.addFreshEntity(entity);
    }

    @org.spongepowered.asm.mixin.injection.Inject(method = "placeSpike", at = @At("TAIL"))
    private void bettervanilladragonfight_onPlaceSpike(ServerLevelAccessor level, net.minecraft.util.RandomSource random, EndSpikeFeature.EndSpike spike, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        if (spike.isGuarded() && com.h3liiix.bettervanilladragonfight.ConfigManager.INSTANCE.getEnableCageCover()) {
            net.minecraft.core.BlockPos.MutableBlockPos pos = new net.minecraft.core.BlockPos.MutableBlockPos();
            for (int dx = -2; dx <= 2; ++dx) {
                for (int dz = -2; dz <= 2; ++dz) {
                    level.setBlock(pos.set(spike.getCenterX() + dx, spike.getHeight() + 4, spike.getCenterZ() + dz), net.minecraft.world.level.block.Blocks.OBSIDIAN.defaultBlockState(), 3);
                }
            }
        }
        
        if (com.h3liiix.bettervanilladragonfight.ConfigManager.INSTANCE.getEnableShulkers() && !com.h3liiix.bettervanilladragonfight.PerchContext.isPerchRegen.get()) {
            int shulkersToSpawn = com.h3liiix.bettervanilladragonfight.ConfigManager.INSTANCE.getShulkersPerTower();
            int baseY = 65;
            int topY = spike.getHeight();
            if (topY > baseY) {
                int minY = baseY + (int) ((topY - baseY) * 0.5);
                int maxY = baseY + (int) ((topY - baseY) * 0.9);
                int radius = spike.getRadius();
                
                // Find and remove existing tower shulkers on this spike
                net.minecraft.world.phys.AABB searchBox = new net.minecraft.world.phys.AABB(
                        spike.getCenterX() - radius - 3, 
                        0, 
                        spike.getCenterZ() - radius - 3, 
                        spike.getCenterX() + radius + 3, 
                        spike.getHeight() + 10, 
                        spike.getCenterZ() + radius + 3
                );
                
                java.util.List<net.minecraft.world.entity.monster.Shulker> existingShulkers = level.getEntitiesOfClass(
                        net.minecraft.world.entity.monster.Shulker.class, 
                        searchBox
                );
                
                for (net.minecraft.world.entity.monster.Shulker shulker : existingShulkers) {
                    if (((com.h3liiix.bettervanilladragonfight.TowerShulker) shulker).betterVanillaDragonFight$isTowerShulker()) {
                        shulker.discard();
                    }
                }
                
                for (int i = 0; i < shulkersToSpawn; i++) {
                    int y = minY + random.nextInt(Math.max(1, maxY - minY + 1));
                    int side = random.nextInt(4);
                    int x = spike.getCenterX();
                    int z = spike.getCenterZ();
                    net.minecraft.core.Direction attachFace;
                    
                    switch (side) {
                        case 0: // NORTH
                            z = z - radius - 1;
                            attachFace = net.minecraft.core.Direction.SOUTH;
                            break;
                        case 1: // SOUTH
                            z = z + radius + 1;
                            attachFace = net.minecraft.core.Direction.NORTH;
                            break;
                        case 2: // WEST
                            x = x - radius - 1;
                            attachFace = net.minecraft.core.Direction.EAST;
                            break;
                        default: // EAST
                            x = x + radius + 1;
                            attachFace = net.minecraft.core.Direction.WEST;
                            break;
                    }
                    
                    net.minecraft.world.entity.monster.Shulker shulker = net.minecraft.world.entity.EntityTypes.SHULKER.create(level.getLevel(), net.minecraft.world.entity.EntitySpawnReason.STRUCTURE);
                    if (shulker != null) {
                        shulker.setPos(x + 0.5, y, z + 0.5);
                        ((ShulkerAccessor) shulker).bettervanilladragonfight_setAttachFace(attachFace);
                        ((com.h3liiix.bettervanilladragonfight.TowerShulker) shulker).betterVanillaDragonFight$setTowerShulker(true);
                        level.addFreshEntity(shulker);
                    }
                }
            }
        }
    }
}

