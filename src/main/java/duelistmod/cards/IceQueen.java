package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class IceQueen extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("IceQueen");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.ICE_QUEEN);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public IceQueen() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tributes = this.baseTributes = 3;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.EXODIA_DECK);
		this.exodiaDeckCopies = 1;
		this.setupStartingCopies();
        this.misc = 0;
        this.baseMagicNumber = this.magicNumber = 2;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 3;
		this.showInvertValue = true;
		this.showInvertOrbs = 1;
		this.originalName = this.name;
    }
    
    @Override
    public void update()
    {
		super.update();
    	this.showEvokeOrbCount = this.magicNumber + 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	boolean hadNoOrbs = (p.filledOrbCount() == 0);
    	tribute(p, this.tributes, false, this);
    	invertIceQueen(2, this.magicNumber);
    	if (hadNoOrbs)
    	{
    		channel(new Frost(), this.magicNumber);
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new IceQueen();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
        }
    }
    


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		spellcasterSynTrib(tributingCard);
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

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
   
}
