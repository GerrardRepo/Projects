package game.strategy;

import game.action.Action;
import game.action.BasicAttack;
import game.combatant.Combatant;
import game.engine.BattleContext;

//always just attacks the player
public class BasicAttackStrategy implements EnemyActionStrategy {

    public Action decideAction(Combatant enemy, BattleContext context) {
        return new BasicAttack(context.getPlayer());
    }
}
