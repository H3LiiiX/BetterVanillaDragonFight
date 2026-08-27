package com.h3liiix.bettervanilladragonfight

import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

object ConfigManager {

    /*---- Default configuration values ----*/
    private const val DEFAULT_ENABLE_MOD = true
    private const val DEFAULT_SCALE_WITH_ONE_PLAYER = false
    private const val DEFAULT_COUNT_CREATIVE_MODE_PLAYERS = false
    private const val DEFAULT_BASE_DRAGON_HEALTH = 200.0f
    private const val DEFAULT_ADDITIONAL_HEALTH_PER_PLAYER = 100.0f
    private const val DEFAULT_ENABLE_BROADCAST = false

    // Values for delay dragon spawn
    private const val DEFAULT_ENABLE_INITIAL_SPAWN_DELAY = false
    private const val DEFAULT_INITIAL_SPAWN_DELAY_SECONDS = 60
    private const val DEFAULT_SHOW_SPAWN_DELAY_COUNTDOWN = false

    private const val DEFAULT_HEAL_DRAGON_MULTIPLIER = 10.0f
    private const val DEFAULT_CAGE_ALL_END_CRYSTALS = true
    private const val DEFAULT_DISABLE_SMALL_TOWERS = true
    private const val DEFAULT_ENABLE_CAGE_COVER = true

    private const val DEFAULT_ENABLE_SHULKERS = true
    private const val DEFAULT_SHULKERS_PER_TOWER = 3

    private const val DEFAULT_ENABLE_CAGE_MINING_FATIGUE = true
    private const val DEFAULT_MINING_FATIGUE_LEVEL = 2
    private const val DEFAULT_MINING_FATIGUE_RANGE = 6.0
    private const val DEFAULT_MINING_FATIGUE_DURATION = 5

    private const val DEFAULT_SPLIT_XP = true
    private const val DEFAULT_BASE_DRAGON_XP = 12000
    private const val DEFAULT_ADDITIONAL_XP_PER_PLAYER = 3000
    private const val DEFAULT_RESPAWN_DRAGON_XP = 500
    private const val DEFAULT_ADDITIONAL_RESPAWN_XP_PER_PLAYER = 200

    private const val DEFAULT_PERCH_RESPAWNS_END_CRYSTALS = true
    private const val DEFAULT_END_CRYSTALS_RESPAWNED = 4
    private const val DEFAULT_HALF_NEXT_END_CRYSTALS_RESPAWNED = true
    private const val DEFAULT_PERCH_REGENERATES_CAGES = true

    /*---- Configurable values ----*/
    var enableMod: Boolean = DEFAULT_ENABLE_MOD
    var scaleWithOnePlayer: Boolean = DEFAULT_SCALE_WITH_ONE_PLAYER
    var countCreativeModePlayers: Boolean = DEFAULT_COUNT_CREATIVE_MODE_PLAYERS
    var baseDragonHealth: Float = DEFAULT_BASE_DRAGON_HEALTH
    var additionalHealthPerPlayer: Float = DEFAULT_ADDITIONAL_HEALTH_PER_PLAYER
    var enableBroadcast: Boolean = DEFAULT_ENABLE_BROADCAST

    // Values for delay dragon
    var enableInitialSpawnDelay: Boolean = DEFAULT_ENABLE_INITIAL_SPAWN_DELAY
    var initialSpawnDelaySeconds: Int = DEFAULT_INITIAL_SPAWN_DELAY_SECONDS
    var showSpawnDelayCountdown: Boolean = DEFAULT_SHOW_SPAWN_DELAY_COUNTDOWN

    var healDragonMultiplier: Float = DEFAULT_HEAL_DRAGON_MULTIPLIER
    var cageAllEndCrystals: Boolean = DEFAULT_CAGE_ALL_END_CRYSTALS
    var disableSmallTowers: Boolean = DEFAULT_DISABLE_SMALL_TOWERS
    var enableCageCover: Boolean = DEFAULT_ENABLE_CAGE_COVER
    
