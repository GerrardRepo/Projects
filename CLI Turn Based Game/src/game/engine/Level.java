package game.engine;

public enum Level {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private String displayName;

    Level(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
