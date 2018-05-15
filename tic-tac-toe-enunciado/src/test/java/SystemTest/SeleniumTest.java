/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SystemTest;

import es.codeurjc.ais.tictactoe.WebApp;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Jorge Manzaneque, Steven Córdova
 */
public class SeleniumTest {
    
    protected WebDriver browser1;
    protected WebDriver browser2;
    
    public SeleniumTest() {
    }
    
    @BeforeClass
    public static void setUpClass(){
        ChromeDriverManager.getInstance().setup();
        WebApp.start();
    }
    
    @Before
    public void setUpTest(){
        browser1 = new ChromeDriver();
        browser2 = new ChromeDriver();
    }
    
    @After
    public void tearDown(){
        if (browser1 != null){
            browser1.quit();
        }
        if (browser2 != null){
            browser2.quit();
        } 
    }
    
    @AfterClass
    public static void tearDownClass(){
        WebApp.stop();
    }
    
    @Test
    public void testWinnerPlayer1(){
        String player1Name = "Jugador 1";
        String player2Name = "Jugador 2";
        int[] sequence = {0,3,1,4,5,6,7,8,2};
        String actual = testExec(sequence, player1Name, player2Name);
        String expected = player1Name + " wins! " + player2Name + " looses.";
        assertEquals(actual, expected);
    }
    
    @Test
    public void testWinnerPlayer2(){
        String player1Name = "Jugador 1";
        String player2Name = "Jugador 2";
        int[] sequence = {0,1,2,6,3,7,4,8};
        String actual = testExec(sequence, player1Name, player2Name);
        String expected = player2Name + " wins! " + player1Name + " looses.";
        assertEquals(actual, expected);
    }
    
    @Test
    public void testDraw(){
        String player1Name = "Jugador 1";
        String player2Name = "Jugador 2";
        int[] sequence = {1,0,4,7,3,5,8,6,2};
        String actual = testExec(sequence, player1Name, player2Name);
        String expected = "Draw!";
        assertEquals(actual, expected);
    }
    
    
    
    public String testExec(int [] sequence, String player1Name, String player2Name){
        String url = "http://localhost:8080/";
        browser1.get(url);
        browser1.findElement(By.id("nickname")).sendKeys(player1Name);
        browser1.findElement(By.id("startBtn")).click();
        
        browser2.get(url);
        browser2.findElement(By.id("nickname")).sendKeys(player2Name);
        browser2.findElement(By.id("startBtn")).click();
        String standardCell = "cell-";
        String actualCell;
        WebDriver currentBrowser;
 
        for (int i=0; i<sequence.length; i++){
            if((i%2)==0){   //Si es par, le toca al jugador 1, sino, al jugador 2
                currentBrowser = browser1;
            } else{
                currentBrowser = browser2;
            }
            actualCell = standardCell + sequence[i];
            currentBrowser.findElement(By.id(actualCell)).click();
        }
        //Como el alert se manda a ambos navegadores, comprobaremos si se manda el mismo mensaje a ambos
        String alert2 = browser2.switchTo().alert().getText();
        String alert1 = browser1.switchTo().alert().getText();
        browser1.switchTo().alert().accept();
        browser2.switchTo().alert().accept();
        assertEquals(alert1, alert2);
        return alert1;
    }
    
    
}
