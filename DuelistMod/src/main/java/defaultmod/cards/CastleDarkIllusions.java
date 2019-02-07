package defaultmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.CastlePower;

public class CastleDarkIllusions extends DuelistCard 
{
    // TEXT DECLARATION 
    public static final String ID = defaultmod.DefaultMod.makeID("CastleDarkIllusions");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.CASTLE_DARK);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION 	
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 3;
    // /STAT DECLARATION/


    public CastleDarkIllusions() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (this.upgraded) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CastlePower(p, p, true))); }
    	else {  AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CastlePower(p, p, false))); }
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CastleDarkIllusions();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBaseCost(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}