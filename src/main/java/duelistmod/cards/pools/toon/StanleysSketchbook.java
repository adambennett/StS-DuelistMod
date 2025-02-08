package duelistmod.cards.pools.toon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StanleysSketchbook extends DuelistCard {

    public static final String ID = DuelistMod.makeID("StanleysSketchbook");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("StanleysSketchbook.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;
    private int usesRemaining;

    public StanleysSketchbook(int uses) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.NEVER_GENERATE);
        this.misc = 0;
        this.originalName = this.name;
        this.usesRemaining = uses;
        this.purgeOnUse = true;
        this.cardsToPreview = new ToonWorld();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(
                "Consumable (" + this.getUsesRemaining() + ")",
                "This card can be played " + this.getUsesRemaining() + " time" + (this.getUsesRemaining() == 1 ? "" : "s") + " before it is removed from your deck.")
        );
        return retVal;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.getEnemy() != null) return; // Enemy duelist should never have or use this card

        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new StanleysSketchbookAddToonWorld(this.upgraded));
        choices.add(new StanleysSketchbookRemoveCard());
        CardGroup cardsToChooseFrom = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        cardsToChooseFrom.group.addAll(choices);
        Consumer<ArrayList<AbstractCard>> resummon = group -> group.forEach(DuelistCard::resummon);
        SelectScreenHelper.open(cardsToChooseFrom, 1, "Choose an effect", true, resummon);
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new StanleysSketchbook(this.getUsesRemaining());
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        if (card instanceof StanleysSketchbook) {
            StanleysSketchbook token = (StanleysSketchbook) card;
            token.setUsesRemaining(this.usesRemaining);
            return token;
        }
        return card;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public String onSave() {
        return this.usesRemaining+"";
    }

    @Override
    public void onLoad(String attributeString) {
        if (attributeString == null || attributeString.equals("")) {
            return;
        }
        try {
            this.usesRemaining = Integer.parseInt(attributeString);
        } catch (Exception ex) {
            Util.logError("Error loading Stanley's Sketchbook's number of uses remaining. Defaulting to 1. Sorry if you had more...", ex);
            this.usesRemaining = 1;
        }
    }

    public boolean updateOnPlay() {
        this.decrementUsesRemaining();
        this.fixUpgradeDesc();
        this.initializeDescription();
        return this.getUsesRemaining() <= 0;
    }

    public int getUsesRemaining() {
        return usesRemaining;
    }

    public void setUsesRemaining(int usesRemaining) {
        this.usesRemaining = usesRemaining;
    }

    public void decrementUsesRemaining() {
        this.usesRemaining--;
    }

}
