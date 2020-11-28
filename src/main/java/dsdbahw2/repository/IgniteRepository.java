package dsdbahw2.repository;

import dsdbahw2.model.ComputeLog;
import dsdbahw2.model.Log;
import dsdbahw2.ignite.IgniteLoader;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.store.cassandra.datasource.DataSource;
import org.apache.ignite.configuration.ClientConnectorConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.endpoint.MetricsEndpointMetricReader;
import org.springframework.context.annotation.Bean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EnableIgniteRepositories
public class IgniteRepository {
    @Autowired
    DataSource datasource;

    @Bean
    public MetricsEndpointMetricReader metricsEndpointMetricReader(final MetricsEndpoint metricsEndpoint) {
        return new MetricsEndpointMetricReader(metricsEndpoint);
    }

    public static Connection getConnection() {
        try {
            IgniteConfiguration cfg = new IgniteConfiguration()
                    .setClientConnectorConfiguration(new ClientConnectorConfiguration());
            Class.forName("org.apache.ignite.IgniteJdbcThinDriver");

            // Open the JDBC connection.
            Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1");
            return conn;
        } catch (Exception e) {

        }
        return null;
    }

    public static List<ComputeLog> selectFromIgnite(String scale) throws ParseException {
        IgniteCache<Long, Log> cache = IgniteLoader.ignite.getOrCreateCache("Log");
        // Load cache with data from the database.
        cache.loadCache(null);
        // Execute query on cache.
        QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery("SELECT key, time, value FROM log ORDER BY time,key ASC limit 1000"));
        List<List<?>> log = cursor.getAll();
        List<ComputeLog> logs = new ArrayList<ComputeLog>();
        for (List<?> logT : log) {
            ComputeLog tempLog = new ComputeLog((Integer) logT.get(0), (Date) logT.get(1),(Integer) logT.get(2));
            System.out.println(tempLog.getTime());
            tempLog.scaleTimeStamp(scale);
            System.out.println(tempLog.getTime());
            logs.add(tempLog);
        }

        return logs;
    }
}
