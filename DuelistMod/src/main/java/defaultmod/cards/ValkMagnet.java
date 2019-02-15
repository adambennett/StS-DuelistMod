package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class ValkMagnet extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("ValkMagnet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.VALK_MAGNET);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    private static final int DAMAGE = 65;
    private static final int SUMMONS = 3;
    // /STAT DECLARATION/

    public ValkMagnet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.MAGNETWARRIOR);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, SUMMONS);
    	attack(m, AFX, this.damage);
    	
    	// Remove magnets
    	if (!this.upgraded)
    	{
	    	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, AlphaMagPower.POWER_ID, 1));
	    	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, BetaMagPower.POWER_ID, 1));
	    	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, GammaMagPower.POWER_ID, 1));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ValkMagnet();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); 
            //this.upgradeBaseCost(1);
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
    	
    	// Check for magnets
    	else { if (p.hasPower(AlphaMagPower.POWER_ID)) { if (p.hasPower(BetaMagPower.POWER_ID)) { if (p.hasPower(GammaMagPower.POWER_ID)) { return true; } } } }
    	
    	// Player doesn't have something required at this point
    	this.cantUseMessage = "Need all 3 Magnets";
    	return false;
    }
}