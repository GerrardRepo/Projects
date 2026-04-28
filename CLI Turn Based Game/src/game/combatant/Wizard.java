package game.combatant;

// HP:200 ATK:50 DEF:10 SPD:20 
public class Wizard extends Player {
    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }

    // +10 ATK per enemy killed by arcane blast (permanent for the battle)
    public void addArcaneBlastBonus(int bonus){
        setAttack( getAttack() + bonus );
    }
}
