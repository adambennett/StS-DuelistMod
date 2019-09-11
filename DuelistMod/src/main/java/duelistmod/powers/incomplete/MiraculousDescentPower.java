package duelistmod.powers.incomplete;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.incomplete.MiraculousDescent;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class MiraculousDescentPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("MiraculousDescentPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("MiraculousDescentPower.png");
	
	public CardType cardType = CardType.STATUS;
	public CardTags cardTag = Tags.ALL;
	public boolean hasArcaneCards = false;
	public boolean hasExemptCards = false;
	public int typeID = -1;
	
	public MiraculousDescentPower(final AbstractCreature owner, final AbstractCreature source, int startAmount, CardType startingType, CardTags startingTag) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = startAmount;
		this.cardTag = startingTag;
		this.cardType = startingType;
		resetArcaneExempt();
		updateDescription();
	}
	
	public MiraculousDescentPower(final AbstractCreature owner, final AbstractCreature source, int startAmount) 
	{
		this(owner, source, startAmount, CardType.STATUS, Tags.ALL);
	}
	
	public MiraculousDescentPower(final AbstractCreature owner, final AbstractCreature source, int startAmount, CardType startingType) 
	{
		this(owner, source, startAmount, startingType, Tags.ALL);
	}
	
	public MiraculousDescentPower(final AbstractCreature owner, final AbstractCreature source, int startAmount, CardTags startingType) 
	{
		this(owner, source, startAmount, CardType.STATUS, startingType);
	}
	
	@Override
	public void updateDescription() 
	{
		if (this.cardTag.equals(Tags.ALL) && this.cardType.equals(CardType.STATUS)) { this.typeID = generateNewType(); }
		boolean useAn = useAnForDesc(this.cardType);
		boolean arcaneDesc = arcaneDesc(this.cardTag);
		if (useAn)
		{
			if (this.cardTag.equals(Tags.ALL))
			{
				this.description = DESCRIPTIONS[4] + cardTypeToString(this.cardType) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
			}
			else if (this.cardType.equals(CardType.STATUS))
			{
				this.description = DESCRIPTIONS[4] + cardTypeToString(this.cardTag) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
			}
			else
			{
				this.description = DESCRIPTIONS[3];
			}
		}
		else if (arcaneDesc)
		{
			if (this.cardTag.equals(Tags.ALL))
			{
				this.description = DESCRIPTIONS[5] + cardTypeToString(this.cardType) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
			}
			else if (this.cardType.equals(CardType.STATUS))
			{
				this.description = DESCRIPTIONS[5] + cardTypeToString(this.cardTag) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
			}
			else
			{
				this.description = DESCRIPTIONS[3];
			}
		}
		else
		{
			if (this.cardTag.equals(Tags.ALL))
			{
				this.description = DESCRIPTIONS[0] + cardTypeToString(this.cardType) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
			}
			else if (this.cardType.equals(CardType.STATUS))
			{
				this.description = DESCRIPTIONS[0] + cardTypeToString(this.cardTag) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
			}
			else
			{
				this.description = DESCRIPTIONS[3];
			}
		}
	}
	
	private boolean useAnForDesc(CardType type)
	{
		if (type.equals(CardType.ATTACK)) { return true; }
		else { return false; }
	}
	
	private boolean arcaneDesc(CardTags tag)
	{
		if (tag.equals(Tags.ARCANE)) { return true; }
		else if (tag.equals(Tags.EXEMPT)) { return true; }
		else { return false; }
	}
	
	private String cardTypeToString(CardType type)
	{
		if (type.equals(CardType.ATTACK)) { return "Attack"; }
		else if (type.equals(CardType.SKILL)) { return "Skill"; }
		else if (type.equals(CardType.POWER)) { return "Power"; }
		else { return "BadCardType - status/curse"; }
	}
	
	private String cardTypeToString(CardTags type)
	{
		if (type.equals(Tags.MONSTER)) { return "Monster"; }
		else if (type.equals(Tags.SPELL)) { return "Spell"; }
		else if (type.equals(Tags.TRAP)) { return "Trap"; }
		else if (type.equals(Tags.ARCANE)) { return "Arcane"; }
		else if (type.equals(Tags.EXEMPT)) { return "Exempt"; }
		else { return "NonSupportedType - tag other than Monster/Spell/Trap/Arcane/Exempt"; }
	}
	
	private void resetArcaneExempt()
	{
		this.hasArcaneCards = false;
		this.hasExemptCards = false;
		for (AbstractCard c : AbstractDungeon.player.drawPile.group)
		{
			if (c.hasTag(Tags.ARCANE) && !(c instanceof MiraculousDescent)) { this.hasArcaneCards = true; }
			if (c.hasTag(Tags.EXEMPT)) { this.hasExemptCards = true; }
		}
		for (AbstractCard c : AbstractDungeon.player.discardPile.group)
		{
			if (c.hasTag(Tags.ARCANE) && !(c instanceof MiraculousDescent)) { this.hasArcaneCards = true; }
			if (c.hasTag(Tags.EXEMPT)) { this.hasExemptCards = true; }
		}
		for (AbstractCard c : AbstractDungeon.player.hand.group)
		{
			if (c.hasTag(Tags.ARCANE) && !(c instanceof MiraculousDescent)) { this.hasArcaneCards = true; }
			if (c.hasTag(Tags.EXEMPT)) { this.hasExemptCards = true; }
		}
		Util.log("Reset hasArcane and hasExempt! hasExempt=" + hasExemptCards + ", hasArcane=" + hasArcaneCards);
	}
	
	public int generateNewType() { return generateNewType(-1); }
	
	
	// Rechecks for Arcane and Exempt cards in discard, draw and hand
	// Resets selected card type
	// Chooses a new random card type, including Arcane and Exempt if either type of cards exist somewhere
	// Return ID of selected card type
	// Exclude parameter removes that card type from being selected
	public int generateNewType(int exclude)
	{
		this.cardTag = Tags.ALL;
		this.cardType = CardType.STATUS;
		int maxRoll = 6;
		resetArcaneExempt();
		if (this.hasArcaneCards) { maxRoll++; }
		if (this.hasExemptCards) { maxRoll++; }
		int roll = AbstractDungeon.cardRandomRng.random(1, maxRoll);
		while (roll == exclude) { roll = AbstractDungeon.cardRandomRng.random(1, maxRoll); }
		switch (roll)
		{
			case 1:
				this.cardType = CardType.ATTACK;
				break;
			case 2:
				this.cardType = CardType.SKILL;
				break;
			case 3:
				this.cardType = CardType.POWER;
				break;
			case 4:
				this.cardTag = Tags.MONSTER;
				break;
			case 5:
				this.cardTag = Tags.SPELL;
				break;
			case 6:
				this.cardTag = Tags.TRAP;
				break;
			case 7:
				this.cardTag = Tags.ARCANE;
				break;
			case 8:
				this.cardTag = Tags.EXEMPT;
				break;
			default:
				this.cardType = CardType.ATTACK;
				break;
		}
		if (roll < 1 || roll > 8) { return 1; }
		return roll;
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) 
	{
		Util.log("Miraculous Descent reporting in! CardType=" + this.cardTypeToString(this.cardType) + ", CardTag=" + this.cardTypeToString(this.cardTag));
		if (DuelistMod.lastCardPlayed.type.equals(this.cardType) && this.cardType != CardType.STATUS)
		{
			 this.flash(); 
			 if (this.amount > 0) { DuelistCard.staticBlock(this.amount); }
			 this.typeID = generateNewType();
			 Util.log("Last card played was correct!");
		}
		else if (DuelistMod.lastCardPlayed.hasTag(this.cardTag) && this.cardTag != Tags.ALL)
		{
			 this.flash(); 
			 if (this.amount > 0) { DuelistCard.staticBlock(this.amount); }
			 this.typeID = generateNewType();
			 Util.log("Last card played was correct!");
		}
		
		if ((this.cardTag.equals(Tags.ARCANE) || this.cardTag.equals(Tags.EXEMPT))) 
		{ 
			resetArcaneExempt();
			if (!this.hasArcaneCards && this.cardTag.equals(Tags.ARCANE)) { this.flash(); this.typeID = generateNewType(7); }
			if (!this.hasExemptCards && this.cardTag.equals(Tags.EXEMPT)) { this.flash(); this.typeID = generateNewType(8); }
		}
		updateDescription();
	}
}
