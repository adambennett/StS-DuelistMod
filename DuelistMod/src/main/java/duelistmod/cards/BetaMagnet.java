package duelistmod.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.patches.*;
import duelistmod.powers.*;

public class BetaMagnet extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("BetaMagnet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.BETA_MAGNET);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    private static final int BLOCK = 6;
    // /STAT DECLARATION/

    public BetaMagnet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.MAGNETWARRIOR);
        this.tags.add(Tags.LIMITED);
        this.tags.add(Tags.MAGNET_DECK);
		this.superheavyDeckCopies = 1;
		this.setupStartingCopies();
        this.originalName = this.name;
        this.summons = this.baseSummons = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	
    	// Gain Beta Magnet
    	if (!p.hasPower(BetaMagPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new BetaMagPower(p, p))); }
    	
    	// Gain block
    	AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BetaMagnet();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.exhaust = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var)
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new BetaMagPower(p, p)));
    	AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new BetaMagPower(p, p)));
    	AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
		
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