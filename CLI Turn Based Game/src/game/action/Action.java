package game.action;

import game.combatant.Combatant;
import game.engine.BattleContext;

public interface Action {
    void execute(Combatant actor, BattleContext context);
}
