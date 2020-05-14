package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.ui.DuelistCardSelectScreen;

public class MillenniumStone extends DuelistRelic
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("MillenniumStone");
    public static final String IMG = DuelistMod.makeRelicPath("MillenniumStone.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MillenniumStone_Outline.png");
    public boolean cardSelected = false;
	private DuelistCardSelectScreen dcss;
	private static final int maxHPLoss = 3;
    // /FIELDS

    public MillenniumStone() 
    { 
    	super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL); 
    	this.dcss = new DuelistCardSelectScreen(true);
    }
    
    
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public void onEquip()
    {
    	CardGroup group = AbstractDungeon.player.masterDeck.getUpgradableCards();
		AbstractDungeon.gridSelectScreen = this.dcss;
		DuelistMod.wasViewingSelectScreen = true;
		DuelistMod.selectingForRelics = true;
		((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(group, group.size(), "Choose any cards to Upgrade - Lose 3 Max HP for each Upgrade");
    }
    
    @Override
	public void update() 
	{
		super.update();
		if (this.dcss != null && (this.dcss.selectedCards.size() != 0) && !DuelistMod.selectingForRelics) 
		{
			for (AbstractCard c : this.dcss.selectedCards) 
			{ 
				AbstractDungeon.player.masterDeck.removeCard(c);
				AbstractCard card = c.makeStatEquivalentCopy();
				card.upgrade();
				AbstractDungeon.player.masterDeck.addToBottom(card);
				AbstractDungeon.player.decreaseMaxHealth(maxHPLoss); 
			}
			this.dcss.selectedCards.clear();
		}
	}
}
