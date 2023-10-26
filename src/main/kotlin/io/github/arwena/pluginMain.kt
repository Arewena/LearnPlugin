package io.github.arwena

import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.title.Title
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
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


        kommand()
        setupRecipe()
        server.pluginManager.registerEvents(this, this) //Essential to use EventHandler
    }


    fun kommand() {
        kommand {
            //Type 1
            register("ping") {
                executes {
                    sender.sendMessage("pong")
                }
            }
            //Type 2
            register("a") {
                requires { sender.isOp }
                executes { sender.sendMessage("b") }
            }
            //Type 3
            register("T1") {
                then("fighting") {
                    executes {
                        //코드 참조: https://github.com/monun/series-survival/blob/master/src/main/kotlin/com/github/monun/survival/Bio.kt
                        val title = Title.title(
                            text("T1 Fighting").color(NamedTextColor.RED).decorate(TextDecoration.BOLD),
                            text("LCK Fighting")
                        )

                        Bukkit.getServer().showTitle(title)
                    }
                }
            }

            //Type 4
            register("LCK") {
                then("team" to string()) {
                    executes {
                        val team: String by it
                        sender.sendMessage("LCK $team Fighting")
                    }
                }
            }
            register("firework") {
                requires { sender is Player }
                executes {
                    val sender = sender as Player
                    val location = sender.location
                    val world = location.world

                    world.spawn(location, ArmorStand::class.java).apply {
                        customName = "Firework!"
                        isCustomNameVisible = true
                        isInvisible = true
                        setGravity(false)
                        isInvulnerable = true
                    }
                }
            }
        }

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