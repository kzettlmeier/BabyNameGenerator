package models;

public class NextCharacter {
    private String character;
    private int count;

    public NextCharacter(String character) {
        this.character = character;
        this.count = 1;
    }

    public String getCharacter() {
        return this.character;
    }

    public int getCount() {
        return this.count;
    }

    public int incrementCount() {
        this.count++;
        return this.count;
    }
}
