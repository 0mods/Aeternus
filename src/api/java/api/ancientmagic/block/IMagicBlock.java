package api.ancientmagic.block;

import api.ancientmagic.atomic.AtomicUse;

@FunctionalInterface
public interface IMagicBlock {
    void use(AtomicUse<?> use);
}
