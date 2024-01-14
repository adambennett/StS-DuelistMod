package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.abstracts.DuelistCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.orbs.AirOrb;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
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
        this.exhaust = true;
        this.originalName = this.name;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        summon();

        Predicate<AbstractCard> gustoBeast = c -> c.hasTag(Tags.MONSTER) && c.hasTag(Tags.BEAST);
        ArrayList<AbstractCard> foundCards = CardFinderHelper.find(2, TheDuelist.cardPool.group, DuelistMod.myCards, gustoBeast);
        if (upgraded) {
            foundCards.forEach(AbstractCard::upgrade);
            addToBot(new MakeTempCardInDiscardAction(foundCards.get(1).makeStatEquivalentCopy(), 1));
        }
        addToBot(new MakeTempCardInDrawPileAction(foundCards.get(0).makeStatEquivalentCopy(), 1, true, false));

        p.channelOrb(new AirOrb());
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgraded = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        block(1);
    }
}
