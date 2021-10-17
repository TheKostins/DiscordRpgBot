package org.thekostins.discordrpgbot.listeners.commands;

import lombok.Getter;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractCommand implements Command {

    @Getter
    private final String commandUsage;
    @Getter
    private final String command;

    private final boolean hasArguments;
    private String[] argumentNames;

    protected AbstractCommand(String prefix, String command, String description) {
        this.command = command;
        this.hasArguments = false;
        this.commandUsage = generateCommandUsage(prefix, command, description, true, null);
    }

    protected AbstractCommand(String prefix, String command, String description, boolean isArgumentsOptional, String... argumentNames) {
        this.command = command;
        this.hasArguments = true;
        this.argumentNames = argumentNames;
        this.commandUsage = generateCommandUsage(prefix, command, description, isArgumentsOptional, argumentNames);
    }

    protected void processCommandWithArgs(MessageCreateEvent event, Map<String, String> args) {
        illegalCommandUsage(event);
    }

    protected void processCommandWithoutArgs(MessageCreateEvent event) {
        illegalCommandUsage(event);
    }

    protected void sendTextMessage(String text, MessageCreateEvent event) {
        event.getChannel().sendMessage(text);
    }

    private void illegalCommandUsage(MessageCreateEvent event) {
        sendTextMessage(String.format("Illegal usage of command:%s\nHow to use: %s", event.getMessageContent(), commandUsage), event);
    }

    private String generateCommandUsage(String prefix, String command, String description, boolean isArgumentsOptional, String[] argumentNames) {
        if (hasArguments) {
            StringBuilder argsStringBuilder = new StringBuilder();
            for (String arg : Objects.requireNonNull(argumentNames))
                argsStringBuilder.append(wrapArgument(arg, isArgumentsOptional)).append(' ');
            return String.format("`%s%s %s` - *%s*", prefix, command, argsStringBuilder.substring(0, argsStringBuilder.length() - 1), description);
        } else return String.format("`%s%s` - *%s*", prefix, command, description);
    }

    private String wrapArgument(String argument, boolean optional) {
        if (optional) return '(' + argument + ')';
        else return '[' + argument + ']';
    }

    private Map<String, String> mapArguments(String... args) {
        return IntStream.range(0, argumentNames.length).boxed().collect(Collectors.toMap(i -> argumentNames[i], i -> args[i]));
    }

    @Override
    public void execute(MessageCreateEvent event) {
        String[] splitMessage = event.getMessageContent().split(" ");
        if (splitMessage.length > 1) {
            if (!hasArguments || splitMessage.length - 1 != argumentNames.length) illegalCommandUsage(event);
            else processCommandWithArgs(event, mapArguments(Arrays.copyOfRange(splitMessage, 1, splitMessage.length)));
        } else processCommandWithoutArgs(event);
    }

}
