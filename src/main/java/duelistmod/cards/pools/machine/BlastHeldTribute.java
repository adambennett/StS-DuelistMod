package duelistmod.cards.pools.machine;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class BlastHeldTribute extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("BlastHeldTribute");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BlastHeldTribute.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public BlastHeldTribute() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);	
        this.tags.add(Tags.FORCE_TRIB_FOR_RESUMMONS);
        this.tags.add(Tags.X_COST);
		this.tags.add(Tags.DETONATE_DMG_SELF_DISABLED);
		this.tags.add(Tags.DETONATE_DMG_ENEMIES_ALLOWED);
        this.originalName = this.name;
        this.xDetonate = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int tokens = detonationTribute(true);
    	addCardToHand(DuelistCardLibrary.getRandomTokenForCombat(false, false, true, false, false, true, new ArrayList<>()), tokens);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BlastHeldTribute();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }







    public String failedCardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) { return "You need Explosive Tokens"; }

    public boolean cardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) {
        return p.hasPower(SummonPower.POWER_ID) && ((SummonPower) p.getPower(SummonPower.POWER_ID)).hasExplosiveTokens();
    }






}
