package bot.example.farm_stonks_bot.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotConfig {
    
    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;
}
