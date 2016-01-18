package com.moshx.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.WeakHashMap;
import java.util.regex.Pattern;

public class FileUtils {

    private static WeakHashMap<String, Pattern> PATTERNS_POOL = new WeakHashMap<String, Pattern>();

    public static File[] listFiles(File dir, String excludePattern) {

        if (dir == null || !dir.isDirectory()) {
            return new File[0];
        }

        Pattern pattern = null;

        if (excludePattern != null) {
            pattern = PATTERNS_POOL.get(excludePattern);
            if (pattern == null) {
                pattern = Pattern.compile(excludePattern);
                PATTERNS_POOL.put(excludePattern, pattern);
            }
        }

        final Pattern finalPattern = pattern;
        File[] result = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isFile()) {
                    if (finalPattern != null) {
                        return !finalPattern.matcher(f.getName()).matches();
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });

        return result;
    }


    public static File[] listDirectories(File dir) {

        if (dir == null || !dir.isDirectory()) {
            return new File[0];
        }

        File[] result = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }
        });
        return result;
    }

}
