package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tokens.Token;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;


@SuppressWarnings("unused")
public class ReleaseFromStonePower extends DuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("ReleaseFromStonePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("ReleaseFromStonePower.png");
	private boolean finished = false;
	
	public ReleaseFromStonePower(int amt) 
	{
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
        this.amount = amt;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
		 
	}
	
	@Override
	public void atStartOfTurn()
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower pow = (SummonPower) p.getPower(SummonPower.POWER_ID);
			int rocks = pow.getNumberOfTypeSummoned(Tags.ROCK);
			if (rocks == 0 && this.amount > 0) { DuelistCard.addCardToHand(DuelistCard.returnTrulyRandomFromSet(Tags.ROCK), this.amount); }
		}
	}
}
