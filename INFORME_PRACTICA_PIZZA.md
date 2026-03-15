# Informe practica - Jetpack Compose Pizza + Lifecycle

## Checklist de requisits

- [x] Flux per passos: Nom -> Quantitat -> Tipus -> Resum
- [x] Navegacio per estat amb `PizzaStep` (sense Navigation Component)
- [x] Validacio per pantalla abans d'avancar
- [x] Pantalla final amb boto `Reiniciar` (sense `Seguent`)
- [x] Monitoratge de cicle de vida a Logcat + UI
- [x] `ViewModel` per historial del cicle de vida
- [x] Boto `Reset historial`
- [x] 3 comptadors (`normal`, `remember`, `rememberSaveable`) visibles

## Proves obligatories

### Prova 1 - Premer boto `+1 comptadors`

**Passos:**
1. Obrir l'app.
2. Premer `+1 comptadors` diverses vegades.

**Observat:**
- `contadorNormal`:
- `contadorRemember`:
- `contadorSaveable`:

### Prova 2 - Girar pantalla (rotacio)

**Passos:**
1. Posar valors diferents als 3 comptadors.
2. Girar el dispositiu (portrait <-> landscape).

**Observat:**
- `contadorNormal`:
- `contadorRemember`:
- `contadorSaveable`:
- Ordre aproximat del lifecycle vist a UI/Logcat:

### Prova 3 - Enviar app a segon pla i tornar

**Passos:**
1. Amb l'app oberta, prem Home.
2. Torna a entrar a l'app.

**Observat:**
- `contadorNormal`:
- `contadorRemember`:
- `contadorSaveable`:
- Ordre aproximat del lifecycle vist a UI/Logcat:

## Diferencies entre tipus de variables

### `contadorNormal`
- Es conserva en recomposicio?:
- Es conserva en rotacio?:
- Es conserva en segon pla i tornada?:
- Explicacio:

### `contadorRemember`
- Es conserva en recomposicio?:
- Es conserva en rotacio?:
- Es conserva en segon pla i tornada?:
- Explicacio:

### `contadorSaveable`
- Es conserva en recomposicio?:
- Es conserva en rotacio?:
- Es conserva en segon pla i tornada?:
- Explicacio:

## Cicle de vida de l'Activity

### Etapes registrades (Logcat i UI)
- [ ] onCreate
- [ ] onStart
- [ ] onResume
- [ ] onPause
- [ ] onStop
- [ ] onDestroy
- [ ] onRestart

### Quan es crida cadascuna (resum amb paraules teves)
- `onCreate`:
- `onStart`:
- `onResume`:
- `onPause`:
- `onStop`:
- `onRestart`:
- `onDestroy`:

## Us del ViewModel

- Diferencia entre `ViewModel` i variables de comptador:
- Avantatges que veus d'usar `ViewModel` en aquest exercici:

## Captures de pantalla obligatories

- [ ] Pantalla de Nom
- [ ] Pantalla de Quantitat
- [ ] Pantalla de Tipus de pizza
- [ ] Pantalla de Resum
- [ ] Llista visible del cicle de vida
- [ ] Boto `Reset historial`
- [ ] Comptadors abans/despres d'una prova
- [ ] Min. una captura despres de girar pantalla

## Notes finals

- L'objectiu de la practica es observar canvis d'estat i lifecycle.
- Si un comptador es reinicia, es correcte: cal descriure-ho i explicar per que passa.

