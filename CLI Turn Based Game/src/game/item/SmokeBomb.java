package game.item;

import game.combatant.Player;
import game.engine.BattleContext;
import game.statuseffect.SmokeBombInvulnerability;

// blocks all incoming damage for 2 rounds
public class SmokeBomb implements Item {
    public void use(Player player, BattleContext context) {
        player.addStatusEffect(new SmokeBombInvulnerability(2));
        System.out.println("Enemy attacks deal 0 damage this turn + next turn");
    }

    public String getName() {
        return "Smoke Bomb";
    }
}
