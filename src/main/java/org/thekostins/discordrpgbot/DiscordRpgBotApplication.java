package org.thekostins.discordrpgbot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class DiscordRpgBotApplication {

    private final Environment env;

    public DiscordRpgBotApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(DiscordRpgBotApplication.class, args);
    }

    @Bean
    @ConfigurationProperties(value = "discord-api")
    public DiscordApi discordApi() {
        return new DiscordApiBuilder().setToken(env.getProperty("TOKEN"))
                .setAllNonPrivilegedIntents()
                .login()
                .join();
    }

}
