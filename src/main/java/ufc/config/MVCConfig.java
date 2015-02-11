package ufc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ufc.core.service.secondLayer.UserServiceL2;
import ufc.utils.entity.UserUtils;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"ufc.rest", "ufc.core", "ufc.spring", "ufc.utils"})
@EnableScheduling
public class MVCConfig {

    @Autowired
    private UserServiceL2 userServiceL2;

    @Autowired
    private UserUtils userUtils;

    @Bean
    public TaskExecutor taskExecutor() {
        TaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        return taskExecutor;
    }

}
