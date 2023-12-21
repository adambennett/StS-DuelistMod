package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.variables.Tags;

public class MayakashiReturnPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("MayakashiReturnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("MayakashiReturnPower.png");
	
	public MayakashiReturnPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public MayakashiReturnPower(AbstractCreature owner, AbstractCreature source, int stacks) 
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
	public void onPlayCard(final AbstractCard card, final AbstractMonster m) 
	{
		if (card.hasTag(Tags.ZOMBIE))
		{
			if (card instanceof DuelistCard)
			{
				DuelistCard dc = (DuelistCard)card;
				ArrayList<CardTags> list = dc.getAllMonsterTypes();
				ArrayList<CardTags> listB = new ArrayList<>();
				for (CardTags t : list) {
					if (!t.equals(Tags.ZOMBIE)) { listB.add(t); }
				}
				
				if (listB.size() > 0)
				{
					CardTags tag = listB.get(AbstractDungeon.cardRandomRng.random(listB.size() - 1));
					ArrayList<AbstractCard> listC = DuelistCard.findAllOfType(tag, 1);
					for (AbstractCard c : listC)
					{
						this.addToBot(new RandomizedHandAction(c, true, 0, 3));
					}
				}
			}
		}
    }

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
	}
}
