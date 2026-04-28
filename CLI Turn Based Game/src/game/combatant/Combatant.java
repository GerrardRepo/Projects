package game.combatant;

import game.statuseffect.StatusEffect;
import java.util.ArrayList;
import java.util.List;

public abstract class Combatant {
    private String name;
    private int maxHP;
    private int currentHP;
    private int attack;
    private int defense;
    private int speed;
    private List<StatusEffect> statusEffects;

    public Combatant(String name, int maxHP, int attack, int defense, int speed) {
        this.name = name;
        this.maxHP = maxHP;
        currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        statusEffects = new ArrayList<StatusEffect>();
    }

    // checks for damage-blocking effects before applying
    public void takeDamage(int damage) {
        for (int i = 0; i < statusEffects.size(); i++) {
            if (statusEffects.get(i).blocksDamage()) {
                damage = 0;
                break;
            }
        }
        currentHP -= damage;
        if (currentHP < 0) currentHP = 0;
    }

    public void heal(int amount) {
        currentHP += amount;
        if (currentHP > maxHP) currentHP = maxHP;
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    // returns true if any effect prevents action
    public boolean isStunned() {
        for (int i = 0; i < statusEffects.size(); i++) {
            if (statusEffects.get(i).preventsAction()) {
                return true;
            }
        }
        return false;
    }

    public void addStatusEffect(StatusEffect effect) {
        statusEffects.add(effect);
        effect.applyTo(this);
    }

    // tick durations down, then clean up expired effects
    public void updateEffects() {
        // Tick all durations down by 1
        for (int i = 0; i < statusEffects.size(); i++) {
            statusEffects.get(i).tick();
        }
        // Remove any that have expired
        ArrayList<StatusEffect> expired = new ArrayList<StatusEffect>();
        for (int i = 0; i < statusEffects.size(); i++) {
            if (statusEffects.get(i).isExpired()) {
                expired.add(statusEffects.get(i));
            }
        }
        for (int i = 0; i < expired.size(); i++) {
            expired.get(i).removeFrom(this);
            statusEffects.remove(expired.get(i));
        }
    }

    public String getName() { return name; }
    public int getMaxHP() { return maxHP; }
    public int getCurrentHP() { return currentHP; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }

    public List<StatusEffect> getStatusEffects() {
        return statusEffects;
    }

    public void setAttack(int attack) { this.attack = attack; }
    public void setDefense(int defense) { this.defense = defense; }
}
