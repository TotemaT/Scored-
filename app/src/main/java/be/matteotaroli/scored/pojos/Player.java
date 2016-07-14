package be.matteotaroli.scored.pojos;

import be.matteotaroli.scored.Constants;

public class Player {

    private String name;
    private int score;
    private String color;

    public Player() {
        this.name = "New Player";
        this.color = Constants.defaultColour;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void incrementScore() {
        score = score++;
    }

    public void decrementScore() {
        score = score--;
    }
}
