package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class OjamaKing extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("OjamaKing");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.OJAMA_KING);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 3;
    private static final int TRIBUTES = 2;
    private static int MIN_BUFF_TURNS_ROLL = 1;
    private static int MAX_BUFF_TURNS_ROLL = 3;
    private static int MIN_DEBUFF_TURNS_ROLL = 1;
    private static int MAX_DEBUFF_TURNS_ROLL = 6;
    private static int RAND_CARDS = 2;
    private static int RAND_BUFFS = 1;
    private static int RAND_DEBUFFS = 2;
    // /STAT DECLARATION/

    public OjamaKing() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.OJAMA);
        this.misc = 0;
		this.originalName = this.name;
    }

    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Tribute
		tribute(p, TRIBUTES, false, this);

		// Add 5 random cards to hand, set cost to 0
		for (int i = 0; i < RAND_CARDS; i++)
		{
			AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
			card.costForTurn = 0;
			if (upgraded) { card.upgrade(); }
			card.isEthereal = true;
			card.rawDescription = "Ethereal. NL " + card.rawDescription;
			addCardToHand(card);
		}
		
		// Give self 3 random buffs
		for (int i = 0; i < RAND_BUFFS; i++)
		{
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_BUFF_TURNS_ROLL, MAX_BUFF_TURNS_ROLL);
			applyRandomBuffPlayer(p, randomTurnNum, false);
		}
		
		// Give 3 random debuffs to enemy
		for (int i = 0; i < RAND_DEBUFFS; i++)
		{
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_DEBUFF_TURNS_ROLL, MAX_DEBUFF_TURNS_ROLL);
			applyPower(getRandomDebuff(p, m, randomTurnNum), m);
		}
		
		// Give 3 Spell Counters to enemy
		//applyPower(new SpellCounterPower(p, p, COUNTERS), m);

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new OjamaKing();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
            MAX_DEBUFF_TURNS_ROLL = 8;
            MAX_BUFF_TURNS_ROLL = 5;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
 // If player doesn't have enough summons, can't play card
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }
    	
  		// Pumpking & Princess
  		else if (this.misc == 52) { return true; }
    	
  		// Mausoleum check
    	else if (p.hasPower(EmperorPower.POWER_ID))
		{
			EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
			if (!empInstance.flag)
			{
				return true;
			}
		}
    	
    	// Check for # of summons >= tributes
    	else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= TRIBUTES) { return true; } } }
    	
    	// Player doesn't have something required at this point
    	this.cantUseMessage = "Not enough Summons";
    	return false;
    }
}