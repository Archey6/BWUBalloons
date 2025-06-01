# Balloons Helper Class

A utility class to automate balloon travel in RuneScape 3 using the BotWithUs API.

## Destinations
```kotlin
CASTLEWARS 
GRANDTREE 
CRAFTINGGUILD 
ENTRANA 
TAVERLEY 
VARROCK
```

## Key Methods

### `isOpen(): Boolean`
Checks if the balloon travel interface is open.

### `getCharges(): Int`
Returns the number of available balloon charges.

### `fly(balloonId: Int)`
Flies to a destination if valid and enough charges are available.

### `bank()`
Bank trick that attempts to fly to entrana with a combat item equipped to open the bank

## Example
Pass your script into the class for logging, as you would your graphics context
```kotlin
val balloons = Balloons(this)
balloons.fly(Balloons.VARROCK)
```
or
```
balloons.bank()
```

## Notes
- Requires 7+ charges to travel.

