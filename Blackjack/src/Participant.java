import java.util.ArrayList;
import java.util.List;

public class Participant {
    private String name;
    private int points;
    private List<Card> cardsInHand;

    public Participant(String name) {
        this.name = name;
        this.points = 0;
        this.cardsInHand = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public int getPoints() {
        return this.points;
    }

    public void addPoint(int point) {
        this.points += point;
    }

    public void addCard(Card card) {
        cardsInHand.add(card);
    }

    public String displayCards() {
        StringBuilder sb = new StringBuilder();

        for(Card card : this.cardsInHand) {
            sb.append(card.toString());
        }

        return sb.toString();
    }

    public int getCardsCount() {
        return this.cardsInHand.size();
    }

    public int countValue() {
        boolean containsAce = false;
        int value = 0;

        for(Card card: cardsInHand) {
            if("A".equals(card.getRank())) containsAce = true;
            value += card.getValue();
        }

        if(containsAce && value <= 11) {
            return value + 10;
        }

        return value;
    }

    public void clearHand() {
        this.cardsInHand = new ArrayList<>();
    }

    public String dealerToString() {
        return "~~~ Dealer ~~~ *** Value in hand: " + countValue() + (countValue() > 21 ? " (OVER 21)" : "")+ 
            "\nCards: " + displayCards();
    }

    public void dealerFlipCard() {
        this.cardsInHand.get(1).turnFacedUp();
    }

    @Override
    public String toString() {
        return "Player: " + this.name + " *** " + this.points + 
            " Points *** Value in hand: " + countValue() + (countValue() > 21 ? " (OVER 21)" : "")+ 
            "\nCards: " + displayCards();
    }
}
