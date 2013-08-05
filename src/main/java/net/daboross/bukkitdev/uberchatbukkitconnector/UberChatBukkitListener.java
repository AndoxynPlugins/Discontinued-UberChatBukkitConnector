/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.uberchatbukkitconnector;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 *
 * @author daboross
 */
public class UberChatBukkitListener implements PluginMessageListener {

    private UberChatBukkitConnectorPlugin plugin;

    public UberChatBukkitListener(UberChatBukkitConnectorPlugin plugin) {
        this.plugin = plugin;
        System.out.println("Initialized.");
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        System.out.println("Channel player=" + player + " channel=" + channel);
        if (!channel.equalsIgnoreCase("BungeeCord")) {
            return;
        }
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        String subchannel;
        try {
            subchannel = in.readUTF();

            if (subchannel.equals("UberChat")) {
                String action;
                action = in.readUTF();
                if (action.equals("SetDisplayName")) {
                    player.setDisplayName(in.readUTF());
                } else if (action.equals("ConsoleMessage")) {
                    plugin.getLogger().log(Level.INFO, in.readUTF());
                }
            }
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error", ex);
        }
    }
}
