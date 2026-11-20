package com.jcpino;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();

        // BUENA PRÁCTICA: Usar un hilo asíncrono para no congelar el servidor de Minecraft
        // mientras esperamos la respuesta de la API.
        org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(McpApiPlugin.getPlugin(McpApiPlugin.class), () -> {
            enviarPeticionApi(playerName);
        });
    }

    private void enviarPeticionApi(String nombre) {
        try {
            URL url = new URL("http://localhost:8080/api/jugadores");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // El JSON que enviaremos a Spring Boot (con la fecha de hoy simplificada)
            String jsonInputString = String.format(
                    "{\"nombre\": \"%s\", \"rango\": \"JUGADOR\", \"ultimaConexion\": \"2026-11-19\"}",
                    nombre
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            McpApiPlugin.getPlugin(McpApiPlugin.class).getLogger().info("API respondió con código: " + code);

        } catch (Exception e) {
            McpApiPlugin.getPlugin(McpApiPlugin.class).getLogger().warning("Error al conectar con la API: " + e.getMessage());
        }
    }
}