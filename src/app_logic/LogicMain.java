package app_logic;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by Piotr on 2015-03-14.
 */

/**
 * begin
 * t := 0;
 * initialise(pop0);
 * evaluate(pop0);
 * while (not stop_condition) do
 *  begin
 *  popt+1 := selection(popt);
 *  popt+1 := crossover(popt+1);
 *  popt+1 := mutation(popt+1);
 *  evaluate(popt+1);
 *  t := t + 1;
 * end
 * end
 */
public class LogicMain
{
    private ArrayList<Node> alist_graph = new ArrayList<Node>();   //graf, na ktorym dzialamy
    private ArrayList<SingleEntity> alist_population = new ArrayList<SingleEntity>(); //nasza populacja
    private int i_population_size;
    private double i_crossover_probability = 0.8;
    private double i_mutation_probability = 0.05;
    private int _i_parentIndexHolder;
    private int _i_report_counter = 0;

    private String s_graph_filepath = "values\\graf.txt";
    private String s_reports_filepath = "values\\reports\\report";

    LogicRandomSelection logicRandom = new LogicRandomSelection();

    //do raportu
    private ArrayList<String> alist_reports = new ArrayList<String>();

    public void run(int t_populationSize, double t_crossoverProbability, double t_mutatuionProbability, int t_howManyTimes, int i_elites, boolean printToFile)
    {
        i_crossover_probability = t_crossoverProbability;
        i_mutation_probability = t_mutatuionProbability;
        alist_reports.clear();
        alist_graph = LogicShared.load_graph(s_graph_filepath);
        initialise(t_populationSize);
        evaluate(alist_population);
        geneticMagic(t_howManyTimes, i_elites);
        printReportToConsole();
        alist_population.get(0).print_entity();
        if(printToFile)
        {
            LogicShared.printReportToFile(alist_reports, s_reports_filepath, ++_i_report_counter);
        }
        for(int i = 0; i < t_howManyTimes; i++)
        {
            logicRandom.run(i_population_size);
        }
        logicRandom.printToFile();
    } //public void run(int t_populationSize, double t_crossoverProbability, double t_mutatuionProbability, int t_howManyTimes, int i_elites, boolean printToFile)

    private void geneticMagic(int i_how_many_times, int i_elites)
    {
        int i_counter = 0;
        while (i_counter < i_how_many_times)
        {
            //do your magic
            alist_population = selection(alist_population);
            alist_population = crossover(alist_population, i_elites);
            alist_population = mutation(alist_population);
            evaluate(alist_population);
            i_counter++;
        } //while (i_counter < i_how_many_times)
    } //public void geneticMagic(int i_how_many_times)

    private void initialise(int population_size)
    {
        alist_population = new ArrayList<SingleEntity>();
        this.i_population_size = population_size;
        for (int i = 0; i < i_population_size; i++)
        {
            alist_population.add(new SingleEntity(alist_graph, true));
        } //for (int i = 0; i < i_population_size; i++)

    } //void initialise(int population_size)

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

        //zabezpieczenie
        if(d_averageRoute >  population.get(population.size() - 1).getRouteLength())
            System.out.println("=============Coś nie tak ze srednia...");

