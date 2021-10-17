package org.thekostins.discordrpgbot.listeners;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thekostins.discordrpgbot.listeners.commands.Command;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CommandListener implements MessageCreateListener {
    @Resource(name = "commands")
    private final Map<String, Command> commands;
    @Value("${discord.command-prefix}")
    private String prefix;

    @Autowired
    public CommandListener(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String messageText = event.getMessageContent();
        if (messageText.startsWith(prefix)) {
            String command = messageText.split(" ")[0].substring(1);
            if (commands.containsKey(command)) commands.get(command).execute(event);
            else event.getChannel().sendMessage(String.format("No such command: `%s`", command));
        }
    }
}
