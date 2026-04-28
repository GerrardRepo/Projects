package game.item;

import game.combatant.Player;
import game.engine.BattleContext;

public interface Item {
    void use(Player player, BattleContext context);
    String getName();
}
