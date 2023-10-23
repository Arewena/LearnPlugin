package io.github.arwena

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler

class pluginMain : JavaPlugin(), Listener { //Listener is needed when using EventHandler
    override fun onEnable() {
        //Do Something
        setupRecipe()
        server.pluginManager.registerEvents(this, this) //Essential to use EventHandler
    }


    fun setupRecipe() {
        //코드 참조: https://github.com/monun/series-survival/blob/master/src/main/kotlin/com/github/monun/survival/plugin/SurvivalPlugin.kt
        server.addRecipe(
            ShapedRecipe(NamespacedKey(this, "1"), ItemStack(Material.TOTEM_OF_UNDYING).apply {
            editMeta {
                it.displayName(text("THE WORLD"))
                it.lore(listOf(text("궁극의 스탠드").color(NamedTextColor.LIGHT_PURPLE)))

            }
        }).apply {
            shape("ENE",
                  "NTN",
                  "ENE")
            setIngredient('E', Material.EMERALD)
            setIngredient('T', Material.TOTEM_OF_UNDYING)
            setIngredient('B', Material.NETHER_STAR)

        })
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

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true) //priority = 우선도, ignoreCancelled = 핸들러가 취소된 이벤트를 무시하는지의 여부
    fun onTeleport(event: PlayerTeleportEvent) {
        if (!event.player.isOp) {
            event.isCancelled = true

            val scheduler = Bukkit.getScheduler().runTaskTimer(this,
                Runnable {
                    event.player.sendMessage("It is not able to Teleport!")
                }, 10L, 0L)
        }

    }


    @EventHandler
    fun onRightClick(event: PlayerInteractEvent) {
        val player = event.player
        if (event.action == Action.RIGHT_CLICK_AIR && player.isSneaking) {
            val item = ItemStack(Material.TOTEM_OF_UNDYING).apply {
                itemMeta = itemMeta.apply {
                    displayName(text("THE WORLD").color(NamedTextColor.GOLD))
                }
            }

            player.inventory.addItem(item)
        }
    }


}