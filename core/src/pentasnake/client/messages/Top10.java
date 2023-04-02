package pentasnake.client.messages;

import pentasnake.client.entities.score.ResultClass;

import java.util.List;

public class Top10 extends Message{

    List<ResultClass> results;

    public Top10() {
    }

    public Top10(List<ResultClass> results) {
        this.results = results;
    }

    public List<ResultClass> getResults() {
        return results;
    }

    public void setResults(List<ResultClass> results) {
        this.results = results;
    }
}
