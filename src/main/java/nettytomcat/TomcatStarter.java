package nettytomcat;

public class TomcatStarter {
    public static void main(String[] args) throws Exception {
        TomcatServer server = new TomcatServer("");
        server.start();
    }
}
