package me.alpha432.oyvey.manager;

import me.alpha432.oyvey.util.traits.Util;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SpotifyManager implements Util {
    private String currentTrack = "Not playing";
    private long lastUpdate = 0;

    public void update() {
        if (System.currentTimeMillis() - lastUpdate < 5000)
            return;
        lastUpdate = System.currentTimeMillis();

        new Thread(() -> {
            try {
                Process process = Runtime.getRuntime().exec(
                        "powershell -command \"Get-Process Spotify | Where-Object {$_.MainWindowTitle} | Select-Object -ExpandProperty MainWindowTitle\"");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = reader.readLine();
                if (line != null && !line.isEmpty()) {
                    currentTrack = line;
                } else {
                    currentTrack = "Not playing";
                }
                process.destroy();
            } catch (Exception ignored) {
                currentTrack = "Not playing";
            }
        }).start();
    }

    public String getCurrentTrack() {
        return currentTrack;
    }
}
