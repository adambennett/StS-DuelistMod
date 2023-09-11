package duelistmod.cards.pools.beast;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class GiantRat extends DuelistCard {
    public static final String ID = DuelistMod.makeID("GiantRat");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GiantRat.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    public static final int baseTrib = 12;

    public GiantRat() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseBlock = this.block = 70;
        this.magicNumber = this.baseMagicNumber = 1;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.GIANT);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.tributes = this.baseTributes = baseTrib;
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }

    @Override
    public void onDrawnWhileInHand(AbstractCard drawnCard) {
        cardDrawn(drawnCard);
    }

    @Override
    public void onDrawnWhileInDiscard(AbstractCard drawnCard) {
        cardDrawn(drawnCard);
    }

    @Override
    public void onDrawnWhileInDraw(AbstractCard drawnCard) {
        cardDrawn(drawnCard);
    }

    private void cardDrawn(AbstractCard drawnCard) {
        if (this.tributes > 0 && drawnCard.hasTag(Tags.BEAST)) {
            AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(this, -this.magicNumber, true));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        tribute();
        block();
        if (this.tributes == 0 || this.tributes != baseTrib) {
            AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, baseTrib - this.tributes, true));
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new GiantRat();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(10);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
