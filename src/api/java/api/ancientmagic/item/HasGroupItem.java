package api.ancientmagic.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class HasGroupItem extends Item {
    private final CreativeModeTab group;

    public HasGroupItem(Properties p_41383_, CreativeModeTab group) {
        super(p_41383_);
        this.group = group;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addItemToTab);
    }

    private void addItemToTab(CreativeModeTabEvent.BuildContents e) {
        if (e.getTab() == this.group) e.accept(()-> this);
    }
}
