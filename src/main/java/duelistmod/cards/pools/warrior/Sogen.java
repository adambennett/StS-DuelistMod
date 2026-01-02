package duelistmod.cards.pools.warrior;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.SogenPower;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class Sogen extends DuelistCard {

    public static final String ID = DuelistMod.makeID("Sogen");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Sogen.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;

    public Sogen() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.FIELDSPELL);
        this.tags.add(Tags.ALLOYED);
        this.originalName = this.name;
        this.magicNumber = this.baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.applyPowerToSelf(new SogenPower(duelist, this.magicNumber));
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sogen();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public DuelistConfigurationData getConfigurations() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        RESET_Y();
        LINEBREAK();
        LINEBREAK();
        LINEBREAK();
        LINEBREAK();
        settingElements.add(new ModLabel("Configurations for " + this.name + " not setup yet.", (DuelistMod.xLabPos), (DuelistMod.yPos), DuelistMod.settingsPanel, (me) -> {
        }));
        return new DuelistConfigurationData(this.name, settingElements, this);
    }

}
