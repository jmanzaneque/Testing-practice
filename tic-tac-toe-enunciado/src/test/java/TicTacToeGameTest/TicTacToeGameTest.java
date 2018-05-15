/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacToeGameTest;

import es.codeurjc.ais.tictactoe.Connection;
import es.codeurjc.ais.tictactoe.Player;
import es.codeurjc.ais.tictactoe.TicTacToeGame;
import es.codeurjc.ais.tictactoe.TicTacToeGame.CellMarkedValue;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import org.mockito.hamcrest.MockitoHamcrest;

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
    public void ticTacToeGameTestDoubleWithDraw(){
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
        //Cuando solo se ha añadido un jugador, cada conexión recibe un JOIN_GAME con el primer jugador.
        verify(player1Con).sendEvent(eq(EventType.JOIN_GAME) , MockitoHamcrest.argThat(hasItems(p1)));
        verify(player2Con).sendEvent(eq(EventType.JOIN_GAME) , MockitoHamcrest.argThat(hasItems(p1)));
        //Reseteamos para volver a verificar que se ha llamado al método
        reset(player1Con);
        reset(player2Con);
        game.addPlayer(p2);
        //Comprueba que cada conexión recibe el mensaje JOIN_GAME con ambos jugadores
        verify(player1Con).sendEvent(eq(EventType.JOIN_GAME) , MockitoHamcrest.argThat(hasItems(p1,p2)));
        verify(player2Con).sendEvent(eq(EventType.JOIN_GAME) , MockitoHamcrest.argThat(hasItems(p1,p2)));
        reset(player1Con);
        reset(player2Con);
        
        //Simularemos la dinámica de juego siguiente (provocando un empate):
        //  O   X   X
        //  X   X   O
        //  O   O   X
        
        //Posición 1: X
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(1);
        ArgumentCaptor<CellMarkedValue> argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(1));
        assertThat(argument.getValue().getPlayer(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(1));
        assertThat(argument.getValue().getPlayer(), is(p1));
                
        //Verificamos que se notifica el cambio de turno al jugador 2 (en el array players, es la segunda posicion (1))
        ArgumentCaptor<Player> argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 0: O
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(0);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(0));
        assertThat(argument.getValue().getPlayer(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(0));
        assertThat(argument.getValue().getPlayer(), is(p2));
                
        //Verificamos que se notifica el cambio de turno al jugador 1
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 4: X
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(4);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(4));
        assertThat(argument.getValue().getPlayer(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(4));
        assertThat(argument.getValue().getPlayer(), is(p1));
                
        //Verificamos que se notifica el cambio de turno al jugador 2
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 7: O
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(7);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(7));
        assertThat(argument.getValue().getPlayer(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(7));
        assertThat(argument.getValue().getPlayer(), is(p2));
                
        //Verificamos que se notifica el cambio de turno al jugador 1
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 3: X
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(3);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(3));
        assertThat(argument.getValue().getPlayer(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(3));
        assertThat(argument.getValue().getPlayer(), is(p1));
                
        //Verificamos que se notifica el cambio de turno al jugador 2
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 5: O
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(5);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(5));
        assertThat(argument.getValue().getPlayer(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(5));
        assertThat(argument.getValue().getPlayer(), is(p2));
                
        //Verificamos que se notifica el cambio de turno al jugador 1
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 8: X
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(8);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(8));
        assertThat(argument.getValue().getPlayer(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(8));
        assertThat(argument.getValue().getPlayer(), is(p1));
                
        //Verificamos que se notifica el cambio de turno al jugador 2
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 6: O
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(6);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(6));
        assertThat(argument.getValue().getPlayer(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(6));
        assertThat(argument.getValue().getPlayer(), is(p2));
                
        //Verificamos que se notifica el cambio de turno al jugador 1
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 2: X
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(2);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(2));
        assertThat(argument.getValue().getPlayer(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(2));
        assertThat(argument.getValue().getPlayer(), is(p1));
        
        //Comprobamos que se notifica el empate
        verify(player1Con).sendEvent(eq(EventType.GAME_OVER), eq(null));
        verify(player2Con).sendEvent(eq(EventType.GAME_OVER), eq(null));
        
    }
    
    @Test
    public void ticTacToeGameTestDoubleWithWinner(){
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
        //Cuando solo se ha añadido un jugador, cada conexión recibe un JOIN_GAME con el primer jugador.
        verify(player1Con).sendEvent(eq(EventType.JOIN_GAME) , MockitoHamcrest.argThat(hasItems(p1)));
        verify(player2Con).sendEvent(eq(EventType.JOIN_GAME) , MockitoHamcrest.argThat(hasItems(p1)));
        //Reseteamos para volver a verificar que se ha llamado al método
        reset(player1Con);
        reset(player2Con);
        game.addPlayer(p2);
        //Comprueba que cada conexión recibe el mensaje JOIN_GAME con ambos jugadores
        verify(player1Con).sendEvent(eq(EventType.JOIN_GAME) , MockitoHamcrest.argThat(hasItems(p1,p2)));
        verify(player2Con).sendEvent(eq(EventType.JOIN_GAME) , MockitoHamcrest.argThat(hasItems(p1,p2)));
        reset(player1Con);
        reset(player2Con);
        
        //Simularemos la dinámica de juego siguiente:
        //  X   X   X
        //  O   O   X
        //  O   X   O
        
        //Posición 0: X
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(0);
        ArgumentCaptor<CellMarkedValue> argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(0));
        assertThat(argument.getValue().getPlayer(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(0));
        assertThat(argument.getValue().getPlayer(), is(p1));
                
        //Verificamos que se notifica el cambio de turno al jugador 2 (en el array players, es la segunda posicion (1))
        ArgumentCaptor<Player> argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 3: O
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(3);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(3));
        assertThat(argument.getValue().getPlayer(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(3));
        assertThat(argument.getValue().getPlayer(), is(p2));
                
        //Verificamos que se notifica el cambio de turno al jugador 1
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 1: X
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(1);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(1));
        assertThat(argument.getValue().getPlayer(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(1));
        assertThat(argument.getValue().getPlayer(), is(p1));
                
        //Verificamos que se notifica el cambio de turno al jugador 2
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 4: O
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(4);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(4));
        assertThat(argument.getValue().getPlayer(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(4));
        assertThat(argument.getValue().getPlayer(), is(p2));
                
        //Verificamos que se notifica el cambio de turno al jugador 1
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 5: X
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(5);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(5));
        assertThat(argument.getValue().getPlayer(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(5));
        assertThat(argument.getValue().getPlayer(), is(p1));
                
        //Verificamos que se notifica el cambio de turno al jugador 2
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 6: O
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(6);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(6));
        assertThat(argument.getValue().getPlayer(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(6));
        assertThat(argument.getValue().getPlayer(), is(p2));
                
        //Verificamos que se notifica el cambio de turno al jugador 1
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 7: X
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(7);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(7));
        assertThat(argument.getValue().getPlayer(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(7));
        assertThat(argument.getValue().getPlayer(), is(p1));
                
        //Verificamos que se notifica el cambio de turno al jugador 2
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p2));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 8: O
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(8);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(8));
        assertThat(argument.getValue().getPlayer(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(8));
        assertThat(argument.getValue().getPlayer(), is(p2));
                
        //Verificamos que se notifica el cambio de turno al jugador 1
        argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(p1));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);
        
        //Posición 2: X
        //Comenzamos a cambiar las casillas llamando al método mark
        game.mark(2);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(2));
        assertThat(argument.getValue().getPlayer(), is(p1));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(2));
        assertThat(argument.getValue().getPlayer(), is(p1));
        
        //Verificamos que se notifica el ganador
        ArgumentCaptor<WinnerValue> argumentWV = ArgumentCaptor.forClass(WinnerValue.class);
        verify(player1Con).sendEvent(eq(EventType.GAME_OVER), argumentWV.capture());
        assertThat(argumentWV.getValue().getPlayer(), is(p1));
        
        //Comprobamos que las casillas que forman la línea coinciden para ambas conexiones
        int [] a = {0,1,2};
        for (int i=0; i<argumentWV.getValue().getPos().length; i++){
            assertEquals(argumentWV.getValue().getPos()[i], a[i]);
        }
        verify(player2Con).sendEvent(eq(EventType.GAME_OVER), argumentWV.capture());
        assertThat(argumentWV.getValue().getPlayer(), is(p1));
        
        for (int i=0; i<argumentWV.getValue().getPos().length; i++){
            assertEquals(argumentWV.getValue().getPos()[i], a[i]);
        }
        
    }


    
}
