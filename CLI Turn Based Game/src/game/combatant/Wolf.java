package game.combatant;

import game.strategy.EnemyActionStrategy;

// HP:40 ATK:45 DEF:5 SPD:35 
public class Wolf extends Enemy {
    public Wolf(String name, EnemyActionStrategy strategy) {
        super(name, 40, 45, 5, 35, strategy);
    }
}
