package game.action;

import game.combatant.Combatant;
import game.combatant.Wizard;
import game.engine.BattleContext;
import java.util.List;

public class ArcaneBlast implements Action {
    private boolean fromPowerStone;

    public ArcaneBlast(boolean fromPowerStone) {
        this.fromPowerStone = fromPowerStone;
    }

    // normal use (not from power stone)
    public ArcaneBlast() {
        this.fromPowerStone = false;
    }

    public void execute(Combatant actor, BattleContext context) {
        if (!(actor instanceof Wizard)) {
            return;
        }
        Wizard wizard = (Wizard) actor;

        List<Combatant> aliveEnemies = context.getAliveEnemies();
        System.out.println(wizard.getName() + " -> performs Arcane Blast on All Enemies:");

        for (int i = 0; i < aliveEnemies.size(); i++) {
            Combatant enemy = aliveEnemies.get(i);

            int damage = wizard.getAttack() - enemy.getDefense();
            if(damage < 0) damage = 0;

            int hpBefore = enemy.getCurrentHP();
            enemy.takeDamage(damage);

            System.out.println(enemy.getName() + " HP: " + hpBefore + " -> " + enemy.getCurrentHP()
                    + " (dmg: " + wizard.getAttack() + "-" + enemy.getDefense() + "=" + damage + ")");

            if (!enemy.isAlive()) {
                wizard.addArcaneBlastBonus(10);
                System.out.println(enemy.getName() + " ELIMINATED! Wizard ATK: "
                        + wizard.getAttack() + " (+10 per Arcane Blast kill)");
            }
        }

        // Power Stone uses don't trigger cooldown
        if (!fromPowerStone) {
            wizard.startCooldown(3);
            System.out.println("Special Skills Cooldown set to 3 rounds");
        }
    }
}
