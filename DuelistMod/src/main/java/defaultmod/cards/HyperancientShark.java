package defaultmod.cards;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class HyperancientShark extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("SevenColoredFish");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.SEVEN_COLORED_FISH);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int SUMMONS = 1;
    //private static final int U_SUMMONS = 1;
    // /STAT DECLARATION/

    public HyperancientShark() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.tags.add(DefaultMod.MONSTER);
        if (Loader.isModLoaded("conspire")) { this.tags.add(DefaultMod.GOOD_TRIB); }
        this.tags.add(DefaultMod.METAL_RAIDERS);
        this.tags.add(DefaultMod.STANDARD_DECK);
        this.tags.add(DefaultMod.ORB_DECK);
        this.tags.add(DefaultMod.AQUA);
		this.startingOrbDeckCopies = 2;
        this.startingDeckCopies = 2;
        this.originalName = this.name;
        this.summons = SUMMONS;
        this.isSummon = true;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	attack(m, AFX, this.damage);
    	
    	// for testing
    	//printSetDetails(new CardTags[] {DefaultMod.MONSTER, DefaultMod.SPELL, DefaultMod.TRAP, DefaultMod.DRAGON, DefaultMod.LEGEND_BLUE_EYES, DefaultMod.METAL_RAIDERS, DefaultMod.PHARAOH_SERVANT});
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new HyperancientShark();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeMagicNumber(U_SUMMONS);
            this.upgradeDamage(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		if (tributingCard.originalName.equals("Legendary Fisherman")) { draw(2); }
		if (tributingCard.hasTag(DefaultMod.AQUA))
		{
			DuelistCard randomAqua = (DuelistCard) returnTrulyRandomFromSets(DefaultMod.AQUA, DefaultMod.MONSTER).makeCopy();
			addCardToHand(randomAqua);
		}
	}

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var)
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
    	attack(m, AFX, this.damage);
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
    	attack(m, AFX, this.damage);
		
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