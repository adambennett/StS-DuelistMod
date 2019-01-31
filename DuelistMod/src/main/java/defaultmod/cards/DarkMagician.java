package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.SpellCounterPower;
import defaultmod.powers.SummonPower;

public class DarkMagician extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In order to understand how image paths work, go to defaultmod/DefaultMod.java, Line ~140 (Image path section).
     *
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("DarkMagician");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // Yes, you totally can use "defaultModResources/images/cards/Attack.png" instead and that would work.
    // It might be easier to use that while testing.
    // Using makePath is good practice once you get the hand of things, as it prevents you from
    // having to change *every single card/file/path* if the image path changes due to updates or your personal preference.

    public static final String IMG = DefaultMod.makePath(DefaultMod.DARK_MAGICIAN);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 15;
    private static final int TRIBUTES = 2;
    private static final int COUNTERS = 3;
    private static final int U_COUNTERS = 2;

    // /STAT DECLARATION/

    public DarkMagician() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = COUNTERS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, SummonPower.POWER_ID, TRIBUTES));
    	AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SpellCounterPower(p, p, this.magicNumber)));
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
            this.upgradeMagicNumber(U_COUNTERS);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    // If player doesn't have enough summons, can't play card
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	if (p.hasPower(SummonPower.POWER_ID)) 
    	{
    		this.magicNumber = (p.getPower(SummonPower.POWER_ID).amount);
    		if (this.magicNumber >= TRIBUTES)
    		{
    			if (p.energy.energy >= COST)
    			{
    				return true;
    			}
    		}
    		else
    		{
    			return false;
    		}
    	}
    	
    	return false;
    }
   
}