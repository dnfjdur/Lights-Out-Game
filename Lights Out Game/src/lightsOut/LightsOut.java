package lightsOut;

import javax.swing.SwingUtilities;

/**
 * Created for CS1410 by Oliver Yu
 */
public class LightsOut
{
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater( () -> new LightsOutView());
    }
}
