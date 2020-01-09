package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.Util;
import duelistmod.ui.DuelistMasterCardViewScreen;

public class CardPoolBasicRelic extends DuelistRelic implements ClickableRelic
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("CardPoolBasicRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("CardPoolBasicRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolRelic_Outline.png");
	public CardGroup pool;

	public CardPoolBasicRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.MASTER_DECK);
		setDescription();
	}
	
	public void refreshPool()
	{
		if (DuelistMod.duelistChar != null)
		{
			pool.clear();
			if (AbstractDungeon.colorlessCardPool.group != null)
			{
				pool.group.addAll(AbstractDungeon.colorlessCardPool.group);
			}
			setDescription();
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
		return new CardPoolBasicRelic();
	}

	@Override
	public void onRightClick() 
	{
		DuelistMasterCardViewScreen dmcvs = new DuelistMasterCardViewScreen("Basic Card Pool", pool);
		AbstractDungeon.deckViewScreen = dmcvs;
		DuelistMod.lastDeckViewWasCustomScreen = true;
		if (pool.size() > 0) { dmcvs.open(); }
	}
	
	public void setDescription()
	{
		this.description = getUpdatedDescription();
		/* Relic Description			*/
		String poolDesc = "";

		/* Colorless Cards	*/
		// Basic Cards
		boolean holiday = DuelistMod.holidayNonDeckCards.size() > 0;
		boolean basic = AbstractDungeon.colorlessCardPool.group.size() > 0;
		if (holiday && basic)
		{
			poolDesc += " NL NL #yColorless #b(" + (AbstractDungeon.colorlessCardPool.group.size() + DuelistMod.holidayNonDeckCards.size()) + "): NL Basic Cards NL ";
			if (DuelistMod.addedBirthdayCards)  
			{ 
				if (Util.whichBirthday() == 1) { poolDesc += "Birthday Cards NL (Nyoxide's Birthday)";  }
				else if (Util.whichBirthday() == 2) 
				{
					String playerName = CardCrawlGame.playerName;
					poolDesc += "Birthday Cards NL (" + playerName + "'s Birthday)";
				}
				else if (Util.whichBirthday() == 3) { poolDesc += "Birthday Cards NL (DuelistMod's Birthday)"; }
			}
			if (DuelistMod.addedHalloweenCards) 
			{ 
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL Halloween Cards"; }
				else { poolDesc += "Halloween Cards"; }
			}
			
			else if (DuelistMod.addedXmasCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL Christmas Cards"; }
				else { poolDesc += "Christmas Cards"; }
			}
			
			else if (DuelistMod.addedWeedCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL 420 Cards"; }
				else { poolDesc += "420 Cards"; }
			}
		}
		else if (basic)
		{
			poolDesc += " NL NL #yColorless #b(" + AbstractDungeon.colorlessCardPool.group.size() + "): NL Basic Cards";
		}
		else if (holiday)
		{
			poolDesc += " NL NL #yColorless #b(" + DuelistMod.holidayNonDeckCards.size() + "): NL ";
			if (DuelistMod.addedBirthdayCards)  
			{ 
				if (Util.whichBirthday() == 1) { poolDesc += "Birthday Cards NL (Nyoxide's Birthday)";  }
				else if (Util.whichBirthday() == 2) 
				{
					String playerName = CardCrawlGame.playerName;
					poolDesc += "Birthday Cards NL (" + playerName + "'s Birthday)";
				}
				else if (Util.whichBirthday() == 3) { poolDesc += "Birthday Cards NL (DuelistMod's Birthday)"; }
			}
			if (DuelistMod.addedHalloweenCards) 
			{ 
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL Halloween Cards"; }
				else { poolDesc += "Halloween Cards"; }
			}
			
			else if (DuelistMod.addedXmasCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL Christmas Cards"; }
				else { poolDesc += "Christmas Cards"; }
			}
			
			else if (DuelistMod.addedWeedCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL 420 Cards"; }
				else { poolDesc += "420 Cards"; }
			}
		}
		if (!poolDesc.equals("") && TheDuelist.cardPool.size() > 0) { description += poolDesc; }
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
}
