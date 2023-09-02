package com.example.thechathon22.configs;

import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SessionBuilderConfigurer;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;

import java.util.Properties;

@Configuration
@EnableCassandraRepositories(basePackages = {"com.example.thechathon22"})
public class cassandraConfig extends AbstractCassandraConfiguration {

    Logger LOG = LoggerFactory.getLogger(cassandraConfig.class);

    @Override
    protected String getKeyspaceName() {
        return "techathon22";
    }

    @Override
    protected String getContactPoints() {
        return "127.0.0.1";
    }

    @Override
    protected int getPort() {
        return 9042;
    }

//  @Override
//  public String[] getEntityBasePackages() {
//    return new String[] {"com.walmart.gk.*","com.walmart.GkAuditPayloadReceiver"};
//  }


    @Override
    public String getLocalDataCenter() {
        return "datacenter1";
    }

    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean session = super.cassandraSession();
        session.setLocalDatacenter(getLocalDataCenter());
        session.setContactPoints(getContactPoints());
        session.setKeyspaceName(getKeyspaceName());
        session.setPort(getPort());
        return session;
    }

    @Bean
    public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
        return new CassandraTemplate(sessionFactory, converter);

    }

    @Override
    public SessionBuilderConfigurer getSessionBuilderConfigurer() {
        return new SessionBuilderConfigurer() {
            @Override
            public CqlSessionBuilder configure(CqlSessionBuilder cqlSessionBuilder) {
                return cqlSessionBuilder.withConfigLoader(DriverConfigLoader.programmaticBuilder()
                        .withString(DefaultDriverOption.REQUEST_TIMEOUT,
                                ("25 seconds")) //60
                        .withString(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT,
                                ("25 seconds"))
                        .withString(DefaultDriverOption.CONNECTION_CONNECT_TIMEOUT,
                                ("25 seconds"))
                        .withString(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT,
                                ("25 seconds"))
                        .withString(DefaultDriverOption.CONTROL_CONNECTION_TIMEOUT,
                                ("25 seconds"))
                        .withString(DefaultDriverOption.REPREPARE_TIMEOUT,
                                ("25 seconds"))
                        .build());
            }
        };
    }

}