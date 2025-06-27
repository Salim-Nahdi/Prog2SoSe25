package Controller;
import Model.Player;
import Model.Attack;



public class GameController {
    private Player player1;
    private Player player2;
    private boolean player1Turn = true;

    public GameController(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    public Player getCurrentPlayer() {
        return player1Turn ? player1 : player2;
    }

    public Player getOpponent() {
        return player1Turn ? player2 : player1;
    }

    public boolean takeTurn(Attack attack) {
        getOpponent().takeDamage(attack.getDamage());
        player1Turn = !player1Turn;
        return getOpponent().isFainted();
    }
}
