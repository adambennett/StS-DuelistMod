package duelistmod.events;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.incomplete.MillenniumSpellbook;
import duelistmod.helpers.Util;
import duelistmod.potions.MillenniumElixir;

public class MillenniumItems extends AbstractImageEvent {


    public static final String ID = DuelistMod.makeID("MillenniumItems");
    public static final String IMG = DuelistMod.makeEventPath("MillenniumItems.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
   // private boolean relicSelected = true;
   // private RelicSelectScreen relicSelectScreen;

    public MillenniumItems() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
       // if (AbstractDungeon.ascensionLevel < 15) { 
        imageEventText.setDialogOption(OPTIONS[0]);
        //}
       // else 
       // { 
        //	imageEventText.setDialogOption(OPTIONS[2]);
       // }
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        //this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        boolean hasEveryMillenniumItem = true;
                        for (AbstractRelic t : Util.getAllMillenniumItems(false)) { if (!AbstractDungeon.player.hasRelic(t.relicId)) { hasEveryMillenniumItem = false; }}
                        if (!hasEveryMillenniumItem) 
                        {
                        	ArrayList<Object> mills = new ArrayList<Object>();
                        	//for (AbstractRelic r : Util.getAllMillenniumItems(false)) { if (!(AbstractDungeon.player.hasRelic(r.relicId))) { mills.add(r.makeCopy()); }}
                        	//mills.add(new MillenniumElixir());    
                        	mills.add(new MillenniumSpellbook());
                        	Object randMill = mills.get(AbstractDungeon.eventRng.random(mills.size() - 1));
                        	if (randMill instanceof AbstractRelic)
                        	{
                        		 AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), (AbstractRelic) randMill);
                        	}
                        	
                        	else if (randMill instanceof AbstractPotion)
                        	{
                        		AbstractDungeon.player.obtainPotion((AbstractPotion) randMill);
                        	}
                        	
                        	else if (randMill instanceof DuelistCard)
                        	{
                        		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect((AbstractCard) randMill, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        	}
                        	
                        	else if (DuelistMod.debug && randMill != null)
                        	{
                        		DuelistMod.logger.info("Millennium Items event generated a random object from the object array that wasn't a potion, relic or card? Hmm. How did that happen? The object: " + randMill.toString());
                        	}
                        	
                            AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                            AbstractCard b2 = DuelistCardLibrary.getRandomDuelistCurse();
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b2, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                            /*if (AbstractDungeon.ascensionLevel >= 15)
                            {
                            	  AbstractCard b3 = DuelistCardLibrary.getRandomDuelistCurse();
                                  AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b3, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                            }*/
                        }
                        else
                        {
                        	if (DuelistMod.debug)
                            {
                            	DuelistMod.logger.info("Triggered hasEveryMillenniumItem boolean, so do you have them all? Coin, Rod, Key, Eye, Ring, Necklace, Scale, Token");
                            }
                        	
                        	ArrayList<Object> mills = new ArrayList<Object>();
                        	mills.add(new MillenniumElixir());    
                        	mills.add(new MillenniumSpellbook());
                        	Object randMill = mills.get(AbstractDungeon.eventRng.random(mills.size() - 1));
                        	if (randMill instanceof AbstractPotion)
                        	{
                        		AbstractDungeon.player.obtainPotion((AbstractPotion) randMill);
                        	}
                        	
                        	else if (randMill instanceof DuelistCard)
                        	{
                        		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect((AbstractCard) randMill, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        	}
                        	
                        	else if (DuelistMod.debug && randMill != null)
                        	{
                        		DuelistMod.logger.info("Millennium Items event generated a random object from the object array that wasn't a potion, relic or card? Hmm. How did that happen? The object: " + randMill.toString());
                        	}
                        	
                            AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        }
                        
                        screenNum = 1;
                        break;
                    case 1:
                        //this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                }
                break;
            case 1:
                openMap();
                break;

        }
    }
}

