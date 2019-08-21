package duelistmod.helpers;

import java.util.ArrayList;

import GifTheSpire.util.GifAnimation;
import duelistmod.DuelistMod;

public class GifSpireHelper 
{
	// Animated Cards
	public static GifAnimation pharaohBlessing;
	public static GifAnimation metronome;
	private ArrayList<GifAnimation> animations;
	private ArrayList<GifAnimation> pharaohAnimations;
	
	public GifSpireHelper()
	{
		pharaohBlessing = new GifAnimation(DuelistMod.makeAnimatedPath("PharaohBlessing2.png"), 2, 11, 0, 0, 1, 1, false);
		metronome = new GifAnimation(DuelistMod.makeAnimatedPath("Metronome.png"), 2, 29, 0, 0, 1, 1, false);
		animations = new ArrayList<GifAnimation>();
		pharaohAnimations = new ArrayList<GifAnimation>();
		animations.add(pharaohBlessing);
		animations.add(metronome);
		for (GifAnimation g : this.pharaohAnimations) { this.animations.add(g); }
		setupCards();
	}
	
	private void setupCards()
	{
		for (GifAnimation g : this.animations) { g.create(); }
		pharaohBlessing.addAsCardAnimation("theDuelist:PharaohBlessing");
		metronome.addAsCardAnimation("theDuelist:Metronome");
	}
	
	public void hidePharaohAnimations(boolean set)
	{
		for (GifAnimation g : this.pharaohAnimations)
		{
			g.ishidden = set;
		}
	}
}
