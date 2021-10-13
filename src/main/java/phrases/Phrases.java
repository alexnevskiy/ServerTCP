package phrases;

public enum Phrases {
    SERVER("Сервер"),
    NAME_IS_TAKEN("Это имя уже занято, пожалуйста, введите другое."),
    WELCOME("К нам подключился "),
    EXIT("-exit"),
    USER_DISCONNECT(" вышел из чата.");


    private String phrase;

    private Phrases(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }
}
