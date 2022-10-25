package bot.example.farm_stonks_bot.service;

import bot.example.farm_stonks_bot.config.BotConfig;
import bot.example.farm_stonks_bot.controllers.TinkoffAPIControllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    final TinkoffAPIControllers tinkoffAPIControllers;
    @Autowired
    final BotConfig botConfig;

    @Autowired
    final TinkoffApiService tinkoffApiService;

    public TelegramBot(TinkoffAPIControllers tinkoffAPIControllers, BotConfig botConfig, TinkoffApiService tinkoffApiService) {
        this.tinkoffAPIControllers = tinkoffAPIControllers;
        this.botConfig = botConfig;
        this.tinkoffApiService = tinkoffApiService;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId(); // Получение id пользователя(индетевицировать пользователя)
            String messageText = update.getMessage().getText(); // Получение сообщения пользователя

            if(messageText.equals("/start")) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                System.out.println(update.getMessage().getText());
            } else {
                startCommandReceived(chatId, "sorry, an unrecognized message");
                //tinkoffAPIControllers.getLastPricesStocks();
                tinkoffApiService.getSharesRussian();

            }
        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Hi, " + name;

        sendMessage(chatId, answer);
    }
    private void sendMessage(long chatId, String textToSend) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        }catch (TelegramApiException e) {

        }
    }
}
