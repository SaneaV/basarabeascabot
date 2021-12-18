package md.basarabeasca.bot.controller;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.bot.BasarabeascaBot;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@AllArgsConstructor
public class WebHookController {

    private final BasarabeascaBot basarabeascaBot;

    @PostMapping(value = "/")
    public ResponseEntity<BotApiMethod<?>> onUpdateReceived(@RequestBody Update update) {
        return ok().body(basarabeascaBot.onWebhookUpdateReceived(update));
    }
}