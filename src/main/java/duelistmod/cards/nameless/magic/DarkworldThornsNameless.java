package duelistmod.cards.nameless.magic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class DarkworldThornsNameless extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("Nameless:Magic:DarkworldThorns");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkworldThorns.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public DarkworldThornsNameless() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.PLANT);       
        this.tags.add(Tags.PLANT_DECK);
        this.plantDeckCopies = 1;
        this.summons = this.baseSummons = 2;			
        this.baseBlock = this.block = 4;
        this.magicNumber = this.baseMagicNumber = 1 + DuelistMod.namelessTombMagicMod;
        this.originalName = this.name;
        this.exhaust = true;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	block();    
    	applyPowerToSelf(new ThornsPower(p, this.magicNumber));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkworldThornsNameless();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
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
