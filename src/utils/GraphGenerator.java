package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class GraphGenerator {

    public static void generateGraph(int size_x, int size_y, int nodes_number) {
        Random random = new Random(System.currentTimeMillis());

        if(nodes_number > size_x * size_y) {
            System.err.println("Blad podczas generowania grafu: za duzo wezlow/za male rozmiary dla x: " + size_x + ", y: " + size_y + ", nodes:" + nodes_number);
            return;
        }

        int[] xs = new int[nodes_number];
        int[] ys = new int[nodes_number];
        int random_x = 0;
        int random_y = 0;

        for(int i = 0; i < nodes_number; i++) {
            random_x = random.nextInt(size_x) + 1;
            while (table_contains(xs, random_x))
                random_x = random.nextInt(size_x) + 1;

            random_y = random.nextInt(size_y) + 1;
            while (table_contains(ys, random_y))
                random_y = random.nextInt(size_y) + 1;

            xs[i] = random_x;
            ys[i] = random_y;
        }

        printToFile(xs, ys);
    }

    private static boolean table_contains(int[] table, int number) {

        for (int aTable : table) {
            if (aTable == number) return true;
        }
        return false;
    }

    //drukowanie raportu do pliku
    private static void printToFile(int[] x_coordinates, int[] y_coordinates)
    {
        String s_filepath = Consts.GRAPH_FILE_LOCATION;

        String node_list = "c pierwszy znak w linijce oznacza, co zawiera dana linijka\n";
        node_list += "c n - wspolrzedne wezla, EOF - koniec pliku\n";
        node_list += "c format wspolrzednych wezla: n <wsp.x> <wsp.y>\n\n";

        for(int i = 0; i < x_coordinates.length; i++) {
            node_list += "n " + x_coordinates[i] + " " + y_coordinates[i] + "\n";
        }

        node_list += "\nEOF";

        File file = new File(s_filepath);

        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            } //try...catch (IOException e)
        } //if(!file.exists())

        try
        {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter(s_filepath));
            buffWriter.write(node_list);
            buffWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } //try...catch (IOException e)

    } //public void printReportToFile()
}
