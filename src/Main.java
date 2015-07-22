import app_logic.LogicMain;
import app_logic.LogicRandomSelection;
import interfaces.MainWindow;

/**
 * Created by Piotr on 2015-03-14.
 */
public class Main
{
    public static void main(String[] args)
    {
        LogicMain logic = new LogicMain();
        //logic.run(60, 0.8, 0.05, 500, true);
        MainWindow mainWindow = new MainWindow(logic);
    }
}
