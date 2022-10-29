package bot.example.farm_stonks_bot.service;

import bot.example.farm_stonks_bot.config.TinkoffApiInitialization;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.*;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.tinkoff.piapi.core.utils.DateUtils.timestampToString;
import static ru.tinkoff.piapi.core.utils.MapperUtils.quotationToBigDecimal;

// ToDo 1.записать данные по акциях в БД(Таблица акции), записать последнею цену акций(Таблица - мб последние цены акций),
//  Вывести джоином имя и последнею цену в валюте и разницу в процентах за день

// ToDo Реализовать список диведендных акций
@Service
public class TinkoffApiService {
    static final Logger log = LoggerFactory.getLogger(TinkoffApiService.class);

    @Autowired
    final TinkoffApiInitialization tinkoffAPI;


    public TinkoffApiService(TinkoffApiInitialization tinkoffAPI) {
        this.tinkoffAPI = tinkoffAPI;
    }

    // ToDo 1.записать данные по акциях в БД(Таблица акции),
    public List<Share> getSharesRussian() {
        var sharesRussian =  tinkoffAPI.getApi().
                getInstrumentsService().
                getAllSharesSync().
                stream().
                filter(e -> e.getCountryOfRiskName().equals("Российская Федерация")).
                toList();
        for(Share share : sharesRussian) {
            System.out.println(share.getCountryOfRiskName() + " / " + share.getName() + " / " + share.getNominal());
        }

        return sharesRussian;
    }

    public void getLastPrice() {
        var lastPriceRussianShares = tinkoffAPI.getApi().
                getMarketDataService().
                getLastPricesSync(getSharesRussian().stream().map(Share::getFigi).toList()).
                stream().map(LastPrice::getPrice).toList();
        for (Quotation lastPrice : lastPriceRussianShares) {
            System.out.println(lastPrice);
        }
    }

    // ToDo Сделать запрос к своей БД -> Получить оттуда фиги -> По фигам найти цену открытия за 1 день
    public void getCandlePriceOpen() {
        var figis = getSharesRussian();

        for(Share figi : figis) {
            var candlesDay = tinkoffAPI.getApi().
                    getMarketDataService().
                    getCandlesSync(figi.getFigi(), Instant.now().minus(1, ChronoUnit.DAYS),
                            Instant.now(), CandleInterval.CANDLE_INTERVAL_DAY);

            for(HistoricCandle candle : candlesDay) {
                long openPrice = candle.getOpen().getUnits();
                long closePrice = candle.getClose().getUnits();
                float percent = (float) (((openPrice - closePrice) / openPrice) * 100);
                System.out.println("Name: " + figi.getName() +
                        " Opening price: " + openPrice +
                        " close price: " + closePrice +
                        "(" + percent + ")");
            }
        }

        //log.info("получено {} 1-дневных свечей для инструмента с figi {}", candlesDay.size(), "BBG004730N88");
        /*for (HistoricCandle candle : candlesDay) {
            printCandle(candle);
        }*/
    }

    private static void printCandle(HistoricCandle candle) {
        var open = quotationToBigDecimal(candle.getOpen());
        var close = quotationToBigDecimal(candle.getClose());
        var high = quotationToBigDecimal(candle.getHigh());
        var low = quotationToBigDecimal(candle.getLow());
        var volume = candle.getVolume();
        var time = timestampToString(candle.getTime());
        log.info(
                "цена открытия: {}, цена закрытия: {}, минимальная цена за 1 лот: {}, максимальная цена за 1 лот: {}, объем " +
                        "торгов в лотах: {}, время свечи: {}",
                open, close, low, high, volume, time);
    }

}
