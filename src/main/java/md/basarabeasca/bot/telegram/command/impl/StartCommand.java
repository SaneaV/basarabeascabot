package md.basarabeasca.bot.telegram.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import md.basarabeasca.bot.telegram.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand implements Command {

  private static final String WELCOME_MESSAGE = "Добро пожаловать в Бессарабка бот V2.0. Воспользуйтесь клавишами меню, чтобы узнать о новостях в нашем городе.";
  private static final String START_COMMAND = "/start";

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final SendMessage startMessage = getSendMessageWithReplyKeyboardMarkup(update.getMessage(),
        WELCOME_MESSAGE, getMainReplyKeyboardMarkup());
    return singletonList(startMessage);
  }

  @Override
  public String getCommand() {
    return START_COMMAND;
  }

}
