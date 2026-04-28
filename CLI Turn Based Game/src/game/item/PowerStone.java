package game.item;

import game.action.ArcaneBlast;
import game.action.ShieldBash;
import game.combatant.Combatant;
import game.combatant.Player;
import game.combatant.Warrior;
import game.combatant.Wizard;
import game.engine.BattleContext;

public class PowerStone implements Item {
    private Combatant target;

    // GameCLI calls this before use() to set the Shield Bash target
    public void setTarget(Combatant target) {
        this.target = target;
    }

    public void use(Player player, BattleContext context) {
        System.out.println("  Power Stone triggers special skill effect!");

        if (player instanceof Warrior) {
            // Warrior gets a free Shield Bash (no cooldown)
            ShieldBash bash = new ShieldBash(target, true);
            bash.execute(player, context);
        } else if (player instanceof Wizard) {
            // Wizard gets a free Arcane Blast (no cooldown)
            ArcaneBlast blast = new ArcaneBlast(true);
            blast.execute(player, context);
        }
    }

    public String getName() {
        return "Power Stone";
    }
}