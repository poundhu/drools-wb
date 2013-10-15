package org.drools.workbench.jcr2vfsmigration.migrater.asset;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.drools.guvnor.client.common.AssetFormats;
import org.drools.guvnor.client.rpc.Module;
import org.drools.guvnor.server.RepositoryAssetService;
import org.drools.repository.AssetItem;
//import org.kie.workbench.io.IOService;
//import org.kie.workbench.java.nio.base.options.CommentedOption;
//import org.kie.workbench.java.nio.file.NoSuchFileException;
import org.drools.workbench.jcr2vfsmigration.migrater.PackageImportHelper;
import org.drools.workbench.jcr2vfsmigration.migrater.util.MigrationPathManager;
import org.uberfire.io.IOService;
import org.uberfire.java.nio.base.options.CommentedOption;
import org.uberfire.java.nio.file.Files;
import org.uberfire.java.nio.file.NoSuchFileException;
import org.uberfire.java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.backend.vfs.Path;

@ApplicationScoped
public class TestScenarioMigrater extends BaseAssetMigrater {

    protected static final Logger logger = LoggerFactory.getLogger( TestScenarioMigrater.class );

    @Inject
    protected RepositoryAssetService jcrRepositoryAssetService;

    @Inject
    protected MigrationPathManager migrationPathManager;

    @Inject
    private Paths paths;

    @Inject
    @Named("ioStrategy")
    private IOService ioService;

    @Inject
    protected PackageImportHelper packageImportHelper;

    public Path migrate( Module jcrModule,
                         AssetItem jcrAssetItem,
                         Path previousVersionPath) {
        if ( !AssetFormats.TEST_SCENARIO.equals( jcrAssetItem.getFormat() ) ) {
            throw new IllegalArgumentException( "The jcrAsset (" + jcrAssetItem.getName()
                                                        + ") has the wrong format (" + jcrAssetItem.getFormat() + ")." );
        }

        Path path = migrationPathManager.generatePathForAsset( jcrModule, jcrAssetItem );
        final org.uberfire.java.nio.file.Path nioPath = paths.convert( path );
        //The asset was renamed in this version. We move this asset first.
        if(previousVersionPath != null && !previousVersionPath.equals(path)) {
             ioService.move(paths.convert( previousVersionPath ), nioPath, StandardCopyOption.REPLACE_EXISTING);
        }
        
        if ( !Files.exists( nioPath ) ) {
            ioService.createFile( nioPath );
        }

        String content = jcrAssetItem.getContent();

        String sourceContentWithPackage = packageImportHelper.assertPackageNameXML( content, path );
        sourceContentWithPackage = packageImportHelper.assertPackageImportXML( sourceContentWithPackage, path );

        ioService.write( nioPath, content, migrateMetaData(jcrModule, jcrAssetItem), new CommentedOption( jcrAssetItem.getLastContributor(), null, jcrAssetItem.getCheckinComment(), jcrAssetItem.getLastModified().getTime() ) );
        
        return path;
    }
}
