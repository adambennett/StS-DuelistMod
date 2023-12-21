package duelistmod.cards;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.VioletCrystalPower;
import duelistmod.variables.*;

import java.util.ArrayList;

public class VioletCrystal extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("VioletCrystal");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.VIOLET_CRYSTAL);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public VioletCrystal() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.FIELDSPELL);
        this.tags.add(Tags.ALLOYED);
        this.originalName = this.name;
        this.magicNumber = this.baseMagicNumber = 4;
        this.secondMagic = this.baseSecondMagic = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	applyPowerToSelf(new VioletCrystalPower(this.magicNumber, this.secondMagic));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new VioletCrystal();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSecondMagic(1);
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
        settingElements.add(new ModLabel("Configurations for " + this.name + " not setup yet.", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        return new DuelistConfigurationData(this.name, settingElements, this);
    }













}
