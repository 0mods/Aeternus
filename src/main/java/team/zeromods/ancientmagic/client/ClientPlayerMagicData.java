package team.zeromods.ancientmagic.client;

public class ClientPlayerMagicData {
    public static int playerData;

    public static void setPlayerData(int playerData) {
        ClientPlayerMagicData.playerData = playerData;
    }

    public static int getPlayerData() {
        return playerData;
    }
}
