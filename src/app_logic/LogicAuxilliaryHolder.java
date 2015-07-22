package app_logic;

/**
 * Created by Piotr on 2015-03-17.
 */
public class LogicAuxilliaryHolder
{
    private SingleEntity entity;
    private int key;

    public LogicAuxilliaryHolder(int i, SingleEntity s)
    {
        this.entity = s;
        this.key = i;
    }

    public int getKey()
    {
        return key;
    }

    public SingleEntity getEntity()
    {
        return entity;
    }
}
