package huce.edu.vn.appdocsach.configs;

import org.springframework.context.annotation.Configuration;

import huce.edu.vn.appdocsach.services.abstracts.auth.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
// @EnableScheduling
@RequiredArgsConstructor
public class ScheduleConfig {
    
    private final IRefreshTokenService refreshTokenService;

    // @Scheduled(cron = "${application.cron.clear-refresh-token-db}")
    public void clearRefreshTokenDb() {
        log.info("Start clearRefreshTokenDb by " + getClass().getSimpleName());
        refreshTokenService.cleanUp();
    }

    
}
