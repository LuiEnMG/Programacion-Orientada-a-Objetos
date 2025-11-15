package practica10;

import java.io.IOException;
import java.nio.file.*;
import java.util.logging.*;

public final class LogConfigMatta {
    private static boolean listo = false;
    private LogConfigMatta() {}

    public static synchronized void init() {
        if (listo) return;
        try {
            Path dir = Paths.get("test", "practica10");
            Files.createDirectories(dir);
            Path log = dir.resolve("exec_2114428.log");

            Logger root = Logger.getLogger("");
            for (Handler h : root.getHandlers()) root.removeHandler(h);

            FileHandler fh = new FileHandler(log.toString(), true);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.FINE);

            root.addHandler(fh);
            root.setLevel(Level.FINE);

            listo = true;
            Logger.getLogger(LogConfigMatta.class.getName())
                  .info("logger inicializado para Luis Enrique Matta Gonzalez (2114428)");
        } catch (IOException e) {
            throw new RuntimeException("no se pudo inicializar el logger", e);
        }
    }
}