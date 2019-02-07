package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class WingedDragonRa extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("WingedDragonRa");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.WINGED_DRAGON_RA);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final AttackEffect AFX = AttackEffect.FIRE;
    private static final int COST = -1;
    private static int TRIBUTES = 4;
    // /STAT DECLARATION/

    public WingedDragonRa() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int manaUsed = getXEffect();
    	int summons = getSummons(p) * 2;
    	int cardsPlayed = (p.cardsPlayedThisTurn - 1) * 3;
    	if (cardsPlayed < 0) { cardsPlayed = 0; }
    	int maxSummons = getMaxSummons(p);
    	int damageTotal = (cardsPlayed + summons) * manaUsed;
    	if (upgraded) { damageTotal += maxSummons; }
    	attack(m, AFX, damageTotal);
    	tribute(p, TRIBUTES, false);
    	useXEnergy();
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new WingedDragonRa();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            TRIBUTES = 3;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}