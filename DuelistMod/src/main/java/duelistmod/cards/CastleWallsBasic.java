package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.BaseModCardTags;
import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;

public class CastleWallsBasic extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CastleWallsBasic");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Castle_Walls_Basic.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/ 
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    // /STAT DECLARATION/

    public CastleWallsBasic() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.tags.add(Tags.TRAP);
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
        this.tags.add(Tags.STANDARD_DECK);
        this.tags.add(Tags.DRAGON_DECK);
        this.tags.add(Tags.SPELLCASTER_DECK);
        this.tags.add(Tags.NATURE_DECK);
        this.tags.add(Tags.TOON_DECK);
        this.tags.add(Tags.ORB_DECK);
        this.tags.add(Tags.RESUMMON_DECK);
        this.tags.add(Tags.HEAL_DECK);
        this.tags.add(Tags.CREATOR_DECK);
        this.tags.add(Tags.GENERATION_DECK);
        this.tags.add(Tags.MACHINE_DECK);
        this.tags.add(Tags.ZOMBIE_DECK);
        this.tags.add(Tags.FIEND_DECK);
        this.tags.add(Tags.AQUA_DECK);
        this.zombieDeckCopies = 3;
        this.fiendDeckCopies = 3;
        this.zombieDeckCopies = 3;
        this.machineDeckCopies = 2;
		this.generationDeckCopies = 3;
        this.standardDeckCopies = 2;
        this.dragonDeckCopies = 2;
        this.spellcasterDeckCopies = 2;
        this.natureDeckCopies = 3;
        this.toonDeckCopies = 2;
        this.creatorDeckCopies = 3;
        this.orbDeckCopies = 2;
        this.resummonDeckCopies = 2;
        this.healDeckCopies = 3;
        this.aquaDeckCopies = 2;
        this.originalName = this.name;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	block(this.block);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CastleWallsBasic();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
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

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}