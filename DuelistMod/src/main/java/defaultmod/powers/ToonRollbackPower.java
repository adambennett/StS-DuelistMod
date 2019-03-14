package defaultmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;


public class ToonRollbackPower extends AbstractPower 
{
	public AbstractCreature source;
	public static final String POWER_ID = defaultmod.DefaultMod.makeID("ToonRollbackPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DefaultMod.makePath(DefaultMod.TOON_ROLLBACK_POWER);
	public ArrayList<AbstractCard> pieces = new ArrayList<AbstractCard>();
	
	public ToonRollbackPower(final AbstractCreature owner, final AbstractCreature source) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 0;
		this.updateDescription();
	}
	
	@Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
		if(DuelistCard.isToon(c)) { DuelistCard.addCardToHand(c.makeCopy()); }
		//if(DuelistCard.isToon(c)) { DuelistCard.addCardToHand(c); }
		updateDescription();
    }
	

	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		if (AbstractDungeon.player.hasPower(ToonRollbackPower.POWER_ID)) { DuelistCard.removePower(this, AbstractDungeon.player); }
		updateDescription();
	}

	@Override
	public void updateDescription() 
	{
		this.description = DESCRIPTIONS[0];
	}
}
