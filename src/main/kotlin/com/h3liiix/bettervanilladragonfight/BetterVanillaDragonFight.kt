package com.h3liiix.bettervanilladragonfight

import net.fabricmc.api.ModInitializer

class BetterVanillaDragonFight : ModInitializer {

    override fun onInitialize() {
        LOGGER.info("Better Vanilla Dragon Fight mod initializing...")

        ConfigManager.loadConfig()
        DragonEventHandler.register()
        ModCommands.register()

        LOGGER.info("Better Vanilla Dragon Fight mod initialized. Event listener and reload command registered.")
    }
}