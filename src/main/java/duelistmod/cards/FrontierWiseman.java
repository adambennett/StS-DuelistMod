package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.*;
import duelistmod.helpers.Util;
import duelistmod.orbs.*;
import duelistmod.patches.*;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class FrontierWiseman extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("FrontierWiseman");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("FrontierWiseman.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public FrontierWiseman() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.IS_OVERFLOW);
		this.misc = 0;
		this.originalName = this.name;
		this.summons = this.baseSummons = 2;
		this.magicNumber = this.baseMagicNumber = 2;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
		this.isSummon = true;
	}
	
    @Override
    public void triggerOverflowEffect()
    {
    	super.triggerOverflowEffect();
    	 applyPowerToSelf(new EnergizedBluePower(AbstractDungeon.player, 1));
    }
	
	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, this.summons, this);
		AbstractOrb earth = new Earth();
		channel(earth);
	}

	

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new FrontierWiseman();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		spellcasterSynTrib(tributingCard);
	}



	@Override
	public void onResummon(int summons)
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		
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
