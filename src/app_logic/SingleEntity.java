package app_logic;

import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Piotr on 2015-03-15.
 */
public class SingleEntity
{
    private ArrayList<Node> alist_internal_nodes = new ArrayList<Node>();

    public SingleEntity(int size)
    {
        //pusta tablica
        for(int i = 0; i < size; i++)
        {
            alist_internal_nodes.add(null);
        } //for(int i = 0; i < size; i++)
    } //public SingleEntity(int size)

    public SingleEntity(ArrayList<Node> graph, boolean param)
    {
        //jesli param = true, randomizujemy kolejnosc, jesli false - nie

        //kopiowanie grafu
        for(Node n : graph)
        {
            alist_internal_nodes.add(new Node(n.getX(), n.getY()));
        } //for(Node n : graph)

        if(param)
        {
            Collections.shuffle(alist_internal_nodes);
        } //if(param)
    } //public SingleEntity(ArrayList<Node> graph)

    public SingleEntity(SingleEntity other)
    {
        this(other.getNodes(), false);
    }

    public ArrayList<Node> getNodes()
    {
        return alist_internal_nodes;
    } //public ArrayList<Node> getNodes()

    public void swapNodes(int i_first_index, int i_second_index)
    {
        Node node_temp = alist_internal_nodes.get(i_first_index);
        alist_internal_nodes.set(i_first_index, alist_internal_nodes.get(i_second_index));
        alist_internal_nodes.set(i_second_index, node_temp);
    } //public void swapNodes(int i_first_index, int i_second_index)

    //oblicanie trasy osobnika
    public double getRouteLength()
    {
        double d_temp_route = 0;
        //zliczanie odleglosci pomiedzy miastami
        for(int i = 0; i < alist_internal_nodes.size() - 1; i++)
        {
            d_temp_route += alist_internal_nodes.get(i).distanceTo(alist_internal_nodes.get(i+1));
        } //for(int i = 0; i < alist_internal_nodes.size() - 1; i++)

        //polaczenie miedzy ostatnim a pierwszym
        d_temp_route += alist_internal_nodes.get(0).distanceTo(alist_internal_nodes.get(alist_internal_nodes.size() - 1));

        return  d_temp_route;
    } //public double getRouteLength()

    //drukowanie grafu
    public void print_entity()
    {
        System.out.println("---ENTITY---");
        for(Node n : alist_internal_nodes)
        {
            System.out.println(n.toString());
        } //for(Node n : alist_internal_nodes)
        System.out.println("---ENDENTITY---");
    } //public void print_entity()

    @Override
    public boolean equals(Object other)
    {
        if(other == null) return false;
        if(other.getClass() != this.getClass())
        {
            return false;
        }
        for(int i = 0; i < alist_internal_nodes.size(); i++)
        {
            if(alist_internal_nodes.get(i).getX() != ((SingleEntity) other).getNodes().get(i).getX()
                    || alist_internal_nodes.get(i).getY() != ((SingleEntity) other).getNodes().get(i).getY())
                return false;
        }
        return true;
    }

    public boolean contains(Node n)
    {
        boolean b_found = false;
        for(int i = 0; i < alist_internal_nodes.size(); i++)
        {
            if(alist_internal_nodes.get(i).equals(n))
            {
                b_found = true;
            }
        }
        return b_found;
    } //public boolean contains(Node n)

} //public class SingleEntity
