package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.interfaces.MillenniumItem;

public class MillenniumCoin extends DuelistRelic implements MillenniumItem
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("MillenniumCoin");
	public static final String IMG = DuelistMod.makeRelicPath("MillenniumCoinRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MillenniumCoin_Outline.png");
	private static final int pickupGold = 120;
	private static final int afterPickupGold = 40;


	public MillenniumCoin() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
		setDescription();
	}

	public void gainGold() {
		AbstractDungeon.player.gainGold(afterPickupGold);
	}
	
	@Override
	public void onEquip()
	{
		for (AbstractRelic r : AbstractDungeon.player.relics) {
			if (r instanceof MillenniumItem && !(r instanceof MillenniumCoin)) {
				AbstractDungeon.player.gainGold(pickupGold);
			}
		}
		
		for (AbstractPotion p : AbstractDungeon.player.potions) {
			if (p instanceof MillenniumItem) {
				AbstractDungeon.player.gainGold(pickupGold);
			}
		}
		
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
			if (c instanceof MillenniumItem) {
				AbstractDungeon.player.gainGold(pickupGold);
			}
		}
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumCoin();
	}
}
