package duelistmod.cards.pools.insects;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.patches.*;
import duelistmod.variables.*;

import java.util.ArrayList;

public class JiraiGumo extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("JiraiGumo");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.JIRAI_GUMO);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    //private static final int U_SUMMONS = 1;
    // /STAT DECLARATION/

    public JiraiGumo() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 18;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GOOD_TRIB);
        this.tags.add(Tags.INSECT);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.METAL_RAIDERS);
        this.originalName = this.name;
        this.summons = this.baseSummons = 2;
        this.isSummon = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	int roll = AbstractDungeon.cardRandomRng.random(1, 2);
    	if (roll == 1) { attack(m, AFX, this.damage); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new JiraiGumo();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
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
