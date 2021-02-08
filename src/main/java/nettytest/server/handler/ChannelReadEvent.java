package nettytest.server.handler;

public class ChannelReadEvent {
    private String name;

    ChannelReadEvent(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
