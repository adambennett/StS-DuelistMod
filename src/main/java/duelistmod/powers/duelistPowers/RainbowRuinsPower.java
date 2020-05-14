package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Tags;


@SuppressWarnings("unused")
public class RainbowRuinsPower extends DuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("RainbowRuinsPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("RainbowRuinsPower.png");
	private boolean finished = false;
	
	public RainbowRuinsPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		// Description Layout: Effect, singular, plural
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

	}
	
	@Override
	public void onPlayCard(AbstractCard c, AbstractMonster m)
	{
		if (c.hasTag(Tags.MONSTER) && !c.hasTag(Tags.MEGATYPED) && c instanceof DuelistCard)
		{
			DuelistCard dc = (DuelistCard)c;
			DuelistCard.gainTempHP(this.amount * dc.getAllMonsterTypes().size());
		}
	}
	
    @Override
    public void onEnemyUseCard(AbstractCard card)
    {
    	onPlayCard(card, null);
    }
}
