package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.*;
import duelistmod.orbs.*;
import duelistmod.patches.*;

public class OjamaDeltaHurricane extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("OjamaDeltaHurricane");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.OJAMA_DELTA_HURRICANE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;

    // /STAT DECLARATION/

    public OjamaDeltaHurricane() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.OJAMA);
        this.tags.add(Tags.OJAMA_DECK);
		this.ojamaDeckCopies = 1;
		this.originalName = this.name;
		this.setupStartingCopies();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		AbstractOrb black = new Black();
		AbstractOrb earth = new Earth();
		AbstractOrb mud = new Mud();
		channel(black);
		channel(earth);
		channel(mud);
	}

	@Override
	public AbstractCard makeCopy() { return new OjamaDeltaHurricane(); }

	@Override
	public void upgrade() 
	{
		if (!upgraded) 
		{
			this.upgradeName();
			this.upgradeBaseCost(1);
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