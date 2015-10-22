package app_logic;

public class Node
{
    private int i_coordinate_x;
    private int i_coordinate_y;

    public Node(int x_coordinate, int y_coordinate)
    {
        i_coordinate_x = x_coordinate;
        i_coordinate_y = y_coordinate;
    } //public Node(int x_coordinate, int y_coordinate)

    public double distanceTo(Node other_node)
    {
        //pierwiastek z sumy kwadratów różnic odpowiadających sobie współrzędnych
        return Math.sqrt((this.i_coordinate_x - other_node.getX())*(this.i_coordinate_x - other_node.getX()) +
                (this.i_coordinate_y - other_node.getY())*(this.i_coordinate_y - other_node.getY()));
    } //public double distanceTo(Node other_node)

    //coordinates geters
    public int getX()
    {
        return i_coordinate_x;
    } //public int getX()

    public int getY()
    {
        return i_coordinate_y;
    } //public int getY()

    public String toString()
    {
        //w formacie: "Node: x = <wsp_x>, y = <wsp_y>"
        return "Node: x = " + i_coordinate_x + ", y = " + i_coordinate_y;
    } //public String toString()

    @Override
    public boolean equals(Object other)
    {
        if(other == null) return false;
        if(other.getClass() != this.getClass()) return false;
        if(((Node) other).getX() != this.getX() || ((Node) other).getY() != this.getY()) return false;
        return true;
    } //public boolean equals(Object other)
}
