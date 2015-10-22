package app_logic;

public class LogicAuxiliaryHolder
{
    private SingleEntity entity;
    private int key;

    public LogicAuxiliaryHolder(int i, SingleEntity s)
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
