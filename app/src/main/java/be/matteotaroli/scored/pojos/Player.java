package be.matteotaroli.scored.pojos;

import android.os.Parcel;
import android.os.Parcelable;

// Parcelable implemented thanks to http://www.parcelabler.com
public class Player implements Parcelable {

    private String name;
    private int score;
    private int color;

    public Player() {
        this.name = "";
        this.color = -11751600;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void incrementScore() {
        score++;
    }

    public void decrementScore() {
        score--;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", color=" + "#" + Integer.toHexString(color).toUpperCase() +
                '}';
    }

    protected Player(Parcel in) {
        name = in.readString();
        score = in.readInt();
        color = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeInt(color);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}