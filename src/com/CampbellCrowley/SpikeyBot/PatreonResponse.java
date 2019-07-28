package com.CampbellCrowley.SpikeyBot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.lucko.luckperms.api.DataMutateResult;
import me.lucko.luckperms.api.Group;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.User;

public class PatreonResponse implements Callback {
  Player player;
  boolean sendReply = false;
  Plugin plugin;

  public PatreonResponse(Player player, Plugin plugin) {
    this.player = player;
    this.plugin = plugin;
  }

  public PatreonResponse(Player player, Plugin plugin, boolean sendReply) {
    this.player = player;
    this.sendReply = sendReply;
    this.plugin = plugin;
  }

  public void callback(final String res) {
    if (this.sendReply) {
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
    FileConfiguration config = this.plugin.getConfig();
    String groupName = config.getString("luckperms.donator-group");
    if (this.player.isOnline() && config.getBoolean("luckperms.enabled")
        && groupName != null && groupName.length() > 0) {
      boolean goalGroup = res.equals("pledged");

      RegisteredServiceProvider<LuckPermsApi> provider = Bukkit
          .getServicesManager().getRegistration(LuckPermsApi.class);
      if (provider != null) {
        LuckPermsApi lpApi = provider.getProvider();
        User user = lpApi.getUserManager().getUser(this.player.getUniqueId());
        Group group = lpApi.getGroupManager().getGroup(groupName);

        if (group == null) {
          this.plugin.getLogger().severe("Unable to find group: " + groupName);
          return;
        }

        Node node = lpApi.getNodeFactory().makeGroupNode(group).build();

        if (goalGroup) {
          DataMutateResult result = user.setTransientPermission(node);
          if (result != DataMutateResult.ALREADY_HAS) {
            String message = config.getString("luckperms.give-role-message");
            if (message != null && message.length() > 0) {
              this.player.sendMessage(message);
            }
          }
        } else if (!goalGroup) {
          user.unsetTransientPermission(node);
        }
      }
    }
  }
}
