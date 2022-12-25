package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.orbs.Alien;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class AlienTelepath extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("AlienTelepath");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AlienTelepath.png");
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

    public AlienTelepath() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);  
        this.summons = this.baseSummons = 1;	
        this.baseMagicNumber = this.magicNumber = 3;
        this.secondMagic = this.baseSecondMagic = 1;
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	if (p.drawPile.group.size() >= this.magicNumber)
    	{
	    	ArrayList<AbstractCard> topCards = new ArrayList<AbstractCard>();
	    	for (int i = 0; i < this.magicNumber; i++)
	    	{
	    		AbstractCard c = p.drawPile.getNCardFromTop(i);
	    		topCards.add(c);
	    	}
	    	CardGroup newGroup = new CardGroup(CardGroupType.UNSPECIFIED);
	    	for (AbstractCard c : topCards) { newGroup.addToTop(c); }
	    	if (newGroup.group.size() >= this.secondMagic) { AbstractDungeon.actionManager.addToTop(new FetchAction(newGroup, this.secondMagic)); }
    	}
    	else if (p.drawPile.group.size() > 0)
    	{
    		ArrayList<AbstractCard> topCards = new ArrayList<AbstractCard>();
	    	for (int i = 0; i < p.drawPile.group.size(); i++)
	    	{
	    		AbstractCard c = p.drawPile.getNCardFromTop(i);
	    		topCards.add(c);
	    	}
	    	CardGroup newGroup = new CardGroup(CardGroupType.UNSPECIFIED);
	    	for (AbstractCard c : topCards) { newGroup.addToTop(c); }
	    	if (newGroup.group.size() >= this.secondMagic) { AbstractDungeon.actionManager.addToTop(new FetchAction(newGroup, this.secondMagic)); }
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new AlienTelepath();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
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
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		
		
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
