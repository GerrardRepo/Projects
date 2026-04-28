package game.item;

import game.combatant.Player;
import game.engine.BattleContext;

public class Potion implements Item {

    public void use(Player player, BattleContext context) {
        int hpBefore = player.getCurrentHP();
        player.heal(100);
        System.out.println("Heal 100HP: HP: " + hpBefore + " -> " + player.getCurrentHP());
    }

    public String getName() {
        return "Potion";
    }
}
