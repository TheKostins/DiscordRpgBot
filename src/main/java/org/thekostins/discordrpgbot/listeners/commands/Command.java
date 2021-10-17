package org.thekostins.discordrpgbot.listeners.commands;


import org.javacord.api.event.message.MessageCreateEvent;


public interface Command {
    void execute(MessageCreateEvent event);

    String getCommand();

    String getCommandUsage();
}