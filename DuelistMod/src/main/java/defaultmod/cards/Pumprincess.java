package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.actions.unique.PlayRandomFromDiscardAction;
import defaultmod.patches.*;

public class Pumprincess extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("Pumprincess");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.PUMPRINCESS);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public Pumprincess() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.NO_PUMPKIN);
        this.tags.add(DefaultMod.REDUCED);
        this.tags.add(DefaultMod.RESUMMON_DECK);
        this.startingResummonDeckCopies = 1;
        this.summons = 1;
		this.originalName = this.name;
		this.isSummon = true;
		this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	AbstractDungeon.actionManager.addToTop(new PlayRandomFromDiscardAction(1, this.upgraded, m));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Pumprincess();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBaseCost(0);
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
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		summon(p, this.summons, this);
    	AbstractDungeon.actionManager.addToTop(new PlayRandomFromDiscardAction(1, this.upgraded, m));
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, this.summons, this);
    	AbstractDungeon.actionManager.addToTop(new PlayRandomFromDiscardAction(1, this.upgraded, m));
	}

	@Override
	public String getID() {
		return ID;
	}
}