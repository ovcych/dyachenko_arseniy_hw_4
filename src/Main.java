import java.util.Random;
public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Golem", "Lucky", "Witcher", "Thor", "Medic"};
    public static int[] heroesHealth = {280, 270, 250, 200, 150, 140, 160, 170};
    public static int[] heroesDamage = {10, 15, 20, 5, 8, 0, 10, 15};
    public static int roundNumber;
    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }
    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        showStatistics();
        applyMedicHealing();
        applyGolemDamageReduction();
        applyLuckyEvade();
        applyWitcherRevive();
        applyThorStun();
    }
    public static void applyMedicHealing() {
        int medicIndex = 3;
        int healingAmount = 30;
        if (heroesHealth[medicIndex] > 0 && heroesHealth[medicIndex] < 100) {
            // Ищем первого члена команды с наименьшим здоровьем, но не меньше 0
            int minHealthIndex = -1;
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i != medicIndex && heroesHealth[i] > 0 && (minHealthIndex == -1 || heroesHealth[i] < heroesHealth[minHealthIndex])) {
                    minHealthIndex = i;
                }
            }
            if (minHealthIndex != -1) {
                heroesHealth[minHealthIndex] += healingAmount;
                if (heroesHealth[minHealthIndex] > 100) {
                    heroesHealth[minHealthIndex] = 100;
                }
            }
        }
    }
    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); //  0, 1, 2
        bossDefence = heroesAttackType[randomIndex];
    }
    public static void bossAttack() {
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
    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
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
    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " --------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
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
    public static void applyGolemDamageReduction() {
        int golemIndex = 4;
        double damageReduction = 0.2;
        if (heroesHealth[golemIndex] > 0) {
            int damageToGolem = (int) (bossDamage * damageReduction);
            heroesHealth[golemIndex] -= damageToGolem;
        }
    }
    public static void applyLuckyEvade() {
        int luckyIndex = 5;
        if (heroesHealth[luckyIndex] > 0) {
            Random random = new Random();
            boolean didEvade = random.nextBoolean();
            if (didEvade) {
                System.out.println("Lucky уклонился от удара босса!");
            }
        }
    }
    public static void applyWitcherRevive() {
        int witcherIndex = 6;
        if (heroesHealth[witcherIndex] > 0) {
            Random random = new Random();
            boolean didRevive = random.nextBoolean();
            if (didRevive) {
                for (int i = 0; i < heroesHealth.length; i++) {
                    if (i != witcherIndex && heroesHealth[i] <= 0) {
                        heroesHealth[i] = 1;
                        System.out.println("Witcher оживил героя!");
                        break;
                    }
                }
            }
        }
    }
    public static void applyThorStun() {
        int thorIndex = 7;
        if (heroesHealth[thorIndex] > 0) {
            Random random = new Random();
            boolean didStun = random.nextBoolean();

            if (didStun) {
                System.out.println("Thor оглушил босса!");
                bossDefence = null;
            }
        }
    }
}
