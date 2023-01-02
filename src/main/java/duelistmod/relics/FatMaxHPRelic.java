package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.Util;
import duelistmod.variables.Strings;

public class FatMaxHPRelic extends DuelistRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("FatMaxHPRelic");
    public static final String IMG = DuelistMod.makeRelicPath("HPBox.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("HPBox_Outline.png");

	public FatMaxHPRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
		setDescription();
	}
	
	@Override
	public int getPrice() { return 25; }
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return AbstractDungeon.floorNum > 20 && Util.getChallengeLevel() < 10 && AbstractDungeon.ascensionLevel < 20;
	}
	
	@Override
	public void onEquip()
	{
		AbstractDungeon.player.increaseMaxHp(200, true);
		ArrayList<AbstractCard> cards = new ArrayList<>();
		int deckSize = AbstractDungeon.player.masterDeck.group.size();
		while (cards.size() < deckSize / 2)
		{
			AbstractCard c = AbstractDungeon.player.masterDeck.getRandomCard(true);
			AbstractDungeon.player.masterDeck.removeCard(c);
			cards.add(c);
		}
		AbstractDungeon.player.masterDeck.group.clear();
		AbstractDungeon.player.masterDeck.group.addAll(cards);
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
		return new FatMaxHPRelic();
	}
}
