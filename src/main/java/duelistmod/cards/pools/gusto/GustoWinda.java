package duelistmod.cards.pools.gusto;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
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
import java.util.stream.Collectors;
import java.util.Collections;

public class GustoWinda extends DuelistCard {
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("GustoWinda");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoWinda.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String FETCHED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;


    // /STAT DECLARATION/

    public GustoWinda() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.SPELLCASTER);
        this.originalName = this.name;
        this.baseSummons = this.summons = 1;
        this.baseBlock = this.block = 3;
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeName();
        this.upgradeSummons(1);
        this.upgradeBlock(4);
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        summon();
        block();
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        block(1);
    }

    @Override
    public void onIncrementWhileInDiscard(int amount, int newMaxSummons) {
        this.exhaust = true;
        this.rawDescription = FETCHED_DESCRIPTION;
        this.initializeDescription();
        addToBot(new FetchAction(player().discardPile, c -> c == this, 1));
    }
}