    var enableShulkers: Boolean = DEFAULT_ENABLE_SHULKERS
    var shulkersPerTower: Int = DEFAULT_SHULKERS_PER_TOWER
    
    var enableCageMiningFatigue: Boolean = DEFAULT_ENABLE_CAGE_MINING_FATIGUE
    var miningFatigueLevel: Int = DEFAULT_MINING_FATIGUE_LEVEL
    var miningFatigueRange: Double = DEFAULT_MINING_FATIGUE_RANGE
    var miningFatigueDuration: Int = DEFAULT_MINING_FATIGUE_DURATION

    var splitXP: Boolean = DEFAULT_SPLIT_XP
    var baseDragonXP: Int = DEFAULT_BASE_DRAGON_XP
    var additionalXPPerPlayer: Int = DEFAULT_ADDITIONAL_XP_PER_PLAYER
    var respawnDragonXP: Int = DEFAULT_RESPAWN_DRAGON_XP
    var additionalRespawnXPPerPlayer: Int = DEFAULT_ADDITIONAL_RESPAWN_XP_PER_PLAYER

    var perchRespawnsEndCrystals: Boolean = DEFAULT_PERCH_RESPAWNS_END_CRYSTALS
    var endCrystalsRespawned: Int = DEFAULT_END_CRYSTALS_RESPAWNED
    var halfNextEndCrystalsRespawned: Boolean = DEFAULT_HALF_NEXT_END_CRYSTALS_RESPAWNED
    var perchRegeneratesCages: Boolean = DEFAULT_PERCH_REGENERATES_CAGES

    private val configFilePath: Path = FabricLoader.getInstance().configDir.resolve("$MOD_ID.properties")

