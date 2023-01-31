package duelistmod.cards.other.tempCards;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.AddCardTagsToListAction;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.other.tokens.*;
import duelistmod.cards.pools.warrior.DarkCrusader;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.RockSunrisePower;
import duelistmod.variables.Tags;

public class DynamicTypeCard extends DuelistCard 
{

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private DuelistCard callCard;
    private String imgSave;
    private CardTags tagSave;
    // /STAT DECLARATION/

    public DynamicTypeCard(String ID, String NAME, String IMG, String DESCRIPTION, CardTags tag, DuelistCard callingCard, int magicNum) 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.ARCHETYPE);
    	this.tags.add(tag);
    	this.purgeOnUse = true;
    	this.dontTriggerOnUseCard = true;
    	this.callCard = callingCard;
    	this.imgSave = IMG;
    	this.tagSave = tag;
    	this.magicNumber = this.baseMagicNumber = magicNum;
		this.sendToMasterDeck = false;
		this.sendToGraveyard = false;
		CommonKeywordIconsField.useIcons.set(this, false);
    }
    

    @Override public AbstractCard makeCopy() { return new DynamicTypeCard(this.cardID, this.name, this.imgSave, this.rawDescription, this.tagSave, this.callCard, this.baseMagicNumber); } 
    @Override public AbstractCard makeStatEquivalentCopy() { return new DynamicTypeCard(this.cardID, this.name, this.imgSave, this.rawDescription, this.tagSave, this.callCard, this.magicNumber); }
    @Override public void use(AbstractPlayer p, AbstractMonster m)  
    {
    	if (this.callCard instanceof RainbowJar)
    	{
    		applyPowerToSelf(new RainbowCapturePower(p, p, this.tagSave, this.magicNumber));
    	}
    	
    	if (this.callCard instanceof ShardGreed)
    	{
    		applyPowerToSelf(new GreedShardPower(p, p, this.magicNumber, this.tagSave));
    	}
    	
    	if (this.callCard instanceof RockSunrise)
    	{
    		applyPowerToSelf(new RockSunrisePower(this.magicNumber, this.tagSave));
    		DuelistMod.chosenRockSunriseTag = this.tagSave;
    	}
    	
    	if (this.callCard instanceof WingedKuriboh9)
    	{
    		drawTag(this.magicNumber, this.tagSave);
    	}
    	
    	if (this.callCard instanceof WingedKuriboh10)
    	{
    		drawTag(this.magicNumber, this.tagSave);
    	}
    	
    	if (this.callCard instanceof YamiForm)
    	{
    		ArrayList<AbstractCard> list = DuelistCard.findAllOfTypeForResummon(tagSave, Tags.MONSTER, 1);
    		for (AbstractCard c : list) { AbstractMonster mon = AbstractDungeon.getRandomMonster(); if (mon != null) { resummon(c, mon); }} 		
    	}
    	
    	if (this.callCard instanceof SummonToken)
    	{
    		int loopCap = 50;
    		DuelistCard randMon = (DuelistCard)DuelistCard.returnTrulyRandomFromSet(tagSave);
    		while (!randMon.hasTag(Tags.MONSTER) && loopCap> 0) { randMon = (DuelistCard)DuelistCard.returnTrulyRandomFromSet(tagSave); loopCap++; }
    		summon(p, 1, randMon);
    	}
    	
    	if (this.callCard instanceof TributeToken)
    	{
    		DuelistCard randMon = (DuelistCard)DuelistCard.returnTrulyRandomFromOnlyFirstSet(tagSave, Tags.MEGATYPED);
    		//while (getAllMonsterTypes(randMon).size() != 1 || !getAllMonsterTypes(randMon).contains(tagSave)) { randMon = (DuelistCard)DuelistCard.returnTrulyRandomFromSet(tagSave); }
    		tribute(p, 1, false, randMon);
    		if (DuelistMod.debug) { DuelistMod.logger.info("Tribute Token just called tribute with this randomly generated monster: " + randMon.originalName + " :: and tagSave was: " + tagSave.toString()); }
    	}
    	
    	if (this.callCard instanceof DarkCrusader)
    	{
    		if (this.tagSave.equals(Tags.WARRIOR)) 
    		{ 
    			DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new WarriorToken());
    			summon(p, this.magicNumber, tok); 
    		}
    		else if (this.tagSave.equals(Tags.SUPERHEAVY)) 
    		{ 
    			DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new SuperheavyToken());
    			summon(p, this.magicNumber, tok); 
    		}
    		else { Util.log("How'd you choose something besides Warrior or Superheavy for Dark Crusader??"); }
    	}
    	
    	if (this.callCard instanceof RainbowGravity)
    	{
    		ArrayList<AbstractCard> monstersToModify = new ArrayList<AbstractCard>();
    		
    		if (this.callCard.upgraded)
    		{
	    		for (AbstractCard c : player().drawPile.group)
	    		{
	    			if (c.hasTag(Tags.MONSTER) && !c.hasTag(Tags.MEGATYPED) && !c.hasTag(tagSave))
	    			{
	    				monstersToModify.add(c);
	    			}
	    		}
    		}
    		
    		for (AbstractCard c : player().hand.group)
    		{
    			if (c.hasTag(Tags.MONSTER) && !c.hasTag(Tags.MEGATYPED) && !c.hasTag(tagSave))
    			{
    				monstersToModify.add(c);
    			}
    		}    	
    		
    		if (player().hasPower(SummonPower.POWER_ID))
    		{
    			SummonPower summonsInstance = (SummonPower)player().getPower(SummonPower.POWER_ID);
    			for (AbstractCard c : summonsInstance.getCardsSummoned())
    			{
    				if (c.hasTag(Tags.MONSTER) && !c.hasTag(Tags.MEGATYPED) && !c.hasTag(tagSave)) { monstersToModify.add(c); }    				
    			}
    		}
    		
    		AbstractDungeon.actionManager.addToTop(new AddCardTagsToListAction(monstersToModify, tagSave));
    	}
    }   
	
	@Override public void upgrade()  {}	
	
}
