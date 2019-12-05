package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class SeafaringPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("SeafaringPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
	public SeafaringPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public SeafaringPower(AbstractCreature owner, AbstractCreature source, int stacks) 
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
        this.amount = stacks;
		updateDescription();
	}
	
	@Override
    public float atDamageGive(float damage, final DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage += this.amount;
        }
        return damage;
    }
	
	@Override
	public float modifyBlock(final float blockAmount, final AbstractCard card) {
		if (card.type.equals(CardType.SKILL)) {
            return blockAmount + this.amount;
        }
		return blockAmount;
    }
	
	@Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.type.equals(CardType.ATTACK) || card.type.equals(CardType.SKILL)) {
            this.flash();
            DuelistCard.removePower(this, this.owner);
        }
    }

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
	}
}
