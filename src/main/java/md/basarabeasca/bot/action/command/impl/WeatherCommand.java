package md.basarabeasca.bot.action.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getUsefulReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.service.WeatherService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class WeatherCommand implements Command {

  private static final String WEATHER = "Погода на неделю";

  private final WeatherService weatherService;

  @SneakyThrows
  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return singletonList(sendWeather(update.getMessage()));
  }

  private SendMessage sendWeather(Message message) {
    final String weather = weatherService.getWeather();
    return getSendMessageWithReplyKeyboardMarkup(message, weather, getUsefulReplyKeyboardMarkup());
  }

  @Override
  public String getCommand() {
    return WEATHER;
  }
}
