package duelistmod.cards.pools.gusto;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.abstracts.DuelistCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.orbs.AirOrb;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GustoPilica extends DuelistCard {

    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GustoPilica");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoPilica.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public GustoPilica() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.SPELLCASTER);
        this.baseSummons = this.summons = 1;
        this.originalName = this.name;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Predicate<AbstractCard> beasts = c -> c.hasTag(Tags.MONSTER) && c.hasTag(Tags.BEAST);
        Consumer<List<AbstractCard>> reduceCost = selectedCards -> {
            if (!upgraded) return;
            selectedCards.forEach(card -> card.setCostForTurn(0));
        };

        summon();
        addToBot(new FetchAction(p.drawPile, beasts, 1, reduceCost));
        p.channelOrb(new AirOrb());
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.upgradeBaseCost(1);
            this.initializeDescription();
        }
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        block(1);
    }
}
