package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class Tierra extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("Tierra");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("Tierra.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public Tierra() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tributes = this.baseTributes = 2;
		this.magicNumber = this.baseMagicNumber = 2;
		this.damage = this.baseDamage = 32;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.FIEND);
        this.tags.add(Tags.BAD_MAGIC);
		this.misc = 0;
		this.originalName = this.name;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		attack(m);
		tribute();		
		ArrayList<DuelistCard> handTribs = new ArrayList<DuelistCard>();
		if (this.magicNumber > 0)
		{
			for (AbstractCard c : p.hand.group)
			{
				if (c instanceof DuelistCard && c.hasTag(Tags.MONSTER) && !c.uuid.equals(this.uuid))
				{
					DuelistCard dC = (DuelistCard)c;
					if (dC.isTributeCard())
					{
						handTribs.add(dC);
					}
				}
			}
			
			for (AbstractCard c : p.drawPile.group)
			{
				if (c instanceof DuelistCard && c.hasTag(Tags.MONSTER) && !c.uuid.equals(this.uuid))
				{
					DuelistCard dC = (DuelistCard)c;
					if (dC.isTributeCard())
					{
						handTribs.add(dC);
					}
				}
			}
			
			for (AbstractCard c : p.discardPile.group)
			{
				if (c instanceof DuelistCard && c.hasTag(Tags.MONSTER) && !c.uuid.equals(this.uuid))
				{
					DuelistCard dC = (DuelistCard)c;
					if (dC.isTributeCard())
					{
						handTribs.add(dC);
					}
				}
			}
			
			for (AbstractCard c : p.exhaustPile.group)
			{
				if (c instanceof DuelistCard && c.hasTag(Tags.MONSTER) && !c.uuid.equals(this.uuid))
				{
					DuelistCard dC = (DuelistCard)c;
					if (dC.isTributeCard())
					{
						handTribs.add(dC);
					}
				}
			}
			
			if (handTribs.size() > 0)
			{
				for (DuelistCard pick : handTribs)
				{
					AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(pick, this.magicNumber, true));
				}
			}
		}
	}
	
	@Override
    public void atTurnStart() 
    {
    	
    }

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new Tierra();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(-1);
			this.upgradeDamage(6);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
	

	@Override
	public void onTribute(DuelistCard tributingCard)
	{
		fiendSynTrib(tributingCard);
	}

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
