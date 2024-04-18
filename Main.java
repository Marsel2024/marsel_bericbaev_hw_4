import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {290, 270, 250, 200, 350, 250, 250, 250};
    public static int[] heroesDamage = {10, 15, 20, 0, 10, 0, 0, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic","Medic", "Golem", "Lucky", "Witcher", "Thor"};
    public static int roundNumber = 0;
    public static int medicHeal = 50;
    public static boolean thorAlive = true;
    public static boolean bossStunned = false;
    public static boolean witcherAlive = true;
    public static boolean luckyAlive = true;
    public static boolean golemAlive = true;
    public static void golem(){
        if (golemAlive ){
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && heroesAttackType[i] != "Golem"){
                    heroesHealth[i] = heroesHealth[i] - bossDamage / 5;
                    if (heroesHealth[i] < 0) {
                        heroesHealth[i] = 0;
                    }
                }
            }
            System.out.println("Golem took 1/5 damage " + bossDamage);
        }
    }
    public static void medic(){
        int medic = 3;
        if(heroesHealth[3] <= 0 ){
            return;
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && i != medic){
                heroesHealth[i] = heroesHealth[i] + medicHeal;
                System.out.println("Medic healed "+ heroesAttackType[i]+ medicHeal + " hp");
                break;
            }
        }
    }

    public static void luckyChance(){
        if (luckyAlive && bossDamage >0 ){
            Random random = new Random();
            boolean isLucky = random.nextBoolean();
            if (isLucky){
                System.out.println("Lucky dodged the boss punches");
            }
        }
    }
    public static void witcherRevive(){
        if (witcherAlive){
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i]== 0 && i != 3){
                    heroesHealth[i] = 150;
                    witcherAlive = false;
                    System.out.println("Witcher revive " + heroesAttackType[i]);
                    break;
                }
            }
        }
    }
    public static void thorStunner(){
        if (thorAlive && ! bossStunned ) {
            Random random = new Random();
            boolean isStunned = random.nextBoolean();
            if (isStunned ){
                bossStunned = true;
                System.out.println("Thor stunned boss for 1 round");
            }
        }
    }
    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        medic();
        golem();
        luckyChance();
        thorStunner();
        witcherRevive();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomInd = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomInd];
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");

        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}