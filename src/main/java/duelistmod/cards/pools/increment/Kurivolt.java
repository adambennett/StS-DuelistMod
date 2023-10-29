package duelistmod.cards.pools.increment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class Kurivolt extends DuelistCard {
    public static final String ID = DuelistMod.makeID("Kurivolt");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Kurivolt.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public Kurivolt() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.X_COST);
        this.tags.add(Tags.KURIBOH);
        this.misc = 0;
        this.originalName = this.name;
        this.baseSummons = this.summons = 1;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int tokens = xCostTribute(Tags.KURIBOH);
        if (tokens > 0) {
            ArrayList<AbstractCard> list = findAllOfTypeForResummon(Tags.KURIBOH, tokens);
            for (AbstractCard toResummon : list) {
                if (toResummon instanceof DuelistCard) {
                    m = AbstractDungeon.getRandomMonster();
                    if (m != null) {
                        DuelistCard.resummon(toResummon, m);
                    }
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Kurivolt();
    }

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
}
