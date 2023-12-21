package duelistmod.interfaces;

import duelistmod.dto.AnyDuelist;

@FunctionalInterface
public interface EndureCard {

    void onEndure(AnyDuelist duelist);

}
