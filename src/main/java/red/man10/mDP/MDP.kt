package red.man10.mDP

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

var s : CommandSender? = null
var p : Player? = null
var macrodetection = false
var move = true
var prefix = "§a[MDP]§f"
lateinit var plugin : MDP
var time = 0
class MDP : JavaPlugin() {


    override fun onEnable() {
        plugin = this
        server.pluginManager.registerEvents(EventListener, plugin)
        saveDefaultConfig()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (label == "mdp") {
            // 引数が足りない場合はエラーメッセージを表示
            if (args.isEmpty()) {
                sender.sendMessage(prefix + "コマンドには引数が必要です。")
                return false
            }

            when (args[0]) {
                "help" -> {
                    sender.sendMessage("§a================MacroDetectionPlugin================")
                    sender.sendMessage("§9/mdp action (Player) (time) §aプレイヤーの行動を監視します")
                    sender.sendMessage("§9/mdp action stop §a監視をストップします")
                    sender.sendMessage("§9/mdp move §aPlayerMoveEventのオンオフを切り替えられます")
                    sender.sendMessage("§9/mdp warn (title or spam) (Player) §a警告をします")
                    sender.sendMessage("§a=================================Another tororo_1066")
                }

                "move" -> {
                    if (move) {
                        move = false
                        sender.sendMessage(prefix + "PlayerMoveEventをoffにしました")
                    } else {
                        move = true
                        sender.sendMessage(prefix + "PlayerMoveEventをonにしました")
                    }
                }

                "action" -> {
                    if (args.size < 3) {
                        sender.sendMessage(prefix + "使用方法: /mdp action (Player) (time)")
                        return false
                    }
                    if (args.size == 2 && args[1] == "stop") {
                        time = 0
                        return false
                    }

                    if (macrodetection) {
                        sender.sendMessage(prefix + "他のプレイヤーが実行中です！")
                        return false
                    }

                    s = sender
                    val player: Player? = Bukkit.getPlayer(args[1])
                    p = player!!
                    try {
                        time = args[2].toInt()
                        if (player.isOnline) {
                            s!!.sendMessage(prefix + "開始")
                            object : BukkitRunnable() {
                                override fun run() {
                                    if (time > 0) {
                                        macrodetection = true
                                    }
                                    if (time == 0 || !macrodetection) {
                                        macrodetection = false
                                        sender.sendMessage(prefix + "終了")
                                        cancel()
                                    }
                                    time--
                                }
                            }.runTaskTimer(this, 0, 20)
                        } else {
                            sender.sendMessage(prefix + "このプレイヤーはこのサーバーにいません！")
                        }
                    } catch (e: NumberFormatException) {
                        sender.sendMessage(prefix + "最後は数字を入力してください！")
                    }
                }

                "warn" -> {
                    if (args.size != 3) return false
                    when (args[1]) {
                        "title" -> {
                            saveDefaultConfig()
                            val name = Bukkit.getPlayer(args[2])
                            if (name!!.isOnline) {
                                name.sendTitle(config.getString("titlemessage").toString(), "", 1, 80, 20)
                                name.playSound(
                                    name.location,
                                    Sound.valueOf(config.getString("warnsound").toString()),
                                    100f,
                                    1f
                                )
                            } else {
                                sender.sendMessage("このプレイヤーはこのサーバーにはいません！")
                            }
                        }

                        "spam" -> {
                            saveDefaultConfig()
                            val name = Bukkit.getPlayer(args[2])
                            if (name!!.isOnline) {
                                for (kaisuu in 1..10) {
                                    name.sendMessage(config.getString("spammessage").toString())
                                    name.playSound(
                                        name.location,
                                        Sound.valueOf(config.getString("warnsound").toString()),
                                        100f,
                                        1f
                                    )
                                }
                            } else {
                                sender.sendMessage("このプレイヤーはこのサーバーにはいません！")
                            }
                        }
                    }
                }
            }
        }
        return true
    }
}
