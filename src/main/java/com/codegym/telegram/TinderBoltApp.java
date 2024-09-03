package com.codegym.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TinderBoltApp extends SimpleTelegramBot {

    public static final String TELEGRAM_BOT_TOKEN = "7336024764:AAG3FwlnGu3PcRAfcLmshij1f-HmsVRmlo0"; //TODO: añadir el token del bot entre comillas
    public static final String OPEN_AI_TOKEN = "gpt:A2gQbHsLDAWUSnqkc9YOJFkblB3TW5PRmBWYGqt6MukbU13n";
    //TODO: añadir el token de ChatGPT entre comillas

    private final ChatGPTService chatGPT = new ChatGPTService(OPEN_AI_TOKEN);
    private DialogMode mode;


    public TinderBoltApp() {
        super(TELEGRAM_BOT_TOKEN);
    }

    //TODO: escribiremos la funcionalidad principal del bot aquí

    public void startCommand(){
        mode = DialogMode.MAIN;
        System.out.println("MODE: "+mode);
        String text = loadMessage("main");
        sendPhotoMessage("main");
        sendTextMessage(text);

        showMainMenu(
                "start", "menú principal del bot",
                "profile", "generación de perfil de Tinder \uD83D\uDE0E",
                "opener", "mensaje para iniciar conversación \uD83E\uDD70",
                "message", "correspondencia en su nombre \uD83D\uDE08",
                "date", "correspondencia con celebridades \uD83D\uDD25",
                "gpt", "hacer una pregunta a chat GPT \uD83E\uDDE0"
        );
    }

    public void gptCommand(){
        mode = DialogMode.GPT;
        String text = loadMessage("gpt");
        sendPhotoMessage("gpt");
        sendTextMessage(text);
    }

    public void gptDialog(){
        String text = getMessageText();
        String prompt = loadPrompt("gpt");
        String answer = chatGPT.sendMessage(prompt, text);
        sendTextMessage(answer);
    }


    public void hello(){
        if(mode == DialogMode.GPT){
            gptDialog();
        }else {
            String text = getMessageText();
            sendTextMessage("*Hi*");
            sendTextMessage("_How are you?_");
            sendTextMessage("You wrote: " + text);
            //Photo
            sendPhotoMessage("avatar_main");

            sendTextButtonsMessage("Launch Process", "start", "Start", "stop", "Stop");
        }
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
        addCommandHandler("gpt", this::gptCommand);
        addMessageHandler(this::hello);
        addButtonHandler("^.*", this::startBtn);
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TinderBoltApp());
    }
}
