package phrases;

public enum Phrases {
    SERVER("Сервер"),
    NAME_IS_TAKEN("Это имя уже занято, пожалуйста, введите другое."),
    WELCOME("К нам подключился "),
    USER_DISCONNECT(" вышел из чата."),
    SERVER_CLOSED("Сервер прекратил работу."),
    BAD_COMMANDS("Неверная команда, для помощи введите -help"),
    COMMANDS("-exit - прекратить работу сервера\n-users - вывести список подключившихся пользователей"),

    EXIT("-exit"),
    HELP("-help"),
    USERS("-users");

    private final String phrase;

    private Phrases(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public static Phrases fromString(String text) {
        for (Phrases phrase : Phrases.values()) {
            if (phrase.phrase.equalsIgnoreCase(text)) {
                return phrase;
            }
        }
        throw new IllegalArgumentException(BAD_COMMANDS.getPhrase());
    }
}
