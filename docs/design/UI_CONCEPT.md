# UI Concept — "Sunny Park Arcade"

Interactive design canvas: "Mole Mayhem AR — UI Concept" (https://claude.ai/artifact/MkJghptizUSuAHpCMDKXXE).
It has five phone screens at 390×844: Home, LAN lobby, Swing check, In-game HUD (tap a mole to
try it), and Results. All names, scores and stats shown there are placeholders.

**Visual reference in this repo:** `docs/design/screens/*.png` (look at these first),
`docs/design/preview/*.html` (exact styles), and `docs/design/source/` (canvas files). See
`docs/design/README.md`.

## Tokens (put these in a Compose `MoleTheme`, not in hardcoded colours)
| Token | Hex | Use |
|---|---|---|
| background | #E9F8D6 | Meadow screen background |
| surface | #FFFFFF | Cards, chips, inactive buttons |
| onSurface / text | #1F3A12 | Forest-green text and icons |
| textSecondary | #4E6B3A | Captions, labels (passes 4.5:1 on the background) |
| outline | #BFE09A | Card borders, secondary button borders |
| primary | #FF7A1A | Anything you press or score; text on it is #1F3A12 |
| primaryEdge | #C4560A | The arcade button's solid drop edge (6–8 px) |
| go | #8EDB3F | Ready, safe, locked, combo; edge #5DA324 |
| sky | #BFE6FF | Outdoor sky, illustration backgrounds |
| boardDirt | #B98552 | AR board patch; rim #8A5E35 |
| hole | #3A2614 | Hole; rim #6B4A2B |
| mole | #8A5A3C | Standard mole |
| helmet | #FFD23F | Hard hat (edge #C99A00) |
| golden | #FFC83D | Golden mole; glow #FFF6C2 |
| bomb | #2E2A33 | Bomb mole; danger ring #FF4D4D |
| player1..3 | #FF7A1A, #2FA8FF, #F2559A | Player rings and score pills; player 4 is still to be decided |

Type:
- Lilita One for game words (logo, buttons, scores, "SMASH!").
- Nunito 600–900 for everything else.
- Bundle both fonts with the app, under the OFL.

Shape:
- Buttons: 22–24 radius.
- Cards: 18–20 radius.
- Chips and pills: fully rounded.
- Arcade buttons sit on a solid drop edge, never a gradient.

## Screens
1. **Home:**
   - Logo with a mole popping out of a hole.
   - "QUICK PLAY" as the primary button.
   - The three mode cards.
   - Mallets, How to swing, and Join game.
2. **LAN lobby:**
   - The 4-digit PIN in large tiles.
   - Player roster with a colour ring per player, platform, and a status chip (BOARD LOCKED or
     SCANNING MAT…).
   - Mole Mat instructions.
   - The Start button stays disabled until every board is locked.
3. **Swing check:**
   - "Chop down like hammering a nail."
   - Three-chop progress.
   - Swing-power bar with the player's personal hit line.
   - Safety card.
   - "Skip, use tap mode" is always visible.
4. **In-game HUD:**
   - The AR view is the star. HUD only in the top ~120 px (pause, score pills, timer, combo) and
     the bottom ~140 px (swing meter, Hammer/Tap toggle).
   - The centre stays clear except for the reticle.
   - Feedback pill: SMASH / CLANK / GOLDEN / KABOOM.
5. **Results:**
   - Podium.
   - Stats: accuracy, best combo, golden moles, bombs dodged.
   - Unlock banner.
   - Next round and Home.

## Interaction rules
- Every hit gets a haptic, a sound and a burst within one frame of the strike being confirmed.
  Use local prediction, then reconcile with `HitResult`.
- Touch targets are ≥ 44 pt/dp, and text contrast is ≥ 4.5:1. Colour is never the only cue: mole
  types also differ in shape (hat, fuse, glow).
- Support reduce motion (no screen shake, softer bursts), Dynamic Type/font scale on menus, and
  VoiceOver/TalkBack labels on every control.
- Tablets: the same components on a wider grid, with a seated mode by default.
