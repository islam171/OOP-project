package research;

import java.util.ArrayList;
import java.util.List;

public class ResearchProject {

    private String topic;
    private List<Researcher> participants = new ArrayList<>();

    public ResearchProject(String topic) {
        setTopic(topic);
    }

    public void addParticipant(Object obj)  {
        if (!(obj instanceof Researcher)) {
            System.out.println("User is not a researcher!");
        }
        participants.add((Researcher) obj);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
