package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.dragons.ArtifactSanctum;


@SuppressWarnings("unused")
public class CyberneticOverflowDebuffB extends NoStackDuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("CyberneticOverflowDebuffB");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	private boolean finished = false;
	
	public CyberneticOverflowDebuffB() 
	{
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		 this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void atStartOfTurn()
	{
		
		if (AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID)) 
		{ 
			int amt = AbstractDungeon.player.getPower(ArtifactSanctumPower.POWER_ID).amount;
			if (AbstractDungeon.player.hasPower(ArtifactSanctumPower.POWER_ID)) { ((ArtifactSanctumPower)AbstractDungeon.player.getPower(ArtifactSanctumPower.POWER_ID)).cyberneticTrigger(amt); }
			DuelistCard.removePower(AbstractDungeon.player.getPower(ArtifactPower.POWER_ID), AbstractDungeon.player); 
		}
		DuelistCard.removePower(this, this.owner);
	}
}
