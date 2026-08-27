# Better Vanilla Dragon Fight

A lightweight **Fabric mod** that has various configurable options to enhance the **Ender Dragon Fight** whilst keeping the **Vanilla feel of Minecraft**. For servers, no installation is needed on the client.

---

![Dragon Fight](https://raw.githubusercontent.com/H3LiiiX/BetterVanillaDragonFight/refs/heads/main/images/Overview.png)

---

## Features

- **Dynamic Health Scaling:** The Ender Dragon's max health increases with each eligible player in The End.
- **XP Distribution:** Split dragon kill XP equally among all players and scale XP payouts based on player count.
- **Enhanced Towers & Crystals:** Cage every end crystal, replace small towers with bigger ones, and apply a crystal healing multiplier.
- **Tower Shulkers:** Spawns Shulkers on the sides of the end towers.
- **Cage Mining Fatigue:** Localized mining fatigue around end crystals to make breaking cages more challenging.
- **Dynamic Crystal Respawns:** The dragon can regenerate end crystals and cages when perching.
- **Initial Spawn Delay:** Optionally delay the very first dragon spawn and show a countdown on players’ XP bars.
- **Client Config:** Fully integrates with Mod Menu for an intuitive settings screen.
- **Broadcast Messages:** Optional announcements when a scaled dragon appears.
- **In-Game Reload:** `/bettervanilladragonfight reload` to reload config without restarting the server.

---

## Configuration

After first launch, a config file will be generated at:

```
/config/bettervanilladragonfight.properties
```

You can configure:

```
enableMod=true                          # Enable or disable the mod
scaleWithOnePlayer=false                # Whether scaling starts with the first player
countCreativeModePlayers=false          # Include creative players in scaling
baseDragonHealth=200.0                  # Base health of the Ender Dragon
additionalHealthPerPlayer=100.0         # Health added per eligible player
enableBroadcast=true                    # Enable broadcast when dragon spawns

# Initial Spawn Delay
enableInitialSpawnDelay=true            # Delay the first dragon spawn in The End
initialSpawnDelaySeconds=60             # Time (in seconds) before first dragon spawns
showSpawnDelayCountdown=true            # Show countdown above XP bar during delay

# End Towers & Crystals
healDragonMultiplier=10.0               # Multiplier for how much crystals heal the dragon
cageAllEndCrystals=true                 # Generate cages on every end crystal
disableSmallTowers=true                 # Increases the size of the smaller towers
enableCageCover=true                    # Covers cages with additional blocks

# Shulkers
enableShulkers=true                     # Spawns shulkers on the towers
shulkersPerTower=3                      # Number of shulkers to spawn per tower

# Cage Mining Fatigue
enableCageMiningFatigue=true            # End crystals apply mining fatigue to nearby players
miningFatigueLevel=2                    # The level/amplifier of the mining fatigue effect
miningFatigueRange=6.0                  # Radius in blocks where players receive mining fatigue
miningFatigueDuration=5                 # Duration in seconds of the mining fatigue effect

# XP
splitXP=true                            # Splits dragon XP equally among players instead of dropping orbs
baseDragonXP=12000                      # Base XP given for the first dragon kill
additionalXPPerPlayer=3000              # Additional XP for each extra player on the first kill
respawnDragonXP=500                     # Base XP given for respawned dragons
additionalRespawnXPPerPlayer=200        # Additional XP for each extra player on respawned kills

# Perch End Crystal Respawn
perchRespawnsEndCrystals=true           # Dragon respawns crystals when perching
endCrystalsRespawned=4                  # Number of crystals to respawn per perch
halfNextEndCrystalsRespawned=true       # Halves the number of crystals respawned on subsequent perches
perchRegeneratesCages=true              # Regenerate cages along with respawned crystals
```

---

## Commands

| Command                       | Description                         |
|------------------------------|-------------------------------------|
| `/bettervanilladragonfight reload`  | Reloads the mod’s configuration     |

Requires permission level 2 (OP status).

---

## Compatibility

- Minecraft: `26.2`
- Fabric Loader: Latest stable version for your Minecraft version
- Fabric API required


> **Mod/Datapack Interactions:** This mod modifies vanilla Ender Dragon spawning logic. It may exhibit unintended behavior or conflicts with other mods/datapacks that introduce custom dragon spawning mechanics (e.g., True Ending). While I aim to improve compatibility in the future, full support for third-party dragon overhauls is not guaranteed.
---

## Installation

1. Install [Fabric Loader](https://fabricmc.net/) on your server.
2. Install [Fabric API](https://modrinth.com/mod/fabric-api) on the server.
3. Place the `bettervanilladragonfight-vX.X.X_X.X.X.jar` into your server's `mods` folder. 
   *(Note: The first version number is the mod version, and the second is the Minecraft version, e.g., `v1.0.0_26.2.0`)*
4. Start the server once to generate the config file.
5. Modify the config if desired and reload with `/bettervanilladragonfight reload`.

---

## License

MIT License - free to use, modify, and distribute.

---

## Authors

Original [mod](https://modrinth.com/mod/scaled-dragon-fight) by [ZephByte](https://github.com/ZephByte)

Overhaled by [H3LiiiX](https://github.com/H3LiiiX)
