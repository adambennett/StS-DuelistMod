package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.orbs.Summoner;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class DarkMagician extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("DarkMagician");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.DARK_MAGICIAN);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_DIAGONAL;
    private static final int COST = 2;
    private static final int DAMAGE = 14;
    // /STAT DECLARATION/

    public DarkMagician() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.EXODIA_DECK);
        this.tags.add(Tags.SPELLCASTER_DECK);
        this.tags.add(Tags.OP_SPELLCASTER_DECK);
        this.tags.add(Tags.ASCENDED_ONE_DECK);
        this.a1DeckCopies = 1;
        this.startingOPSPDeckCopies = 1;
        this.exodiaDeckCopies = 1;
        this.spellcasterDeckCopies = 1;
        this.magicNumber = this.baseMagicNumber = 1;
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 2;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(p, this.tributes, false, this);
    	attack(m, AFX, this.damage);
    	AbstractOrb summoner = new Summoner(this.magicNumber);
    	channel(summoner);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkMagician();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
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
