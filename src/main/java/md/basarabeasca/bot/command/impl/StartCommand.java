package md.basarabeasca.bot.command.impl;

import md.basarabeasca.bot.command.ICommand;
import md.basarabeasca.bot.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
public class StartCommand implements ICommand {

    @Override
    public SendMessage execute(Update update) {
        return sendStartMessage(update.getMessage());
    }

    private SendMessage sendStartMessage(final Message message) {
        return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                "Добро пожаловать в Бессарабка бот V2.0. Воспользуйтесь клавишами меню, чтобы узнать о новостях в " +
                        "нашем городе.", ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    }
}
