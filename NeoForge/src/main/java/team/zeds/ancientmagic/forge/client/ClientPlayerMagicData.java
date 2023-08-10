package team.zeds.ancientmagic.forge.client;

public class ClientPlayerMagicData {
    private static int playerData;

    public static synchronized void setPlayerData(int data) {
        playerData = data;
    }

    public static synchronized int getPlayerData() {
        return playerData;
    }
}
