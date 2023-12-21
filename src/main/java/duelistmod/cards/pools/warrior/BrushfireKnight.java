package duelistmod.cards.pools.warrior;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.cards.other.orbCards.*;
import duelistmod.dto.LavaOrbEruptionResult;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class BrushfireKnight extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("BrushfireKnight");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BrushfireKnight.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public BrushfireKnight() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);  
        this.tags.add(Tags.WARRIOR); 
        this.summons = this.baseSummons = 1;	
        this.baseDamage = this.damage = 10;
        this.magicNumber = this.baseMagicNumber = 2;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
        this.originalName = this.name;
    }
    
    @Override
    public LavaOrbEruptionResult lavaEvokeEffect() {
        applyPowerToSelf(new StrengthPower(AbstractDungeon.player, this.magicNumber));
        return new LavaOrbEruptionResult();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	attack(m);
    	ArrayList<AbstractCard> orbs = new ArrayList<>();
    	orbs.add(new LavaOrbCard());
    	orbs.add(new FireOrbCard());
    	AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(orbs, 1, false, false, false, true));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BrushfireKnight();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }












}
