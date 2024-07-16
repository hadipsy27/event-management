package mii.co.id.emsclientside.config;

//<editor-fold defaultstate="collapsed" desc="Import">
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//</editor-fold>

@Configuration
public class TemplateEngineConfig {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
