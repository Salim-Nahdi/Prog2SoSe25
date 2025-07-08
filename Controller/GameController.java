package Controller;
import Model.Player;
import Model.Attack;


public class GameController {
    private Player[] player1;
    private Player[] player2;
    private boolean player1Turn = true;
    private int[] selPlayer = {0, 0}; //Humen Playber; PC Player

    public GameController(Player[] p1, Player[] p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    

    public Player[] getAllPlayers1(){ return player1; }

    public Player[] getAllPlayers2(){ return player2; }

    public Player getPlayer1(){ return player1[selPlayer[0]]; }

    public Player getPlayer2(){ return player2[selPlayer[1]]; }

    public Player getCurrentPlayer() {
        return player1Turn ? player1[selPlayer[0]] : player2[selPlayer[1]];
    }

    public Player getOpponent() {
        return player1Turn ? player2[selPlayer[1]] : player1[selPlayer[0]];
    }


    public void switchTurn() {
        player1Turn = !player1Turn;
    }

    public boolean isPlayer1(){ return player1Turn; }


    public boolean takeTurn(int attack) {
        getOpponent().takeDamage(attack);
        return getOpponent().isFainted();
    }

    public int[] getSelectedPlayer() {
        return selPlayer;
    }


    public void switchPlayer(boolean isHumen){
        if(isHumen) selPlayer[0] = ((selPlayer[0] + 1) % 2);
        else selPlayer[1] = ((selPlayer[1] + 1) % 2);
    }

}

