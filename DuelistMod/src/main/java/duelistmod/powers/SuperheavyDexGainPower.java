package duelistmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;


public class SuperheavyDexGainPower extends AbstractPower 
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("SuperheavyDexGainPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.SUPERHEAVY_DEX_GAIN_POWER);
	public ArrayList<AbstractCard> pieces = new ArrayList<AbstractCard>();
	
	public SuperheavyDexGainPower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = newAmount;
		this.updateDescription();
	}
	
	@Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
		if(c.type.equals(CardType.POWER)) { DuelistCard.applyPower(new DexterityPower(this.owner, this.amount), this.owner); }
		updateDescription();
    }
	

	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		if (this.amount > 0){ DuelistCard.reducePower(this, this.owner, 1); }
		else { DuelistCard.removePower(this, this.owner); }
		updateDescription();
	}

	@Override
	public void updateDescription() 
	{
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
