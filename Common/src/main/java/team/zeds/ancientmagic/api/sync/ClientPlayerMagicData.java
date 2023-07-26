package team.zeds.ancientmagic.api.sync;

public class ClientPlayerMagicData {
    private static int playerData;

    public static synchronized void setPlayerData(int data) {
        playerData = data;
    }

    public static synchronized int getPlayerData() {
        return playerData;
    }
}
