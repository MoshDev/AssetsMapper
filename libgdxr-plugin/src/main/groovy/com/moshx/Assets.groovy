package com.moshx

class Assets {

    private final File rootDir;
    private List<File> assetsDirs;
    private final List<File> excludedBuildDirs

    public Assets(File rootDir, List<File> excludedBuildDirs) {
        this.rootDir = rootDir;
        this.excludedBuildDirs = excludedBuildDirs
    }

    public List<File> getAssetsDirs() {
        if (assetsDirs != null && !assetsDirs.isEmpty()) {
            return assetsDirs;
        }

        assetsDirs = new ArrayList<File>();
        findAssetsDirs(rootDir);

        return assetsDirs;
    }

    private void findAssetsDirs(File dir) {
        if (dir == null || dir.isFile()) {
            return;
        }

        for (File f : excludedBuildDirs) {
            if (dir.absolutePath.startsWith(f.absolutePath)) {
                return;
            }
        }

        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                String name = file.getName();
                if (name.equals("assets")) {
                    assetsDirs.add(file);
                } else {
                    findAssetsDirs(file);
                }
            }
        }
    }

}
