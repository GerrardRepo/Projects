package game.combatant;

import game.strategy.EnemyActionStrategy;

// HP:55 ATK:35 DEF:15 SPD:25
public class Goblin extends Enemy {
    public Goblin(String name, EnemyActionStrategy strategy) {
        super(name, 55, 35, 15, 25, strategy);
    }
}
