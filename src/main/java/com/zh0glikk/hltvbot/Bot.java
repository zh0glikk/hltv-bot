package com.zh0glikk.hltvbot;

import com.zh0glikk.hltvbot.KeyBoards.HomeKeyboard;
import com.zh0glikk.hltvbot.KeyBoards.PlayersKeyboard;
import com.zh0glikk.hltvbot.KeyBoards.TopTeamsKeyboard;
import com.zh0glikk.hltvbot.TextPatterns.MatchPattern;
import com.zh0glikk.hltvbot.TextPatterns.PlayerPattern;
import com.zh0glikk.hltvbot.TextPatterns.ResultsPattern;
import com.zh0glikk.hltvbot.TextPatterns.TeamPattern;
import model.Match;
import model.Player;
import model.Result;
import model.TopTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parser.MatchesParser;
import parser.PlayersParser;
import parser.ResultsParser;
import parser.TopTeamsParser;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    private static final String TOKEN = "1592191900:AAGmb-DhWdOyni2rccFOgCDn2m168PFIjKs";
    private static final String USERNAME = "hltv_free_bot";

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
            } else if ( msg.getText().equals(HomeKeyboard.topTeams) ) {
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
            } else if ( msg.getText().equals(HomeKeyboard.todayMatches) ) {
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
            } else if ( msg.getText().equals(HomeKeyboard.results) ) {
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

                if ( team != null ) {
                    text += TeamPattern.all(team);
                }

                SendMessage sendMessage = new SendMessage();

                sendMessage.setText(text);
                sendMessage.setChatId(chat_id);
                assert team != null;
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

                SendMessage sendMessage = new SendMessage();

                PlayersParser playersParser = new PlayersParser("https://www.hltv.org/" + playerUrl);

                Player player = playersParser.getContent().get(0);

                sendMessage.setText(PlayerPattern.all(player));
                sendMessage.setChatId(chat_id);

                try {
                    execute(sendMessage);
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
