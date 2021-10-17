package org.thekostins.discordrpgbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thekostins.discordrpgbot.listeners.commands.Command;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DiscordBeans {
    @Bean
    public Map<String, Command> commands(List<Command> commandList) {
        return commandList.stream().collect(Collectors.toMap(Command::getCommand, cmd -> cmd));
    }

    @Bean()
    public String helpMessage(List<Command> commandList) {
        StringBuilder helpMessage = new StringBuilder();
        commandList.forEach(command -> helpMessage.append(command.getCommandUsage()).append('\n'));
        return helpMessage.toString();
    }
}
