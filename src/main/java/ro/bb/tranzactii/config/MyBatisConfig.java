package ro.bb.tranzactii.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan("ro.bb.tranzactii.repositories")
public class MyBatisConfig {

//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource ds1DataSource() throws Exception {
//        return DataSourceBuilder.create().build();
//    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource,
                                               @Value("mybatis.typeAliasesPackage") String typeAliasesPackage,
                                               @Value("mybatis.mapperLocations") String mapperLocations) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        // The next two sentences are for *.xml files only, if the XML file is not needed for the entire
        // persistence layer operation (only annotations will do), they are not added
        sessionFactory.setTypeAliasesPackage("ro.bb.tranzactii.model"); //Specify base package
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));

        return sessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}
