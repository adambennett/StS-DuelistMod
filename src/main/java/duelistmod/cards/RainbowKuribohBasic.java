package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

import java.util.*;

public class RainbowKuribohBasic extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("RainbowKuribohBasic");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RainbowKuriboh.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public RainbowKuribohBasic() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 1;
        this.secondMagic = this.baseSecondMagic = 2;
        this.summons = this.baseSummons = 1;
        this.isSummon = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.MEGATYPE_DECK);
        this.tags.add(Tags.ASCENDED_THREE_DECK);
        this.tags.add(Tags.NEVER_GENERATE);
        this.a3DeckCopies = 1;
        this.megatypeDeckCopies = 1;
        this.setupStartingCopies();
        this.makeMegatyped();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    	summon();
    	incMaxSummons(p, this.magicNumber);
		ArrayList<CardTags> allButMega = new ArrayList<>();
		for (CardTags tag : DuelistMod.monsterTypes) {
			if (!tag.equals(Tags.MEGATYPED)) {
				allButMega.add(tag);
			}
		}
    	CardTags randomTypeSelection = allButMega.get(AbstractDungeon.cardRandomRng.random(allButMega.size() - 1));
    	for (int i = 0; i < this.secondMagic; i++)
    	{
    		DuelistCard randTypeMon = (DuelistCard) DuelistCard.returnTrulyRandomFromSets(randomTypeSelection, Tags.MONSTER);
    		AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randTypeMon, true));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new RainbowKuribohBasic();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
  

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		megatypeTrib(tributingCard);
	}
	



	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
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
