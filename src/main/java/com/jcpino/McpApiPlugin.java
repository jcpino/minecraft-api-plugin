package com.jcpino;

import org.bukkit.plugin.java.JavaPlugin;

public class McpApiPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("¡Plugin de conexión a API encendido!");
        // Le decimos al servidor que empiece a escuchar los eventos de los jugadores
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }
}