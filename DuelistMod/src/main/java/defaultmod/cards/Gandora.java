package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class Gandora extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("Gandora");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.GANDORA);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final AttackEffect AFX = AttackEffect.FIRE;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public Gandora() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(DefaultMod.SPELL);
        this.originalName = this.name;
        this.baseDamage = this.damage = 50;
        this.tributes = 5;
        this.baseMagicNumber = this.magicNumber = 30;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Tribute
    	tribute(p, this.tributes, false, this);
    	
    	// Deal half your HP damage to yourself
    	damageSelf(player().currentHealth/2);

    	// Record hand size
    	int handSize = AbstractDungeon.player.hand.size() - 1;
    	if (handSize < 0) { handSize = 0; }
    	
    	// Exhaust all cards
    	AbstractDungeon.actionManager.addToTop(new ExhaustAction(p, p, handSize, true));
    	
    	// Deal 50 damage + 30 for every card exhausted
    	attack(m, AFX, this.damage + (this.magicNumber * handSize));

    	// Add 5 random 0 cost, upgraded dragons to draw pile
    	for (int i = 0; i < 5; i++)
    	{
	    	AbstractCard randomDragon = newCopyOfDragon("random");
	    	randomDragon.upgrade();
	    	randomDragon.modifyCostForCombat(0);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(randomDragon, 1, true, true));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Gandora();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.tributes = 4;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}