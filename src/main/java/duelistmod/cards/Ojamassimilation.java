package duelistmod.cards;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedDrawPileAction;
import duelistmod.actions.unique.ExhaustSpecificCardSuperFastAction;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.patches.*;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class Ojamassimilation extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Ojamassimilation");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Ojamassimilation.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    // /STAT DECLARATION/

    public Ojamassimilation() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.OJAMA);
        this.tags.add(Tags.ALLOYED);
		this.originalName = this.name;
		this.magicNumber = this.baseMagicNumber = 2;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		int spellsExhausted = 0;
		for (AbstractCard c : player().drawPile.group)
		{
			if (c.hasTag(Tags.SPELL))
			{
				AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardSuperFastAction(c, player().drawPile));
				spellsExhausted++;
			}
		}
		
		for (AbstractCard c : player().discardPile.group)
		{
			if (c.hasTag(Tags.SPELL))
			{
				AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, player().discardPile));
				spellsExhausted++;
			}
		}
		
		for (AbstractCard c : player().hand.group)
		{
			if (c.hasTag(Tags.SPELL))
			{
				AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, player().hand));
				spellsExhausted++;
			}
		}
		
		int topRoll = 6;
		for (int i = 0; i < spellsExhausted; i++)
		{
			DuelistCard ojama = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.SPELL);
			AbstractDungeon.actionManager.addToBottom(new RandomizedDrawPileAction(ojama, this.upgraded, true, false, false));
			int buffRoll = AbstractDungeon.cardRandomRng.random(1, topRoll);
			if (buffRoll == 1)
			{
				topRoll+=this.magicNumber;
				int turnRoll = AbstractDungeon.cardRandomRng.random(1, 3);
				DuelistCard.applyRandomBuffPlayer(p, turnRoll, false);
			}
		}
	}

	@Override
	public AbstractCard makeCopy() { return new Ojamassimilation(); }

	@Override
	public void upgrade() 
	{
		if (!upgraded) 
		{
			this.upgradeName();
			this.upgradeMagicNumber(-1);
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
