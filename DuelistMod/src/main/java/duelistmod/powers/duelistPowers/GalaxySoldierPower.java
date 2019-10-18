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
import duelistmod.variables.Tags;

public class GalaxySoldierPower extends NoStackDuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("GalaxySoldierPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
	public GalaxySoldierPower() 
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
		updateDescription(); 
	}
	
	@Override
	public void onPlayCard(AbstractCard c, AbstractMonster m)
	{
		if (c.hasTag(Tags.TRAP))
		{
			ArrayList<AbstractCard> reduce = new ArrayList<AbstractCard>();
			for (AbstractCard card : AbstractDungeon.player.hand.group)
			{
				if (!card.uuid.equals(c.uuid) && (c.cost > 0 || c.costForTurn > 0) && !c.type.equals(CardType.STATUS) && !c.type.equals(CardType.CURSE))
				{
					reduce.add(card);
				}
			}
			
			if (reduce.size() > 0)
			{
				AbstractCard rand = reduce.get(AbstractDungeon.cardRandomRng.random(reduce.size() - 1));
				rand.setCostForTurn(-rand.cost);
			}
		}
	}

	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
}
