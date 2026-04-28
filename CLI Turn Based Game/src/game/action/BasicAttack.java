package game.action;

import game.combatant.Combatant;
import game.engine.BattleContext;

//damage = ATK - DEF (min 0)
public class BasicAttack implements Action {
    private Combatant target;

    public BasicAttack(Combatant target) {
        this.target = target;
    }

    public void execute(Combatant actor, BattleContext context) {
        int damage = actor.getAttack() - target.getDefense();
        if (damage < 0) damage = 0;

        int hpBefore = target.getCurrentHP();
        target.takeDamage(damage);

        System.out.println(actor.getName() + " -> performs BasicAttack on-> " + target.getName()
                + ": HP: " + hpBefore + " -> " + target.getCurrentHP()
                + " (dmg: " + actor.getAttack() + "-" + target.getDefense() + "=" + damage + ")");

        if (!target.isAlive()) {
            System.out.println(target.getName() + " has been ELIMINATED!");
        }
    }
}
