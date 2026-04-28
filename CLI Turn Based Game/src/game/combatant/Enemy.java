package game.combatant;

import game.strategy.EnemyActionStrategy;

public abstract class Enemy extends Combatant {
    private EnemyActionStrategy actionStrategy;

    public Enemy(String name, int maxHP, int attack, int defense, int speed, EnemyActionStrategy strategy) {
        super(name, maxHP, attack, defense, speed);
        actionStrategy = strategy;
    }

    public EnemyActionStrategy getActionStrategy() { return actionStrategy; }
}
