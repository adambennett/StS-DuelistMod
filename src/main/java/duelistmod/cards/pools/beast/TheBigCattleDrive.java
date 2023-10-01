package duelistmod.cards.pools.beast;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class TheBigCattleDrive extends DuelistCard {
    public static final String ID = DuelistMod.makeID("TheBigCattleDrive");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("TheBigCattleDrive.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public TheBigCattleDrive() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.SPELL);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.baseTributes = this.tributes = 3;
        this.baseMagicNumber = this.magicNumber = 2;
        this.exhaust = true;
    	this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        tribute();
        if (this.magicNumber > 0) {
            ArrayList<AbstractCard> randomCards = CardFinderHelper.find(
                    this.magicNumber,
                    TheDuelist.cardPool.group,
                    DuelistMod.myCards,
                    CardFinderHelper.hasAnyTags(Tags.BEAST)
            );
            AnyDuelist duelist = AnyDuelist.from(this);
            randomCards.forEach(c -> {
                if (c.cost > 0) {
                    c.cost = 0;
                    c.costForTurn = 0;
                    c.isCostModified = true;
                }
            });
            if (duelist.player()) {
                DuelistCard.addCardsToHand(randomCards);
            } else if (duelist.getEnemy() != null) {
                for (AbstractCard c : randomCards) {
                    duelist.getEnemy().addCardToHand(AbstractEnemyDuelist.fromCard(c));
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new TheBigCattleDrive();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
