package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class Hinotoma extends DuelistCard
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("Hinotoma");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.HINOTOMA);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final AttackEffect AFX = AttackEffect.FIRE;
    private static final int COST = 1;
    private static int MIN_TIMES = 2;
    private static int MAX_TIMES = 3;
    private static int MIN_TIMES_U = 2;
    private static int MAX_TIMES_U = 4;
    // /STAT DECLARATION/

    public Hinotoma() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 4;
		this.originalName = this.name;
		this.tags.add(DefaultMod.LEGEND_BLUE_EYES);
		this.tags.add(DefaultMod.SPELL);
		this.tags.add(DefaultMod.GENERATION_DECK);
		this.startingGenDeckCopies = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (this.upgraded)
    	{
    		int randomTimes = AbstractDungeon.cardRandomRng.random(MIN_TIMES_U, MAX_TIMES_U);
    		for (int i = 0; i < randomTimes; i++) { attack(m, AFX, this.damage); }
    	}
    	else
    	{
    		int randomTimes = AbstractDungeon.cardRandomRng.random(MIN_TIMES, MAX_TIMES );
	        for (int i = 0; i < randomTimes; i++) { attack(m, AFX, this.damage); }
    	}	
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Hinotoma();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) 
        {
            this.upgradeName();
            //this.upgradeDamage(2);
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
}