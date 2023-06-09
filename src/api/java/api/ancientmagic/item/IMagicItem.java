package api.ancientmagic.item;

@FunctionalInterface
public interface IMagicItem {
    void use(MagicItemReturnableUse returnableUse);
}
