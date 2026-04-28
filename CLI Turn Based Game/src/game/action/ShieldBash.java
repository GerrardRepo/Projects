package game.action;

import game.combatant.Combatant;
import game.combatant.Player;
import game.engine.BattleContext;
import game.statuseffect.StunEffect;

public class ShieldBash implements Action {
    private Combatant target;
    private boolean fromPowerStone;

    public ShieldBash(Combatant target, boolean fromPowerStone) {
        this.target = target;
        this.fromPowerStone = fromPowerStone;
    }

    public ShieldBash(Combatant target) {
        this.target = target;
        this.fromPowerStone = false;
    }

    public void execute(Combatant actor, BattleContext context) {
        int damage = Math.max(0, actor.getAttack() - target.getDefense());

        int hpBefore = target.getCurrentHP();
        target.takeDamage(damage);

        System.out.println(actor.getName() + " -> performs Shield Bash on -> " + target.getName()
                + ": HP: " + hpBefore + " -> " + target.getCurrentHP()
                + " (dmg: " + actor.getAttack() + "-" + target.getDefense() + "=" + damage + ")");

        if (target.isAlive()) {
            target.addStatusEffect(new StunEffect(2));
            System.out.println("  " + target.getName() + " STUNNED (2 turns)");
        } else {
            System.out.println("  " + target.getName() + " has been ELIMINATED!");
        }

        // Power Stone uses don't trigger cooldown
        if (!fromPowerStone) {
            if (actor instanceof Player) {
                Player player = (Player) actor;
                player.startCooldown(3);
                System.out.println("Special Skills Cooldown set to 3 rounds");
            }
        }
    }
}
