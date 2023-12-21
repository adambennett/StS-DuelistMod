package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnChannelRelic;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.Util;

public class SpellcasterOrb extends DuelistRelic implements OnChannelRelic {

	public static final String ID = DuelistMod.makeID("SpellcasterOrb");
	public static final String IMG = DuelistMod.makeRelicPath("SpellcasterOrb.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("SpellcasterOrb_Outline.png");

	public SpellcasterOrb() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
		setDescription();
	}
	
	@Override
	public void onChannel(AbstractOrb arg0) 
	{
		DuelistCard.damageAllEnemiesThornsFire(3);
	}
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return Util.deckIs("Spellcaster Deck");
	}
	
	@Override
	public void onEquip()
	{
		setDescription();
	}

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

	@Override
	public AbstractRelic makeCopy() {
		return new SpellcasterOrb();
	}
}
