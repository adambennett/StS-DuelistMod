package duelistmod.cards.incomplete;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.LavaOrbEruptionResult;
import duelistmod.orbs.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class FlameTiger extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("FlameTiger");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("FlameTiger.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public FlameTiger() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.summons = this.baseSummons = 2;
        this.isSummon = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 2;
    }
    
    @Override
    public LavaOrbEruptionResult lavaEvokeEffect()
    { 
    	int roll = AbstractDungeon.cardRandomRng.random(1, 3);
    	if (roll == 1) {
            channel(new FireOrb(), 1);
        } else if (roll == 2) {
            channel(new Lava(), 1);
        } else {
            channel(new Blaze(), 1);
        }
        return new LavaOrbEruptionResult();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	channel(new FireOrb(), 1);
    	channel(new Lava(), 1);
    }

    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeSummons(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }


	



	


	
	@Override
    public AbstractCard makeCopy() { return new FlameTiger(); }
	
}
