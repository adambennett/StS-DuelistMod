package duelistmod.cards.pools.toon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.incomplete.HauntedPower;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.List;

public class ToonTerror extends DuelistCard {

    public static final String ID = DuelistMod.makeID("ToonTerror");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ToonTerror.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 2;

    public ToonTerror() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.TRAP);
    	this.misc = 0;
    	this.originalName = this.name;
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("Haunted", "#yHaunted cards cost #b0. Whenever you play a #yHaunted card, trigger a random negative effect."));
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
        if (duelist.player() && !duelist.hasPower(HauntedPower.POWER_ID)) {
            duelist.applyPowerToSelf(new HauntedPower(duelist.creature(), duelist.creature(), 1, Tags.MONSTER));
        }
        List<List<? extends AbstractCard>> allGroups = new ArrayList<>();
        allGroups.add(TheDuelist.cardPool.group);
        allGroups.add(DuelistMod.duelColorlessCards);
        allGroups.add(DuelistMod.myCards);
        ArrayList<AbstractCard> randomCards = CardFinderHelper.find(this.magicNumber, allGroups, (c) ->  c instanceof RevengeCard && !c.hasTag(Tags.NEVER_GENERATE));
        duelist.addCardsToHand(randomCards);
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new ToonTerror();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
