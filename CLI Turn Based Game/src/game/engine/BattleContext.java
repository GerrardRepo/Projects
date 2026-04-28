package game.engine;

import game.combatant.Combatant;
import game.combatant.Enemy;
import game.combatant.Player;
import java.util.ArrayList;
import java.util.List;

// holds the state of the current battle - player, enemies, round number etc
public class BattleContext {
    private Player player;
    private List<Enemy> enemies;
    private int currentRound;
    private boolean backupSpawned;

    public BattleContext(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = new ArrayList<Enemy>(enemies);
        currentRound = 0;
        backupSpawned = false;
    }

    public Player getPlayer() { return player; }
    public List<Enemy> getEnemies() { return enemies; }
    public int getCurrentRound() { return currentRound; }
    public boolean isBackupSpawned() { return backupSpawned; }

    public void setBackupSpawned(boolean spawned) {
        this.backupSpawned = spawned;
    }

    public void incrementRound() { currentRound++; }

    // only returns enemies that are still alive
    public List<Combatant> getAliveEnemies() {
        ArrayList<Combatant> alive = new ArrayList<Combatant>();
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isAlive()) {
                alive.add(enemies.get(i));
            }
        }
        return alive;
    }

    // player + alive enemies (used for turn ordering)
    public List<Combatant> getAllAliveCombatants() {
        ArrayList<Combatant> alive = new ArrayList<Combatant>();
        if (player.isAlive()) {
            alive.add(player);
        }
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isAlive()) {
                alive.add(enemies.get(i));
            }
        }
        return alive;
    }

    public boolean allEnemiesDefeated() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isAlive()) {
                return false;
            }
        }
        return true;
    }

    public void addEnemies(List<Enemy> backupEnemies) {
        enemies.addAll(backupEnemies);
    }
}
