package com.project.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    // JPA 의 설정 정보

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("yekim");      // TODO: 스프링 시큐리티로 인증을 붙이게 될 때, 수정하기
    }
}
