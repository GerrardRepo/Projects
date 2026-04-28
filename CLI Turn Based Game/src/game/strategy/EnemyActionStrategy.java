package game.strategy;

import game.action.Action;
import game.combatant.Combatant;
import game.engine.BattleContext;

public interface EnemyActionStrategy {
    Action decideAction(Combatant enemy, BattleContext context);
}
