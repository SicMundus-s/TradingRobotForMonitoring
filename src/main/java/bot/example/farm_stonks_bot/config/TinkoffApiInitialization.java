package bot.example.farm_stonks_bot.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.InvestApi;

@Component
@PropertySource("classpath:application.properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TinkoffApiInitialization{
    @Value("${tinkoff.token}")
    String token;


    InvestApi api = InvestApi.create(token);

}
