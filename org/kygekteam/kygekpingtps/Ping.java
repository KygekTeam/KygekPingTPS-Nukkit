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

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class Ping {

    private final CommandSender sender;
    private final String[] cmdArgs;
    private final Main main;

    public Ping(CommandSender sender, String[] cmdArgs, Main main) {
        this.sender = sender;
        this.cmdArgs = cmdArgs;
        this.main = main;
    }

    public void execute() {
        if (!this.sender.hasPermission("kygekpingtps.ping")) {
            String noperm = Main.replace(this.main.getConfig().getString("no-permission", ""));
            noperm = noperm.isEmpty() ? Main.PREFIX + TextFormat.RED + "You do not have permission to use this command" : noperm;

            this.sender.sendMessage(noperm);
            return;
        }

        if (this.cmdArgs.length < 1) {
            if (!(this.sender instanceof Player)) {
                this.sender.sendMessage(Main.PREFIX + TextFormat.WHITE + "Usage: /ping <player>");
            } else {
                String ping = Main.replace(this.main.getConfig().getString("player-ping", "")).replace("{player}", "Your").replace("{ping}", Integer.toString(((Player) sender).getPing()));
                ping = ping.isEmpty() ? Main.PREFIX + TextFormat.GREEN + "Your current ping: " + TextFormat.AQUA + ((Player) sender).getPing() : ping;

                this.sender.sendMessage(ping);
            }
            return;
        }

        Player player = Server.getInstance().getPlayer(this.cmdArgs[0]);
        if (player == null) {
            String notfound = Main.replace(this.main.getConfig().getString("player-not-found", ""));
            notfound = notfound.isEmpty() ? Main.PREFIX + TextFormat.RED + "Player was not found" : notfound;

            this.sender.sendMessage(notfound);
            return;
        }

        String ping = Main.replace(this.main.getConfig().getString("player-ping", "")).replace("{player}", player.getName()).replace("{ping}", Integer.toString(player.getPing()));
        ping = ping.isEmpty() ? Main.PREFIX + TextFormat.GREEN + player.getName() + "'s current ping: " + TextFormat.AQUA + player.getPing() : ping;

        this.sender.sendMessage(ping);
    }

}
