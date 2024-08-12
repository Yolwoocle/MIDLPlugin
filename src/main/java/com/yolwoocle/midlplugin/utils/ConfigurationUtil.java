package com.yolwoocle.midlplugin.utils;

import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.BoundingBox;

public class ConfigurationUtil {
    public static BoundingBox getBoundingBox(ConfigurationSection section) {
        if (section == null)
            return null;
        if (!hasProperties(section, "x1", "x2", "y1", "y2", "z1", "z2"))
            return null;

        return new BoundingBox(
                section.getDouble("x1"),
                section.getDouble("x2"),
                section.getDouble("y1"),
                section.getDouble("y2"),
                section.getDouble("z1"),
                section.getDouble("z2")
        );
    }

    public static void setBoundingBox(ConfigurationSection section, String path, BoundingBox boundingBox) {
        section.set(path + ".x1", boundingBox.getMinX());
        section.set(path + ".x2", boundingBox.getMaxX());
        section.set(path + ".y1", boundingBox.getMinY());
        section.set(path + ".y2", boundingBox.getMaxY());
        section.set(path + ".z1", boundingBox.getMinZ());
        section.set(path + ".z2", boundingBox.getMaxZ());
    }

    public static Color getColor(ConfigurationSection section, String path) {
        if (section == null || !section.contains(path))
            return null;

        String color = section.getString(path);

        try {
            return ChatUtil.parseColor(color);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setColor(ConfigurationSection section, String path, Color color) {
        section.set(path, "#" + Integer.toHexString(color.asRGB()));
    }

    private static boolean hasProperties(ConfigurationSection section, String... names) {
        for (String name : names) {
            if (!section.contains(name))
                return false;
        }
        return true;
    }
}
