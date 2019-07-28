package com.CampbellCrowley.SpikeyBot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpikeyBot extends JavaPlugin {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label,
      String[] args) {
    if (cmd.getName().equalsIgnoreCase("patreon")) {
      if (!(sender instanceof Player)) {
        sender.sendMessage("This command can only be run by a player.");
        return true;
      }
      final String uuid = ((Player) sender).getUniqueId().toString()
          .replace("-", "");
      if (args.length > 0 && (args[0].equalsIgnoreCase("link")
          || args[0].equalsIgnoreCase("unlink"))) {
        Thread t = new Thread(new HttpsRequest(
            "https://www.spikeycraft.com/begin/minecraft/" + uuid,
            new TokenResponse((Player) sender)));
        t.start();
      } else {
        Thread t = new Thread(new HttpsRequest(
            "https://www.spikeycraft.com/fetch/patreon/" + uuid,
            new PatreonResponse((Player) sender)));
        t.start();
      }
      return true;
    }
    return false;
  }
}