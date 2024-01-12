package duelistmod.cards.pools.gusto;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.FetchFromTag;
import duelistmod.actions.unique.GustoCodorAction;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import eatyourbeets.actions.pileSelection.FetchFromPile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GustoCodor extends DuelistCard {
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GustoCodor");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoCodor.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String FETCHED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    private boolean fetchedFromDiscard = false;

    public GustoCodor(){
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.BEAST);
        this.baseDamage = this.damage = 3;
        this.baseSummons = this.summons = 1;
        this.originalName = this.name;
    }

    @Override
    public void upgrade() {
        if (upgraded) return;
        upgradeDamage(4);
        upgradeName();
        this.rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        summon();
        attack(m);
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        block(1);
    }

    @Override
    public void onTributeWhileInDiscard(DuelistCard tributedMon, DuelistCard tributingMon) {
        Util.log("Fetching Codor from discard");
        if (!tributedMon.hasTag(Tags.SPELLCASTER) || fetchedFromDiscard) return;
        this.exhaust = true;
        this.rawDescription = FETCHED_DESCRIPTION;
        this.initializeDescription();
        fetchedFromDiscard = true;
        addToBot(new GustoCodorAction(this));

    }
}
