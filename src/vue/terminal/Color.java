package terminal;

public enum Color{
    BLACK_COLOR("\u001B[40m"),
    GREEN_COLOR("\u001B[42m"),
    RESET_COLOR("\u001B[0m");

    private final String colorCode;

    /**
        * Le constructeur permet d'instancier un objet Color
        * @param colorCode correspond à une String
    */
    Color(String colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public String toString(){
        return this.colorCode;
    }
}

