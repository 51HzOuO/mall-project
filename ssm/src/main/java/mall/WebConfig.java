package mall;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.File;
import java.util.Objects;

@Configuration
@EnableWebMvc
@ComponentScan(value = "mall")
@PropertySource("classpath:app-config.properties")
@EnableTransactionManagement
@MapperScan("mall.mapper")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public DataSource dataSource(@Value("${db.username}") String dbUsername, @Value("${db.password}") String dbPassword, @Value("${db.url}") String dbUrl) {
        return new DruidDataSource() {{
            setUrl(dbUrl);
            setUsername(dbUsername);
            setPassword(dbPassword);
        }};
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
//        sqlSessionFactoryBean.setConfiguration(configuration);
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(new java.util.Properties() {{
            setProperty("helperDialect", "mysql");
            setProperty("reasonable", "true");
            setProperty("supportMethodsArguments", "true");
            setProperty("params", "count=countSql");
        }});
        sqlSessionFactoryBean.addPlugins(pageInterceptor);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 允许跨域的路径
                .allowedOrigins("https://page.51hzouo.top", "http://localhost:8088").allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true)  // 是否允许发送Cookie
                .maxAge(3600);  // 预检请求的缓存时间
    }

    @Bean
    public String ContextPath() {
        String path = Objects.requireNonNull(this.getClass().getResource("/")).getPath();
        new File(path + "/img").mkdir();
        return path;
    }
}
