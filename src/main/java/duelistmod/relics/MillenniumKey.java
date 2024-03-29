package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.Util;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.variables.Strings;

public class MillenniumKey extends DuelistRelic implements MillenniumItem {
    public static final String ID = DuelistMod.makeID("MillenniumKey");
    public static final String IMG = DuelistMod.makePath(Strings.M_KEY_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.M_KEY_RELIC_OUTLINE);

    public MillenniumKey() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public boolean canSpawn() {
        return super.canSpawn() && !Util.deckIs(StartingDeck.BEAST.getDeckName(), StartingDeck.INCREMENT.getDeckName());
    }

    @Override
    public void atBattleStart() {}
    
    @Override
	public void onEquip() {
		//DuelistMod.hasKey = true;
		AbstractDungeon.player.energy.energyMaster++;
	}
	
	@Override
	public void onUnequip() {
		AbstractDungeon.player.energy.energyMaster--;
	}

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MillenniumKey();
    }
}
