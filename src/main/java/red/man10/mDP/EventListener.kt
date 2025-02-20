package red.man10.mDP

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.*

object EventListener : Listener {
    var move = false
    @EventHandler
    fun click(e: PlayerInteractEvent) {
        if (macrodetection && e.player == p) {
            if (e.action == Action.RIGHT_CLICK_BLOCK || e.action == Action.RIGHT_CLICK_AIR) {
                s?.sendMessage(prefix + e.player.name + "の右クリックを検知しました(PlayerInteractEvent)")
            } else {
                s?.sendMessage(prefix + e.player.name + "の左クリックを検知しました(PlayerInteractEvent)")
            }
        }
    }

    @EventHandler
    fun move(e: PlayerMoveEvent) {
        if (macrodetection && e.player == p && EventListener.move) {
            if (e.from.x == e.to.x && e.from.y == e.to.y && e.from.z == e.to.z) {
                s?.sendMessage(prefix + e.player.name + "の視点の変更を検知しました(PlayerMoveEvent)")
            } else {
                s?.sendMessage(prefix + e.player.name + "の移動を検知しました(PlayerMoveEvent)")
            }
        }
    }


    @EventHandler
    fun command(e: PlayerCommandPreprocessEvent) {
        if (macrodetection && e.player == p) {
            s?.sendMessage(prefix + e.player.name + "がコマンド§a" + e.message + "§fを使用しました(PlayerCommandPreprocessEvent)")
        }
    }

    @EventHandler
    fun sneak(e: PlayerToggleSneakEvent) {
        if (macrodetection && e.player == p) {
            s?.sendMessage(prefix + e.player.name + "がスニーク状態を切り替えました(PlayerCommandPreprocessEvent)")
        }
    }

    @EventHandler
    fun openinv(e: InventoryOpenEvent) {
        if (macrodetection && e.player == p) {
            if (e.inventory == e.player.inventory) {
                s?.sendMessage(prefix + e.player.name + "が自分のインベントリを開きました(InventoryOpenEvent)")
            } else {
                s?.sendMessage(prefix + e.player.name + "がインベントリを開きました(InventoryOpenEvent)")
            }
        }
    }

    @EventHandler
    fun eat(e: PlayerItemConsumeEvent) {
        if (macrodetection && e.player == p) {
            s?.sendMessage(prefix + e.player.name + "が" + e.item.type.name + "を食べました(PlayerItemConsumeEvent)")

        }


    }
}