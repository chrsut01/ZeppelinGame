package com.mygdx.game.SideScrollers;


public class HealthBar {
    private int maxHealth;
    private static int currentHealth;

    public HealthBar(int maxHealth) {
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
    }

    public static void decreaseHealth(int amount) {
        currentHealth -= amount;
        currentHealth = Math.max(0, currentHealth); // Ensure health doesn't go below 0
    }

    public void increaseHealth(int amount) {
        currentHealth += amount;
        currentHealth = Math.min(maxHealth, currentHealth); // Ensure health doesn't exceed maximum
    }

    public void update() {
        System.out.print("Health: [");
        int barsToDisplay = (int) Math.ceil((double) currentHealth / maxHealth * 20); // Calculate number of bars to display
        for (int i = 0; i < 20; i++) {
            if (i < barsToDisplay)
                System.out.print("#"); // Display filled bar
            else
                System.out.print("-"); // Display empty bar
        }
        System.out.println("] " + currentHealth + "/" + maxHealth);
    }

    public static int getCurrentHealth() {
        return currentHealth;
    }

    public float getMaxHealth() {
        return maxHealth;
    }
}
