package duelistmod.cards.pools.zombies;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class PatricianDarkness extends DuelistCard
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("PatricianDarkness");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.PATRICIAN_DARKNESS);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public PatricianDarkness() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 11;
    	this.tags.add(Tags.MONSTER);
    	this.tags.add(Tags.ZOMBIE);
    	this.tags.add(Tags.VAMPIRE);
    	this.tags.add(Tags.ZOMBIE_DECK);
		this.tags.add(Tags.ASCENDED_TWO_DECK);
        this.a2DeckCopies = 1;
        this.zombieDeckCopies = 1;
    	this.misc = 0;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
    	tribute();
    	attack(m);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
    	return new PatricianDarkness();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		zombieSynTrib(tributingCard);
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
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
