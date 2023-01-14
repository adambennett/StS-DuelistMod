General:
- Fix basemod dev console application of all custom powers

Main Menu:
- Move 'Play' for the Duelist into separate menu with more layers - decks, location (spire/kingdom/etc), settings, anything else
	- Add custom main menu option for special Duelist settings screen
	- Add stats screen inside special settings menu - cards seen, heart kills with each deck, etc.

Starter Decks:
- Give all decks 2 unlock criteria (score and something unique for each deck, triggering either should unlock deck)
- Unlocks and deck selections moved to new Play screens
- Reorder deck unlocks
- Remove Giant, Predaplant

Art/UI:
- Card pool relics and other in-run settings moved to a top panel option menu
- Improve sorting options for card pool screens (allow sort by monster/spell/trap, fix arrows on big card view screen, etc.)
    - Hook into Compendium Filters mod and add extra sorting options?
- In combat: Add enemy hand icon (like floating summon icon) that enables right-click to view cards in the enemy's hand
- Add tooltip card previews for any appropriate cards (for any card that specifically references another card or token, things like Messenger of Peace, etc.)
- Add unique Power icons for all custom powers
- Add unique Relic icons for all custom relics
- Add Pegasus, Joey, Mako Tsunami, Tristan, Bakura, Tea and other character models
- Tribute and Summon card icons
	- Create or subcontract out creation of icons similar to StSLib common keyword icons
	- Add config option in Visual settings to switch card text for new icons
	- Add config option in Visual settings to simply show the new icons without replacing card text

Enemies:
- Improve card play visuals (slow it down, allow player to interact and make them press continue maybe? config option?)
    - Check Downfall mod code, characterbosses apparently play cards and it looks good here
- Possibly implement a two-phase system for enemy duelists to enable showing the cards that triggered overflows (and other end-of-turn effects?)
- Add support for channeling orbs
- Add support for Resummoning
- Add new duelist enemies
    - Orb monster
        - Scripted moves
        - Use duelist orbs to punish certain builds
    - Nob variations
        - Get stronger when you tribute/summon
        - Get stronger when you channel
    - Anti-Resummon enemy
        - Resists resummon damage
        - Gets stronger when you resummon
    - Mirror duelist
        - Plays your hand each turn
        - Determines the best outcome based on the moves available at the time
    - Elite Thief
        - Does more damage
        - Steals relics instead of gold (max of 2-3 per battle?)
        - Sticks around 1-2 more turns
        - Has more health
        - Blocks for higher amount
        - Bigger (visually)
- Replace old Yugi and Kaiba art with new animations, if possible
- Prevent player from fighting Yugi or Kaiba more than once per run
- Ensure player always fights the correct enemy based on their chosen player model
- Improve AI 'logic'
- Update card functions to match current card descriptions
    - Possibly implement a better system than hardcoding effects this way

Card Pool Relics:
- Add other deck pools to card pool options relic
- Add other options to card pool options relic
- Add more explanation about how these work
	- Keywords for 'Colored Pool' and 'Basic Pool'
	- Ftue tips?

Balance Changes:
- Give Rocks some unique effects/mechanics and more cards
- Make Duelist orbs work with Lock On
- Reduce damage buff given to source when target is Frozen

Card Pools:
- Properly setup all base game card additions for all pools
- Filter/update basic card set for each deck
- Remove Predaplant/Giant decks from selection, change into simple Pools (like Rock/Arcane)
- Completely Redesign: Standard, Toon, Warrior, Megatype, Increment, Ojama/Beast, Creator, Ascended I, Ascended II
- Moderately Rework: Spellcaster, Fiend, Plant, Insect, Metronome
- Design and Implement: Ascended III, Pharaoh I, Pharaoh II, Pharaoh III, Pharaoh IV, Pharaoh V

Bug Fixes
- Character select screen with character level < 5: needs to show score progress instead of level progress under relics
- Properly rewrite 'attack multiple random enemies' functions (and anything similar, like Constrict multiple random enemies) as an action
- Fix 'canCancel' implementation issues, remove all instances of 'CancelCard' if possible
- Dragon puzzle effect is buggy?
- Tribute and Summon modification logic
	- Check issues with Cyber Dragon cards that deal with modified tribute/energy cost
		- Cyber Dragon Core interaction
	- Check issues with 'permanent' effects like Yugi's Mirror not persisiting through save and exit

Config Menu Improvements
- Challenge & Ascension settings page
    - Unlock all challenge levels for all decks
    - Unlock all Ascension levels for all decks
- Card specific configs
    - Add configs for all currently listed cards to modify percentages, add damage, etc
    - Add global card configs
    - Add a few more Token configs
    - Move Nameless Tomb card configs to event configs menu
- Orb specific configs
    - Add configs for some orbs to modify specific roll percentages
- Relic specific configs
    - Add configs for some Duelist relics to:
        - Modify relic values and percent chances
- Power specific configs
  - Modify power values, triggers, etc
- Stance specific configs
    - Add configs to modify stance values and percent chances
- Potion specific configs
    - Add configs for all Duelist potions to:
        - Modify specific potion values and percent chances
        - Add bonus damage to any damage potions
    - Add potion images?
- Event specific configs
  - Add config to nameless tomb event to add exhaust to all red-border nameless tomb cards
- All config menu pages
	- Reset page to default
	- Randomize page settings
- General settings
	- Reset all config settings to default
	- Randomize all config settings
- Gameplay Settings
	- Fix 'Card Pool Relics' toggle
- Card Pool settings
	- Pool Fill settings: add "Default, but force 75 card limit"
- Metrics Settings
  - Implement 'View My Runs' button functionality

Cross-Mod Compatability
- Spire With Friends
    - Add Challenge Mode selector to lobby menu
    - Check for other possible supporting options that can be added
    - Check modes other than Co-Op to ensure starting deck is properly supported in any mode
- Compendium Filter
  - Add patch to automatically close Duelist config screen when compendium filters settings is opened mid-run
- Pen Nib is broken with either Minty Spire or Compendium Filters
  - Game gets choppy and frame rate goes to hell when relic is added
