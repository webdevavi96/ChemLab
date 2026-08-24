# ChemLab Simulator

ChemLab Simulator is an Android application for interactive chemistry laboratory simulation. It features a physics- and stoichiometry-based simulation engine capable of modeling chemical reactions, thermodynamic state transitions, dynamic direct synthesis, acid-base equilibria, radioactive decay, and thermal damage to laboratory apparatus in real time.

---

## Overview

The application provides an interactive workspace where users can combine chemical reagents, manipulate laboratory apparatus, regulate thermal energy, and observe physical and chemical phenomena. The simulation calculates reactions deterministically using standard thermodynamic and stoichiometric principles rather than pre-rendered animations.

---

## 📱 Application Screenshots

The following screenshots demonstrate the primary workflow of ChemLab Simulator, from application startup through the chemistry database and laboratory simulation.

<table>
  <tr>
    <td align="center">
      <strong>1. Splash Screen</strong><br><br>
      <img src="https://github.com/user-attachments/assets/a3f846a3-836e-47a2-b702-60ad3f72673f" width="350" alt="ChemLab Simulator Splash Screen">
    </td>
    <td align="center">
      <strong>2. Home Screen</strong><br><br>
      <img src="https://github.com/user-attachments/assets/aba32f2f-20c7-4774-9c4a-85efe400c9e2" width="350" alt="ChemLab Simulator Home Screen">
    </td>
  </tr>

  <tr>
    <td align="center">
      <strong>3. Guided Experiment Library</strong><br><br>
      <img src="https://github.com/user-attachments/assets/c9d84762-6862-4f83-a5e2-880cb6617f61" width="350" alt="ChemLab Simulator Guided Experiment Library">
    </td>
    <td align="center">
      <strong>4. Guided Laboratory Experiment</strong><br><br>
      <img src="https://github.com/user-attachments/assets/baf2e705-7c51-49c5-a21f-6a5e3dc171a9" width="350" alt="ChemLab Simulator Guided Laboratory Experiment">
    </td>
  </tr>

  <tr>
    <td align="center">
      <strong>5. Interactive Lab Workspace</strong><br><br>
      <img src="https://github.com/user-attachments/assets/3ce16419-8c69-4eb4-b74d-ea710345fc39" width="350" alt="ChemLab Simulator Interactive Lab Workspace">
    </td>
    <td align="center">
      <strong>6. Chemical Element Details</strong><br><br>
      <img src="https://github.com/user-attachments/assets/266a3a2b-c814-4116-a460-328337389737" width="350" alt="ChemLab Simulator Chemical Element Details">
    </td>
  </tr>

  <tr>
    <td align="center">
      <strong>7. Reaction Calculation Inspector</strong><br><br>
      <img src="https://github.com/user-attachments/assets/e30700ef-a9b4-49f2-b556-ec54388057c3" width="350" alt="ChemLab Simulator Reaction Calculation Inspector">
    </td>
    <td align="center">
      <strong>8. Experiment Result & Thermal Simulation</strong><br><br>
      <img src="https://github.com/user-attachments/assets/619a1cd2-a69c-48bb-8e68-e28f9a5659b7" width="350" alt="ChemLab Simulator Experiment Result and Thermal Simulation">
    </td>
  </tr>
</table>

---

## Core Capabilities

### 1. Deterministic Chemistry Simulation Engine
* **Stoichiometry and Kinetics**: Calculates limiting reagents, mass balance, molar concentrations, gas production, and precipitate yields.
* **Dynamic Direct Combination**: Evaluates elemental properties (electronegativity, valence electron configurations, and oxidation states) to predict synthesis reactions dynamically for uncataloged reagent mixtures.
* **Acid-Base Equilibria**: Tracks hydronium and hydroxide concentrations, auto-ionization of water ($K_w$), solution pH, pOH, and indicator color transitions.
* **Thermodynamics and Phase Transitions**: Computes enthalpy changes ($\Delta H^\circ$), system heat capacity, conductive/convective heat loss, boiling, vaporization, and solidification.
* **Solubility and Precipitation**: Models temperature-dependent solubility limits and sediment formation.
* **Radioactive Decay**: Simulates alpha and beta decay pathways with Cherenkov radiation and ionization tracking.

### 2. Physical Apparatus and Thermal Stress Modeling
* **Glassware Types**: Beakers, Erlenmeyer flasks, test tubes, graduated cylinders, burettes, pipettes, crucibles, evaporating dishes, and gas syringes.
* **Thermal Stress Progression**:
  * **$T \le 100^\circ\text{C}$**: Safe operating conditions with intact glassware.
  * **$150^\circ\text{C} < T \le 300^\circ\text{C}$**: Hairline fracture network with thermal color tinting.
  * **$300^\circ\text{C} < T \le 500^\circ\text{C}$**: Advanced spiderweb cracking with incandescent base glow.
  * **$500^\circ\text{C} < T \le 750^\circ\text{C}$**: Structural failure and melting with content discharge.
  * **$T > 750^\circ\text{C}$**: Overpressure explosion with projectile shards and smoke generation.
* **Interactivity Restrictions**: Prevents reagent additions or liquid pouring into compromised or destroyed apparatus.
* **Safety Reset Subsystem**: Configurable auto-clearing countdown with user-adjustable dwell time.

### 3. Guided Experiment Library
* Structured laboratory experiments with step-by-step procedures, target milestones, theoretical context, and safety observations.

---

## Architecture and Technology Stack

* **Platform**: Android (minSdk 26, targetSdk 35)
* **Language**: Kotlin 2.0.21
* **UI Toolkit**: Jetpack Compose, Material Design 3, Compose Multiplatform Canvas
* **Architecture**: Model-View-ViewModel (MVVM) with Unidirectional Data Flow (UDF)
* **State Management**: Kotlin Coroutines and StateFlow
* **Audio Subsystem**: SoundPool with dynamic synthetic PCM waveform generation for reactions, effervescence, and explosions
* **Build System**: Gradle with Kotlin DSL and Configuration Caching

---

## Project Structure

```
app/src/
├── main/
│   ├── java/com/webdevavi/chemlabsimulator/
│   │   ├── audio/              # Sound synthesis and audio playback management
│   │   ├── simulation/         # Core deterministic simulation logic
│   │   │   ├── chemistry/      # Stoichiometry, reaction rules, element profiles
│   │   │   ├── data/           # Experiment definitions and preset data
│   │   │   └── model/          # Container, substance, and visual state data classes
│   │   ├── theme/              # Typography, color schemes, and app themes
│   │   └── ui/                 # UI components, canvas renderers, and screens
│   │       ├── components/     # Custom canvas drawing routines and widgets
│   │       └── screens/        # Workspace, Library, Inventory, and Home screens
│   └── res/                    # Drawables, mipmaps, values, and XML resources
└── test/                       # Unit tests for stoichiometry, reactions, and physics
```

---

## Building and Testing

### Prerequisites
* Java Development Kit (JDK) 17 or higher
* Android SDK (API Level 35)
* Android Build Tools 35.0.0

### Running Unit Tests
Execute the local unit test suite via Gradle:

```bash
./gradlew testDebugUnitTest
```

### Assembling Debug Build
To build the debug APK:

```bash
./gradlew assembleDebug
```

The output APK will be generated in `app/build/outputs/apk/debug/`.

### Assembling Release Bundle
To build the signed release Android App Bundle (AAB):

```bash
./gradlew bundleRelease
```

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

