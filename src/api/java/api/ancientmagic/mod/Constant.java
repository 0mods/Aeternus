package api.ancientmagic.mod;

import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final String Key = "ancientmagic";
    public static final Logger LOGGER = LoggerFactory.getLogger("Ancient Magic");
    public static final List<ItemLike> LIST_OF_ITEMS_TO_MAGIC = new ArrayList<>();
    public static final List<ItemLike> LIST_OF_BLOCK_TO_MAGIC = new ArrayList<>();
    public static final List<ItemLike> LIST_OF_BLOCK_TO_DECORATE = new ArrayList<>();
    public static final String CurioKey = "curios";
}
