package duelistmod.cards.pools.beast;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class ObedienceSchooled extends DuelistCard {
    public static final String ID = DuelistMod.makeID("ObedienceSchooled");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ObedienceSchooled.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;

    public ObedienceSchooled() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseMagicNumber = this.magicNumber = 4;
    	this.tags.add(Tags.SPELL);
    	this.misc = 0;
    	this.originalName = this.name;
        this.exhaust = true;
    	this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        ArrayList<AbstractCard> beasts = new ArrayList<>();
        ArrayList<AbstractCard> exhaust = new ArrayList<>();
        AnyDuelist duelist = AnyDuelist.from(this);
        int counter = 0;
        for (int i = duelist.drawPile().size() - 1; i > 0 && counter < this.magicNumber; i--, counter++) {
            AbstractCard c = duelist.drawPile().get(i);
            if (c.hasTag(Tags.BEAST)) {
                beasts.add(c);
            } else {
                exhaust.add(c);
            }
        }
        if (!beasts.isEmpty()) {
            duelist.gainEnergy(beasts.size());
            duelist.addCardsToHand(beasts);
        }
        for (AbstractCard c : exhaust) {
            this.addToBot(new ExhaustSpecificCardAction(c, duelist.discardPileGroup()));
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new ObedienceSchooled();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
