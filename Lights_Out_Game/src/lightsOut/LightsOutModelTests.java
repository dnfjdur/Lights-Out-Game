package lightsOut;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class LightsOutModelTests
{

    private LightsOutModel test = new LightsOutModel(5, 5);

    private LightsOutModel reset (LightsOutModel a)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                a.lightSwitch(i, j);
            }
        }
        
        return a;
    }
    
    @Test
    public void blankWin() {
        assertEquals(0, test.move(0, 3));
        assertEquals(0, test.move(0, 4));
        assertEquals(0, test.move(1, 0));
        assertEquals(0, test.move(1, 1));
        assertEquals(0, test.move(1, 3));
        assertEquals(0, test.move(1, 4));
        assertEquals(0, test.move(2, 0));
        assertEquals(0, test.move(2, 1));
        assertEquals(0, test.move(2, 2));
        assertEquals(0, test.move(3, 1));
        assertEquals(0, test.move(3, 2));
        assertEquals(0, test.move(3, 3));
        assertEquals(0, test.move(4, 0));
        assertEquals(0, test.move(4, 2));
        assertEquals(1, test.move(4, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest ()
    {
        new LightsOutModel(5, 0);
    }

    @Test
    public void testLightSwitch ()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                test.lightSwitch(i, j);
                assertEquals(1, test.getOccupant(i, j));
            }
        }

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                test.lightSwitch(i, j);
                assertEquals(0, test.getOccupant(i, j));
            }
        }
    }

    @Test
    public void testMove ()
    {
        test.lightSwitch(0, 2);
        assertEquals(1, test.getOccupant(0, 2));
        test.lightSwitch(1, 1);
        assertEquals(1, test.getOccupant(1, 1));
        test.lightSwitch(1, 3);
        assertEquals(1, test.getOccupant(1, 3));
        test.lightSwitch(2, 0);
        assertEquals(1, test.getOccupant(2, 0));
        test.lightSwitch(2, 4);
        assertEquals(1, test.getOccupant(2, 4));
        test.lightSwitch(3, 1);
        assertEquals(1, test.getOccupant(3, 1));
        test.lightSwitch(3, 3);
        assertEquals(1, test.getOccupant(3, 3));
        test.lightSwitch(4, 2);
        assertEquals(1, test.getOccupant(4, 2));
        assertEquals(0, test.move(0, 0));
        assertEquals(0, test.move(0, 4));
        assertEquals(0, test.move(2, 2));
        assertEquals(0, test.move(4, 0));
        assertEquals(1, test.move(4, 4));

        test = new LightsOutModel(5, 5);
        assertEquals(0, test.getWins());
        
        blankWin();
        assertEquals(1, test.getWins());
        
        for(int i = 2; i <= 10; i++) {
            reset(test);
            blankWin();
            assertEquals(i, test.getWins());
        }
    }

    @Test
    public void testGetOccupant ()
    {
        test = new LightsOutModel(5, 5);
        test.move(2, 2);
        assertEquals(1, test.getOccupant(2, 2));
        assertEquals(0, test.getOccupant(1, 1));
        assertEquals(1, test.getOccupant(1, 2));
        assertEquals(0, test.getOccupant(1, 3));
        assertEquals(1, test.getOccupant(2, 1));
        assertEquals(0, test.getOccupant(3, 1));
        assertEquals(1, test.getOccupant(2, 3));
        assertEquals(0, test.getOccupant(3, 3));
        assertEquals(1, test.getOccupant(3, 2));
    }

}
