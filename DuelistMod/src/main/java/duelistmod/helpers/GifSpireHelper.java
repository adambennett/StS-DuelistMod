package duelistmod.helpers;

import java.util.ArrayList;

import GifTheSpire.util.GifAnimation;
import duelistmod.DuelistMod;

public class GifSpireHelper 
{
	// Animated Cards
	public static GifAnimation pharaohBlessing;
	public static GifAnimation skillMetronome;	
	public static GifAnimation attackMetronome;
	public static GifAnimation powerMetronome;
	public static GifAnimation rainbowMedicine;
	private ArrayList<GifAnimation> animations;
	private ArrayList<GifAnimation> pharaohAnimations;
	
	public GifSpireHelper()
	{
		pharaohBlessing = new GifAnimation(DuelistMod.makeAnimatedPath("PharaohBlessing.png"), 2, 11, 0, 0, 1, 1, false);
		skillMetronome = new GifAnimation(DuelistMod.makeAnimatedPath("SkillMetronome.png"), 8, 61, 0, 0, 1, 1, false);
		attackMetronome = new GifAnimation(DuelistMod.makeAnimatedPath("AttackMetronome.png"), 16, 14, 0, 0, 1, 1, false);
		powerMetronome = new GifAnimation(DuelistMod.makeAnimatedPath("PowerMetronome.png"), 16, 19, 0, 0, 1, 1, false);
		rainbowMedicine = new GifAnimation(DuelistMod.makeAnimatedPath("RainbowMedicine.png"), 10, 10, 0, 0, 1, 1, false);
		animations = new ArrayList<GifAnimation>();
		pharaohAnimations = new ArrayList<GifAnimation>();
		animations.add(pharaohBlessing);
		animations.add(skillMetronome);
		animations.add(attackMetronome);
		animations.add(powerMetronome);
		animations.add(rainbowMedicine);
		for (GifAnimation g : this.pharaohAnimations) { this.animations.add(g); }
		setupCards();
	}
	
	private void setupCards()
	{
		for (GifAnimation g : this.animations) { g.create(); }
		pharaohBlessing.addAsCardAnimation("theDuelist:PharaohBlessing");
		skillMetronome.addAsCardAnimation("theDuelist:Metronome");
		skillMetronome.addAsCardAnimation("theDuelist:SkillMetronome");
		skillMetronome.addAsCardAnimation("theDuelist:UncommonMetronome");
		skillMetronome.addAsCardAnimation("theDuelist:RareSkillMetronome");
		attackMetronome.addAsCardAnimation("theDuelist:AttackMetronome");
		attackMetronome.addAsCardAnimation("theDuelist:RareAttackMetronome");
		powerMetronome.addAsCardAnimation("theDuelist:PowerMetronome");
		powerMetronome.addAsCardAnimation("theDuelist:RarePowerMetronome");
		rainbowMedicine.addAsCardAnimation("theDuelist:RainbowMedicine");
	}
	
	public void hidePharaohAnimations(boolean set)
	{
		for (GifAnimation g : this.pharaohAnimations)
		{
			g.ishidden = set;
		}
	}
}
