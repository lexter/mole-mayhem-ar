# Design assets — "Sunny Park Arcade"

These are the generated UI concept screens. They're the visual reference for building the Compose
UI. The rules and tokens are in `UI_CONCEPT.md`.

| Folder | What | Use it for |
|---|---|---|
| `screens/` | PNG of each screen at 390×844 pt (@2x), plus `00-overview.png` | Look at these first. Match the layout and hierarchy. |
| `preview/` | Standalone HTML for each screen. Open it in a browser (it needs internet for Google Fonts). | Exact sizes, spacing, radii and hex values from the inline styles. Buttons link between screens. |
| `source/` | The original design-canvas files (`*.dc.html` + `canvas.json`) | Archive and round-trip only. They need the canvas runtime, so don't open them directly. |

Screens:
1. `01-home`: Home
2. `02-lan-lobby`: LAN lobby
3. `03-swing-check`: Swing check (calibration)
4. `04-in-game-hud`: In-game HUD
5. `05-results`: Results

Notes for implementation:
- **Fonts in the PNGs:** they were rendered with stand-in fonts (Inter Display Black for Lilita
  One, and Inter for Nunito), because Google Fonts wasn't reachable when they were rendered. The
  real fonts are Lilita One and Nunito, as specified in `UI_CONCEPT.md` and visible in `preview/`.
- **Placeholders:** all names, scores, the PIN and the stats are placeholders, not real data.
- **The HUD is an overlay:** in `04-in-game-hud`, the sky, grass, dirt board and moles stand in for
  the live AR camera view and the 3D scene. In the app, they're rendered by
  `ArSceneController` (RealityKit/SceneView), not by Compose. Compose draws only the HUD layers:
  the top bar, the feedback pill, the reticle and the bottom controls.
- **Build with tokens:** use `MoleTheme` tokens and reusable components (ArcadeButton, ModeCard,
  PlayerChip, ScorePill, StatusChip, StatTile), not one-off hex values in screens.
- **Phones only:** tablet layouts aren't drawn yet. Use the same components on a wider grid, as
  `UI_CONCEPT.md` describes.
- **Living canvas:** https://claude.ai/artifact/MkJghptizUSuAHpCMDKXXE (it's private to the
  owner, so you may not be able to open it).
