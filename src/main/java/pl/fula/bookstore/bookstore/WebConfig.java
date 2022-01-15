package pl.fula.bookstore.bookstore;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.fula.bookstore.bookstore.common.validation.StringToOrderStatusEnumConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToOrderStatusEnumConverter());
    }
}
