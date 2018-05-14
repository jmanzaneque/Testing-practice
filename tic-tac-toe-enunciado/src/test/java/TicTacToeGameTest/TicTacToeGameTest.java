/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacToeGameTest;

import es.codeurjc.ais.tictactoe.Connection;
import es.codeurjc.ais.tictactoe.Player;
import es.codeurjc.ais.tictactoe.TicTacToeGame;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import static org.hamcrest.CoreMatchers.hasItems;
import org.hamcrest.Matcher;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Jorge
 */
public class TicTacToeGameTest {
    
    private TicTacToeGame game;
    
    public TicTacToeGameTest() {
        
        game = new TicTacToeGame();
                
    }
    
    @Test
    public void ticTacToeGameTestDoble(){
        //Creo los dobles
        Connection player1Con = mock(Connection.class);
        Connection player2Con = mock(Connection.class);
        //Los añado al TicTacToeGame
        game.addConnection(player1Con);
        game.addConnection(player2Con);
        //Creo los jugadores y los añado
        Player p1 = new Player(1, "X", "Jugador 1");
        Player p2 = new Player(2, "O", "Jugador 2");
        game.addPlayer(p1);
        game.addPlayer(p2);
        //Comprueba que cada conexión recibe el mensaje JOIN_GAME
        verify(player1Con).sendEvent(eq(EventType.JOIN_GAME) , argThat(hasItems(p1,p2)));
        verify(player2Con).sendEvent(eq(EventType.JOIN_GAME) , argThat(hasItems(p1,p2)));
        
    }

    private Object argThat(Matcher<Iterable<Player>> hasItems) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
