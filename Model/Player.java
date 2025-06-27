package Model;
import java.util.List;

public class Player {
    private final String name;
    private final String imagePath;
    private int hp;
    private final int maxHp;
    private final List<Attack> attacks;

    public Player(String name, String imagePath, int maxHp, List<Attack> attacks) {
        this.name = name;
        this.imagePath = imagePath;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.attacks = attacks;
    }

    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public List<Attack> getAttacks() { return attacks; }

    public void takeDamage(int dmg) { this.hp = Math.max(0, this.hp - dmg); } 
    public boolean isFainted() { return hp <= 0; }
}