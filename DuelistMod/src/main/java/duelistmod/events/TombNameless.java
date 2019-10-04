package duelistmod.events;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.*;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.cards.MonsterEggSuper;
import duelistmod.relics.*;

public class TombNameless extends DuelistEvent {


    public static final String ID = DuelistMod.makeID("TombNameless");
    public static final String IMG = DuelistMod.makeEventPath("TombNamelessB.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
    private int shopGoldGain = -1;
    private ArrayList<AbstractRelic> possibleOfferings;
    private AbstractRelic offering;
    
    public TombNameless() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
        this.possibleOfferings = new ArrayList<AbstractRelic>();
        AbstractPlayer p = AbstractDungeon.player;
        boolean hasMCoin = p.hasRelic(MillenniumCoin.ID);
        boolean hasShpTk = p.hasRelic(ShopToken.ID);
        boolean hasMEgg = p.hasRelic(MonsterEggRelic.ID);
        boolean hasTEgg = p.hasRelic(TributeEggRelic.ID);
        if (hasMCoin) { this.possibleOfferings.add(p.getRelic(MillenniumCoin.ID)); }
        if (hasShpTk) { this.possibleOfferings.add(p.getRelic(ShopToken.ID)); }
        if (hasMEgg)  { this.possibleOfferings.add(p.getRelic(MonsterEggRelic.ID)); }
        if (hasTEgg)  { this.possibleOfferings.add(p.getRelic(TributeEggRelic.ID)); }
        if (this.possibleOfferings.size() > 0)
        {
        	this.offering = this.possibleOfferings.get(AbstractDungeon.cardRandomRng.random(this.possibleOfferings.size() - 1));
        	if (this.offering instanceof MillenniumCoin)
        	{
            	imageEventText.setDialogOption(OPTIONS[0] + this.offering.name + OPTIONS[1] + OPTIONS[7], new DuelistCoin());
        	}
        	else if (this.offering instanceof ShopToken)
        	{
        		this.shopGoldGain = AbstractDungeon.cardRandomRng.random(65, 200);
            	imageEventText.setDialogOption(OPTIONS[0] + this.offering.name + OPTIONS[1] + OPTIONS[10] + this.shopGoldGain + OPTIONS[11]);
        	}
        	else if (this.offering instanceof MonsterEggRelic)
        	{
        		imageEventText.setDialogOption(OPTIONS[0] + this.offering.name + OPTIONS[1] + OPTIONS[8], new MonsterEggSuper());
        	}
        	else if (this.offering instanceof TributeEggRelic)
        	{
        		imageEventText.setDialogOption(OPTIONS[0] + this.offering.name + OPTIONS[1] + OPTIONS[9]);
        	}
        }
        else { imageEventText.setDialogOption(OPTIONS[6], true); }
        
        imageEventText.setDialogOption(OPTIONS[2], new CursedHealer());
        imageEventText.setDialogOption(OPTIONS[3]);
        imageEventText.setDialogOption(OPTIONS[4]);
        imageEventText.setDialogOption(OPTIONS[5]);
    }

    @Override
    protected void buttonEffect(int i) 
    {
        switch (screenNum) 
        {
            case 0:
            	switch (i) 
            	{
	            	// Offering
	            	case 0:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		if (this.offering != null)
	            		{
	            			if (this.offering instanceof MillenniumCoin)
	                    	{
	            				AbstractDungeon.player.loseRelic(MillenniumCoin.ID);
	            				AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new DuelistCoin());
	            				logMetric(NAME, "Offering - offered Millennium Coin");
	                    	}
	            			
	                    	else if (this.offering instanceof ShopToken)
	                    	{
	                    		AbstractDungeon.player.gainGold(this.shopGoldGain);
	                    		AbstractDungeon.player.loseRelic(ShopToken.ID);
	                    		logMetric(NAME, "Offering - offered Shop Token");
	                    	}
	            			
	                    	else if (this.offering instanceof MonsterEggRelic)
	                    	{
	                    		AbstractDungeon.player.loseRelic(MonsterEggRelic.ID);
	                    		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new MonsterEggSuper(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	                    		logMetric(NAME, "Offering - offered Monster Egg Relic");
	                    	}
	            			
	                    	else if (this.offering instanceof TributeEggRelic)
	                    	{
	                    		AbstractDungeon.player.loseRelic(TributeEggRelic.ID);
	                    		AbstractDungeon.player.heal((int) (AbstractDungeon.player.maxHealth/2.0f));
	                    		logMetric(NAME, "Offering - offered Tribute Egg");
	                    	}
	            		}
	            		screenNum = 1;
	            		break;
	
	            	// Succumb - Gain 25 HP, obtain Cursed Relic
	            	case 1:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		AbstractDungeon.player.heal(25); 	            		
	            		AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new CursedHealer());
	            		logMetric(NAME, "Succumb - +25HP/Cursed Relic");
	            		screenNum = 1;
	            		break;
	
	            	// Worship - Gamble 20% roll to get 12 max hp, 100% chance to get 1 random duelist curse
	            	case 2:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();                        
	            		AbstractCard curse = DuelistCardLibrary.getRandomDuelistCurse();	
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		int rolly = AbstractDungeon.cardRandomRng.random(1, 10);
	            		if (rolly < 3) { AbstractDungeon.player.increaseMaxHp(12, true); }
	            		logMetric(NAME, "Worship - 20% roll at +12 Max HP");
	            		screenNum = 1;
	            		break;
	
	            	// Break - 3 random duelist curses, gain 12 max HP
	            	case 3:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();      
	            		AbstractCard c = DuelistCardLibrary.getRandomDuelistCurse();	
	            		AbstractCard c2 = DuelistCardLibrary.getRandomDuelistCurse();
	            		AbstractCard c3 = DuelistCardLibrary.getRandomDuelistCurse(); 
	            		while (c2.name.equals(c.name)) { c2 = DuelistCardLibrary.getRandomDuelistCurse(); }
	            		while (c3.name.equals(c.name) || c3.name.equals(c2.name)) { c3 = DuelistCardLibrary.getRandomDuelistCurse(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c2, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c3, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));	
	            		AbstractDungeon.player.increaseMaxHp(12, true);
	            		logMetric(NAME, "Break - +12 Max HP");
	            		screenNum = 1;
	            		break;

	            	
	            	// Leave
	            	case 4:
	            		//this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		logMetric(NAME, "Leave");
	            		screenNum = 1;
	            		break;
            	}
            	break;
            case 1:
                openMap();
                break;
        }
    }
}

