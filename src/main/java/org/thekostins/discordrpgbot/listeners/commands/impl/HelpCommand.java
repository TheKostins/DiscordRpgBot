package org.thekostins.discordrpgbot.listeners.commands.impl;

import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thekostins.discordrpgbot.listeners.commands.AbstractCommand;
import org.thekostins.discordrpgbot.listeners.commands.Command;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class HelpCommand extends AbstractCommand {

    @Resource(name = "helpMessage")
    private String helpMessage;
    @Resource(name = "commands")
    private Map<String, Command> commands;

    protected HelpCommand(@Value("${discord.command-prefix}") String prefix) {
        super(prefix, "help", "Prints help of all commands or for specific command", true, "command");

    }

    @Override
    protected void processCommandWithArgs(MessageCreateEvent event, Map<String, String> args) {
        String command = args.get("command");
        if (commands.containsKey(command)) sendTextMessage(commands.get(command).getCommandUsage(), event);
        else sendTextMessage("No such command: `" + command + '`', event);
    }

    @Override
    protected void processCommandWithoutArgs(MessageCreateEvent event) {
        sendTextMessage(helpMessage, event);
    }
}