    fun loadConfig() {
        LOGGER.info("Loading Better Vanilla Dragon Fight configuration...")
        val properties = Properties()

        if (Files.exists(configFilePath)) {
            try {
                Files.newInputStream(configFilePath).use { inputStream ->
                    properties.load(inputStream)
                }

                /*---- Load configuration values ----*/
                enableMod = properties.getProperty("enableMod", DEFAULT_ENABLE_MOD.toString()).toBooleanStrictOrNull()
                    ?: DEFAULT_ENABLE_MOD
                scaleWithOnePlayer =
                    properties.getProperty("scaleWithOnePlayer", DEFAULT_SCALE_WITH_ONE_PLAYER.toString())
                        .toBooleanStrictOrNull() ?: DEFAULT_SCALE_WITH_ONE_PLAYER
                countCreativeModePlayers =
                    properties.getProperty("countCreativeModePlayers", DEFAULT_COUNT_CREATIVE_MODE_PLAYERS.toString())
                        .toBooleanStrictOrNull() ?: DEFAULT_COUNT_CREATIVE_MODE_PLAYERS
                baseDragonHealth =
                    properties.getProperty("baseDragonHealth", DEFAULT_BASE_DRAGON_HEALTH.toString()).toFloatOrNull()
                        ?: DEFAULT_BASE_DRAGON_HEALTH
                additionalHealthPerPlayer =
                    properties.getProperty("additionalHealthPerPlayer", DEFAULT_ADDITIONAL_HEALTH_PER_PLAYER.toString())
                        .toFloatOrNull() ?: DEFAULT_ADDITIONAL_HEALTH_PER_PLAYER
                enableBroadcast = properties.getProperty("enableBroadcast", DEFAULT_ENABLE_BROADCAST.toString())
                    .toBooleanStrictOrNull() ?: DEFAULT_ENABLE_BROADCAST

                // Values for delay dragon spawn
                enableInitialSpawnDelay =
                    properties.getProperty("enableInitialSpawnDelay", DEFAULT_ENABLE_INITIAL_SPAWN_DELAY.toString())
                        .toBooleanStrictOrNull() ?: DEFAULT_ENABLE_INITIAL_SPAWN_DELAY
                initialSpawnDelaySeconds =
                    properties.getProperty("initialSpawnDelaySeconds", DEFAULT_INITIAL_SPAWN_DELAY_SECONDS.toString())
                        .toIntOrNull() ?: DEFAULT_INITIAL_SPAWN_DELAY_SECONDS
                showSpawnDelayCountdown =
                    properties.getProperty("showSpawnDelayCountdown", DEFAULT_SHOW_SPAWN_DELAY_COUNTDOWN.toString())
                        .toBooleanStrictOrNull() ?: DEFAULT_SHOW_SPAWN_DELAY_COUNTDOWN

                healDragonMultiplier =
                    properties.getProperty("healDragonMultiplier", DEFAULT_HEAL_DRAGON_MULTIPLIER.toString())
                        .toFloatOrNull() ?: DEFAULT_HEAL_DRAGON_MULTIPLIER

                cageAllEndCrystals =
                    properties.getProperty("cageAllEndCrystals", DEFAULT_CAGE_ALL_END_CRYSTALS.toString())
                        .toBooleanStrictOrNull() ?: DEFAULT_CAGE_ALL_END_CRYSTALS

                disableSmallTowers =
                    properties.getProperty("disableSmallTowers", DEFAULT_DISABLE_SMALL_TOWERS.toString())
                        .toBooleanStrictOrNull() ?: DEFAULT_DISABLE_SMALL_TOWERS
                enableCageCover =
                    properties.getProperty("enableCageCover", DEFAULT_ENABLE_CAGE_COVER.toString())
                        .toBooleanStrictOrNull() ?: DEFAULT_ENABLE_CAGE_COVER

                enableShulkers =
                    properties.getProperty("enableShulkers", DEFAULT_ENABLE_SHULKERS.toString())
                        .toBooleanStrictOrNull() ?: DEFAULT_ENABLE_SHULKERS
                shulkersPerTower =
                    properties.getProperty("shulkersPerTower", DEFAULT_SHULKERS_PER_TOWER.toString())
                        .toIntOrNull()?.coerceIn(0, 16) ?: DEFAULT_SHULKERS_PER_TOWER

                enableCageMiningFatigue =
                    properties.getProperty("enableCageMiningFatigue", DEFAULT_ENABLE_CAGE_MINING_FATIGUE.toString())
                        .toBooleanStrictOrNull() ?: DEFAULT_ENABLE_CAGE_MINING_FATIGUE
                miningFatigueLevel =
                    properties.getProperty("miningFatigueLevel", DEFAULT_MINING_FATIGUE_LEVEL.toString())
                        .toIntOrNull()?.coerceIn(1, 4) ?: DEFAULT_MINING_FATIGUE_LEVEL
                miningFatigueRange =
                    properties.getProperty("miningFatigueRange", DEFAULT_MINING_FATIGUE_RANGE.toString())
                        .toDoubleOrNull()?.coerceIn(1.0, 100.0) ?: DEFAULT_MINING_FATIGUE_RANGE
                miningFatigueDuration =
                    properties.getProperty("miningFatigueDuration", DEFAULT_MINING_FATIGUE_DURATION.toString())
                        .toIntOrNull()?.coerceAtLeast(1) ?: DEFAULT_MINING_FATIGUE_DURATION

                splitXP = properties.getProperty("splitXP", DEFAULT_SPLIT_XP.toString()).toBooleanStrictOrNull() ?: DEFAULT_SPLIT_XP
                baseDragonXP = properties.getProperty("baseDragonXP", DEFAULT_BASE_DRAGON_XP.toString()).toIntOrNull() ?: DEFAULT_BASE_DRAGON_XP
                additionalXPPerPlayer = properties.getProperty("additionalXPPerPlayer", DEFAULT_ADDITIONAL_XP_PER_PLAYER.toString()).toIntOrNull() ?: DEFAULT_ADDITIONAL_XP_PER_PLAYER
                respawnDragonXP = properties.getProperty("respawnDragonXP", DEFAULT_RESPAWN_DRAGON_XP.toString()).toIntOrNull() ?: DEFAULT_RESPAWN_DRAGON_XP
                additionalRespawnXPPerPlayer = properties.getProperty("additionalRespawnXPPerPlayer", DEFAULT_ADDITIONAL_RESPAWN_XP_PER_PLAYER.toString()).toIntOrNull() ?: DEFAULT_ADDITIONAL_RESPAWN_XP_PER_PLAYER

                perchRespawnsEndCrystals = properties.getProperty("perchRespawnsEndCrystals", DEFAULT_PERCH_RESPAWNS_END_CRYSTALS.toString()).toBooleanStrictOrNull() ?: DEFAULT_PERCH_RESPAWNS_END_CRYSTALS
                endCrystalsRespawned = properties.getProperty("endCrystalsRespawned", DEFAULT_END_CRYSTALS_RESPAWNED.toString()).toIntOrNull()?.coerceIn(1, 10) ?: DEFAULT_END_CRYSTALS_RESPAWNED
                halfNextEndCrystalsRespawned = properties.getProperty("halfNextEndCrystalsRespawned", DEFAULT_HALF_NEXT_END_CRYSTALS_RESPAWNED.toString()).toBooleanStrictOrNull() ?: DEFAULT_HALF_NEXT_END_CRYSTALS_RESPAWNED
                perchRegeneratesCages = properties.getProperty("perchRegeneratesCages", DEFAULT_PERCH_REGENERATES_CAGES.toString()).toBooleanStrictOrNull() ?: DEFAULT_PERCH_REGENERATES_CAGES

                LOGGER.info("Configuration loaded: Mod Enabled = $enableMod, Scale w/ 1 Player = $scaleWithOnePlayer, Count Creative = $countCreativeModePlayers, Base Health = $baseDragonHealth, Additional Health/Player = $additionalHealthPerPlayer, Enable Broadcast = $enableBroadcast, Heal Dragon Multiplier = $healDragonMultiplier, Cage All End Crystals = $cageAllEndCrystals, Disable Small Towers = $disableSmallTowers, Enable Cage Mining Fatigue = $enableCageMiningFatigue")
                // Ensure config file is up-to-date with current or default values if parsing failed for some
                saveConfig()
            } catch (e: Exception) {
                LOGGER.error(
                    "Failed to load configuration for $MOD_ID. Using default values and attempting to save a new config file.",
                    e
                )
                resetToDefaultsAndSave()
            }
        } else {
            LOGGER.info("No configuration file found for $MOD_ID. Creating with default values.")
            resetToDefaultsAndSave()
        }
    }

