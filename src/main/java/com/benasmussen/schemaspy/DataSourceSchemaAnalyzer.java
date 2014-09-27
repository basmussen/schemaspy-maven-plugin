package com.benasmussen.schemaspy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.SchemaAnalyzer;

/**
 * Datasource schema analyzer
 * 
 * 
 * @author Ben Asmussen
 *
 */
public class DataSourceSchemaAnalyzer extends SchemaAnalyzer
{

    private DataSource datasource;

    public DataSourceSchemaAnalyzer(DataSource datasource)
    {
        super();
        this.datasource = datasource;
    }

    @Override
    protected Connection getConnection(Config config, String connectionURL, String driverClass, String driverPath)
            throws FileNotFoundException, IOException
    {
        try
        {
            return datasource.getConnection();
        }
        catch (SQLException e)
        {
            throw new IOException("Unable to get database connection", e);
        }
    }
}
