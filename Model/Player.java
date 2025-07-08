package Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    private final String name;
    private final String imagePath;
    private int hp;
    private final int maxHp;
    private final List<Attack> attacks;
    private final Attack normalAttack;
    private final Attack ultimateAttack;

    public Player(String name, String imagePath, int maxHp, Attack nrmlAtk, Attack ultAtk) {
        this.name = name;
        this.imagePath = imagePath;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.attacks = Arrays.asList(nrmlAtk, ultAtk);
        this.normalAttack = nrmlAtk;
        this.ultimateAttack = ultAtk;
    }

    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public List<Attack> getAttacks() { return attacks; }
    public Attack getNrmlAtk() { return normalAttack; }
    public Attack getUltAtk() { return ultimateAttack; }

    public boolean ultIsAvailable(){ return (ultimateAttack.getUltCooldown() == 2); }

    public List<Attack> getAvailabeAttacks() {
        List<Attack> availableAttacks = new ArrayList<Attack> ();
        availableAttacks.add(normalAttack);
        if(ultIsAvailable()) availableAttacks.add(ultimateAttack);
        return availableAttacks;
    }

    public void takeDamage(int dmg) { this.hp = Math.max(0, this.hp - dmg); } 
    public boolean isFainted() { return hp <= 0; }

    public static Player createPlayerFromPath(String imagePath) {
        if (imagePath.contains("alien")) {
            return new Player("Alien", imagePath, 200,
                    new Attack("assets/attacks/Air_Attack_Basic.png", "Slimy Punch", 15, false),
                    new Attack("assets/attacks/Air_Attack_Ultimate.png", "UFO Blast", 30, true));
        } else if (imagePath.contains("mummy")) {
            return new Player("Mummy", imagePath, 200,
                    new Attack("assets/attacks/comet_Basic.png", "Wrap Attack", 10, false),
                    new Attack("assets/attacks/comet_Ultimate.png", "Ancient Curse", 35, true));
        } else if (imagePath.contains("Baum")) {
            return new Player("Tree Monster", imagePath, 210,
                    new Attack("assets/attacks/projectile_Red_Basic.png", "Root Whip", 18, false),
                    new Attack("assets/attacks/projectile_Red_Ultimate.png", "Earthquake", 40, true));
        } else if (imagePath.contains("Stein")) {
            return new Player("Rock Golem", imagePath, 220,
                    new Attack("assets/attacks/kleinStein_Basic.png", "Stone Fist", 20, false),
                    new Attack("assets/attacks/kleinStein_Ultimate.png", "Meteor Slam", 45, true));
        }
        return null;
    }
}
