package com.example.battle_bot.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Value {
    public static final long ID_ADMIN_1 = 386044775;
    public static final long ID_ADMIN_2 = 494058982;
    public static final String BOT_USERNAME = "battleClub_bot";
    public static final String BOT_TOKEN = "6128401778:AAFPayu7bnxTg3rey_GW6NPJRvKPTpX0zSs";
    public static final String FILE_INFO_URI = "https://api.telegram.org/bot{token}/getFile?file_id={fileId}";
    public static final String FILE_STORAGE_URI = "https://api.telegram.org/file/bot{token}/{filePath}";
    public static final String ID_GROUP_THE_BATTLE_CLUB = "-1001324649463";
    public static final String COMMAND_START = "Привет! Я чатбот TheBattle.club";//
    public static final String COMMAND_CONDITIONS_BONUS_CODE = "Получи бонус код " +
            "за выполненное условие!" +
            " \n" +
            "\n\n \u26A1Подпишись на наш телеграм: @thebattleclub \n"+
            "\nПризы уже ждут тебя\uD83C\uDF81\uD83C\uDF81\uD83C\uDF81";
    public static final String COMMAND_GET_COUNT_USERS = "Получить количество пользователей";

    public static final String COMMAND_GET_NAMES_USERS = "Получить список имен пользователей";
    public static final String COMMAND_GET_SUPPORT = "\u2139\uFE0FОнлайн-поддержка\u2139\uFE0F";
    public static final String COMMAND_GET_BONUS_CODE = "\uD83C\uDF81Получить бонус код\uD83C\uDF81";
    public static final String COMMAND_I_DID_CONDITIONS_TELEGRAM = "Я подписался на Телеграм канал  @thebattleclub\u2705";
    public static final String COMMAND_MENU = "\u2B06Главное меню\u2B06";
    public static final String COMMAND_GREETINGS_FOR_ADMINISTRATOR = "Приветствую в разделе администратора \uD83D\uDC51";
    public static final String CHOOSE_COMMAND = "Выберите команду\uD83D\uDC47";

    //NEWSLETTER
    public static final String COMMAND_CREATE_NEWSLETTER = "Создать рассылку";
    public static final String COMMAND_START_NEWSLETTER = "Преступим к созданию рассылки, какую форму будет содержать пост?";
    public static final String COMMAND_1_FORM = "Только текст";
    public static final String COMMAND_2_FORM = "Текст + Изображение";

}
