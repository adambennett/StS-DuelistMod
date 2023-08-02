package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnChannelRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.enums.StartingDeck;

public class SpellcasterToken extends DuelistRelic implements OnChannelRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("SpellcasterToken");
	public static final String IMG = DuelistMod.makeRelicPath("SpellcasterToken.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("SpellcasterToken_Outline.png");

	private boolean finished = false;
	
	public SpellcasterToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
		setDescription();
	}
	
	@Override
	public void onChannel(AbstractOrb arg0) 
	{
		if (!finished)
		{
			DuelistCard.spellcasterPuzzleChannel();
		}
		finished = true;
	}

	@Override
	public void onPlayCard(AbstractCard c, AbstractMonster m) 
	{
		finished = false;
	}
	
	@Override
	public void atTurnStart() 
	{
		finished = false;
	}

	@Override
	public void onPlayerEndTurn() 
	{
		finished = false;
	}
	
	@Override
	public void onMonsterDeath(AbstractMonster m) 
	{
		finished = false;
	}

	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return StartingDeck.currentDeck == StartingDeck.SPELLCASTER;
	}
	
	@Override
	public void onEquip()
	{
		setDescription();
	}
	

	@Override
	public void onUnequip()
	{
		
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
		return new SpellcasterToken();
	}
}
