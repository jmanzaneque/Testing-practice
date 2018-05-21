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
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import org.mockito.hamcrest.MockitoHamcrest;

/**
 *
 * @author Jorge Manzaneque, Steven Córdova
 */
public class TicTacToeGameTest {
    
    private TicTacToeGame game;
    private Connection player1Con;
    private Connection player2Con;
    private Player p1;
    private Player p2;
    
    public TicTacToeGameTest() {
        game = new TicTacToeGame();
                
    }
    
    @Before
    public void setUp(){
        game = new TicTacToeGame();
        player1Con = mock(Connection.class);
        player2Con = mock(Connection.class);
        //Creo los jugadores
        p1 = new Player(1, "X", "Jugador 1");
        p2 = new Player(2, "O", "Jugador 2");
    }
    
    @Test
    public void testTicTacToeGameDraw(){
        /*
        Se simula el siguiente tablero (empate):
            O   X   X
            X   X   O
            O   O   X
        */
        //Secuencia completa: 1,0,4,7,3,5,8,6,2 (J1-J2-J1-J2 ...)
        int [] sequence = {1,0,4,7,3,5,8,6};
        this.fillBoard(sequence);
        
        //Realizamos el último movimiento, que determinará el empate
        ArgumentCaptor<CellMarkedValue> argument;
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
    public void testTicTacToeGameWinnerPlayer1(){
        /*
        Se simula el siguiente tablero (gana Jugador 1 (X)):
            X   X   X
            O   O   X
            O   X   O
        */
        //Secuencia completa: 0,3,1,4,5,6,7,8,2 (J1-J2-J1-J2 ...)
        int [] sequence = {0,3,1,4,5,6,7,8};
        this.fillBoard(sequence);
        
        //Realizamos el último movimiento, que determinará la victoria del jugador 1
        game.mark(2);
        ArgumentCaptor<CellMarkedValue> argument;
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
    
    @Test
    public void testTicTacToeGameWinnerPlayer2(){
        /*
        Se simula el siguiente tablero (gana Jugador 2 (O)):
            X   O   X
            X   X   
            O   O   O
        */
        //Secuencia completa: 0,1,2,6,3,7,4,8 (J1-J2-J1-J2 ...)
        int [] sequence = {0,1,2,6,3,7,4};
        this.fillBoard(sequence);
        
        //Realizamos el último movimiento, que determinará la victoria del jugador 2
        game.mark(8);
        ArgumentCaptor<CellMarkedValue> argument;
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(8));
        assertThat(argument.getValue().getPlayer(), is(p2));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(8));
        assertThat(argument.getValue().getPlayer(), is(p2));
        
        //Verificamos que se notifica el ganador
        ArgumentCaptor<WinnerValue> argumentWV = ArgumentCaptor.forClass(WinnerValue.class);
        //Notificación a la conexión del J1
        verify(player1Con).sendEvent(eq(EventType.GAME_OVER), argumentWV.capture());
        assertThat(argumentWV.getValue().getPlayer(), is(p2));
        
        //Comprobamos que las casillas que forman la línea coinciden para ambas conexiones
        int [] a = {6,7,8};
        for (int i=0; i<argumentWV.getValue().getPos().length; i++){
            assertEquals(argumentWV.getValue().getPos()[i], a[i]);
        }
        //Notificación a la conexión del J2
        verify(player2Con).sendEvent(eq(EventType.GAME_OVER), argumentWV.capture());
        assertThat(argumentWV.getValue().getPlayer(), is(p2));
        
        for (int i=0; i<argumentWV.getValue().getPos().length; i++){
            assertEquals(argumentWV.getValue().getPos()[i], a[i]);
        }
        
    }
    
    public void fillBoard(int [] sequence){
        //Añado los dobles al TicTacToeGame
        game.addConnection(player1Con);
        game.addConnection(player2Con);
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
        
        Player currentPlayer;
        Player nextPlayer;
        ArgumentCaptor<CellMarkedValue> argument;
        //Simularemos la dinámica de juego marcada por la secuencia
        //La secuencia acaba en el movimiento anterior a la finalización del juego (para distinguir entre los diferentes tests)
        for(int i=0; i<sequence.length; i++){
            if ((i % 2) == 0){  //Asigno los jugadores en función del turno (par: J1, impar: J2)
                currentPlayer = p1;
                nextPlayer = p2;
            } else {
                currentPlayer = p2;
                nextPlayer = p1;
            }
            
        game.mark(sequence[i]);
        argument = ArgumentCaptor.forClass(CellMarkedValue.class);
        verify(player1Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(sequence[i]));
        assertThat(argument.getValue().getPlayer(), is(currentPlayer));
        verify(player2Con).sendEvent(eq(EventType.MARK), argument.capture());
        assertThat(argument.getValue().getCellId(), is(sequence[i]));
        assertThat(argument.getValue().getPlayer(), is(currentPlayer));
                
        //Verificamos que se notifica el cambio de turno
        ArgumentCaptor<Player> argumentPl = ArgumentCaptor.forClass(Player.class);
        verify(player1Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(nextPlayer));
        verify(player2Con).sendEvent(eq(EventType.SET_TURN), argumentPl.capture());
        assertThat(argumentPl.getValue(), is(nextPlayer));
        //Una vez finalizado el turno, reseteamos los mocks para volver a verificar la llamada de los métodos
        reset(player1Con);
        reset(player2Con);    
            
        }
        
    }
        
}
