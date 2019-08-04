package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.incomplete.MillenniumSpellbook;
import duelistmod.helpers.Util;
import duelistmod.potions.MillenniumElixir;
import duelistmod.variables.Strings;

public class MillenniumCoin extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("MillenniumCoin");
	public static final String IMG = DuelistMod.makePath(Strings.M_COIN_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_COIN_RELIC_OUTLINE);

	public MillenniumCoin() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
		setDescription();
	}
	
	@Override
	public void onEquip()
	{
		for (AbstractRelic r : AbstractDungeon.player.relics)
		{
			if (Util.isMillenniumItem(r, true) && !r.name.equals(this.name))
			{
				DuelistCard.gainGold(100, AbstractDungeon.player, true);
				Util.log("Relic that triggered gold gain: " + r.name);
			}
		}
		
		for (AbstractPotion p : AbstractDungeon.player.potions)
		{
			if (p instanceof MillenniumElixir)
			{
				DuelistCard.gainGold(100, AbstractDungeon.player, true);
				Util.log("Millennium Elixir triggered Millennium Coin");
			}
		}
		
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c instanceof MillenniumSpellbook)
			{
				DuelistCard.gainGold(100, AbstractDungeon.player, true);
				Util.log("Millennium Spellbook triggered Millennium Coin");
			}
		}
	}
	
	/* 	Enable this block of code if you want to put this relic back into the tomb event pool
	@Override
	public void obtain()
	{
		for (AbstractRelic r : AbstractDungeon.player.relics)
		{
			if (Utilities.isMillenniumItem(r, true) && !r.name.equals(this.name))
			{
				DuelistCard.gainGold(100, AbstractDungeon.player, true);
			}
		}
	}
	*/
	
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
