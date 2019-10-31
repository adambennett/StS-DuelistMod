package duelistmod.powers.incomplete;

import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.*;
import duelistmod.helpers.HauntedHelper;
import duelistmod.variables.Tags;


@SuppressWarnings("unused")
public class HauntedDebuff extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("HauntedDebuff");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("HauntedPower.png");
	private boolean finished = false;
	public CardTags hauntedCardType = Tags.DRAGON;
	public CardType hauntedCardBaseType = CardType.CURSE;
	private int randRoll;
	public boolean isCardTag = false;
	public boolean isCardType = false;
	private String lastAction = "None.";
	
	public HauntedDebuff(final AbstractCreature owner, final AbstractCreature source, int amount, CardTags hauntedCardType) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		this.hauntedCardType = hauntedCardType;
		this.isCardTag = true;
		updateDescription();
	}
	
	public HauntedDebuff(final AbstractCreature owner, final AbstractCreature source, int amount, CardType hauntedCardType) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		this.hauntedCardBaseType = hauntedCardType;
		this.isCardType = true;
		updateDescription();
	}
	
	public HauntedDebuff(final AbstractCreature owner, final AbstractCreature source, int amount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		//this.randRoll = ThreadLocalRandom.current().nextInt(1, 7);
		this.randRoll = AbstractDungeon.cardRandomRng.random(1, 6);
		if (this.randRoll == 1) { this.hauntedCardType = Tags.MONSTER; this.isCardTag = true;}
		else if (this.randRoll == 2) { this.hauntedCardType = Tags.SPELL; this.isCardTag = true;}
		else if (this.randRoll == 3) { this.hauntedCardType = Tags.TRAP; this.isCardTag = true;}
		else if (this.randRoll == 4) { this.hauntedCardBaseType = CardType.ATTACK; this.isCardType = true;}
		else if (this.randRoll == 5) { this.hauntedCardBaseType = CardType.SKILL; this.isCardType = true;}
		else if (this.randRoll == 6) { this.hauntedCardBaseType = CardType.POWER; this.isCardType = true;}
		updateDescription();
	}
	
	// Call this with the Haunted card that triggers the negative effect(s)
	// Prevents that card from having the summons/tributes modified after you've already played it
	public void triggerHaunt(AbstractCard triggerCard)
	{
		if (this.amount > 0)
		{
			AbstractPlayer p = AbstractDungeon.player;
			AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new GhostIgniteEffect(p.hb.cX, p.hb.cY), 1.0F));
			lastAction = HauntedHelper.triggerRandomAction(this.amount, triggerCard, true);
			int buffRoll = AbstractDungeon.cardRandomRng.random(1, 15);
			if (buffRoll == 1 && DuelistMod.debug) { this.amount++;  DuelistMod.logger.info("Haunted Debuff stacked additional stacks by playing " + triggerCard.name + ", since we rolled a " + buffRoll); }
			else if (buffRoll == 1) { this.amount++;  }
			else if (DuelistMod.debug) { DuelistMod.logger.info("Haunted Debuff did not stack additional stacks by playing " + triggerCard.name + ", since we rolled a " + buffRoll + " and we needed a 1."); }
			updateDescription();
		}
		else
		{
			DuelistCard.removePower(this, this.owner);
		}
		
	}
	
	@Override
	public void updateDescription() 
	{
		// Card Type selected is either: Attack, Skill, or Power
		if (!this.hauntedCardBaseType.equals(CardType.CURSE)) 
		{ 
			boolean useAn = false;
			if (this.hauntedCardBaseType == CardType.ATTACK) { useAn = true; }
			
			if (useAn)
			{
				String cardTypeString = this.hauntedCardBaseType.toString().toLowerCase();
				cardTypeString = cardTypeString.substring(0, 1).toUpperCase() + cardTypeString.substring(1);
				if (this.amount > 1) { this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[2] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[5] + lastAction; }
				else { this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[2] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4] + lastAction; }
			}
			else
			{
				String cardTypeString = this.hauntedCardBaseType.toString().toLowerCase();
				cardTypeString = cardTypeString.substring(0, 1).toUpperCase() + cardTypeString.substring(1);
				if (this.amount > 1) { this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[1] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[5] + lastAction; }
				else { this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[1] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4] + lastAction; }
			}
		}
		
		// Card Type selected is either Monster, Spell or Trap
		else if (!this.hauntedCardType.equals(Tags.DRAGON))
		{ 		
			String cardTypeString = this.hauntedCardType.toString().toLowerCase();
			cardTypeString = cardTypeString.substring(0, 1).toUpperCase() + cardTypeString.substring(1);
			if (this.amount > 1) { this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[1] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[5] + lastAction; }
			else { this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[1] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4] + lastAction; }
		}	
	}
	
	@Override
    public void onInitialApplication() 
    {
		AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.BLACK, p.hb.cX, p.hb.cY), 0.33f));
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.PURPLE, p.hb.cX, p.hb.cY), 0.33f));
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.CYAN, p.hb.cX, p.hb.cY), 0.0f));
        this.addToBot(new VFXAction(p, new BorderLongFlashEffect(Color.MAGENTA), 0.0f, true));
		if (AbstractDungeon.player.hasPower(HauntedPower.POWER_ID))
		{
			DuelistCard.removePower(AbstractDungeon.player.getPower(HauntedPower.POWER_ID), AbstractDungeon.player);
		}
		
		if (!(this.hauntedCardBaseType.equals(CardType.CURSE)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedReductionAction(1, this.hauntedCardBaseType));
		}
		else if (!(this.hauntedCardType.equals(Tags.DRAGON)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedReductionAction(1, this.hauntedCardType));
		}    	
    }
    
    @Override
    public void onRemove() 
    {
    	if (!(this.hauntedCardBaseType.equals(CardType.CURSE)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedRemovedAction(1, this.hauntedCardBaseType));
		}
		else if (!(this.hauntedCardType.equals(Tags.DRAGON)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedRemovedAction(1, this.hauntedCardType));
		}    
    	
		else if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Haunted debuff was removed, but both card types were equal to their init values (Curse/Dragon)");
		}
    }
    
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) 
    {
    	if (!(this.hauntedCardBaseType.equals(CardType.CURSE)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedReductionAction(1, this.hauntedCardBaseType));
		}
		else if (!(this.hauntedCardType.equals(Tags.DRAGON)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedReductionAction(1, this.hauntedCardType));
		}    
    }
    
    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) 
    {
    	if (!(this.hauntedCardBaseType.equals(CardType.CURSE)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedReductionAction(1, this.hauntedCardBaseType));
		}
		else if (!(this.hauntedCardType.equals(Tags.DRAGON)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedReductionAction(1, this.hauntedCardType));
		}    
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (!(this.hauntedCardBaseType.equals(CardType.CURSE)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedReductionAction(1, this.hauntedCardBaseType));
		}
		else if (!(this.hauntedCardType.equals(Tags.DRAGON)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedReductionAction(1, this.hauntedCardType));
		}    
    }
  
    @Override
    public void atStartOfTurnPostDraw() 
    {
    	flash();
    	if (!(this.hauntedCardBaseType.equals(CardType.CURSE)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedReductionAction(1, this.hauntedCardBaseType));
		}
		else if (!(this.hauntedCardType.equals(Tags.DRAGON)))
		{
			AbstractDungeon.actionManager.addToTop(new HauntedReductionAction(1, this.hauntedCardType));
		}    
    }
    
}
