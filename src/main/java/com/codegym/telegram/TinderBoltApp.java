package com.codegym.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TinderBoltApp extends SimpleTelegramBot {

    public static final String TELEGRAM_BOT_TOKEN = "7336024764:AAG3FwlnGu3PcRAfcLmshij1f-HmsVRmlo0"; //TODO: añadir el token del bot entre comillas
    public static final String OPEN_AI_TOKEN = "chat-gpt-token"; //TODO: añadir el token de ChatGPT entre comillas

    public TinderBoltApp() {
        super(TELEGRAM_BOT_TOKEN);
    }

    //TODO: escribiremos la funcionalidad principal del bot aquí

    public void startCommand(){
        String text = loadMessage("main");
        sendPhotoMessage("main");
        sendTextMessage(text);


    }

    public void hello(){
        String text = getMessageText();
        sendTextMessage("*Hi*");
        sendTextMessage("_How are you?_");
        sendTextMessage("You wrote: "+text);
        //Photo
        sendPhotoMessage("avatar_main");

        sendTextButtonsMessage("Launch Process", "start", "Start", "stop", "Stop");
    }

    public void startBtn(){
        String key = getButtonKey();
        System.out.println("BTN KEY: "+key);
        if(key.equals("start")){
            sendTextMessage("The process has been launched");
        }else{
            sendTextMessage("The process has been stopped");
        }
    }


    @Override
    public void onInitialize() {
        //TODO: y un poco más aquí :)
        addCommandHandler("start", this::startCommand);
        addMessageHandler(this::hello);
        addButtonHandler("^.*", this::startBtn);
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TinderBoltApp());
    }
}
