package com.CampbellCrowley.SpikeyBot;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PatreonResponse implements Callback {
  Player player;

  public PatreonResponse(Player player) {
    this.player = player;
  }

  public void callback(final String res) {
    if (res.equals("pledged")) {
      this.player.sendMessage(ChatColor.GREEN
          + "Thank you for supporting Spikey! Your accounts are linked.");
    } else if (res.equals("nopledge")) {
      this.player.sendMessage(
          ChatColor.GREEN + "Your account is linked with Spikey's Patreon, "
              + "but you have not pledged.");
    } else if (res.equals("unlinked")) {
      this.player.sendMessage(ChatColor.YELLOW
          + "Your account is not yet linked with Spikey's Patreon."
          + "\nLink with '/patreon link'.");
    } else {
      this.player.sendMessage(ChatColor.RED
          + "Failed to check Patreon status. Please try again later.\n(" + res
          + ")");
    }
  }
}
