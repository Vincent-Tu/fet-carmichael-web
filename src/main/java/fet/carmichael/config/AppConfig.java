package fet.carmichael.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:/config.xml"})
@ComponentScan({"fet.carmichael.dao", "fet.carmichael.services"})
public class AppConfig {

}
