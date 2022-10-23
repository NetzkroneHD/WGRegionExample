package de.netzkronehd.wgregionevents.example;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.netzkronehd.wgregionevents.events.RegionEnterEvent;
import de.netzkronehd.wgregionevents.events.RegionEnteredEvent;
import de.netzkronehd.wgregionevents.events.RegionLeaveEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;

public class WGRegionExample extends JavaPlugin implements Listener {

    public static final String PREFIX = "§8[§aWGE-Example§8]§7 ";

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        final WgCommand wgCommand = new WgCommand();
        getCommand("wge").setExecutor(wgCommand);
        getCommand("wge").setTabCompleter(wgCommand);

    }

    @EventHandler
    public void onRegionEnter(RegionEnterEvent e) {
        e.getPlayer().sendMessage(PREFIX+"You are entering§e "+e.getRegion().getId()+"§7.");
    }

    @EventHandler
    public void onRegionEntered(RegionEnteredEvent e) {
        e.getPlayer().sendMessage(PREFIX+"You entered§e "+e.getRegion().getId()+"§7.");
    }

    @EventHandler
    public void onRegionLeave(RegionLeaveEvent e) {
        e.getPlayer().sendMessage(PREFIX+"You are leaving§e "+e.getRegion().getId()+"§7.");
    }

    @EventHandler
    public void onRegionLeft(RegionLeftEvent e) {
        e.getPlayer().sendMessage(PREFIX+"You left§e "+e.getRegion().getId()+"§7.");
    }

}
