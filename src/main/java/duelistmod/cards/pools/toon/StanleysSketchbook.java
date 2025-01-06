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

    public StanleysSketchbook(int magicNumber) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.NEVER_GENERATE);
        this.misc = 0;
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = magicNumber;
        this.exhaust = true;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(
                "Fleeting (" + this.magicNumber + ")",
                "This card can be played " + this.magicNumber + " time" + (this.magicNumber == 1 ? "" : "s") + " before it is removed from your deck.")
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
        return new StanleysSketchbook(this.magicNumber);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
