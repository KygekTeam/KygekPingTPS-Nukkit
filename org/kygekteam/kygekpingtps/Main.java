/*
 * See server TPS and a player's ping
 * Copyright (C) 2020 KygekTeam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package org.kygekteam.kygekpingtps;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import java.io.File;

public class Main extends PluginBase {

    public static final String PREFIX = TextFormat.YELLOW + "[KygekPingTPS] ";

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.checkConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.getConfig().reload();
        switch (command.getName().toLowerCase()) {
            case "tps":
                new TPS(sender, this).execute();
                break;
            case "ping":
                new Ping(sender, args, this).execute();
                break;
        }
        return true;
    }

    private void checkConfig() {
        if (!this.getConfig().getString("config-version").equals("1.0")) {
            this.getLogger().notice("Your configuration file is outdated, updating the config.yml...");
            this.getLogger().notice("The old configuration file can be found at config_old.yml");
            this.renameConfig();
            this.saveDefaultConfig();
            this.reloadConfig();
        }
    }

    private void renameConfig() {
        File oldConfig = new File(this.getDataFolder() + "/config.yml");
        File newConfig = new File(this.getDataFolder() + "/config-old.yml");

        if (newConfig.exists()) newConfig.delete();

        oldConfig.renameTo(newConfig);
    }

    public static String replace(String string) {
        return string.replace("{prefix}", PREFIX).replace("&", "§");
    }

}
