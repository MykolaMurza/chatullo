package ua.mykolamurza.chatullo.config;

public class JoinQuitMessageConfig {
    private static String join;
    private static String quit;

    public static void setJoinQuit(String newJoin, String newQuit) {
        join = newJoin;
        quit = newQuit;
    }

    public static String getJoin() {
        return join;
    }

    public static String getQuit() {
        return quit;
    }

}
