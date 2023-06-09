import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static String[] heroesAttackType = {"Physical", "Magical", "Mental"};
    public static int[] heroesHealth = {270, 260, 250};
    public static int[] heroesDamage = {10, 15, 20};
    public static int roundNumber = 0;
    public static int medicHealth = 200;
    public static int medicHealAmount = 50;
    public static int golemHealth = 500;
    public static int golemDamage = 10;
    public static boolean luckyDodged = false;
    public static int berserkBlockAmount = 30;
    public static int thorStunChance = 25;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        medicHeal();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i].equals(bossDefence)) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (luckyDodged) {
                    System.out.println("Lucky dodged the boss attack!");
                    luckyDodged = false;
                    continue;
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (golemHealth > 0 && i != 3) {
                    if (heroesHealth[i] - bossDamage / 5 < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] -= bossDamage / 5;
                    }
                    golemHealth -= bossDamage * 4 / 5;
                } else {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] -= bossDamage;
                    }
                }
            }
        }
        if (golemHealth > 0) {
            if (golemHealth - bossDamage < 0) {
                golemHealth = 0;
            } else {
                golemHealth -= bossDamage;
            }
        }
        if (thorStunChance > 0 && new Random().nextBoolean()) {
            thorStunChance--;
            System.out.println("Thor stunned the boss!");
            return;
        }
    }

    public static void medicHeal() {
        if (medicHealth > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    heroesHealth[i] += medicHealAmount;
                    System.out.println("Medic healed " + heroesAttackType[i] + " by " + medicHealAmount + " health.");
                    break;
                }
            }
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

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " --------------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
        if (medicHealth > 0) {
            System.out.println("Medic health: " + medicHealth);
        }
        if (golemHealth > 0) {
            System.out.println("Golem health: " + golemHealth);
        }
        if (luckyDodged) {
            System.out.println("Lucky dodged the boss attack!");
        }
        if (thorStunChance > 0) {
            System.out.println("Thor stun chance: " + thorStunChance);
        }
        System.out.println("---------------------------------------------");
    }
}
