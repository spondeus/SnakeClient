package pentasnake.client.entities.score;

public class ResultClass {
    String name;
    Float result;

    public String getName()
    {
        return name;
    }

    public Float getResult()
    {
        return result;
    }

    public ResultClass(String name, Float result)
    {
        this.name = name;
        this.result = result;
    }
}
