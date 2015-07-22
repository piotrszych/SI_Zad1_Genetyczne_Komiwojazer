package app_logic;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Piotr on 2015-03-17.
 */
public class LogicRandomSelection
{
    private ArrayList<Node> alist_graph = new ArrayList<Node>();    //graf, na ktorym dzialamy
    private ArrayList<SingleEntity> alist_population = new ArrayList<SingleEntity>(); //nasza populacja
    private ArrayList<String> alist_report = new ArrayList<String>(); //tablica stringów

    private String s_graph_filepath = "values\\graf.txt";
    private String s_reports_filepath = "values\\reports\\reportRandom";
    private int _i_report_counter = 0;

    public LogicRandomSelection()
    {
        alist_graph = LogicShared.load_graph(s_graph_filepath);
    }

    public void run(int i_population_size)
    {
        alist_population.clear();
        for(int i = 0; i < i_population_size; i++)
        {
            alist_population.add(new SingleEntity(alist_graph, true));
        }
        evaluate(alist_population);
        printReportToConsole();
    }

    public void printToFile()
    {
        LogicShared.printReportToFile(alist_report, s_reports_filepath, ++_i_report_counter);
    }

    private void evaluate(ArrayList<SingleEntity> population)
    {
        //do zaokraglania
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH); //zeby miejsca po przecinku byly oddzielane kropka
        DecimalFormat df = (DecimalFormat) numberFormat;
        df.applyPattern("#.####");

        //sortowanie populacji
        Collections.sort(population, new Comparator<SingleEntity>()
        {
            @Override
            public int compare(SingleEntity o1, SingleEntity o2)
            {
                if (o1.getRouteLength() == o2.getRouteLength())
                    return 0;

                return o1.getRouteLength() < o2.getRouteLength() ? -1 : 1;
            } //public int compare(SingleEntity o1, SingleEntity o2)
        }); //Collections.sort(alist_population, new Comparator<SingleEntity>()

        double d_averageRoute = 0.00;
        for (SingleEntity s : population)
        {
            d_averageRoute += s.getRouteLength();
        }
        d_averageRoute = d_averageRoute / population.size();

        String s_toreport = "b " + df.format(population.get(0).getRouteLength()) + " w " + df.format(population.get(population.size() - 1).getRouteLength()) + " avg " + df.format(d_averageRoute);
        alist_report.add(s_toreport);
    } //private void evaluate(ArrayList<SingleEntity> population)

    //drukowanie raportu w konsoli
    public void printReportToConsole()
    {
        System.out.println("--REPORT-RANDOM--");
        for (String s : alist_report)
        {
            System.out.println(s);
        } //for(String s : alist_reports)
        System.out.println("--ENDREPORT-RANDOM--");
    } //public void printReportToConsole()
}
