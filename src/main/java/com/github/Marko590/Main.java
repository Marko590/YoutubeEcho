package com.github.Marko590;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.json.*;


import java.awt.*;

public class Main {

    public static void main(String[] args) {


        EmbedBuilder embed=new EmbedBuilder()
                .setColor(Color.RED)
                ;

        Youtube youtube=new Youtube();
        DiscordApi api = new DiscordApiBuilder()
                .addMessageCreateListener(event ->{
                    Message message = event.getMessage();

                    if(message.mentionsEveryone()){
                        message.getChannel().sendMessage("Dont do that.");
                        message.addReaction("\uD83E\uDD22");

                    }

                    if(message.getContent().startsWith("!youtube"))
                    {
                        youtube.setUrl(message.getContent().substring(9, message.getContent().length()));
                        if (youtube.getUrl() != null)
                        {
                            String jsonString=youtube.getJson(youtube.getUrl());
                            if(jsonString!=null){
                                JSONObject jsonObject=new JSONObject(jsonString);

                                embed.setImage(jsonObject.getString("thumbnail_url"));
                                embed.setAuthor(jsonObject.getString("author_name"),jsonObject.getString("author_url"),"https://cdn.discordapp.com/embed/avatars/0.png");
                                embed.setTitle(jsonObject.getString("title"));

                                event.getChannel().sendMessage(embed);
                            }
                        else{
                            event.getChannel().sendMessage("Wrong youtube url!");
                            }

                        }
                    }
                    if(message.isPrivateMessage()){

                        event.addReactionsToMessage("\uD83E\uDD22");

                    }
                })
                .addServerBecomesAvailableListener(event -> {

                    System.out.println("Loaded " + event.getServer().getName());

                })
                .setToken(System.getenv("DiscordToken"))
                .setWaitForServersOnStartup(false)
                .login()
                .join();

    }
}