package com.moshx.utils;

import java.util.regex.Pattern;

public class FileUtils {

    private static WeakHashMap<String, Pattern> PATTERNS_POOL = new WeakHashMap<String, Pattern>();
    private static final Pattern DEFAULT_PATTER = Pattern.compile('^.*');


    public static class PatternFileFilter implements FileFilter {

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
            return  file.isFile() && !pattern.matcher(file.getName()).matches();
        }
    }

    public static class DirectoryFileFilter implements FileFilter {

        @Override
        public boolean accept(File file) {
            return file.isDirectory();
        }
    }

    public static class HiddenFileFilter implements FileFilter {

        final boolean includeHidden;

        HiddenFileFilter() {
            this(false)
        }

        HiddenFileFilter(boolean includeHidden) {
            this.includeHidden = includeHidden
        }


        @Override
        boolean accept(File pathname) {
            if (includeHidden) {
                return true;
            } else {
                return !pathname.hidden;
            }
        }
    }


    public static class ChainFilter implements FileFilter {

        final FileFilter[] filters;

        ChainFilter(FileFilter... filters) {
            this.filters = filters
        }

        @Override
        boolean accept(File file) {
            boolean result = true;
            if (filters) {
                filters.each {
                    result &= it.accept(file);
                }
            }
            return result;
        }
    }

}
