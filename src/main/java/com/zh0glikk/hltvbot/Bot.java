package com.zh0glikk.hltvbot;

import com.zh0glikk.hltvbot.KeyBoards.HomeKeyboard;
import com.zh0glikk.hltvbot.KeyBoards.PlayersKeyboard;
import com.zh0glikk.hltvbot.KeyBoards.TopTeamsKeyboard;
import com.zh0glikk.hltvbot.TextPatterns.*;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parser.*;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

//    private static final String TOKEN = "1592191900:AAGmb-DhWdOyni2rccFOgCDn2m168PFIjKs";
//    private static final String USERNAME = "hltv_free_bot";

    private static final String USERNAME = "testzh0glikkbot";
    private static final String TOKEN = "1581504979:AAFNpi89JhXIdz3y3DtrvNqB57M_90wsAqo";

    @Autowired
    private HomeKeyboard homeKeyboard;

    @Autowired
    private TopTeamsKeyboard topTeamsKeyboard;

    @Autowired
    private PlayersKeyboard playersKeyboard;

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if ( update.hasMessage() && update.getMessage().hasText() ) {
            Message msg = update.getMessage();

            if ( msg.getText().equals("/start") ) {
                SendMessage sendMessage = new SendMessage();

                String greetingMsg = "Hello, " + msg.getFrom().getUserName() + "\nI'm hltv-bot, can send you some info about csgo cybersport scene.\n" +
                        "To see more type /help";

                sendMessage.setText(greetingMsg);
                sendMessage.setChatId(String.valueOf(msg.getChatId()));
                sendMessage.setReplyMarkup(this.homeKeyboard.getReplyKeyboardMarkup());

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if ( msg.getText().contains(HomeKeyboard.topTeams) ) {
                TopTeamsParser topTeamsParser = new TopTeamsParser();

                List<TopTeam> topTeamsList= topTeamsParser.getContent();


                StringBuilder text = new StringBuilder();

                text.append("Current statistics\n");
                for ( TopTeam topTeam : topTeamsList ) {
                    text.append(TeamPattern.forStat(topTeam));
                }

                SendMessage sendMessage = new SendMessage();

                sendMessage.setText(String.valueOf(text));
                sendMessage.setChatId(String.valueOf(msg.getChatId()));
                sendMessage.setReplyMarkup(topTeamsKeyboard.getKeyboard(topTeamsList));

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if ( msg.getText().contains(HomeKeyboard.todayMatches) ) {
                MatchesParser matchesParser = new MatchesParser();

                List<Match> matches = matchesParser.getContent();

                String text = "";

                if ( matches != null ) {
                    for (Match match : matches) {
                        text += MatchPattern.all(match) + "\n";
                    }
                } else {
                    text += "No matches today.\n";
                }

                SendMessage sendMessage = new SendMessage();

                sendMessage.setText(text);
                sendMessage.setChatId(String.valueOf(msg.getChatId()));

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if ( msg.getText().contains(HomeKeyboard.results) ) {
                ResultsParser resultsParser = new ResultsParser();

                List<Result> results = resultsParser.getContent();

                String text = "";

                for ( Result result : results ) {
                    text += ResultsPattern.all(result) + "\n";
                }

                SendMessage sendMessage = new SendMessage();

                sendMessage.setText(text);
                sendMessage.setChatId(String.valueOf(msg.getChatId()));

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if ( msg.getText().contains(HomeKeyboard.events) ) {
                EventsParser eventsParser = new EventsParser();

                List<BigEvent> bigEvents = eventsParser.getContent();

                SendMessage sendMessage = new SendMessage();

                String text = "";

                for ( BigEvent bigEvent : bigEvents ) {
                    text += EventPattern.all(bigEvent) + "\n";
                }

                sendMessage.setText(text);
                sendMessage.setChatId(String.valueOf(msg.getChatId()));

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        if ( update.hasCallbackQuery() ) {
            String query = update.getCallbackQuery().getData();
            String chat_id = String.valueOf(update.getCallbackQuery().getMessage().getChatId());

            if ( query.startsWith("TeamCallBack") ) {
                String teamName = query.split(":")[1];
                String text = "";

                TopTeamsParser topTeamsParser = new TopTeamsParser();

                List<TopTeam> topTeamsList= topTeamsParser.getContent();

                TopTeam team = findByTeamName(teamName, topTeamsList);

                System.out.println(team.getPhotoUrl());

                text += TeamPattern.all(team);

                SendMessage sendMessage = new SendMessage();

                sendMessage.setText(text);
                sendMessage.setChatId(chat_id);
                sendMessage.setReplyMarkup(playersKeyboard.getKeyboard(
                        team.getPlayers(),
                        team.getUrls()));

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if ( query.startsWith("PlayerCallBack") ) {
                String playerUrl = query.split(":")[1];

                SendPhoto sendPhoto = new SendPhoto();

                PlayersParser playersParser = new PlayersParser("https://www.hltv.org/" + playerUrl);

                Player player = playersParser.getContent().get(0);

                InputFile file = new InputFile(player.getPhotoUrl());

                System.out.println(player.getPhotoUrl());

                sendPhoto.setCaption(PlayerPattern.all(player));
                sendPhoto.setPhoto(file);
                sendPhoto.setChatId(chat_id);

                try {
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public TopTeam findByTeamName(String name, List<TopTeam> teams) {
        for (TopTeam team : teams) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }
}
