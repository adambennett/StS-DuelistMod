package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tokens.SpiritToken;
import duelistmod.helpers.DebuffHelper;
import duelistmod.variables.Tags;

public class GhostrickMansionPower extends NoStackDuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("GhostrickMansionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
    private int effectIndex = 0;
    
	public GhostrickMansionPower() 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player);
	}
	
	public GhostrickMansionPower(AbstractCreature owner, AbstractCreature source) 
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
        if (AbstractDungeon.getCurrMapNode() != null) {
        	 this.effectIndex = AbstractDungeon.cardRandomRng.random(0, 14);
        } else { this.effectIndex = 0; }       
		updateDescription();
	}
	
	@Override
	public void atEndOfRound()
	{
		int oldInd = this.effectIndex;
		while (this.effectIndex == oldInd) { this.effectIndex = AbstractDungeon.cardRandomRng.random(0, 14); }
		if (this.effectIndex == 14) { while (this.effectIndex == oldInd) { this.effectIndex = AbstractDungeon.cardRandomRng.random(0, 14); }}
		this.flash();
		updateDescription();
	}
	
	@Override
	public void onResummon(DuelistCard res)
	{
		if (this.effectIndex == 4)
		{
			DuelistCard.staticBlock(5);
			this.flash();
		}
		
		else if (this.effectIndex == 5)
		{
			DuelistCard.staticBlock(10);
			this.flash();
		}
		
		else if (this.effectIndex == 6)
		{
			DuelistCard.gainTempHP(5);
			this.flash();
		}
		
		else if (this.effectIndex == 7)
		{
			AbstractMonster rand = AbstractDungeon.getRandomMonster();
			if (rand != null)
			{
				int turnRoll = AbstractDungeon.cardRandomRng.random(2, 4);
				AbstractPower debuff = DebuffHelper.getRandomDebuff(AbstractDungeon.player, rand, turnRoll);
				DuelistCard.applyPower(debuff, rand);
				this.flash();
			}
		}
		
		else if (this.effectIndex == 8)
		{
			for (AbstractOrb o : AbstractDungeon.player.orbs)
			{
				if (!(o instanceof EmptyOrbSlot))
				{
					o.onEvoke();
					this.flash();
				}
			}
		}
		
		else if (this.effectIndex == 9)
		{
			DuelistCard.channelRandom();
			this.flash();
		}
		
		else if (this.effectIndex == 10)
		{
			AbstractPlayer p = AbstractDungeon.player;
			DuelistCard.summon(p, DuelistCard.getMaxSummons(p), new SpiritToken());
			this.flash();
		}
		
		else if (this.effectIndex == 11)
		{
			AbstractMonster rand = AbstractDungeon.getRandomMonster();
			if (rand != null)
			{
				DuelistCard.siphon(rand, 6);
				this.flash();
			}
		}
		
		else if (this.effectIndex == 13)
		{
			AbstractDungeon.player.heal(3);
			this.flash();
		}
		
		else if (this.effectIndex == 14)
		{
			if (AbstractDungeon.cardRandomRng.random(1, 100) > 95)
			{
				AbstractRelic relic = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON);
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, relic);
				this.flash();
			}
		}
	}
	
	@Override
	public int modifyResummonAmt(AbstractCard resummoningCard) 
	{
		if (this.effectIndex == 0)
		{
			if (resummoningCard.type.equals(CardType.ATTACK))
			{
				return 1;
			}
		}
		else if (this.effectIndex == 1)
		{
			if (resummoningCard.type.equals(CardType.SKILL))
			{
				return 1;
			}
		}
		else if (this.effectIndex == 2)
		{
			if (resummoningCard.type.equals(CardType.POWER))
			{
				return 1;
			}
		}
		else if (this.effectIndex == 1)
		{
			if (resummoningCard.hasTag(Tags.ZOMBIE))
			{
				return 1;
			}
		}
		else if (this.effectIndex == 12)
		{
			if (resummoningCard.type.equals(CardType.ATTACK) || resummoningCard.type.equals(CardType.SKILL))
			{
				return 1;
			}
		}
		return 0; 
	}

	@Override
	public void updateDescription()
	{
		if (this.effectIndex > -1 && this.effectIndex < 15)
		{
			this.description = DESCRIPTIONS[this.effectIndex];
		}
		else
		{
			this.description = DESCRIPTIONS[15];
		}		
	}
}
