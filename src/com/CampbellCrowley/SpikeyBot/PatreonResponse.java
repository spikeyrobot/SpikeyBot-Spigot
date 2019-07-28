package com.CampbellCrowley.SpikeyBot;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PatreonResponse implements Callback {
  Player player;
  JavaPlugin plugin;

  public PatreonResponse(Player player, JavaPlugin plugin) {
    this.player = player;
    this.plugin = plugin;
  }

  public void callback(final String res) {
    if (res.equals("true")) {
      this.player.sendMessage("Your account is linked with Spikey's Patreon.");
    } else {
      this.player.sendMessage(
          "Your account is not yet linked with Spikey's Patreon. Link with '/patreon link'.");
    }
  }
}
