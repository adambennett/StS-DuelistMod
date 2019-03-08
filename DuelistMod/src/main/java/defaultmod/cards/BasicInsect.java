package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.VioletCrystalPower;

public class BasicInsect extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("BasicInsect");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.BASIC_INSECT);
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

    public BasicInsect() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 4;
        this.summons = 1;
        this.baseMagicNumber = this.magicNumber = 4;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.ALL);
        this.tags.add(DefaultMod.LEGEND_BLUE_EYES);
        this.tags.add(DefaultMod.GOOD_TRIB);
        this.tags.add(DefaultMod.INSECT);
        this.tags.add(DefaultMod.NATURE_DECK);
        this.startingDeckCopies = 2;
        this.originalName = this.name;
        this.isSummon = true;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	block(this.block);
    	applyPower(new PoisonPower(m, p, this.magicNumber), m);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BasicInsect();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		// Check for insect
		if (player().hasPower(VioletCrystalPower.POWER_ID) && tributingCard.hasTag(DefaultMod.INSECT)) { poisonAllEnemies(player(), 5); }
		else if (tributingCard.hasTag(DefaultMod.INSECT)) { poisonAllEnemies(player(), 3); }
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
}