package com.h3liiix.bettervanilladragonfight.client

import com.h3liiix.bettervanilladragonfight.ConfigManager
import me.shedaniel.clothconfig2.api.ConfigBuilder
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

object ConfigMenu {
    fun buildScreen(parent: Screen?): Screen {
        val builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Component.literal("Better Vanilla Dragon Fight Configuration"))
            .setSavingRunnable { ConfigManager.saveConfig() }

        val entryBuilder = builder.entryBuilder()
        val general = builder.getOrCreateCategory(Component.literal("General"))

        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Mod"), ConfigManager.enableMod)
            .setDefaultValue(true)
            .setSaveConsumer { ConfigManager.enableMod = it }
            .setTooltip(Component.literal("Globally enables or disables the mod's features."))
            .build())

        general.addEntry(entryBuilder.startFloatField(Component.literal("Base Dragon Health"), ConfigManager.baseDragonHealth)
            .setDefaultValue(200.0f)
            .setSaveConsumer { ConfigManager.baseDragonHealth = it }
            .setTooltip(Component.literal("The base health of the Ender Dragon."))
            .build())

        general.addEntry(entryBuilder.startFloatField(Component.literal("Additional Health Per Player"), ConfigManager.additionalHealthPerPlayer)
            .setDefaultValue(100.0f)
            .setSaveConsumer { ConfigManager.additionalHealthPerPlayer = it }
            .setTooltip(Component.literal("Health added to the dragon for each additional player."))
            .build())

        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Scale With One Player"), ConfigManager.scaleWithOnePlayer)
            .setDefaultValue(false)
            .setSaveConsumer { ConfigManager.scaleWithOnePlayer = it }
            .setTooltip(Component.literal("Whether the dragon should scale when only 1 player is present."))
            .build())

        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Count Creative Mode Players"), ConfigManager.countCreativeModePlayers)
            .setDefaultValue(false)
            .setSaveConsumer { ConfigManager.countCreativeModePlayers = it }
            .setTooltip(Component.literal("Whether to include Creative mode players in the scaling math."))
            .build())

        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Broadcast"), ConfigManager.enableBroadcast)
            .setDefaultValue(false)
            .setSaveConsumer { ConfigManager.enableBroadcast = it }
            .setTooltip(Component.literal("Broadcast a chat message when the dragon spawns."))
            .build())

        val delay = builder.getOrCreateCategory(Component.literal("Initial Spawn Delay"))

        delay.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Initial Spawn Delay"), ConfigManager.enableInitialSpawnDelay)
            .setDefaultValue(false)
            .setSaveConsumer { ConfigManager.enableInitialSpawnDelay = it }
            .setTooltip(Component.literal("Delays the dragon's initial spawn."))
            .build())

        delay.addEntry(entryBuilder.startIntField(Component.literal("Initial Spawn Delay Seconds"), ConfigManager.initialSpawnDelaySeconds)
            .setDefaultValue(60)
            .setSaveConsumer { ConfigManager.initialSpawnDelaySeconds = it }
            .setTooltip(Component.literal("Time in seconds to delay the spawn."))
            .build())

        delay.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Spawn Delay Countdown"), ConfigManager.showSpawnDelayCountdown)
            .setDefaultValue(false)
            .setSaveConsumer { ConfigManager.showSpawnDelayCountdown = it }
            .setTooltip(Component.literal("Display a title countdown while waiting for the dragon."))
            .build())

        val towers = builder.getOrCreateCategory(Component.literal("End Towers & Crystals"))

        towers.addEntry(entryBuilder.startFloatField(Component.literal("Heal Dragon Multiplier"), ConfigManager.healDragonMultiplier)
            .setDefaultValue(10.0f)
            .setSaveConsumer { ConfigManager.healDragonMultiplier = it }
            .setTooltip(Component.literal("Multiplier for how much crystals heal the dragon."))
            .build())

        towers.addEntry(entryBuilder.startBooleanToggle(Component.literal("Cage All End Crystals"), ConfigManager.cageAllEndCrystals)
            .setDefaultValue(true)
            .setSaveConsumer { ConfigManager.cageAllEndCrystals = it }
            .setTooltip(Component.literal("Generate cages on every end crystal."))
            .build())

        towers.addEntry(entryBuilder.startBooleanToggle(Component.literal("Disable Small Towers"), ConfigManager.disableSmallTowers)
            .setDefaultValue(true)
            .setSaveConsumer { ConfigManager.disableSmallTowers = it }
            .setTooltip(Component.literal("Increases the size of the smaller towers."))
            .build())

        towers.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Cage Cover"), ConfigManager.enableCageCover)
            .setDefaultValue(true)
            .setSaveConsumer { ConfigManager.enableCageCover = it }
            .setTooltip(Component.literal("Covers cages with additional blocks."))
            .build())

        val shulkers = builder.getOrCreateCategory(Component.literal("Shulkers"))

        shulkers.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Shulkers"), ConfigManager.enableShulkers)
            .setDefaultValue(true)
            .setSaveConsumer { ConfigManager.enableShulkers = it }
            .setTooltip(Component.literal("Spawns shulkers on the towers."))
            .build())

        shulkers.addEntry(entryBuilder.startIntField(Component.literal("Shulkers Per Tower"), ConfigManager.shulkersPerTower)
            .setDefaultValue(3)
            .setSaveConsumer { ConfigManager.shulkersPerTower = it }
            .setTooltip(Component.literal("Number of shulkers to spawn per tower."))
            .build())

        val fatigue = builder.getOrCreateCategory(Component.literal("Cage Mining Fatigue"))

        fatigue.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Cage Mining Fatigue"), ConfigManager.enableCageMiningFatigue)
            .setDefaultValue(true)
            .setSaveConsumer { ConfigManager.enableCageMiningFatigue = it }
            .setTooltip(Component.literal("End crystals apply mining fatigue to nearby players."))
            .build())

        fatigue.addEntry(entryBuilder.startIntField(Component.literal("Mining Fatigue Level"), ConfigManager.miningFatigueLevel)
            .setDefaultValue(2)
            .setSaveConsumer { ConfigManager.miningFatigueLevel = it }
            .setTooltip(Component.literal("The level/amplifier of the mining fatigue effect."))
            .build())

        fatigue.addEntry(entryBuilder.startDoubleField(Component.literal("Mining Fatigue Range"), ConfigManager.miningFatigueRange)
            .setDefaultValue(6.0)
            .setSaveConsumer { ConfigManager.miningFatigueRange = it }
            .setTooltip(Component.literal("Radius in blocks where players receive mining fatigue."))
            .build())

        fatigue.addEntry(entryBuilder.startIntField(Component.literal("Mining Fatigue Duration"), ConfigManager.miningFatigueDuration)
            .setDefaultValue(5)
            .setSaveConsumer { ConfigManager.miningFatigueDuration = it }
            .setTooltip(Component.literal("Duration in seconds of the mining fatigue effect."))
            .build())

        val xp = builder.getOrCreateCategory(Component.literal("XP"))

        xp.addEntry(entryBuilder.startBooleanToggle(Component.literal("Split XP"), ConfigManager.splitXP)
            .setDefaultValue(true)
            .setSaveConsumer { ConfigManager.splitXP = it }
            .setTooltip(Component.literal("Splits dragon XP equally among players instead of dropping orbs."))
            .build())

        xp.addEntry(entryBuilder.startIntField(Component.literal("Base Dragon XP"), ConfigManager.baseDragonXP)
            .setDefaultValue(12000)
            .setSaveConsumer { ConfigManager.baseDragonXP = it }
            .setTooltip(Component.literal("Base XP given for the first dragon kill."))
            .build())

        xp.addEntry(entryBuilder.startIntField(Component.literal("Additional XP Per Player"), ConfigManager.additionalXPPerPlayer)
            .setDefaultValue(3000)
            .setSaveConsumer { ConfigManager.additionalXPPerPlayer = it }
            .setTooltip(Component.literal("Additional XP for each extra player on the first kill."))
            .build())

        xp.addEntry(entryBuilder.startIntField(Component.literal("Respawn Dragon XP"), ConfigManager.respawnDragonXP)
            .setDefaultValue(500)
            .setSaveConsumer { ConfigManager.respawnDragonXP = it }
            .setTooltip(Component.literal("Base XP given for respawned dragons."))
            .build())

        xp.addEntry(entryBuilder.startIntField(Component.literal("Additional Respawn XP Per Player"), ConfigManager.additionalRespawnXPPerPlayer)
            .setDefaultValue(200)
            .setSaveConsumer { ConfigManager.additionalRespawnXPPerPlayer = it }
            .setTooltip(Component.literal("Additional XP for each extra player on respawned kills."))
            .build())

        val perch = builder.getOrCreateCategory(Component.literal("Perch End Crystal Respawn"))

        perch.addEntry(entryBuilder.startBooleanToggle(Component.literal("Perch Respawns End Crystals"), ConfigManager.perchRespawnsEndCrystals)
            .setDefaultValue(true)
            .setSaveConsumer { ConfigManager.perchRespawnsEndCrystals = it }
            .setTooltip(Component.literal("Dragon respawns crystals when perching."))
            .build())

        perch.addEntry(entryBuilder.startIntField(Component.literal("End Crystals Respawned"), ConfigManager.endCrystalsRespawned)
            .setDefaultValue(4)
            .setSaveConsumer { ConfigManager.endCrystalsRespawned = it }
            .setTooltip(Component.literal("Number of crystals to respawn per perch."))
            .build())

        perch.addEntry(entryBuilder.startBooleanToggle(Component.literal("Half Next End Crystals Respawned"), ConfigManager.halfNextEndCrystalsRespawned)
            .setDefaultValue(true)
            .setSaveConsumer { ConfigManager.halfNextEndCrystalsRespawned = it }
            .setTooltip(Component.literal("Halves the number of crystals respawned on subsequent perches."))
            .build())

        perch.addEntry(entryBuilder.startBooleanToggle(Component.literal("Perch Regenerates Cages"), ConfigManager.perchRegeneratesCages)
            .setDefaultValue(true)
            .setSaveConsumer { ConfigManager.perchRegeneratesCages = it }
            .setTooltip(Component.literal("Regenerate cages along with respawned crystals."))
            .build())

        return builder.build()
    }
}