    fun saveConfig() {
        LOGGER.info("Saving Better Vanilla Dragon Fight configuration...")

        val content = """
            # Better Vanilla Dragon Fight Configuration
            
            # If true, the mod will be active. (Default: $DEFAULT_ENABLE_MOD)
            enableMod=$enableMod
            
            # Base health of the Ender Dragon. (Default: $DEFAULT_BASE_DRAGON_HEALTH)
            baseDragonHealth=$baseDragonHealth
            
            # Extra health added for each eligible player. (Default: $DEFAULT_ADDITIONAL_HEALTH_PER_PLAYER)
            additionalHealthPerPlayer=$additionalHealthPerPlayer
            
            # If true, the dragon's health will increase counting the first eligible player.
            # If false, scaling only starts with the second eligible player.
            # Example (assuming 100 additional health per player):
            #     True:
            #         1 Eligible Player = Base Health + 100
            #         2 Eligible Players = Base Health + 200
            #     False:
            #         1 Eligible Player = Base Health
            #         2 Eligible Players = Base Health + 100
            # (Default: $DEFAULT_SCALE_WITH_ONE_PLAYER)
            scaleWithOnePlayer=$scaleWithOnePlayer
            
            # If true, players in creative mode will be counted when scaling health. (Default: $DEFAULT_COUNT_CREATIVE_MODE_PLAYERS)
            countCreativeModePlayers=$countCreativeModePlayers
            
            # If true, a message will be broadcast when the scaled dragon spawns. (Default: $DEFAULT_ENABLE_BROADCAST)
            enableBroadcast=$enableBroadcast
            
            
            # --- Initial Spawn Delay ---
            
            # If true, the very first Ender Dragon spawn in The End will be delayed. (Default: $DEFAULT_ENABLE_INITIAL_SPAWN_DELAY)
            enableInitialSpawnDelay=$enableInitialSpawnDelay
            
            # How many seconds to delay the initial dragon spawn. (Default: $DEFAULT_INITIAL_SPAWN_DELAY_SECONDS)
            initialSpawnDelaySeconds=$initialSpawnDelaySeconds
            
            # If true, a countdown will be shown on players' XP bars in The End during the delay. (Default: $DEFAULT_SHOW_SPAWN_DELAY_COUNTDOWN)
            showSpawnDelayCountdown=$showSpawnDelayCountdown
            
            
            # --- End Towers & Crystals ---
            
            # Multiplier for the rate at which the ender dragon heals when near an end crystal. (Default: $DEFAULT_HEAL_DRAGON_MULTIPLIER)
            healDragonMultiplier=$healDragonMultiplier
            
            # If true, all end crystals will spawn with cages. (Default: $DEFAULT_CAGE_ALL_END_CRYSTALS)
            cageAllEndCrystals=$cageAllEndCrystals
            
            # If true, small thin end towers will not generate, only medium to large ones will. (Default: $DEFAULT_DISABLE_SMALL_TOWERS)
            disableSmallTowers=$disableSmallTowers
            
            # If true, a 5x5 layer of obsidian covers the top of every cage. (Default: $DEFAULT_ENABLE_CAGE_COVER)
            enableCageCover=$enableCageCover
            
            
            # --- Shulkers ---
            
            # If true, shulkers will spawn on the sides of the towers. (Default: $DEFAULT_ENABLE_SHULKERS)
            enableShulkers=$enableShulkers
            
            # How many shulkers spawn per tower. (Default: $DEFAULT_SHULKERS_PER_TOWER)
            shulkersPerTower=$shulkersPerTower
            
            
            # --- Cage Mining Fatigue ---
            
            # If true, naturally spawned end crystals will give mining fatigue to nearby players. (Default: $DEFAULT_ENABLE_CAGE_MINING_FATIGUE)
            enableCageMiningFatigue=$enableCageMiningFatigue
            
            # The level of mining fatigue given by natural crystals (1-4). (Default: $DEFAULT_MINING_FATIGUE_LEVEL)
            miningFatigueLevel=$miningFatigueLevel
            
            # The range in blocks that the crystal gives mining fatigue. (Default: $DEFAULT_MINING_FATIGUE_RANGE)
            miningFatigueRange=$miningFatigueRange
            
            # The duration in seconds of the mining fatigue effect. (Default: $DEFAULT_MINING_FATIGUE_DURATION)
            miningFatigueDuration=$miningFatigueDuration
            
            
            # --- XP ---
            
            # If true, XP orbs will not spawn. Instead, XP is split evenly among players. (Default: $DEFAULT_SPLIT_XP)
            splitXP=$splitXP
            
            # Base XP dropped by the dragon on its first death. (Default: $DEFAULT_BASE_DRAGON_XP)
            baseDragonXP=$baseDragonXP
            
            # Extra XP given per player on first death. (Default: $DEFAULT_ADDITIONAL_XP_PER_PLAYER)
            additionalXPPerPlayer=$additionalXPPerPlayer
            
            # Base XP dropped by respawned dragons. (Default: $DEFAULT_RESPAWN_DRAGON_XP)
            respawnDragonXP=$respawnDragonXP
            
            # Extra XP given per player for respawned dragons. (Default: $DEFAULT_ADDITIONAL_RESPAWN_XP_PER_PLAYER)
            additionalRespawnXPPerPlayer=$additionalRespawnXPPerPlayer
            
            
            # --- Perch End Crystal Respawn ---
            
            # If true, end crystals will respawn when the dragon perches. (Default: $DEFAULT_PERCH_RESPAWNS_END_CRYSTALS)
            perchRespawnsEndCrystals=$perchRespawnsEndCrystals
            
            # The base number of end crystals that will respawn when the dragon perches (1-10). (Default: $DEFAULT_END_CRYSTALS_RESPAWNED)
            endCrystalsRespawned=$endCrystalsRespawned
            
            # If true, the number of end crystals to respawn will half on each subsequent perch. (Default: $DEFAULT_HALF_NEXT_END_CRYSTALS_RESPAWNED)
            halfNextEndCrystalsRespawned=$halfNextEndCrystalsRespawned
            
            # If true, the cages and obsidian of the end crystals that get respawned will also regenerate. (Default: $DEFAULT_PERCH_REGENERATES_CAGES)
            perchRegeneratesCages=$perchRegeneratesCages
        """.trimIndent()

        try {
            Files.writeString(configFilePath, content)
            LOGGER.info("Configuration saved to $configFilePath")
        } catch (e: Exception) {
            LOGGER.error("Failed to save configuration for $MOD_ID.", e)
        }
    }

