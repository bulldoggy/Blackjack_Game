import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the game of BlackJack!");
        System.out.println("1. Play     2. Exit");
        int choice = sc.nextInt();

        while(choice < 1 || choice > 2) {
           System.out.println("Please enter a valid choice (1/2)"); 
           choice = sc.nextInt();
        }

        if(choice == 1) {
            System.out.println("Enter the number of players (1-5): ");
            int numPlayers = sc.nextInt();
            while(numPlayers < 1 || numPlayers > 5) {
                System.out.println("Please enter a valid player count (1-5)"); 
                numPlayers = sc.nextInt();
            }
            
            initGame(numPlayers);
        } else if(choice == 2) {
            System.out.println("Thank you for playing!");
            System.exit(0);
        }

        sc.close();
    }

    private static void initGame(int numPlayers) {
        boolean proceed = true;
        Scanner sc = new Scanner(System.in);
        List<Participant> participants = new ArrayList<>();
        DeckManager deckManager = new DeckManager();

        participants.add(new Participant("Dealer"));
        System.out.println("==============================================================================");

        for(int i = 1; i <= numPlayers; i++) {
            System.out.println("Enter the name of Player " + i + ": ");
            String name = sc.nextLine();

            participants.add(new Participant(name));
        }

        while(proceed) {
            playRound(participants, sc, deckManager);
            System.out.println("Do you want play another round?");
            System.out.println("1. Yes     2. No");

            int choice = sc.nextInt();

            while(choice < 1 || choice > 2) {
                System.out.println("Please enter a valid choice (1/2)"); 
                choice = sc.nextInt();
            }

            if(choice == 2) {
                proceed = false;
            }
        }

        System.out.println("-------     FINAL RESULTS     -------");
        showResult(participants);
    }

    private static void playRound(List<Participant> participants, Scanner sc, DeckManager deckManager) {
        //dealing cards to everyone
        Participant dealer = participants.get(0);
        dealer.addCard(deckManager.drawCard().turnFacedUp());
        dealer.addCard(deckManager.drawCard());

        for(int i = 1; i < participants.size(); i++) {
            Participant player = participants.get(i);
            player.addCard(deckManager.drawCard().turnFacedUp());
            player.addCard(deckManager.drawCard().turnFacedUp());
        }

        //players start hitting / standing
        for(int i = 1; i < participants.size(); i++) {
            boolean hit = true; 

            while(hit) {
                showTable(participants);
                Participant player = participants.get(i);
                System.out.println("Player " + player.getName() + ", what would you like to do?");
                System.out.println("1. Hit     2. Stay");

                int choice = sc.nextInt();
                while(choice < 1 || choice > 2) {
                    System.out.println("Please enter a valid choice (1/2)"); 
                    choice = sc.nextInt();
                }
                
                if(choice == 1) {
                    player.addCard(deckManager.drawCard().turnFacedUp());

                    if(player.countValue() > 21) {
                        hit = false;
                        System.out.println("## Player " + player.getName() + " over 21! Proceeding to next player ##");
                    }
                } else if(choice == 2) {
                    hit = false;
                }
            }
        }

        //dealer start drawing
        boolean dealerDone = false;
        dealer.dealerFlipCard();
        System.out.println("## Dealer reveals his card ##");
        showTable(participants);

        while(!dealerDone) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            if(dealer.countValue() <= 16) {
                System.out.println("## Dealer draws a card ##");
                dealer.addCard(deckManager.drawCard().turnFacedUp());
            } else {
                dealerDone = true;
            }

            showTable(participants);
        }

        //calculate scores
        System.out.println("## All drawing ended, points awarded to winners ##");
        calculatePoints(participants);
        System.out.println("==============================================================================");
        deckManager.nextRound();
        for(Participant p : participants) {
            p.clearHand();
        }
    }

    private static void calculatePoints(List<Participant> participants) {
        Participant dealer = participants.get(0);

        for(int i = 1; i < participants.size(); i++) {
            Participant player = participants.get(i);

            if(player.countValue() > 21) {
                System.out.println("Player " + player.getName() + ": " + player.getPoints() + " -10 points");
                player.addPoint(-10);
            } else {
                if(dealer.countValue() > 21) {
                    System.out.println("Player " + player.getName() + ": " + player.getPoints() + " +10 points");
                    player.addPoint(10);
                } else if(player.countValue() > dealer.countValue()) {
                    if(player.countValue() == 21 && player.getCardsCount() == 2) {
                        System.out.println("Player " + player.getName() + ": " + player.getPoints() + " +15 points");
                        player.addPoint(15);
                    } else {
                        System.out.println("Player " + player.getName() + ": " + player.getPoints() + " +10 points");
                        player.addPoint(10);
                    }
                } else if(player.countValue() < dealer.countValue()){
                    System.out.println("Player " + player.getName() + ": " + player.getPoints() + " -10 points");
                    player.addPoint(-10);
                } else {
                    System.out.println("Player " + player.getName() + ": " + player.getPoints() + " +0 points");
                }
            }
        }
    }

    private static void showTable(List<Participant> participants) {
        System.out.println("==============================================================================");
        System.out.println(participants.get(0).dealerToString());

        for(int i = 1; i < participants.size(); i++) {
            System.out.println("=----------------------------------------------------------------------------=");
            System.out.println(participants.get(i).toString());
        }

        System.out.println("==============================================================================");
        System.out.println("\n\n");
    }

    private static void showResult(List<Participant> participants) {
        System.out.println("==============================================================================");

        for(int i = 1; i < participants.size(); i++) {
            System.out.println("=----------------------------------------------------------------------------=");
            System.out.println(participants.get(i).getName() + ": " + participants.get(i).getPoints() + " points");
        }

        System.out.println("==============================================================================");
        System.out.println("\n\n");
    }
}
