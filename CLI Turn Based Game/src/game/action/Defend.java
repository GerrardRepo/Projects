package game.action;

import game.combatant.Combatant;
import game.engine.BattleContext;
import game.statuseffect.DefendEffect;
import game.statuseffect.StatusEffect;
import java.util.List;

// boosts DEF by 10 for 2 rounds.
public class Defend implements Action {

    public void execute(Combatant actor, BattleContext context) {
        //Prevents stacking of defense
        List<StatusEffect> effects = actor.getStatusEffects();
        StatusEffect existing = null;
        for (int i = 0; i < effects.size(); i++) {
            if (effects.get(i) instanceof DefendEffect) {
                existing = effects.get(i);
                break;
            }
        }
        if (existing != null) {
            existing.removeFrom(actor);
            effects.remove(existing);
        }
        actor.addStatusEffect(new DefendEffect(2));
        System.out.println(actor.getName() + " -> Defend: Defense increased by 10 for this round and the next.");
    }
}
