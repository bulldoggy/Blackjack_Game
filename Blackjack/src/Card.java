enum Suit {
    Club,
    Heart,
    Spade,
    Diamond,
    HIDDEN;
}

public class Card {
    private Suit suit;
    private String rank;
    private int value;
    private boolean isFacedUp;

    public Card(Suit suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
        isFacedUp = false;
    }

    public Suit getSuit() {
        return isFacedUp ? this.suit : Suit.HIDDEN;
    }

    public String getRank() {
        return isFacedUp ? this.rank : "";
    }

    public int getValue() {
        return isFacedUp ? this.value : 0;
    }

    public Card turnFacedDown() {
        this.isFacedUp = false;

        return this;
    }

    public Card turnFacedUp() {
        this.isFacedUp = true;

        return this;
    }

    @Override
    public String toString() {
        return "|" + getRank() + " " + getSuit() + "|";
    }
}
