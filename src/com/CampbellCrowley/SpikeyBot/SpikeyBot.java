package com.CampbellCrowley.SpikeyBot;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.lucko.luckperms.api.LuckPermsApi;

public class SpikeyBot extends JavaPlugin implements Listener {
  LuckPermsApi lpApi;

  @Override
  public void onEnable() {
    getConfig().options().copyDefaults(true);
    this.saveDefaultConfig();
    
    getServer().getPluginManager().registerEvents(this, this);

    if (getConfig().getBoolean("luckperms.enabled")) {
      RegisteredServiceProvider<LuckPermsApi> provider = Bukkit
          .getServicesManager().getRegistration(LuckPermsApi.class);
      if (provider != null) {
        this.lpApi = provider.getProvider();
      } else {
        getLogger().warning(
            "LuckPerms not found. Automatic role management is disabled.");
      }
    }
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label,
      String[] args) {
    if (cmd.getName().equalsIgnoreCase("patreon")) {
      if (!(sender instanceof Player)) {
        sender.sendMessage("This command can only be run by a player.");
        return true;
      }
      if (!sender.hasPermission("SB.patreon")) {
        sender.sendMessage("You don't have permission for this command.");
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
        fetchPatreon((Player) sender, true);
      }
      return true;
    }
    return false;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent evt) {
    if (this.lpApi == null || !getConfig().getBoolean("luckperms.enabled")
        || !getConfig().getBoolean("luckperms.update-on-login")
        || getConfig().getString("luckperms.donator-group").length() == 0) {
      return;
    }
    final Player player = evt.getPlayer();
    fetchPatreon(player, false);
  }

  private void fetchPatreon(Player player, boolean sendReply) {
    final String uuid = player.getUniqueId().toString().replace("-", "");
    Thread t = new Thread(
        new HttpsRequest("https://www.spikeycraft.com/fetch/patreon/" + uuid,
            new PatreonResponse(player, this, sendReply)));
    t.start();
  }
}