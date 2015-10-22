import app_logic.LogicMain;
import interfaces.MainWindow;
import utils.Consts;
import utils.GraphGenerator;

public class Main
{
    public static void main(String[] args)
    {
        GraphGenerator.generateGraph(Consts.NEW_GRAPH_X_SIZE, Consts.NEW_GRAPH_Y_SIZE, Consts.NEW_GRAPH_NUMBER_OF_NODES);

        LogicMain logic = new LogicMain();
        //logic.run(60, 0.8, 0.05, 500, true);
        MainWindow mainWindow = new MainWindow(logic);
    }
}
