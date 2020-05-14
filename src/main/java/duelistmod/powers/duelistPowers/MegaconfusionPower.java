package duelistmod.powers.duelistPowers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Tags;

public class MegaconfusionPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("MegaconfusionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean posSumm = false;
    private boolean posTrib = false;
    private boolean posDam = false;
    private boolean posBlk = false;
    private boolean posMag = false;
    private boolean posSecondMag = false;
    private boolean posThirdMag = false;
    private boolean posCost = false;
    private float dmgMod = 1.0f;
    private float blkMod = 1.0f;
	
	public MegaconfusionPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public MegaconfusionPower(AbstractCreature owner, AbstractCreature source, int stacks) 
	{ 
		//super(owner, source, stacks);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = true;
        this.loadRegion("confusion");
        this.source = source;
        this.amount = stacks;
        statRescrambler();
		updateDescription();
	}
	
	public void statRescrambler()
	{
		// Reset all flags and modifiers
		this.posBlk = AbstractDungeon.cardRandomRng.randomBoolean();
		this.posDam = AbstractDungeon.cardRandomRng.randomBoolean();
		this.posSumm = AbstractDungeon.cardRandomRng.randomBoolean();
		this.posTrib = AbstractDungeon.cardRandomRng.randomBoolean();
		this.posMag = AbstractDungeon.cardRandomRng.randomBoolean();
		this.posSecondMag = AbstractDungeon.cardRandomRng.randomBoolean();
		this.posThirdMag = AbstractDungeon.cardRandomRng.randomBoolean();
		this.posCost = AbstractDungeon.cardRandomRng.randomBoolean();
		this.blkMod = AbstractDungeon.cardRandomRng.random(1.0f, 5.0f);
		this.dmgMod = AbstractDungeon.cardRandomRng.random(1.0f, 5.0f);
		
		// This looks like a whole lotta shit
		// But, just making sure there's not too many negative effects happening simultaneously
		if (!this.posCost && !this.posTrib) 
		{
			int top = 2;
			if (this.posBlk && this.posDam) { top++; }
			if (this.posMag && this.posSecondMag && this.posThirdMag) { top++; }
			int roll = AbstractDungeon.cardRandomRng.random(1, top);
			if (roll == 1) { this.posCost = true; }
			else if (roll == 2) { this.posTrib = true; }
			else if (roll == 3)
			{
				int secondRoll = AbstractDungeon.cardRandomRng.random(1, 2);
				if (secondRoll == 1)
				{
					roll = AbstractDungeon.cardRandomRng.random(1, 2);
					if (roll == 1) { this.posCost = true; }
					else { this.posTrib = true; }
				}
			}
		}
		AbstractDungeon.player.hand.glowCheck();
		this.flash();
		updateDescription();
	}
	
	@Override
	public void atEndOfRound()
	{
		statRescrambler();
	}
	
	@Override
    public void onAfterUseCard(final AbstractCard card, final UseCardAction action) 
	{
		statRescrambler();
	}
	
	@Override
    public void onCardDraw(final AbstractCard card) 
	{
		if (this.posCost)
		{
			if (card.cost >= 0)
			{
				int costRoll = AbstractDungeon.cardRandomRng.random(0, 1);
				if (card.cost != costRoll) 
				{
					card.cost = costRoll;
					card.costForTurn = card.cost;
					card.isCostModified = true;
				}
			}
		}
		else
		{
			if (card.cost >= 0)
			{
				int upp = card.cost * 2;
				if (upp < 1) { upp = 1; }
				int costRoll = AbstractDungeon.cardRandomRng.random(1, upp);
				if (card.cost != costRoll) 
				{
					card.cost = costRoll;
					card.costForTurn = card.cost;
					card.isCostModified = true;
				}
			}
		}
    }
	
	@Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
		if (this.posDam)
		{
			return damage + (this.amount * this.dmgMod);
		}
		else
		{
			return damage - (this.amount * this.dmgMod);
		}
	}
	
	@Override
	public int modifyTributes(int tmp, AbstractCard card)
	{
		if (this.posTrib && card instanceof DuelistCard)
		{
			return tmp - this.amount;
		}
		else if (card instanceof DuelistCard)
		{
			return tmp + this.amount;
		}
		return tmp;
	}
	
	@Override
	public int modifySummons(int tmp, AbstractCard card)
	{
		if (this.posSumm && card instanceof DuelistCard)
		{
			return tmp + this.amount;
		}
		else if (card instanceof DuelistCard)
		{
			return tmp - this.amount;
		}
		return tmp;
	}
	
	@Override
	public float modifyMagicNumber(float tmp, AbstractCard card)
	{
		boolean allowCard = false;
		if (DuelistMod.magicNumberCards.containsKey(card.cardID)) { allowCard = true; }
		if (this.posMag && !card.hasTag(Tags.ALLOYED) && (card.magicNumber > 0 || allowCard))
		{
			return tmp + this.amount;
		}
		else if (!card.hasTag(Tags.ALLOYED) && (card.magicNumber > 0 || allowCard))
		{
			return tmp - this.amount;
		}
		else
		{
			return tmp;
		}
	}
	
	@Override
	public float modifySecondMagicNumber(float tmp, AbstractCard card)
	{
		if (this.posSecondMag && !card.hasTag(Tags.ALLOYED))
		{
			return tmp + this.amount;
		}
		else if (card.hasTag(Tags.ALLOYED))
		{
			return tmp - this.amount;
		}
		else 
		{
			return tmp;
		}
	}
	
	@Override
	public float modifyThirdMagicNumber(float tmp, AbstractCard card)
	{
		if (this.posThirdMag && !card.hasTag(Tags.ALLOYED))
		{
			return tmp + this.amount;
		}
		else if (card.hasTag(Tags.ALLOYED))
		{
			return tmp - this.amount;
		}
		else 
		{
			return tmp;
		}
	}
	
	@Override
	public float modifyBlock(float tmp, AbstractCard card)
	{
		int extra = (int) (this.amount * this.blkMod);
		if (this.posBlk)
		{
			return tmp + extra;
		}
		else
		{
			return tmp - extra;
		}
	}

	@Override
	public void updateDescription()
	{
		if (this.amount > -1) {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
			this.type = PowerType.BUFF;
		}
		else {
			final int tmp = -this.amount;
			this.description = DESCRIPTIONS[1] + tmp + DESCRIPTIONS[2];
			this.type = PowerType.DEBUFF;
		}
	}
}
