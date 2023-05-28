package api.ancientmagic.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


public class HasGroupBlockItem extends BlockItem {
    private final CreativeModeTab group;

    public HasGroupBlockItem(Block p_40565_, Properties p_40566_, CreativeModeTab group) {
        super(p_40565_, p_40566_);
        this.group = group;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addItemToTab);
    }

    private void addItemToTab(CreativeModeTabEvent.BuildContents e) {
        if (e.getTab() == this.group) e.accept(()-> this);
    }
}
