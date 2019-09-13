package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.abstracts.*;
import duelistmod.interfaces.IShufflePower;

public class PowerOnShufflePatch {

    @SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction", method = SpirePatch.CONSTRUCTOR)
    public static class EmptyDeckShufflePatch {
        public static void Postfix(EmptyDeckShuffleAction obj) 
        {
            // triggers BEFORE deck is reshuffled but after relic onShuffles
            for (final AbstractPower p : AbstractDungeon.player.powers) 
            {
                if (p instanceof IShufflePower) ((IShufflePower)p).onShuffle();
            }
            for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            {
                for (final AbstractPower p : m.powers)
                {
                    if (p instanceof IShufflePower) ((IShufflePower)p).onShuffle();
                }
            }
            
            for (AbstractOrb o : AbstractDungeon.player.orbs)
            {
            	if (o instanceof DuelistOrb)
            	{
            		((DuelistOrb)o).onShuffle();
            	}
            }
            
            if (AbstractDungeon.player.stance instanceof DuelistStance) { DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance; stance.onShuffle();} 
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.ShuffleAction", method = "update")
    public static class ShufflePatch {
        public static void Postfix(ShuffleAction obj) 
        {
            // triggers AFTER deck is reshuffled and after relic onShuffles
            for (final AbstractPower p : AbstractDungeon.player.powers) 
            {
                if (p instanceof IShufflePower) ((IShufflePower)p).onShuffle();
            }
            for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            {
                for (final AbstractPower p : m.powers)
                {
                    if (p instanceof IShufflePower) ((IShufflePower)p).onShuffle();
                }
            }  
            
            for (AbstractOrb o : AbstractDungeon.player.orbs)
            {
            	if (o instanceof DuelistOrb)
            	{
            		((DuelistOrb)o).onShuffle();
            	}
            }
            
            if (AbstractDungeon.player.stance instanceof DuelistStance) { DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance; stance.onShuffle();}
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.actions.defect.ShuffleAllAction", method = SpirePatch.CONSTRUCTOR)
    public static class ShuffleAllPatch {
        public static void Postfix(ShuffleAllAction obj) 
        {
            // triggers BEFORE deck is reshuffled but after relic onShuffles
            for (final AbstractPower p : AbstractDungeon.player.powers) 
            {
                if (p instanceof IShufflePower) ((IShufflePower)p).onShuffle();
            }
            for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            {
                for (final AbstractPower p : m.powers)
                {
                    if (p instanceof IShufflePower) ((IShufflePower)p).onShuffle();
                }
            }
            
            for (AbstractOrb o : AbstractDungeon.player.orbs)
            {
            	if (o instanceof DuelistOrb)
            	{
            		((DuelistOrb)o).onShuffle();
            	}
            }
            
            if (AbstractDungeon.player.stance instanceof DuelistStance) { DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance; stance.onShuffle();}
        }
    }

}
