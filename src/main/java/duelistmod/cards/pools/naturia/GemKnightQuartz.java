package duelistmod.cards.pools.naturia;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.BaseMod;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class GemKnightQuartz extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GemKnightQuartz");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GemKnightQuartz.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public GemKnightQuartz() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 19;
        this.tributes = this.baseTributes = 7;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ROCK);
        this.tags.add(Tags.GEM_KNIGHT);
        this.originalName = this.name;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	attack(m);
    	int cards = BaseMod.MAX_HAND_SIZE - p.hand.group.size();
    	for (int i = 0; i < cards; i++)
    	{
    		AbstractCard randGem = returnTrulyRandomFromSet(Tags.GEM_KNIGHT);
    		while (randGem instanceof GemKnightQuartz) { randGem = returnTrulyRandomFromSet(Tags.GEM_KNIGHT); }
    		if (randGem.cost > 0) { randGem.setCostForTurn(-randGem.cost); }
    		addCardToHand(randGem);
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GemKnightQuartz();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
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
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
	
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