    private fun resetToDefaultsAndSave() {
        /*---- Reset configuration values to defaults ----*/
        enableMod = DEFAULT_ENABLE_MOD
        scaleWithOnePlayer = DEFAULT_SCALE_WITH_ONE_PLAYER
        countCreativeModePlayers = DEFAULT_COUNT_CREATIVE_MODE_PLAYERS
        baseDragonHealth = DEFAULT_BASE_DRAGON_HEALTH
        additionalHealthPerPlayer = DEFAULT_ADDITIONAL_HEALTH_PER_PLAYER
        enableBroadcast = DEFAULT_ENABLE_BROADCAST

        // Values for delay dragon
        enableInitialSpawnDelay = DEFAULT_ENABLE_INITIAL_SPAWN_DELAY
        initialSpawnDelaySeconds = DEFAULT_INITIAL_SPAWN_DELAY_SECONDS
        showSpawnDelayCountdown = DEFAULT_SHOW_SPAWN_DELAY_COUNTDOWN
        
        healDragonMultiplier = DEFAULT_HEAL_DRAGON_MULTIPLIER
        cageAllEndCrystals = DEFAULT_CAGE_ALL_END_CRYSTALS
        disableSmallTowers = DEFAULT_DISABLE_SMALL_TOWERS
        enableCageCover = DEFAULT_ENABLE_CAGE_COVER
        
        enableShulkers = DEFAULT_ENABLE_SHULKERS
        shulkersPerTower = DEFAULT_SHULKERS_PER_TOWER
        
        enableCageMiningFatigue = DEFAULT_ENABLE_CAGE_MINING_FATIGUE
        miningFatigueLevel = DEFAULT_MINING_FATIGUE_LEVEL
        miningFatigueRange = DEFAULT_MINING_FATIGUE_RANGE
        miningFatigueDuration = DEFAULT_MINING_FATIGUE_DURATION

        splitXP = DEFAULT_SPLIT_XP
        baseDragonXP = DEFAULT_BASE_DRAGON_XP
        additionalXPPerPlayer = DEFAULT_ADDITIONAL_XP_PER_PLAYER
        respawnDragonXP = DEFAULT_RESPAWN_DRAGON_XP
        additionalRespawnXPPerPlayer = DEFAULT_ADDITIONAL_RESPAWN_XP_PER_PLAYER

        perchRespawnsEndCrystals = DEFAULT_PERCH_RESPAWNS_END_CRYSTALS
        endCrystalsRespawned = DEFAULT_END_CRYSTALS_RESPAWNED
        halfNextEndCrystalsRespawned = DEFAULT_HALF_NEXT_END_CRYSTALS_RESPAWNED
        perchRegeneratesCages = DEFAULT_PERCH_REGENERATES_CAGES

        saveConfig()
    }
}