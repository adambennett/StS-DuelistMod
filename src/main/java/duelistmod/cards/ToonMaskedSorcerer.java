package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class ToonMaskedSorcerer extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("ToonMaskedSorcerer");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ToonMaskedSorcerer.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public ToonMaskedSorcerer() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons = 2;
        this.damage = this.baseDamage = 28;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
        this.toon = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.TOON_WORLD);
        this.tags.add(Tags.TOON_POOL);
        this.tags.add(Tags.SPELLCASTER);
		this.originalName = this.name;
        this.isSummon = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	damageThroughBlock(m, p, this.damage, this.baseAFX);
    	ArrayList<AbstractCard> orbs = new ArrayList<>();
    	ArrayList<String> orbNames = new ArrayList<>();
    	ArrayList<AbstractCard> orbsToChooseFrom = DuelistCardLibrary.orbCardsForGeneration();
		for (int i = 0; i < 5; i++)
		{
			AbstractCard random = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1));
			while (orbNames.contains(random.name)) { random = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1)); }
			orbs.add((DuelistCard) random.makeCopy());
			orbNames.add(random.name);
		}
    	AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(orbs, 1, false, false, false, true));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ToonMaskedSorcerer();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		spellcasterSynTrib(tributingCard);
		toonSynTrib(tributingCard);
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
