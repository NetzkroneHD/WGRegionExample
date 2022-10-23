package de.netzkronehd.wgregionevents.example;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.netzkronehd.wgregionevents.api.SimpleWorldGuardAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WgCommand implements CommandExecutor, TabExecutor {

    private final SimpleWorldGuardAPI simpleWorldGuardAPI;

    public WgCommand() {
        simpleWorldGuardAPI = new SimpleWorldGuardAPI();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 2) {
            if(args[0].equals("playersinregion")) {
                final List<Player> players = simpleWorldGuardAPI.getPlayersInRegion(args[1]);
                final StringBuilder sb = new StringBuilder(WGRegionExample.PREFIX+"Player: ");
                for(Player player : players) {
                    sb.append(player.getName()).append(", ");
                }

                sender.sendMessage(sb.toString());
                return true;
            } else if(args[0].equalsIgnoreCase("regions")) {
                final Player p = Bukkit.getPlayer(args[1]);
                if(p != null) {
                    final StringBuilder sb = new StringBuilder(WGRegionExample.PREFIX+"Regions: ");
                    for (ProtectedRegion region : simpleWorldGuardAPI.getRegions(p.getLocation())) {
                        sb.append(region.getId()).append("§7, ");
                    }

                    sender.sendMessage(sb.toString());
                } else sender.sendMessage(WGRegionExample.PREFIX+"That player is offline.");

                return true;
            }
        }
        sendHelp(sender);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        final List<String> tabs = new ArrayList<>();
        if(args.length == 1) {
            args[0] = args[0].toLowerCase();

            if("regions".startsWith(args[0])) tabs.add("regions");
            if("playersinregion".startsWith(args[0])) tabs.add("playersinregion");

        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("regions")) {
                args[1] = args[1].toLowerCase();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if(player.getName().toLowerCase().startsWith(args[1])) {
                        tabs.add(player.getName());
                    }
                }
            } else if(args[0].equalsIgnoreCase("playersinregion")) {
                args[1] = args[1].toLowerCase();
                for(ProtectedRegion pr : simpleWorldGuardAPI.getRegions().values()) {
                    if(pr.getId().toLowerCase().startsWith(args[1])) {
                        tabs.add(pr.getId());
                    }
                }
            }
        }
        return tabs;
    }

    private void sendHelp(CommandSender s) {
        s.sendMessage(WGRegionExample.PREFIX+"Wrong usage, please use:");
        s.sendMessage("§e/wge playersinregion§8 <§eRegion§8>");
        s.sendMessage("§e/wge regions§8 <§ePlayer§8>");

    }

}
