package duelistmod.cards.pools.aqua;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.FishAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class SevenColoredFish extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("SevenColoredFish");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SEVEN_COLORED_FISH);
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

    public SevenColoredFish() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.METAL_RAIDERS);
        this.tags.add(Tags.AQUA);
        this.tags.add(Tags.ORIGINAL_DECK);   
        this.tags.add(Tags.AQUA_DECK);
        this.tags.add(Tags.IS_OVERFLOW);
        this.baseDamage = this.damage = 5;
		this.aquaDeckCopies = 2;
    	this.startingOPODeckCopies = 2;
        this.startingOriginalDeckCopies = 2;
        this.baseMagicNumber = this.magicNumber = 1;
        this.secondMagic = this.baseSecondMagic = 2;
        this.originalName = this.name;
        this.summons = this.baseSummons = 1;
        this.isSummon = true;
        this.setupStartingCopies();
    }
    
    @Override
    public void triggerOverflowEffect()
    {
    	super.triggerOverflowEffect();
    	this.addToBot(new FishAction(this.secondMagic));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	attack(m);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SevenColoredFish();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public void customOnTribute(DuelistCard tc)
    {
    	if (tc instanceof LegendaryFisherman) { drawTag(2, Tags.AQUA); }
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
