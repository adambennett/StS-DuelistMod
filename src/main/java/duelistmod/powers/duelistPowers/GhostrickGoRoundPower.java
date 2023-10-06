package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tempCards.CancelCard;

public class GhostrickGoRoundPower extends NoStackDuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("GhostrickGoRoundPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("GhostrickGoRoundPower.png");
	
	public GhostrickGoRoundPower() 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player);
	}
	
	public GhostrickGoRoundPower(AbstractCreature owner, AbstractCreature source) 
	{ 
		//super(owner, source, stacks);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
		updateDescription();
	}
	
	private boolean isCard()
	{
		if (DuelistMod.lastGhostrickPlayed != null && !(DuelistMod.lastGhostrickPlayed instanceof CancelCard))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private AbstractCard getCard()
	{
		if (isCard())
		{
			return DuelistMod.lastGhostrickPlayed.makeStatEquivalentCopy();
		}
		else
		{
			return new CancelCard();
		}
	}
	
	@Override
	public void atStartOfTurn()
	{
		AbstractCard ghost = getCard();
		if (!(ghost instanceof CancelCard))
		{
			DuelistCard.addCardToHand(ghost);
		}
	}

	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
}
