package org.example.getMyId;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MainBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        // Bot username.
        return "";
    }

    @Override
    public String getBotToken() {
        //Bot token.
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            String responseText;

            if (message.isUserMessage()) {
                // Foydalanuvchidan to'g'ridan-to'g'ri xabar
                responseText = "Foydalanuvchi ID: " + message.getFrom().getId();
            } else if (message.isGroupMessage() || message.isSuperGroupMessage()) {
                // Guruhdan xabar
                responseText = "Guruh ID: " + chatId + "\nFoydalanuvchi ID: " + message.getFrom().getId();
            } else {
                // Boshqa turdagi chatlar
                responseText = "Noma'lum chat turi.";
            }

            if (message.getForwardFrom() != null) {
                // Forward qilingan xabar egasining ID sini qaytarish
                responseText = "Forward xabar egasi ID: " + message.getForwardFrom().getId();
            } else if (message.getForwardFrom() == null && message.getForwardDate() != null) {
                // Forward qilingan, lekin forward ID mavjud emas
                responseText = "Bu foydalanuvchi ID sini olish imkoni yo'q.";
            }

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId.toString());
            sendMessage.setText(responseText);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
}
