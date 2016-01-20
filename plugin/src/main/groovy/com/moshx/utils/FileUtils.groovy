package com.moshx.utils;

import java.util.regex.Pattern;

public class FileUtils {

    private static WeakHashMap<String, Pattern> PATTERNS_POOL = new WeakHashMap<String, Pattern>();
    private static final Pattern DEFAULT_PATTER = Pattern.compile(".*");


    public static  class PatternFileFilter implements FileFilter {

        private final Pattern pattern;

        public PatternFileFilter(String regex) {

            if (regex != null) {
                if (PATTERNS_POOL.containsKey(regex)) {
                    pattern = PATTERNS_POOL.get(regex);
                } else {
                    pattern = Pattern.compile(regex);
                    PATTERNS_POOL.put(regex, pattern);
                }
            } else {
                pattern = DEFAULT_PATTER;
            }
        }

        @Override
        public boolean accept(File file) {
            return file.isFile() && pattern.matcher(file.getName()).matches();
        }
    }

    public static   class DirectoryFileFilter implements FileFilter{

        @Override
        public boolean accept(File file) {
            return file.isDirectory();
        }
    }

}
