package bot.example.farm_stonks_bot.controllers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import ru.tinkoff.piapi.contract.v1.Asset;
import ru.tinkoff.piapi.contract.v1.Etf;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;


import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Data
// Получить список всех российских акций - получить их фиги и имена - ...
public class TinkoffAPIControllers {

  /*  public void getLastPricesStocks() {
        Map<String, String> multimap = api.getInstrumentsService().
                getAllSharesSync().
                stream().
                filter(el -> Boolean.TRUE.equals(el.getApiTradeAvailableFlag())).limit(10).
                collect(Collectors.toMap(Share::getName, Share::getFigi));    // Получение данных о акциях


        for(Map.Entry<String, String> entry : multimap.entrySet()) {
            System.out.println("Name: " + entry.getKey() + " Figi: " + entry.getValue());
        }*/

       // var lastPrices = api.getMarketDataService().getLastPricesSync(multimap.values());
       // for(LastPrice lastPrice : lastPrices) {
       //     System.out.println(lastPrice.getPrice());
       // }
        //var etfs = api.getInstrumentsService().getAllEtfsSync();
        //var assets = api.getInstrumentsService().getAssetsSync().stream().limit(5).toList();
        //for (Etf etf : etfs) {
          //  System.out.println(etfs.get(etf.getLot()));
       // }

        //System.out.println(lastPrices);
    }


