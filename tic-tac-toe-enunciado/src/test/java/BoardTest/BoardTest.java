/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BoardTest;

import es.codeurjc.ais.tictactoe.Board;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Jorge
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
    
    @Test 
    public void testCheckDrawFalse(){
        //El tablero está incompleto. Lo comprobamos después de introducir nuevos elementos
        assertFalse(board.checkDraw());
        board.getCell(0).setValue(labelPlayer1);
        assertFalse(board.checkDraw());
        board.getCell(1).setValue(labelPlayer1);
        assertFalse(board.checkDraw());
        board.getCell(2).setValue(labelPlayer2);
        assertFalse(board.checkDraw());
        board.getCell(3).setValue(labelPlayer2);
        assertFalse(board.checkDraw());
    }
    
    @Test
    public void testCheckDrawTrue(){
        
        //Cuando todo el tablero está relleno y no hay una fila del mismo jugador, muestra el empate
        //Player 1: 0,1,5,8. Player 2: 2,3,4,6,7
        board.getCell(0).setValue(labelPlayer1);
        board.getCell(1).setValue(labelPlayer1);
        board.getCell(2).setValue(labelPlayer2);
        board.getCell(3).setValue(labelPlayer2);
        board.getCell(4).setValue(labelPlayer2);
        board.getCell(5).setValue(labelPlayer1);
        board.getCell(6).setValue(labelPlayer2);
        board.getCell(7).setValue(labelPlayer2);
        board.getCell(8).setValue(labelPlayer1);
        
        assertTrue(board.checkDraw());
        
    }
    
    @Test
    public void testGetCellsIfWinner(){
        int [] a = {0,1,2};
        //Player 1: 0,1,2,5. Player 2: 3,4,6,7
        board.getCell(0).setValue(labelPlayer1);
        assertEquals(board.getCellsIfWinner(labelPlayer1),null);
        board.getCell(3).setValue(labelPlayer2);
        assertEquals(board.getCellsIfWinner(labelPlayer2),null);
        board.getCell(1).setValue(labelPlayer1);
        assertEquals(board.getCellsIfWinner(labelPlayer1),null);
        board.getCell(4).setValue(labelPlayer2);
        assertEquals(board.getCellsIfWinner(labelPlayer2),null);
        board.getCell(5).setValue(labelPlayer1);
        assertEquals(board.getCellsIfWinner(labelPlayer1),null);
        board.getCell(6).setValue(labelPlayer2);
        assertEquals(board.getCellsIfWinner(labelPlayer2),null);
        board.getCell(2).setValue(labelPlayer1);
        int [] winnerCells = board.getCellsIfWinner(labelPlayer1);
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
