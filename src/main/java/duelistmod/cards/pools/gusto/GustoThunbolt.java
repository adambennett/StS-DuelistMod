package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.orbs.Lightning;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.function.Consumer;
public class GustoThunbolt extends DuelistCard {

    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("GustoThunbolt");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoThunbolt.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public GustoThunbolt() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.BEAST);
        this.baseSummons = this.summons = 1;
        this.originalName = this.name;
    }

    @Override
    public void upgrade() {
        if (upgraded) return;
        this.upgradeName();
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
        this.upgraded = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        summon();
        long spellcasters = 0;
        spellcasters += player().drawPile.group.stream()
                .filter(card -> card.hasTag(Tags.SPELLCASTER) && card.hasTag(Tags.MONSTER))
                .count();
        if (upgraded) {
            spellcasters += player().discardPile.group.stream()
                    .filter(card -> card.hasTag(Tags.SPELLCASTER) && card.hasTag(Tags.MONSTER))
                    .count();
        }
        for (int i = 0; i < spellcasters; i++)
        {
            channel(new Lightning());
        }
    }
}
