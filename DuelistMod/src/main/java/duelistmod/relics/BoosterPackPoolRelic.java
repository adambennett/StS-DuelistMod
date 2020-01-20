package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.cards.other.tempCards.BoosterPackCard;
import duelistmod.helpers.BoosterHelper;
import duelistmod.rewards.BoosterPack;
import duelistmod.ui.DuelistMasterCardViewScreen;

public class BoosterPackPoolRelic extends DuelistRelic implements ClickableRelic
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("BoosterPackPoolRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("BoosterPackPoolRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolRelic_Outline.png");
	public CardGroup pool;
	public Map<String, String> mapp = new HashMap<>();

	public BoosterPackPoolRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.MASTER_DECK);
		setDescription();
	}
	
	public void refreshPool()
	{
		if (DuelistMod.duelistChar != null)
		{
			pool.clear();
			if (BoosterHelper.packPool != null && BoosterHelper.packPool.size() > 0)
			{
				for (BoosterPack p : BoosterHelper.packPool) 
				{ 
					if (!mapp.containsKey(p.packName + "~" + p.rarity))
					{
						pool.group.add(new BoosterPackCard(p.packName, p.rarity)); 
						mapp.put(p.packName + "~" + p.rarity, p.packName);
					}					
				}
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
		return new BoosterPackPoolRelic();
	}

	@Override
	public void onRightClick() 
	{
		DuelistMasterCardViewScreen dmcvs = new DuelistMasterCardViewScreen("Booster Pack Pool", pool);
		AbstractDungeon.deckViewScreen = dmcvs;
		DuelistMod.lastDeckViewWasCustomScreen = true;
		if (pool.size() > 0) { dmcvs.open(); }
	}
	
	public void setDescription()
	{
		this.description = getUpdatedDescription();
		/* Relic Description			*/
		String poolDesc = "";
		poolDesc += " NL NL #yBoosters #b(" + this.pool.group.size() + ")";
		if (!poolDesc.equals("")) { description += poolDesc; }
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
}
