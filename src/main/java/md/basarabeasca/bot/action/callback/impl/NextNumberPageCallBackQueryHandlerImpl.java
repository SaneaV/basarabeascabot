package md.basarabeasca.bot.action.callback.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.callback.CallbackQueryHandler;
import md.basarabeasca.bot.action.callback.CallbackQueryType;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.FIND_NUMBER;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.NEXT_PAGE;
import static md.basarabeasca.bot.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardForShowNumber;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageToMuchRequests;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithInlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class NextNumberPageCallBackQueryHandlerImpl implements CallbackQueryHandler {

    private final PhoneNumberServiceImpl phoneNumberService;
    private Integer lastDeletion;

    @Override
    public List<? super PartialBotApiMethod<?>> handleCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();

        List<PhoneNumberDto> phoneNumber = getNextPageNumbers(Long.valueOf(callbackQuery.getData().split(EMPTY_REGEX)[1]));
        long lastId = 0L;

        if (phoneNumber.isEmpty()) {
            phoneNumber = getNextPageNumbers(lastId);
        }

        final StringBuilder formattedPhones = formatPhoneNumbers(phoneNumber, new StringBuilder());
        lastId = phoneNumber.get(phoneNumber.size() - 1).getId() + 1;

        try {
            if (!callbackQuery.getMessage().getMessageId().equals(lastDeletion)) {
                lastDeletion = callbackQuery.getMessage().getMessageId();

                final DeleteMessage deleteMessage = new DeleteMessage(chatId, callbackQuery.getMessage().getMessageId());
                final SendMessage sendMessage = getSendMessageWithInlineKeyboardMarkup(chatId, formattedPhones.toString(),
                        getSendInlineKeyboardForShowNumber(SEARCH_NUMBER, FIND_NUMBER.name(), lastId));

                return asList(deleteMessage, sendMessage);
            } else {
                throw new Exception();
            }

        } catch (Exception exception) {
            return singletonList(getSendMessageToMuchRequests(chatId));
        }
    }

    private List<PhoneNumberDto> getNextPageNumbers(Long lastId) {
        return phoneNumberService.getNextPage(lastId);
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return NEXT_PAGE;
    }
}
