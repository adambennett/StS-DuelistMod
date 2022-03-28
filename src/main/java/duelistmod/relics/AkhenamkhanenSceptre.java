package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.ui.DuelistCardSelectScreen;
import duelistmod.variables.Strings;

import java.util.*;

public class AkhenamkhanenSceptre extends DuelistRelic
{

	public static final String ID = DuelistMod.makeID("AkhenamkhanenSceptre");
    public static final String IMG = DuelistMod.makeRelicPath("AkhenamkhanenSceptre.png");
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_ROD_RELIC_OUTLINE);
	private static final int maxHPLoss = 6;

    public AkhenamkhanenSceptre() 
    { 
    	super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public void onEquip()
    {
    	CardGroup group = AbstractDungeon.player.masterDeck.getPurgeableCards();
		DuelistMod.duelistCardSelectScreen.open(true, group, group.size(), "Choose any cards to Remove - Lose 6 Max HP for each Removal", this::confirmLogic);
    }

	private void confirmLogic(List<AbstractCard> selectedCards) {
		for (AbstractCard c : selectedCards) {
				AbstractDungeon.player.masterDeck.removeCard(c);
				AbstractDungeon.player.decreaseMaxHealth(maxHPLoss);
		}
	}
}
