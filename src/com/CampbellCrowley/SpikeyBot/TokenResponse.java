package com.CampbellCrowley.SpikeyBot;

import org.bukkit.entity.Player;

public class TokenResponse implements Callback {
  Player player;

  public TokenResponse(Player player) {
    this.player = player;
  }

  public void callback(final String res) {
    if (res.length() == 0) {
      player.sendMessage("Failed to fetch token. Please try again later.");
    } else {
      player.sendMessage(
          "Begin linking your account here: https://www.spikeycraft.com/"
              + "account/?token=" + res);
    }
  }
}
