package duelistmod.cards.pools.toon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.enemyDuelist.EnemyMakeTempCardInDiscardAction;
import duelistmod.actions.enemyDuelist.EnemyMakeTempCardInDrawPileAction;
import duelistmod.cards.other.tokens.BunnyToken;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

public class Bunilla extends DuelistCard {

    public static final String ID = DuelistMod.makeID("Bunilla");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Bunilla.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public Bunilla() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseBlock = this.block = 7;
        this.baseMagicNumber = this.magicNumber = 1;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.baseSummons = this.summons = 1;
        this.cardsToPreview = new BunnyToken();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        block();
        triggerTokenCreation();
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (isCreatingToken()) {
            this.glowColor = Color.GOLD;
        }
    }

    public void triggerTokenCreation() {
        AnyDuelist duelist = AnyDuelist.from(this);
        if (isCreatingToken()) {
            BunnyToken card = new BunnyToken(true);
            if (this.upgraded) {
                card.setForcingUpgrade(true);
                card.upgrade();
            }
            if (duelist.player()) {
                AbstractGameAction sendTo = this.upgraded
                        ? new MakeTempCardInDrawPileAction(card, this.magicNumber, true, true)
                        : new MakeTempCardInDiscardAction(card, this.magicNumber);
                this.addToBot(sendTo);
            } else if (duelist.getEnemy() != null) {
                AbstractGameAction sendTo = this.upgraded
                        ? new EnemyMakeTempCardInDrawPileAction(card, this.magicNumber, true, true)
                        : new EnemyMakeTempCardInDiscardAction(duelist.getEnemy(), card, this.magicNumber);
                this.addToBot(sendTo);
            }
        }
    }

    private boolean isCreatingToken() {
        AnyDuelist duelist = AnyDuelist.from(this);
        return this.magicNumber > 0 && duelist.getCardsPlayedThisTurn().stream().anyMatch(c -> c.hasTag(Tags.TOON));
    }

    @Override
    public AbstractCard makeCopy() {
    	return new Bunilla();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
