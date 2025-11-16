package practica11;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public final class RecursosMatta {
    private RecursosMatta() {}

    public static final String RDIR    = "resources";
    public static final String LOGO1   = "logo_matta1.png";
    public static final String LOGO2   = "logo_matta2.png";
    public static final String STYLE   = "style.css";
    public static final String CSV_IN = "estudiantes.csv";

    private static Path resolvePaqueteDir() throws Exception {
        URL url = RecursosMatta.class.getResource("");
        if (url != null && "file".equalsIgnoreCase(url.getProtocol())) return Paths.get(url.toURI());
        Path fb = Paths.get("practica11").toAbsolutePath().normalize();
        Files.createDirectories(fb);
        return fb;
    }
    private static Path ensureResourcesDir() throws Exception {
        Path r = resolvePaqueteDir().resolve(RDIR);
        Files.createDirectories(r);
        return r;
    }

    private static void createPlaceholder(Path pngPath, Color bg, String title, String sub) throws IOException {
        int S = 512;
        BufferedImage img = new BufferedImage(S, S, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(bg); g.fillRect(0,0,S,S);
        g.setColor(Color.WHITE); g.setStroke(new BasicStroke(6f)); g.drawRect(8,8,S-16,S-16);
        g.setFont(new Font("SansSerif", Font.BOLD, 56));
        FontMetrics fm = g.getFontMetrics();
        g.drawString(title, (S - fm.stringWidth(title))/2, (S/2) - fm.getAscent()/2);
        g.setFont(new Font("SansSerif", Font.PLAIN, 26));
        fm = g.getFontMetrics();
        g.drawString(sub, (S - fm.stringWidth(sub))/2, (S/2) + 40);
        g.dispose();
        ImageIO.write(img, "png", pngPath.toFile());
    }
    public static void ensureLogos() {
        try {
            Path rdir = ensureResourcesDir();
            Path l1 = rdir.resolve(LOGO1), l2 = rdir.resolve(LOGO2);
            if (!Files.exists(l1)) createPlaceholder(l1, new Color(30,110,190), "LOGO 1", "a");
            if (!Files.exists(l2)) createPlaceholder(l2, new Color(190,80,50), "LOGO 2", "b");
        } catch (Exception e) { throw new RuntimeException("no se pudieron asegurar los logos", e); }
    }
    public static void ensureStyle() {
        try {
            Path css = ensureResourcesDir().resolve(STYLE);
            if (!Files.exists(css)) {
                String def = "button-font-family: SansSerif;\nbutton-font-size: 14;\nbutton-font-style: plain;\n";
                Files.write(css, def.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            }
        } catch (Exception e) { throw new RuntimeException("no se pudo asegurar style.css", e); }
    }
    public static Font loadButtonFont() {
        try {
            Path css = ensureResourcesDir().resolve(STYLE);
            if (!Files.exists(css)) return new Font("SansSerif", Font.PLAIN, 14);
            List<String> lines = Files.readAllLines(css, StandardCharsets.UTF_8);
            String family = "SansSerif"; int size = 14; int style = Font.PLAIN;
            for (String raw: lines) {
                String s = raw.trim(); if (s.isEmpty() || s.startsWith("#") || s.startsWith("/*")) continue;
                String[] kv = s.split(":"); if (kv.length < 2) continue;
                String k = kv[0].trim().toLowerCase(); String v = kv[1].replace(";", "").trim();
                if (k.equals("button-font-family")) family = v;
                else if (k.equals("button-font-size")) { try { size = Integer.parseInt(v);} catch(Exception ignore){} }
                else if (k.equals("button-font-style")) {
                    String vv = v.toLowerCase();
                    style = vv.contains("bold") && vv.contains("italic") ? Font.BOLD|Font.ITALIC
                          : vv.contains("bold") ? Font.BOLD
                          : vv.contains("italic") ? Font.ITALIC : Font.PLAIN;
                }
            }
            return new Font(family, style, size);
        } catch (Exception e) { return new Font("SansSerif", Font.PLAIN, 14); }
    }
    public static ImageIcon loadLogo(String fileName, int targetSize) {
        String cp = "/practica11/" + RDIR + "/" + fileName;
        URL url = RecursosMatta.class.getResource(cp);
        Image img = null;
        try {
            if (url != null) img = new ImageIcon(url).getImage();
            else {
                Path p = ensureResourcesDir().resolve(fileName);
                if (Files.exists(p)) img = new ImageIcon(p.toString()).getImage();
            }
        } catch (Exception ignore) {}
        if (img == null) {
            BufferedImage bi = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            g.setColor(Color.LIGHT_GRAY); g.fillRect(0,0,targetSize,targetSize);
            g.setColor(Color.DARK_GRAY); g.drawRect(1,1,targetSize-2,targetSize-2);
            g.dispose();
            return new ImageIcon(bi);
        }
        return new ImageIcon(img.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH));
    }

    //rutas CSV para lectura y escritura
    public static Path csvInputRuntime() {
        try { return ensureResourcesDir().resolve(CSV_IN); }
        catch (Exception e) { return Paths.get(CSV_IN).toAbsolutePath().normalize(); }
    }
}