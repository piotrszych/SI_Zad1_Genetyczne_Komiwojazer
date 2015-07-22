package app_logic;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Piotr on 2015-03-17.
 */
public class LogicShared
{
    //wczytywanie grafu z pliku
    public static ArrayList<Node> load_graph(String filepath)
    {
        String s_filepath = filepath;
        ArrayList<Node> alist_graph = new ArrayList();

        try
        {
            BufferedReader buff_r = new BufferedReader(new FileReader(s_filepath));
            String s_read_line = buff_r.readLine();
            while (s_read_line != null)
            {
                if (!s_read_line.equals("")) //umozliwia wstawianie pustych linii w pliku z grafem
                {
                    if (s_read_line.charAt(0) == 'n')
                    {
                        //wczytywanie node'ow
                        StringTokenizer tokenizer = new StringTokenizer(s_read_line, " ");
                        tokenizer.nextToken(); //pomijanie 'n'
                        int i_x = Integer.parseInt(tokenizer.nextToken());
                        int i_y = Integer.parseInt(tokenizer.nextToken());
                        alist_graph.add(new Node(i_x, i_y));
                    } //if(s_read_line.charAt(0) == 'n')
                } //if(!s_read_line.equals(""))
                s_read_line = buff_r.readLine();
            } //while(s_read_line != null)

            buff_r.close();
        } //try
        catch (FileNotFoundException e)
        {
            System.out.println("===ERROR: NIE ZNALEZIONO PLIKU W SCIEZCE: " + s_filepath);
            e.printStackTrace();
        } //catch (FileNotFoundException e)
        catch (IOException e)
        {
            System.out.println("===ERROR: NIE MOZNA WCZYTAC LINII");
            e.printStackTrace();
        } //catch (IOException e)

        return alist_graph;
    } //public static ArrayList<Node> load_graph(String filepath)

    //drukowanie raportu do pliku
    public static void printReportToFile(ArrayList<String> alist_reports, String s_report_file, int which)
    {
        String s_filepath = s_report_file + which + ".csv";

        String report = "";

        for(int i = 0; i < alist_reports.size(); i++)
        {
            StringTokenizer tokenizer = new StringTokenizer(alist_reports.get(i));
            tokenizer.nextToken(); //pomijanie b
            report += tokenizer.nextToken(); //dodawanie najlepszego wyniku
            report += ";";
            tokenizer.nextToken(); //pomijanie w
            report += tokenizer.nextToken(); //dodawnie najgorszego wyniku
            report += ";";
            tokenizer.nextToken(); //pomijanie avg
            report += tokenizer.nextToken(); //dodawanie sredniego wyniku
            report += "\r\n"; //koniec linii
        } //for(int i = 0; i < alist_reports.size(); i++)

        //zmieniamy kropki na przecinki
        report = report.replaceAll("\\.", "\\,");

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
            buffWriter.write(report);
            buffWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } //try...catch (IOException e)

    } //public void printReportToFile()
}
