package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.tempCards.*;
import duelistmod.helpers.GridSort;
import duelistmod.variables.*;

public class Monsterbox extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("Monsterbox");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	
	private boolean run = false;

	public Monsterbox() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}
	
	@Override
    public void onEquip()
    {
		run = true;
		CardGroup availableCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		ArrayList<DuelistCard> types = DuelistCard.generateTypeCardsForRelics(this, 0, true, 5);
		for (AbstractCard c : types)
		{
			availableCards.addToTop(c);
		}
		Collections.sort(availableCards.group, GridSort.getComparator());
		availableCards.addToTop(new CancelCard());
		if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(availableCards, 1, "Choose a Monster Type", false);
    }
	
	@Override
	public void update()
	{
		super.update();
		if (run && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1)
		{
			int monsters = 0;
			int upgrades = 0;
			ArrayList<AbstractCard> toKeep = new ArrayList<AbstractCard>();
			ArrayList<AbstractCard> toKeepFromDeck = new ArrayList<AbstractCard>();
			for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
			{
				if (c.hasTag(Tags.MONSTER)) { monsters++; upgrades += c.timesUpgraded; }
				else { toKeepFromDeck.add(c); }
			}
			
			if ( AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof DynamicRelicTypeCard)
			{
				DynamicRelicTypeCard ref = (DynamicRelicTypeCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
				CardTags randType = ref.getTypeTag();
				boolean allowMegatype = false;
				if (randType.equals(Tags.MEGATYPED)) { allowMegatype = true; }
				for (int i = 0; i < monsters; i++)
				{
					AbstractCard c = DuelistCard.returnTrulyRandomFromSet(randType, false, allowMegatype);
					while (upgrades > 0 && c.canUpgrade()) { c.upgrade(); upgrades--; }
					toKeep.add(c.makeStatEquivalentCopy());
				}
				
				AbstractDungeon.player.masterDeck.group.clear();
				if (AbstractDungeon.player.hasRelic(MarkExxod.ID))
				{
					for (AbstractCard c : toKeepFromDeck) { AbstractDungeon.player.masterDeck.addToTop(c); }
					for (AbstractCard c : toKeep) { AbstractDungeon.player.masterDeck.addToBottom(c); }
				}
				else
				{
					for (AbstractCard c : toKeepFromDeck) { AbstractDungeon.player.masterDeck.addToRandomSpot(c); }
					for (AbstractCard c : toKeep) { AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f)); }
				}
			}
			run = false;
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
		}
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new Monsterbox();
	}
	
	@Override
	public int getPrice()
	{
		return 0;
	}
	
}
