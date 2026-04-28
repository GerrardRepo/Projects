package game.engine;

import game.combatant.Enemy;
import game.combatant.Goblin;
import game.combatant.Player;
import game.combatant.Wolf;
import game.item.Item;
import game.strategy.BasicAttackStrategy;
import java.util.ArrayList;
import java.util.List;

// stores the player + level, creates enemies for each difficulty
public class BattleSetup {
    private Player player;
    private Level level;

    public BattleSetup(Player player, Level level, List<Item> chosenItems) {
        this.player = player;
        this.level  = level;
        for (int i = 0; i < chosenItems.size(); i++)
            player.addItem(chosenItems.get(i));
    }

    public Player getPlayer() { return player; }

    // Easy = 3 goblins, Medium = goblin+wolf, Hard = 2 goblins
    //EASY NO BACKUP
    public List<Enemy> createInitialEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        BasicAttackStrategy strategy = new BasicAttackStrategy();

        if (level == Level.EASY) {
            enemies.add(new Goblin("Goblin A", strategy));
            enemies.add(new Goblin("Goblin B", strategy));
            enemies.add(new Goblin("Goblin C", strategy));
        } else if (level == Level.MEDIUM) {
            enemies.add(new Goblin("Goblin", strategy));
            enemies.add(new Wolf("Wolf", strategy));
        } else if (level == Level.HARD) {
            enemies.add(new Goblin("Goblin A", strategy));
            enemies.add(new Goblin("Goblin B", strategy));
        }

        return enemies;
    }

    // Medium backup = 2 wolves, Hard backup = goblin + 2 wolves
    public List<Enemy> createBackupEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        BasicAttackStrategy strategy = new BasicAttackStrategy();

        if (level == Level.MEDIUM) {
            enemies.add(new Wolf("Wolf A", strategy));
            enemies.add(new Wolf("Wolf B", strategy));
        } else if (level == Level.HARD) {
            enemies.add(new Goblin("Goblin C", strategy));
            enemies.add(new Wolf("Wolf A", strategy));
            enemies.add(new Wolf("Wolf B", strategy));
        }

        return enemies;
    }

    // only medium and hard have backup waves
    public boolean hasBackupSpawn() {
        return level == Level.MEDIUM || level == Level.HARD;
    }
}
