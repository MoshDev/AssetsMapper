package com.moshx.plugin

import com.moshx.models.MappedAssets
import org.gradle.api.internal.ClosureBackedAction

class AssetsConfig {

    public List<MappedAssets> mappedAssetsList = new ArrayList<>();

    List<MappedAssets> getMappedAssetsList() {
        return mappedAssetsList
    }

    public MappedAssets map(String path) {
        MappedAssets assets = createMappedAssets(path);
        mappedAssetsList.add(assets);
        return assets;
    }

    public MappedAssets map(String path, Closure<MappedAssets> closure) {
        MappedAssets assets = createMappedAssets(path)
        assets = applyClosure(closure, assets)
        mappedAssetsList.add(assets)
        return assets;
    }

    private static MappedAssets createMappedAssets(String path) {
        MappedAssets assets = new MappedAssets();
        assets.path = path;
        return assets;
    }

    private static <T> T applyClosure(Closure configureClosure, T delegate) {
        ClosureBackedAction<T> action = new ClosureBackedAction<T>(configureClosure, Closure.DELEGATE_FIRST, true);
        action.execute(delegate);
        return delegate;
    }
}
