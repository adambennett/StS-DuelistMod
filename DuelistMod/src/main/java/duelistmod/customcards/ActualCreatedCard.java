package duelistmod.customcards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import duelistmod.*;
import duelistmod.interfaces.*;
import duelistmod.patches.AbstractCardEnum;

public class ActualCreatedCard extends CreatorCard
{
	// TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DamageEffect");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.ACID_TRAP);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public ActualCreatedCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.summons = this.baseSummons = 1;
        this.baseBlock = this.block = 1;
        this.baseDamage = this.damage = 1;
        this.baseTributes = this.tributes = 1;
        this.magicNumber = this.baseMagicNumber = 1;
        this.misc = 0;
        this.isSummon = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// these should only do something if the number is correct
    	// make sure to reset any unused stats to 0 before giving card to player
    	summon();
    	tribute();
    	attack(m);
    	block(this.block);
    	
    	// make an map of powers and indices, add integers to a list with creator incarnate selections and for each int in list trigger associated power
    	// proof of concept temp implementation
    	if (this.applyPoison) 
    	{ 
    		applyPower(new PoisonPower(m, p, this.magicNumber), m);
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ActualCreatedCard();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
    	
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
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
	public String getID()
	{
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) 
	{
		
		
	}
}
