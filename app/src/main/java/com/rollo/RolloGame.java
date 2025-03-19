import java.util.*;

public class RolloGame {
    private Player player;
    private Shop shop;
    private int currentRound;
    private static final int MAX_ROUNDS = 24;
    private static final List<Integer> BOSS_ROUNDS = Arrays.asList(3, 6, 9, 12, 15, 18, 21, 24);
    private static final List<Boss> bosses = Arrays.asList(
        // LIST OF POSSIBLE BOSSES TO GO HERE
    );
    
    public RolloGame() {
        this.player = new Player();
        this.shop = new Shop();
        this.currentRound = 1;
    }
    
    public void startGame() {
        displayMainMenu();
        while (currentRound <= MAX_ROUNDS) {
            startRound();
            currentRound++;
        }
        endGame(true);
    }
    
    private void displayMainMenu() {
        // Show main menu UI, wait for user to start
    }
    
    private void startRound() {
        boolean bossRound = BOSS_ROUNDS.contains(currentRound);
        if (bossRound) {
            Boss boss = selectRandomBoss();
            if (!handleBossFight(boss)) {
                endGame(false);
                return;
            }
        } 
        
        else {
            handleRegularRound();
            shop.visitShop(player);
        }
    }
    
    private Boss selectRandomBoss() {
        return bosses.get(new Random().nextInt(bosses.size()));
    }
    
    private void handleRegularRound() {
        int targetScore = determineTargetScore();
        int accumulatedScore = 0;
        
        while (player.hasRemainingRollsOrPlays() && accumulatedScore < targetScore) {
            if (player.wantsToReroll()) {
                player.rerollSelectedDice();
            } 
            
            else {
                HandType handType = determineHandType(player.getPlayedDice());
                int score = calculateScore(handType);
                accumulatedScore += score;
                player.addScore(score);
            }
        }
        
        player.earnCurrency(calculateEarnings());
    }
    
    private boolean handleBossFight(Boss boss) {
        int accumulatedScore = 0;
        int targetScore = boss.getDifficulty();
        
        while (player.hasRemainingRollsOrPlays() && accumulatedScore < targetScore) {
            if (player.wantsToReroll()) {
                player.rerollSelectedDice();
            } 
            
            else {
                HandType handType = determineHandType(player.getPlayedDice());
                int score = calculateScore(handType);
                accumulatedScore += score;
            }
        }
        
        boolean win = accumulatedScore >= targetScore;
        if (win) {
            player.earnCurrency(15);
        }
        return win;
    }
    
    private HandType determineHandType(List<Dice> dice) {
        // Logic to check for pairs, straights, full house, etc.
        return HandType handPlayed;
    }
    
    private int calculateScore(HandType handType) {
        // Assign points based on hand type
        return 0;
    }
    
    private void endGame(boolean victory) {
        if (victory) {
            // Show victory screen
        }s
        else {
            // Show game over screen
        }
    }
    
    public static void main(String[] args) {
        new RolloGame().startGame();
    }
}