        String s_toreport = "b " + df.format(population.get(0).getRouteLength()) + " w " + df.format(population.get(population.size() - 1).getRouteLength()) + " avg " + df.format(d_averageRoute);
        alist_reports.add(s_toreport);
    } //private void evaluate(ArrayList<SingleEntity> population)

    private ArrayList<SingleEntity> selection(ArrayList<SingleEntity> population)
    {
        ArrayList<SingleEntity> newPop = population;

        for(int i = 0; i < i_population_size; i++)
        {
            int i_first_contestant = randomWithRange(0, i_population_size - 1);
            int i_second_contestant = randomWithRange(0, i_population_size - 1);

            if(newPop.get(i_first_contestant).getRouteLength() >= newPop.get(i_second_contestant).getRouteLength())
            {
                newPop.set(i_first_contestant, newPop.get(i_second_contestant));
            }
            else
            {
                newPop.set(i_second_contestant, newPop.get(i_first_contestant));
            }
        } //for(int i = 0; i < i_population_size; i++)

        return newPop;
    } //private ArrayList<SingleEntity> selection(ArrayList<SingleEntity> population)

    private ArrayList<SingleEntity> crossover(ArrayList<SingleEntity> population, int elite)
    {
        ArrayList<SingleEntity> newPop = population;
        int i_elites = elite; //ilosc elity

        if(i_elites < 0 || i_elites > this.i_population_size)
            throw new IndexOutOfBoundsException("Za duzo elity badz elita jest liczba ujemna");

        int i_count = 0;
        //przepisujemy elite
        for(int i = this.i_population_size - i_elites; i < this.i_population_size; i++)
        {
            newPop.set(i, newPop.get(i_count));
            i_count++;
        } // for(int i = this.i_population_size - i_elites; i < this.i_population_size; i++)

        //iterujemy po wszystkich osobnikach
        for(int i = i_elites; i < this.i_population_size; i++)
        {
            //DEPRACATED wybieramy losowo pierwszego rodzica
            int i_first_parent_random_index = randomWithRange(i_elites, i_population_size - 1);
            int i_second_parent_random_index = randomWithRange(i_elites, i_population_size - 1);
            SingleEntity sEntity_child_1;
            SingleEntity sEntity_child_2;
            ArrayList<Node> alist_child1 = new ArrayList<Node>();
            ArrayList<Node> alist_child2 = new ArrayList<Node>();

            LogicAuxilliaryHolder lah_parent1 = parentsTournament(population);
            LogicAuxilliaryHolder lah_parent2 = parentsTournament(population);
            SingleEntity sEntity_parent1 = lah_parent1.getEntity();
            int iParentKey1 = lah_parent1.getKey();
            SingleEntity sEntity_parent2 = lah_parent2.getEntity();
            int iParentKey2 = lah_parent2.getKey();

            int i_first_split_seed = randomWithRange(0, alist_graph.size());
            int i_second_split_seed = randomWithRange(0, alist_graph.size());
            //pierwszy potomek
            //dodanie wszystkich node az do punktu przeciecia do listy pierwszego dziecka
            for(int i_splitcounter = 0; i_splitcounter < i_first_split_seed; i_splitcounter++)
            {
                alist_child1.add(sEntity_parent1.getNodes().get(i_splitcounter));
            } //for(int i_splitcounter = 0; i_splitcounter < i_first_split_seed; i_splitcounter++)
            SingleEntity sEntity_temp = new SingleEntity(alist_child1, false);
            //dodawanie pozostalych wezlow
            for(int j = 0; j < alist_graph.size(); j++)
            {
                //jesli w dziecku nie ma jeszcze wezla, to dodajemy
                if(!sEntity_temp.contains(sEntity_parent2.getNodes().get(j)))
                {
                    alist_child1.add(sEntity_parent2.getNodes().get(j));
                } //if(!sEntity_temp.contains(sEntity_parent2.getNodes().get(j)))
            } //for(int j = 0; j < alist_graph.size(); j++)
            sEntity_child_1 = new SingleEntity(alist_child1, false);

            //drugi potomek
            for(int i_splitcounter = 0; i_splitcounter < i_second_split_seed; i_splitcounter++)
            {
                alist_child2.add(sEntity_parent2.getNodes().get(i_splitcounter));
            } //for(int i_splitcounter = 0; i_splitcounter < i_second_split_seed; i_splitcounter++)
            sEntity_temp = new SingleEntity(alist_child2, false);
            for(int j = 0; j < alist_graph.size(); j++)
            {
                if(!sEntity_temp.contains(sEntity_parent1.getNodes().get(j)))
                {
                    alist_child2.add(sEntity_parent1.getNodes().get(j));
                } //if(!sEntity_temp.contains(sEntity_parent1.getNodes().get(j)))
            } //for(int j = 0; j < alist_graph.size(); j++)
            sEntity_child_2 = new SingleEntity(alist_child2, false);

            if(randomWithRange(0, 100) < 100*i_crossover_probability) //jesli prawdopodobienstwo sie zgadza
            {
                //dodawanie dzieci zamiast rodzicow
                newPop.set(iParentKey1, sEntity_child_1);

            } //if(randomWithRange(0, 100) < 100*i_crossover_probability)
            else
            {
                newPop.set(iParentKey1, sEntity_parent1);
            }
            if(randomWithRange(0, 100) < 100*i_crossover_probability) //jesli prawdopodobienstwo sie zgadza
            {
                //dodawanie dzieci zamiast rodzicow
                newPop.set(iParentKey1, sEntity_child_2);

            } //if(randomWithRange(0, 100) < 100*i_crossover_probability)
            else
            {
                newPop.set(iParentKey1, sEntity_parent2);
            }
        } //for(int i = 0; i < this.i_population_size; i++)

        return newPop;
    } //private ArrayList<SingleEntity> crossover(ArrayList<SingleEntity> population, int elite)

    private ArrayList<SingleEntity> mutation(ArrayList<SingleEntity> population)
    {
        ArrayList<SingleEntity> newPop = population;
        for(int i = 0; i < population.size(); i++)
        {
            if(randomWithRange(0, 100) < 100*i_mutation_probability)
            {
                int i_first_random_seed = randomWithRange(0, alist_graph.size() - 1);
                int i_second_random_seed = randomWithRange(0, alist_graph.size() - 1);
                newPop.get(i).swapNodes(i_first_random_seed, i_second_random_seed);
            } //if(randomWithRange(0, 100) < 100*i_mutation_probability)
        } //for(int i = 0; i < population.size(); i++)

        return newPop;
    } //public ArrayList<SingleEntity> mutation(ArrayList<SingleEntity> population)

    //TODO tak to zrobic?
    private LogicAuxilliaryHolder parentsTournament(ArrayList<SingleEntity> population)
    {
        ArrayList<SingleEntity> alist_candidates = new ArrayList<SingleEntity>();
        ArrayList<Integer> alist_indexes = new ArrayList<Integer>();

        int best_index;
        int how_many_times = population.size() < 5 ? population.size() : 5;
        for(int i = 0; i < how_many_times; i++)
        {
            int i_random_index = randomWithRange(0, population.size() - 1);
            alist_candidates.add(population.get(i_random_index));
            alist_indexes.add(i_random_index);
        }

        SingleEntity best = alist_candidates.get(0);
        best_index = alist_indexes.get(0);
        for(int i = 0; i < how_many_times; i++)
        {
            if(alist_candidates.get(i).getRouteLength() < best.getRouteLength())
            {
                best = alist_candidates.get(i);
                best_index = alist_indexes.get(i);
            }
        }
        LogicAuxilliaryHolder l = new LogicAuxilliaryHolder(best_index, best);
        return l;
    } //private SingleEntity parentsTournament(ArrayList<SingleEntity> population)

    //wyswietlanie zawartosci grafu w linii polecen
    private void print_graph()
    {
        System.out.println("--GRAF--");
        for (Node n : alist_graph)
        {
            System.out.println(n.toString());
        } //for(Node n : alist_graph)
        System.out.println("--ENDGRAF--");
    } //public void print_graph()

    private int getGraphSize()
    {
        return alist_graph.size();
    } //public int getGraphSize()

    public ArrayList<SingleEntity> getPopulation()
    {
        return alist_population;
    } //public ArrayList<SingleEntity> getPopulation(

    public void setPopulation(ArrayList<SingleEntity> pop)
    {
        this.alist_population = pop;
    } //public void setPopulation(ArrayList<SingleEntity> pop)

    //drukowanie raportu w konsoli
    public void printReportToConsole()
    {
        System.out.println("--REPORT--");
        System.out.println("----Iterations: " + alist_reports.size() + "----");
        for (String s : alist_reports)
        {
            System.out.println(s);
        } //for(String s : alist_reports)
        System.out.println("--ENDREPORT--");
    } //public void printReportToConsole()

    public String getReportString()
    {
        String report = "";
        report += ("c iterations: " + alist_reports.size());
        for (int i = 0; i < alist_reports.size(); i++)
        {
            report += ("\ns it" + i + ": " + alist_reports.get(i));
        } //for(int i = 0; i < alist_reports.size(); i++)

        return report;
    } //public String getReportString()

    //pomocnicza metoda
    private int randomWithRange(int min, int max)
    {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (min <= max ? min : max);
    } //int randomWithRange(int min, int max)

    public void test()
    {
        for(SingleEntity s : alist_population)
        {
            System.out.println("Route length: " + s.getRouteLength());
        }
    } //public void test()
} //public class LogicMain