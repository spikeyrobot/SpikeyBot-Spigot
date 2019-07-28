package com.CampbellCrowley.SpikeyBot;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TokenResponse implements Callback {
  Player player;

  public TokenResponse(Player player) {
    this.player = player;
  }

  public void callback(final String res) {
    if (res.length() == 0) {
      player.sendMessage(
          ChatColor.RED + "Failed to fetch token. Please try again later.");
    } else {
      player.sendMessage(
          "Manage your account here:\n" + ChatColor.AQUA + ChatColor.UNDERLINE
              + "https://www.spikeycraft.com/account/?token=" + res);
    }
  }
}
