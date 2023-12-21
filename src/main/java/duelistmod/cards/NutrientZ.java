package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.DebuffHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class NutrientZ extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("NutrientZ");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.NUTRIENT_Z);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;
    private static int HEAL = 25;
    private static int U_HEAL = 10;
    private static int HP_CHK = 30;
    private static int debuffs = 3;
    // /STAT DECLARATION/

    public NutrientZ() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = HEAL;
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.LEGACY_DARKNESS);
        this.tags.add(Tags.NEVER_GENERATE);
        this.exhaust = true;
		this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
       if (p.currentHealth <= HP_CHK)
       {
    	   heal(p, this.magicNumber);
    	   for (int i = 0; i < debuffs; i++)
    	   {
    		   AbstractPower randomDebuff = DebuffHelper.getRandomPlayerDebuff(p, 3);
    		   applyPowerToSelf(randomDebuff);
    	   }
       }
    }
    
    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new NutrientZ();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(U_HEAL);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    public String failedCardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) { return DuelistMod.nutrientZString; }

    public boolean cardSpecificCanUse(final AbstractCreature owner) {
        return owner.currentHealth < 40;
    }
    


	











}
