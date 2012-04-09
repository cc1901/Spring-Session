package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springWeb.beans.StateBean;

@Configuration
@Import(ApplicationConfig.class)
public class ApplicationImportConfig {
}
