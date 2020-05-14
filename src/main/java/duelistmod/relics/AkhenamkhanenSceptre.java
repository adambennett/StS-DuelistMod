package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.ui.DuelistCardSelectScreen;
import duelistmod.variables.Strings;

public class AkhenamkhanenSceptre extends DuelistRelic
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("AkhenamkhanenSceptre");
    public static final String IMG = DuelistMod.makeRelicPath("AkhenamkhanenSceptre.png");
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_ROD_RELIC_OUTLINE);
	private DuelistCardSelectScreen dcss;
	private static final int maxHPLoss = 6;
    // /FIELDS

    public AkhenamkhanenSceptre() 
    { 
    	super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL); 
    	this.dcss = new DuelistCardSelectScreen(true);
    }
    
    
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public void onEquip()
    {
    	CardGroup group = AbstractDungeon.player.masterDeck.getPurgeableCards();
		AbstractDungeon.gridSelectScreen = this.dcss;
		DuelistMod.wasViewingSelectScreen = true;
		DuelistMod.selectingForRelics = true;
		((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(group, group.size(), "Choose any cards to Remove - Lose 6 Max HP for each Removal");
    }
 
    @Override
	public void update() 
	{
		super.update();
		if (this.dcss != null && (this.dcss.selectedCards.size() != 0) && !DuelistMod.selectingForRelics) 
		{
			for (AbstractCard c : this.dcss.selectedCards) { AbstractDungeon.player.masterDeck.removeCard(c); AbstractDungeon.player.decreaseMaxHealth(maxHPLoss); }
			this.dcss.selectedCards.clear();
		}
	}
}
