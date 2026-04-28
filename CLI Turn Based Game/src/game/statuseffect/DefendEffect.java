package game.statuseffect;

import game.combatant.Combatant;

public class DefendEffect extends StatusEffect {
    private boolean applied = false;

    public DefendEffect(int duration) {
        super(duration);
    }

    public void applyTo(Combatant target) {
        if (!applied) {
            target.setDefense(target.getDefense() + 10);
            applied = true;
        }
    }

    public void removeFrom(Combatant target) {
        if (applied) {
            target.setDefense(target.getDefense() - 10);
            applied = false;
        }
    }

    public String toString() {
        return "Defending (" + duration + " turns remaining)";
    }
}
