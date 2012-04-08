package springWeb.beans;

public class StateBean {
    public StateBean() {
        state = "default state";
    }

    private String state;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
