package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class GustoWindaar extends DuelistCard
{

    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("GustoWindaar");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoWindaar.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public GustoWindaar() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 20;
        this.baseBlock = this.block = 16;
        this.baseTributes = this.tributes = 2;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.SPELLCASTER);
        this.originalName = this.name;
    }

    // Upgrade a random Beast card in discard pile, if upgraded, upgrade all of them
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        tribute();
        attack(m);
        block();
        ArrayList<AbstractCard> upgradeableBeastInDiscard = new ArrayList<AbstractCard>(AbstractDungeon.player.discardPile.group.stream()
                .filter(c -> c.hasTag(Tags.BEAST) && c.canUpgrade())
                .collect(Collectors.toList()));
        if (upgradeableBeastInDiscard.isEmpty())
        {
            Util.log("Sage of Gusto found no Beasts in your discard pile.");
            return;
        }
        if (upgraded)
        {
            upgradeableBeastInDiscard.forEach(cardToUpgrade -> {
                cardToUpgrade.upgrade();
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(cardToUpgrade.makeStatEquivalentCopy()));
                Util.log("Sage of Gusto upgraded: " + cardToUpgrade.originalName);
            });
        } else {
            Collections.shuffle(upgradeableBeastInDiscard);
            AbstractCard cardToUpgrade = upgradeableBeastInDiscard.get(0);
            cardToUpgrade.upgrade();
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(cardToUpgrade.makeStatEquivalentCopy()));
            Util.log("Sage of Gusto upgraded: " + cardToUpgrade.originalName);
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(6);
            this.upgradeBlock(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void customOnTribute(DuelistCard tributingCard)
    {
        block(1);
    }
}
