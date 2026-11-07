# Third-party notices

Mole Mayhem AR includes the third-party software and assets below. Each entry gives the license and where its
full text is kept. Add an entry here whenever you add a dependency or an asset that ships in the app.

## Fonts (bundled in `composeApp/src/commonMain/composeResources/font/`)

| Asset | Copyright | License | Full text |
|---|---|---|---|
| Lilita One (`lilita_one_regular.ttf`) | © 2011 Juan Montoreano, with Reserved Font Name "Lilita" | SIL Open Font License 1.1 | [`licenses/OFL-LilitaOne.txt`](licenses/OFL-LilitaOne.txt) |
| Nunito (`nunito_semibold/bold/extrabold/black.ttf`) | © 2014 The Nunito Project Authors | SIL Open Font License 1.1 | [`licenses/OFL-Nunito.txt`](licenses/OFL-Nunito.txt) |

Source: the official Google Fonts repository (`github.com/google/fonts`, `ofl/lilitaone` and `ofl/nunito`). The
Nunito files are static instances (wght 600/700/800/900) of the upstream variable font, created with fontTools'
`varLib.instancer`. Nunito has no Reserved Font Name, so the instances keep the name. Lilita One is unmodified.
Both license texts are also bundled in the app under `composeResources/files/licenses/`.

## Icons

| Asset | Source | License |
|---|---|---|
| Settings gear (`ic_settings.xml`) | Feather Icons, © 2013–2023 Cole Bemis (via the project UI design) | MIT |
| Play, hammer, versus, shield icons | Project UI design (`docs/design/`) | Project-owned |

MIT License text for Feather Icons:

> Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
> documentation files (the "Software"), to deal in the Software without restriction, including without limitation
> the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
> permit persons to whom the Software is furnished to do so, subject to the following conditions:
>
> The above copyright notice and this permission notice shall be included in all copies or substantial portions of
> the Software.
>
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
> WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
> COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
> OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

## Libraries shipped in the app

All of these are under the Apache License 2.0 (https://www.apache.org/licenses/LICENSE-2.0).

| Library | Version | Copyright |
|---|---|---|
| Kotlin standard library | 2.4.21 | JetBrains s.r.o. and Kotlin Programming Language contributors |
| kotlinx.coroutines | 1.11.0 | JetBrains s.r.o. and contributors |
| kotlinx.serialization | 1.12.0 | JetBrains s.r.o. and contributors |
| Compose Multiplatform (runtime, foundation, ui, components-resources, ui-tooling-preview) | 1.12.2 | JetBrains s.r.o. and The Android Open Source Project |
| AndroidX Activity Compose | 1.13.0 | The Android Open Source Project |
| Ktor (ktor-network) | 3.6.1 | JetBrains s.r.o. and contributors |
| Koin (core, compose) | 4.2.2 | The Koin authors (InsertKoinIO) |
| Multiplatform Settings | 1.3.0 | Russell Wolf |

## Test and build tools (not shipped in the app)

Turbine (Apache 2.0), kotlin.test (Apache 2.0), detekt (Apache 2.0), ktlint (MIT), ktlint-gradle (MIT), Kover
(Apache 2.0), SwiftLint (MIT), Gradle (Apache 2.0), Android Gradle Plugin (Apache 2.0).
