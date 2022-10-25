package bot.example.farm_stonks_bot.service;

import bot.example.farm_stonks_bot.config.TinkoffApiInitialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Share;

@Service
public class TinkoffApiService {

    @Autowired
    final TinkoffApiInitialization tinkoffAPI;


    public TinkoffApiService(TinkoffApiInitialization tinkoffAPI) {
        this.tinkoffAPI = tinkoffAPI;
    }

    public void getSharesRussian() {
        var sharesRussian = tinkoffAPI.getApi().
                getInstrumentsService().
                getAllSharesSync().
                stream().
                filter(e -> e.getCountryOfRiskName().equals("Российская Федерация")).
                toList();

        System.out.println(tinkoffAPI.getToken());
        for(Share share : sharesRussian) {
            System.out.println(share.getCountryOfRiskName() + " / " + share.getName());
        }
    }
}
