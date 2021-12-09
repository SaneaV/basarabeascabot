package md.basarabeasca.bot.action.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessage;

@Component
@RequiredArgsConstructor
public class CommandFacade {

    private static final String ERROR = "Произошла ошибка при отправлении сообщения. Пожалуйста, обратитесь к @SaneaV";

    private final List<Command> commands;

    public List<? super PartialBotApiMethod<?>> processCommand(Update update) {
        try {
            Optional<Command> userCommand = commands.stream().
                    filter(commandTemp ->
                            update.getMessage().getText().contains(commandTemp.getCommand()) ||
                                    (update.getMessage().isReply() &&
                                            update.getMessage().getReplyToMessage().getText().contains(commandTemp.getCommand())
                                    )
                    )
                    .findFirst();

            return userCommand.map(commandTemp ->
                    commandTemp.execute(update))
                    .orElseThrow(Exception::new);

        } catch (Exception exception) {
            exception.printStackTrace();
            return singletonList(getSendMessage(update.getMessage().getChatId().toString(), ERROR));
        }
    }
}
