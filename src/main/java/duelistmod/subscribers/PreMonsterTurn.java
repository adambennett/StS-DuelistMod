package duelistmod.subscribers;

import com.megacrit.cardcrawl.monsters.*;

public class PreMonsterTurn {

    // Return false skips the monster turn
    // Triggers for each monster individually
    // Monsters keep their current action when skipped
    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        return true;
    }

}
