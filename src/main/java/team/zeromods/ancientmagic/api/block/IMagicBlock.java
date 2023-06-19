package team.zeromods.ancientmagic.api.block;

import team.zeromods.ancientmagic.api.atomic.AtomicUse;

@FunctionalInterface
public interface IMagicBlock {
    void use(AtomicUse<?> use);
}
