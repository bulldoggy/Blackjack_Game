import java.util.ArrayList;
import java.util.List;

public class DeckManager {
    private List<Card> deck;
    private List<Card> discardPile;
    private List<Card> inPlay;
    
    public DeckManager(){
        this.discardPile = new ArrayList<>();
        this.inPlay = new ArrayList<>();
        freshDeck();
    }

    public void reshuffle() {
        for(Card card : discardPile) {
            deck.add(card);
        }

        this.discardPile = new ArrayList<>();
    }

    public void nextRound() {
        for(Card card : inPlay) {
            discardPile.add(card.turnFacedDown());
        }

        this.inPlay = new ArrayList<>();
    }

    public Card drawCard() {
        if(deck.size() < 15) reshuffle();

        int pos = (int) (Math.random() * deck.size());
        Card card = deck.get(pos);
        
        inPlay.add(card);
        deck.remove(pos);
        return card;
    }

    public void restart() {
        this.discardPile = new ArrayList<>();
        this.inPlay = new ArrayList<>();
        freshDeck();
    }

    public void freshDeck() {
        this.deck = new ArrayList<>(List.of(
            new Card(Suit.Club, "2", 2), 
            new Card(Suit.Club, "3", 3),
            new Card(Suit.Club, "4", 4), 
            new Card(Suit.Club, "5", 5),
            new Card(Suit.Club, "6", 6), 
            new Card(Suit.Club, "7", 7),
            new Card(Suit.Club, "8", 8), 
            new Card(Suit.Club, "9", 9),
            new Card(Suit.Club, "10", 10), 
            new Card(Suit.Club, "J", 10),
            new Card(Suit.Club, "Q", 10),
            new Card(Suit.Club, "K", 10), 
            new Card(Suit.Club, "A", 1),
            new Card(Suit.Diamond, "2", 2), 
            new Card(Suit.Diamond, "3", 3),
            new Card(Suit.Diamond, "4", 4), 
            new Card(Suit.Diamond, "5", 5),
            new Card(Suit.Diamond, "6", 6), 
            new Card(Suit.Diamond, "7", 7),
            new Card(Suit.Diamond, "8", 8), 
            new Card(Suit.Diamond, "9", 9),
            new Card(Suit.Diamond, "10", 10), 
            new Card(Suit.Diamond, "J", 10),
            new Card(Suit.Diamond, "Q", 10),
            new Card(Suit.Diamond, "K", 10), 
            new Card(Suit.Diamond, "A", 1),
            new Card(Suit.Heart, "2", 2), 
            new Card(Suit.Heart, "3", 3),
            new Card(Suit.Heart, "4", 4), 
            new Card(Suit.Heart, "5", 5),
            new Card(Suit.Heart, "6", 6), 
            new Card(Suit.Heart, "7", 7),
            new Card(Suit.Heart, "8", 8), 
            new Card(Suit.Heart, "9", 9),
            new Card(Suit.Heart, "10", 10), 
            new Card(Suit.Heart, "J", 10),
            new Card(Suit.Heart, "Q", 10),
            new Card(Suit.Heart, "K", 10), 
            new Card(Suit.Heart, "A", 1),
            new Card(Suit.Spade, "2", 2), 
            new Card(Suit.Spade, "3", 3),
            new Card(Suit.Spade, "4", 4), 
            new Card(Suit.Spade, "5", 5),
            new Card(Suit.Spade, "6", 6), 
            new Card(Suit.Spade, "7", 7),
            new Card(Suit.Spade, "8", 8), 
            new Card(Suit.Spade, "9", 9),
            new Card(Suit.Spade, "10", 10), 
            new Card(Suit.Spade, "J", 10),
            new Card(Suit.Spade, "Q", 10),
            new Card(Suit.Spade, "K", 10), 
            new Card(Suit.Spade, "A", 1)));
    }
}
