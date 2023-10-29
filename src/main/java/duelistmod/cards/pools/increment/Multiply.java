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

public class Multiply extends DuelistCard {
    public static final String ID = DuelistMod.makeID("Multiply");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Multiply.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public Multiply() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.KURIBOH);
        this.misc = 0;
        this.originalName = this.name;
        this.baseTributes = this.tributes = 2;
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
        return new Multiply();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
