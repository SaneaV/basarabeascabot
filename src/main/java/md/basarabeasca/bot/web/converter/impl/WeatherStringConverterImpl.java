package md.basarabeasca.bot.web.converter.impl;

import static java.lang.Character.toUpperCase;

import md.basarabeasca.bot.domain.Weather;
import md.basarabeasca.bot.web.converter.WeatherStringConverter;
import org.springframework.stereotype.Component;

@Component
public class WeatherStringConverterImpl implements WeatherStringConverter {

  private static final String TEMPLATE = "Дата: *%s*\n"
      + "Температура днём: %s\n"
      + "Температура ночью: %s\n"
      + "Описание: %s\n\n";

  @Override
  public String toString(Weather weather) {
    return String.format(TEMPLATE, weather.getDate(), weather.getDaytimeTemperature(),
        weather.getNightTemperature(), capitalizeString(weather.getDescription()));
  }

  private String capitalizeString(String string) {
    return toUpperCase(string.charAt(0)) + string.substring(1);
  }
}
