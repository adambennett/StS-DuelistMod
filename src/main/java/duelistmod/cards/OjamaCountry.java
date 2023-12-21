package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class OjamaCountry extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("OjamaCountry");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("OjamaCountry.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;

    // /STAT DECLARATION/

    public OjamaCountry() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.OJAMA);
		this.exhaust = true;
		this.originalName = this.name;
		this.magicNumber = this.baseMagicNumber = 3;
		this.setupStartingCopies();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		ArrayList<AbstractCard> list = returnTrulyRandomDuelistCard(false, false, this.magicNumber);
		for (AbstractCard c : list)
		{
			boolean isTrib = false;
			boolean isSumm = false;
			if (c instanceof DuelistCard)
			{
				if (((DuelistCard)c).isTributeCard())
				{
					isTrib = true;
				}
				
				if (((DuelistCard)c).isSummonCard())
				{
					isSumm = true;
				}
			}
			this.addToBot(new RandomizedHandAction(c, this.upgraded, true, false, true, isTrib, isSumm, false, false, 0, 2, 0, 1, 0, 1));
		}
	}

	@Override
	public AbstractCard makeCopy() { return new OjamaCountry(); }

	@Override
	public void upgrade() 
	{
		if (!upgraded) 
		{
			this.upgradeName();
			this.upgradeMagicNumber(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}

	
	


	








}
