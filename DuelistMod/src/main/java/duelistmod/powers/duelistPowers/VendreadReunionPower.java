package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.pools.zombies.VendreadCore;

public class VendreadReunionPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("VendreadReunionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	public AbstractCard monst;
    
	public VendreadReunionPower(int turns, AbstractCard monster) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns, monster);
	}
	
	public VendreadReunionPower(AbstractCreature owner, AbstractCreature source, int stacks, AbstractCard monster) 
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
        this.monst = monster;
		updateDescription();
	}
	
	public VendreadReunionPower(AbstractCreature owner, AbstractCreature source, int stacks) 
	{ 
		this(owner, source, stacks, new VendreadCore());
	}
	
	@Override
	public void onPlayCard(final AbstractCard card, final AbstractMonster m) 
	{
		if (card.uuid.equals(this.monst.uuid) && this.amount > 0)
		{
			ArrayList<AbstractCard> list = DuelistCard.findAllOfCardTypeForResummon(CardType.ATTACK, this.amount);
			for (AbstractCard c : list)
			{
				AbstractMonster targ = AbstractDungeon.getRandomMonster();
				if (targ != null)
				{
					DuelistCard.resummon(c, targ);
				}
			}
		}
    }


	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.monst.originalName + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]; }
		else { this.description = DESCRIPTIONS[0] + this.monst.originalName + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3]; }
	}
}
