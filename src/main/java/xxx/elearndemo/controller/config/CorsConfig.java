package xxx.elearndemo.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 允许跨域的请求路径
                .allowedOriginPatterns("*")
                .allowedMethods("*")  // 允许提交请求的方法
                .allowedHeaders("*")  // 允许访问的头信息
                .maxAge(3600)  // 预请求的结果有效期
                .allowCredentials(true);
    }
}
