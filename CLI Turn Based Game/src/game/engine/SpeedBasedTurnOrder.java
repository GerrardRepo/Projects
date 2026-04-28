package game.engine;

import game.combatant.Combatant;
import java.util.ArrayList;
import java.util.List;

// sorts combatants by speed descending so fastest goes first
public class SpeedBasedTurnOrder implements TurnOrderStrategy {

    public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
        ArrayList<Combatant> sorted = new ArrayList<Combatant>(combatants);

        // bubble sort by speed (highest first)
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = 0; j < sorted.size()-i-1; j++) {
                if (sorted.get(j).getSpeed() < sorted.get(j+1).getSpeed()) {
                    Combatant temp = sorted.get(j);
                    sorted.set(j, sorted.get(j+1));
                    sorted.set(j+1, temp);
                }
            }
        }
        return sorted;
    }
}
