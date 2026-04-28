package game.engine;

import game.action.Action;
import game.combatant.Combatant;
import game.combatant.Enemy;
import java.util.List;

public class BattleEngine {
    private BattleContext context;
    private TurnOrderStrategy turnOrderStrategy;
    private BattleSetup setup;

    public BattleEngine(BattleSetup setup, TurnOrderStrategy turnOrderStrategy) {
        this.setup = setup;
        this.turnOrderStrategy  = turnOrderStrategy;
    }

    // creates enemies and sets up the battle context
    public BattleContext initializeBattle() {
        List<Enemy> enemies = setup.createInitialEnemies();
        this.context = new BattleContext(setup.getPlayer(), enemies);
        return context;
    }

    // executes one combatant's turn (skips if dead or stunned)
    public void playTurn(Combatant combatant, Action action) {
        if (!combatant.isAlive()) {
            return;
        }
        if (combatant.isStunned()) {
            System.out.println(combatant.getName() + " -> STUNNED: Turn skipped");
        } else if (action != null) {
            action.execute(combatant, context);
        }
    }

    // tick effects for everyone at end of round
    public void endOfRound() {
        // Update effects for alive combatants
        List<Combatant> alive = context.getAllAliveCombatants();
        for (int i = 0; i < alive.size(); i++) {
            alive.get(i).updateEffects();
        }
        // Also update effects on dead enemies
        List<Enemy> allEnemies = context.getEnemies();
        for (int i = 0; i < allEnemies.size(); i++) {
            if (!allEnemies.get(i).isAlive()) {
                allEnemies.get(i).updateEffects();
            }
        }
    }

    public BattleContext getContext() { return context; }

    // battle ends when player dies, or all enemies dead with no backup remaining
    public boolean isBattleOver() {
        if (!context.getPlayer().isAlive()) {
            return true;
        }
        if (context.allEnemiesDefeated() && !(setup.hasBackupSpawn() && !context.isBackupSpawned())) {
            return true;
        }
        return false;
    }

    // true when all current enemies are dead but theres still a backup wave to spawn
    public boolean shouldSpawnBackup() {
        return context.allEnemiesDefeated() && setup.hasBackupSpawn() && !context.isBackupSpawned();
    }

    public void spawnBackup() {
        List<Enemy> backup = setup.createBackupEnemies();
        context.addEnemies(backup);
        context.setBackupSpawned(true);

        System.out.println();
        System.out.println("===================================================");
        System.out.println("Backup Spawned!");
        for (int i = 0; i < backup.size(); i++) {
            System.out.println("  > " + backup.get(i).getName() + " (HP: " + backup.get(i).getCurrentHP() + ")");
        }
        System.out.println("===================================================");
    }

    public boolean isVictory() {
        return context.getPlayer().isAlive() && context.allEnemiesDefeated();
    }

    public List<Combatant> getTurnOrder() {
        return turnOrderStrategy.determineTurnOrder(context.getAllAliveCombatants());
    }
}
