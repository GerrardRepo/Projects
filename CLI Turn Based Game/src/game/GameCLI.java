package game;

import game.action.*;
import game.combatant.*;
import game.engine.*;
import game.item.*;
import game.statuseffect.StatusEffect;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


 //Handles all the user interaction stuff
public class GameCLI {
    private Scanner scanner = new Scanner(System.in);

    // main game loop, keeps going until player chooses to exit
    public void startGame() {
        boolean running = true;
        while (running) {
            showLoadingScreen();
            BattleSetup setup = promptBattleSetup();
            running = playGame(setup);
        }
        System.out.println("\nThank you for playing! Goodbye.");
    }

    // prints the title screen with all the stats
    private void showLoadingScreen() {
        System.out.println();
        System.out.println("============ TURN-BASED COMBAT ARENA =====================");
        System.out.println();
        System.out.println("Players:  Warrior (HP:260 ATK:40 DEF:20 SPD:30)");
        System.out.println("          Wizard  (HP:200 ATK:50 DEF:10 SPD:20)");
        System.out.println();
        System.out.println("Enemies:  Goblin  (HP:55  ATK:35 DEF:15 SPD:25)");
        System.out.println("          Wolf    (HP:40  ATK:45 DEF:5  SPD:35)");
        System.out.println();
        System.out.println("Levels:   1. Easy   - 3 Goblins");
        System.out.println("          2. Medium - 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("          3. Hard   - 2 Goblins | Backup: 1 Goblin + 2 Wolves");
        System.out.println();
        System.out.println("Items:    1. Potion      - Heal 100 HP");
        System.out.println("          2. Power Stone - Free special skill (no cooldown)");
        System.out.println("          3. Smoke Bomb  - Enemy attacks deal 0 dmg for 2 turns");
        System.out.println();
    }

    /**
     * Asks player to pick class, difficulty, and 2 items.
     * Returns a BattleSetup with everything configured.
     */
    private BattleSetup promptBattleSetup() {
        System.out.println("Select your class:  1. Warrior  2. Wizard");
        int classChoice = getInput(1, 2);
        Player player;
        if (classChoice == 1) {
            player = new Warrior();
        } else {
            player = new Wizard();
        }
        System.out.println("You chose: " + player.getName() + "\n");

        System.out.println("Select difficulty:  1. Easy  2. Medium  3. Hard");
        int levelChoice = getInput(1, 3);
        Level level;
        if (levelChoice == 1) {
            level = Level.EASY;
        } else if (levelChoice == 2) {
            level = Level.MEDIUM;
        } else {
            level = Level.HARD;
        }
        System.out.println("Difficulty: " + level.getDisplayName() + "\n");

        ArrayList<Item> items = new ArrayList<Item>();
        System.out.println("Choose 2 items (duplicates allowed):");
        for (int i = 1; i <= 2; i++) {
            System.out.println("Item " + i + ":  1. Potion  2. Power Stone  3. Smoke Bomb");
            int itemChoice = getInput(1, 3);
            Item item;
            if (itemChoice == 1) {
                item = new Potion();
            } else if (itemChoice == 2) {
                item = new PowerStone();
            } else {
                item = new SmokeBomb();
            }
            items.add(item);
            System.out.println("Selected: " + item.getName());
        }
        System.out.println();

        return new BattleSetup(player, level, items);
    }
    //================================ MAIN LOGIC HERE =====================================================================
    // runs a single battle from start to finish, returns true if player wants to replay
    private boolean playGame(BattleSetup setup) {
        BattleEngine engine = new BattleEngine(setup, new SpeedBasedTurnOrder());
        BattleContext context = engine.initializeBattle();

        System.out.println("");
        System.out.println("======================== BATTLE STARTS! =============================");
        System.out.println("\n");

        while (!engine.isBattleOver()) {
            context.incrementRound();
            System.out.println("============== ROUND " + context.getCurrentRound() + " ==============");
            showBattleState(context);

            List<Combatant> turnOrder = engine.getTurnOrder();
            for (int i = 0; i < turnOrder.size(); i++) {
                Combatant combatant = turnOrder.get(i);
                if (!combatant.isAlive() || engine.isBattleOver()) {
                    continue;
                }

                System.out.println("\n--- " + combatant.getName() + "'s Turn ---");

                if (combatant instanceof Player) {
                    Player p = (Player) combatant;
                    p.reduceCooldown();
                    Action action = promptPlayerAction(p, context);
                    engine.playTurn(combatant, action);
                } else if (combatant instanceof Enemy) {
                    Enemy enemy = (Enemy) combatant;
                    Action action = enemy.getActionStrategy().decideAction(enemy, context);
                    engine.playTurn(combatant, action);
                }

                if (engine.shouldSpawnBackup()) {
                    engine.spawnBackup();
                }
            }

            engine.endOfRound();
            showEndOfRound(context);
        }

        return showResult(engine);
    }

    // shows INFORMATION
    private void showBattleState(BattleContext context) {
        Player p = context.getPlayer();
        System.out.println();
        System.out.println(p.getName()
                + " HP: " + p.getCurrentHP() + "/" + p.getMaxHP()
                + " | ATK: " + p.getAttack()
                + " | DEF: " + p.getDefense()
                + " | SPD: " + p.getSpeed());

        List<StatusEffect> playerEffects = p.getStatusEffects();
        if (playerEffects.size() > 0) {
            System.out.print("Status: ");
            for (int i = 0; i < playerEffects.size(); i++) {
                System.out.print(playerEffects.get(i) + " ");
            }
            System.out.println();
        }

        // Show inventory
        List<Item> inv = p.getInventory();
        String itemStr = "None";
        if (inv.size() > 0) {
            itemStr = "";
            for (int i = 0; i < inv.size(); i++) {
                if (i > 0) { itemStr = itemStr + ", "; }
                itemStr = itemStr + inv.get(i).getName();
            }
        }
        System.out.println("Items: " + itemStr);
        System.out.println("Special Skill Cooldown: " + p.getSpecialSkillCooldown() + " rounds\n");

        List<Combatant> aliveEnemies = context.getAliveEnemies();
        for (int i = 0; i < aliveEnemies.size(); i++) {
            Combatant enemy = aliveEnemies.get(i);
            System.out.print(enemy.getName() + " HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP());
            List<StatusEffect> effects = enemy.getStatusEffects();
            if (effects.size() > 0) {
                System.out.print(" [");
                for (int j = 0; j < effects.size(); j++) {
                    if (j > 0) { System.out.print(", "); }
                    System.out.print(effects.get(j));
                }
                System.out.print("]");
            }
            System.out.println();
        }
    }

    private void showEndOfRound(BattleContext context) {
        Player p = context.getPlayer();
        List<Enemy> allEnemies = context.getEnemies();

        System.out.println("\n--- End of Round " + context.getCurrentRound() + " ---");
        if (p.isAlive()) {
            System.out.println(p.getName() + " HP: " + p.getCurrentHP() + "/" + p.getMaxHP() + " [ALIVE]");
        } else {
            System.out.println(p.getName() + " HP: " + p.getCurrentHP() + "/" + p.getMaxHP() + " [DEFEATED]");
        }
        for (int i = 0; i < allEnemies.size(); i++) {
            Enemy e = allEnemies.get(i);
            if (e.isAlive()) {
                System.out.println(e.getName() + " HP: " + e.getCurrentHP() + "/" + e.getMaxHP() + " [ALIVE]");
            } else {
                System.out.println(e.getName() + " HP: " + e.getCurrentHP() + "/" + e.getMaxHP() + " [ELIMINATED]");
            }
        }
        System.out.println();
    }

    // shows victory/defeat, returns true if player wants to go again
    private boolean showResult(BattleEngine engine) {
        BattleContext context = engine.getContext();
        Player p = context.getPlayer();

        System.out.println("\n===================================================");
        if (engine.isVictory()) {
            System.out.println("All enemies defeated! ");
            System.out.println("Remaining HP: " + p.getCurrentHP() + "/" + p.getMaxHP());
            System.out.println("Total Rounds: " + context.getCurrentRound());
        } else {
            System.out.println("Game over, player defeated.");
            int enemiesRemaining = 0;
            List<Enemy> allEnemies = context.getEnemies();
            for (int i = 0; i < allEnemies.size(); i++) {
                if (allEnemies.get(i).isAlive()) enemiesRemaining++;
            }
            System.out.println("Enemies remaining: " + enemiesRemaining);
            System.out.println("Total Rounds Survived: " + context.getCurrentRound());
        }
        System.out.println("===================================================\n");

        System.out.println("1. Start a new game  2. Exit");
        return getInput(1, 2) == 1;
    }

    /*
     * Shows action menu for the player's turn.
    If an option is not available for example, skill cooldown != 0, option is not shown.
     */
    private Action promptPlayerAction(Player p, BattleContext context) {
        if (p.isStunned()) {
            return null;
        }

        boolean hasItems  = p.getInventory().size() > 0;
        boolean skillReady = p.isSpecialSkillReady();
        String skillName = (p instanceof Warrior) ? "Shield Bash" : "Arcane Blast";

        // Always show 1 and 2. Only show 3 if items exist. Only show 4 if skill ready.
        System.out.println("Choose action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        if (hasItems)   { System.out.println("3. Use Item"); }
        if (skillReady) { System.out.println("4. Special Skill (" + skillName + ")"); }

        // Keep asking until the player picks a valid, available option
        while (true) {
            int choice = getInput(1, 4);

            if (choice == 1) {
                return new BasicAttack(selectTarget(context));
            } else if (choice == 2) {
                return new Defend();
            } else if (choice == 3 && hasItems) {
                Item item = selectItem(p, context);
                return new UseItem(item);
            } else if (choice == 4 && skillReady) {
                if (p instanceof Warrior) {
                    return new ShieldBash(selectTarget(context));
                } else {
                    return new ArcaneBlast();
                }
            }
            System.out.println("That option is not available right now.");
        }
    }

    // auto selects if theres only one enemy left
    private Combatant selectTarget(BattleContext context) {
        List<Combatant> aliveEnemies = context.getAliveEnemies();
        if (aliveEnemies.size() == 1) {
            System.out.println("Target: " + aliveEnemies.get(0).getName());
            return aliveEnemies.get(0);
        }
        System.out.println("Select target:");
        for (int i = 0; i < aliveEnemies.size(); i++) {
            Combatant e = aliveEnemies.get(i);
            System.out.println((i + 1) + ". " + e.getName()
                    + " (HP: " + e.getCurrentHP() + "/" + e.getMaxHP() + ")");
        }
        return aliveEnemies.get(getInput(1, aliveEnemies.size()) - 1);
    }

    // if its a PowerStone and player is Warrior, we also need to ask for a target
    private Item selectItem(Player p, BattleContext context) {
        List<Item> inventory = p.getInventory();
        if (inventory.size() == 1) {
            System.out.println("Using: " + inventory.get(0).getName());
            Item item = inventory.get(0);
            if (item instanceof PowerStone && p instanceof Warrior) {
                ((PowerStone) item).setTarget(selectTarget(context));
            }
            return item;
        }
        System.out.println("Select item:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i).getName());
        }
        Item item = inventory.get(getInput(1, inventory.size()) - 1);
        if (item instanceof PowerStone && p instanceof Warrior) {
            ((PowerStone) item).setTarget(selectTarget(context));
        }
        return item;
    }

    // keeps asking until user gives a valid number in range
    private int getInput(int min, int max){
        while(true){
            System.out.print("\nEnter choice (" + min + "-" + max + "): ");
            try{
                int value = Integer.parseInt(scanner.nextLine().trim());
                if(value >= min && value <= max) return value;
            }catch(NumberFormatException e){
                // not a number, reprompt user
            }
            System.out.println("Invalid input.");
        }
    }
}
