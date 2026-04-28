package game.statuseffect;

import game.combatant.Combatant;

// prevents the target from acting on their turn
public class StunEffect extends StatusEffect {

    public StunEffect(int duration) {
        super(duration);
    }

    public void applyTo(Combatant target) {}

    public void removeFrom(Combatant target) {}

    public boolean preventsAction() {
        return true;
    }

    public String toString() {
        return "Stunned (" + duration + " turns remaining)";
    }
}
