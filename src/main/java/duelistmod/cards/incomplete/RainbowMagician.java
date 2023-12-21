package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.RainbowMagicianAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class RainbowMagician extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("RainbowMagician");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RainbowMagician.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_DIAGONAL;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public RainbowMagician() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 15;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.EXEMPT);
        this.tags.add(Tags.NEVER_GENERATE);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 3;
        this.magicNumber = this.baseMagicNumber = 2;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 2;
        this.makeMegatyped();
    }
    
	@Override
	public void update()
	{
		super.update();
		this.showEvokeOrbCount = this.magicNumber;
	}

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	attack(m, AFX, this.damage);
    	ArrayList<DuelistCard> orbs = new ArrayList<DuelistCard>();
    	ArrayList<String> orbNames = new ArrayList<String>();
    	ArrayList<AbstractCard> orbsToChooseFrom = DuelistCardLibrary.orbCardsForGeneration();
		for (int i = 0; i < 5; i++)
		{
			AbstractCard random = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1));
			while (orbNames.contains(random.name)) { random = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1)); }
			orbs.add((DuelistCard) random.makeCopy());
			orbNames.add(random.name);
		}
    	AbstractDungeon.actionManager.addToBottom(new RainbowMagicianAction(orbs, 1, false, false, false, true, this.magicNumber));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new RainbowMagician();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!this.upgraded) 
        {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    














   
}
