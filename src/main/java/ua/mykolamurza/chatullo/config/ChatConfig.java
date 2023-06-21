package ua.mykolamurza.chatullo.config;

public class ChatConfig {
    private static int localChatDistance;
    private static String itemToPay;
    private static int amountToPay;

    public static void setItemAndAmount(String newItem, int newAmount) {
        itemToPay = newItem;
        amountToPay = newAmount;
    }

    public static int getLocalChatDistance() {
        return localChatDistance;
    }

    public static void setLocalChatDistance(int newDistance) {
        localChatDistance = newDistance;
    }

    public static String getItemToPay() {
        return itemToPay;
    }

    public static int getAmountToPay() {
        return amountToPay;
    }
}
