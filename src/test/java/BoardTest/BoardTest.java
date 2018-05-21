/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BoardTest;

import es.codeurjc.ais.tictactoe.Board;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Jorge Manzaneque, Steven Córdova
 */
public class BoardTest {
    
    private Board board;
    String labelPlayer1 = "X";
    String labelPlayer2 = "O";
    
    public BoardTest(){
        board = new Board();
        board.enableAll();
    }
    
    @Before
    public void setUp(){
        board = new Board();
        board.enableAll();
    }
    
    public void fillBoard (int [] sequence){
        String currentValue;
        for (int i=0; i<sequence.length; i++){
            if ((i % 2) ==0){
                currentValue = labelPlayer1;
            } else {
                currentValue = labelPlayer2;
            }
            board.getCell(sequence[i]).setValue(currentValue);
        }
    }
    
    @Test 
    public void testCheckDraw(){
        /*
        Se simula el siguiente tablero (empate):
            O   X   X
            X   X   O
            O   O   X
        */
        int [] sequence = {1,0,4,7,3,5,8,6,2};  //Secuencia con la que se completan los huecos del tablero
        this.fillBoard(sequence);
        assertTrue(board.checkDraw());
        
    }
    
    @Test
    public void testGetCellsIfWinnerWins1(){
        /*
        Se simula el siguiente tablero (gana Jugador 1 (X)):
            X   X   X
            O   O   X
            O   X   O
        */
        int [] sequence = {0,3,1,4,5,6,7,8,2};  //Secuencia con la que se completan los huecos del tablero
        this.fillBoard(sequence);
        int [] a = {0,1,2};
        int [] winnerCells = board.getCellsIfWinner(labelPlayer1);
        assertFalse(winnerCells == null);
        for (int i=0; i<winnerCells.length; i++){
            assertEquals(winnerCells[i], a[i]);
        }   
    }
    
    @Test
    public void testGetCellsIfWinnerWins2(){
        /*
        Se simula el siguiente tablero (gana Jugador 2 (O)):
            X   O   X
            X   X   
            O   O   O
        */
        int [] sequence = {0,1,2,6,3,7,4,8};  //Secuencia con la que se completan los huecos del tablero
        this.fillBoard(sequence);
        int [] a = {6,7,8};
        int [] winnerCells = board.getCellsIfWinner(labelPlayer2);
        assertFalse(winnerCells == null);
        for (int i=0; i<winnerCells.length; i++){
            assertEquals(winnerCells[i], a[i]);
        }   
    }
    
    @After
    public void tearDown(){
        board.disableAll();
    }
}
