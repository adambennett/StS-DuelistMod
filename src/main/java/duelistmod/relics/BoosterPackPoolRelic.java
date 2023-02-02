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
import duelistmod.interfaces.*;
import duelistmod.rewards.BoosterPack;
import duelistmod.ui.DuelistMasterCardViewScreen;

public class BoosterPackPoolRelic extends DuelistRelic implements ClickableRelic, VisitFromAnubisRemovalFilter
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
	
	public void refreshPool() {
		if (DuelistMod.duelistChar != null) {
			pool.clear();
			mapp.clear();
			if (BoosterHelper.packPool != null && BoosterHelper.packPool.size() > 0) {
				BoosterHelper.packPool.forEach(p -> {
					if (!mapp.containsKey(p.packName + "~" + p.rarity) && p.canSpawn()) {
						pool.group.add(new BoosterPackCard(p.packName, p.rarity));
						mapp.put(p.packName + "~" + p.rarity, p.packName);
					}
				});
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
		if (pool.size() > 0) {
			DuelistMod.duelistMasterCardViewScreen.open("Booster Pack Pool", pool);
		}
	}
	
	public void setDescription()
	{
		this.description = getUpdatedDescription();
		description += " NL NL #yBoosters #b(" + this.pool.group.size() + ")";
		tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
}
