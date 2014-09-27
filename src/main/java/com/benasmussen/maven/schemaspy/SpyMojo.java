package com.benasmussen.maven.schemaspy;

import java.io.File;
import java.util.List;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.SchemaAnalyzer;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.benasmussen.schemaspy.DataSourceSchemaAnalyzer;

@Mojo(name = "spy", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class SpyMojo extends AbstractMojo
{

    /**
     * Default output directory
     */
    @Parameter(defaultValue = "${project.build.directory}/generated/schemaspy", required = true)
    private File outputDirectory;

    /**
     * Sql files
     */
    @Parameter(property = "sqlFiles", required = true)
    private List<String> sqlFiles;

    private EmbeddedDatabase db;

    public void execute() throws MojoExecutionException, MojoFailureException
    {

        try
        {
            initialise();

            Config conf = new Config();

            conf.setOutputDir(outputDirectory);
            conf.setUser("sa");
            conf.setDbType("hsqldb");
            conf.setHost("localhost");
            conf.setSchema("PUBLIC");
            conf.setConnectionProperties("connectionSpec=jdbc:hsqldb:mem:embeddedDatabase");
            conf.setDb("embeddedDatabase");

            conf.setHighQuality(true);

            SchemaAnalyzer analyzer = new DataSourceSchemaAnalyzer(db);
            analyzer.analyze(conf);

        }
        catch (Exception e)
        {
            throw new MojoExecutionException("Unable to run schema spy.", e);
        }
    }

    private void initialise()
    {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

        builder.setType(EmbeddedDatabaseType.HSQL);
        builder.setScriptEncoding("UTF-8");
        builder.ignoreFailedDrops(true);

        for (String sqlFile : sqlFiles)
        {
            builder.addScript(sqlFile);
            getLog().info("Process sql file:  " + sqlFile);
        }

        db = builder.build();
    }
}
