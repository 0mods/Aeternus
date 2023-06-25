package team.zeds.ancientmagic.client;

public class ClientPlayerMagicData {
    public static int playerData;

    public static synchronized void setPlayerData(int playerData) {
        ClientPlayerMagicData.playerData = playerData;
    }

    public static synchronized int getPlayerData() {
        return playerData;
    }
}
