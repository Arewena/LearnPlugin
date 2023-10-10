package io.github.arwena

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class pluginMain : JavaPlugin(), Listener { //Listener is needed when using EventHandler
    override fun onEnable() {
        //Do Something
        server.pluginManager.registerEvents(this, this) //Essential to use EventHandler
    }

    override fun onDisable() {
        //Do Something
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        // Run when player join
        val sender = event.player
        event.joinMessage(text("${sender.name} joined game").color(NamedTextColor.AQUA))

    }


}