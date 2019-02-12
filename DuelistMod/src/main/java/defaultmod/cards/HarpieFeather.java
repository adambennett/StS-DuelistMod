package defaultmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.SpellCounterPower;

public class HarpieFeather extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("HarpieFeather");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.HARPIE_FEATHER);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public HarpieFeather() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.damage = this.baseDamage = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int damageTotal = 0;
    	ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
    	
    	for (AbstractMonster g : monsters)
    	{
    		if (g.hasPower(SpellCounterPower.POWER_ID))
    		{
    			SpellCounterPower counterInstance = (SpellCounterPower)g.getPower(SpellCounterPower.POWER_ID);
    			int counters = counterInstance.amount;
    			counterInstance.amount = 0;
    			damageTotal += counters;
    		}
    	}
    	
    	if (damageTotal < 0) { damageTotal = 0; }
    	this.baseDamage = damageTotal * 2;
    	this.multiDamage = new int[] {damageTotal, damageTotal, damageTotal,damageTotal, damageTotal, damageTotal, damageTotal, damageTotal, damageTotal, damageTotal };
    	attackAllEnemies(AFX, this.multiDamage);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new HarpieFeather();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}