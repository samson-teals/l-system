# Starter files for Lindenmayer Systems

The `Main` class itself isn't supposed to be very interesting: it only hosts a simple `Canvas` that we can draw on to.
Classes that implement the `LSystemImage` interface can be used in the `Main` class.

The `Main` class then draws the image from `LSystemImage` classes.
Feel free to modify `Main`.
For example, you may way to change how the keyboard handler behaves.

This `Main` is slightly different from the iteration systems we've looked at before.
Where the other examples automatically called `iterate` on every timer event, this one contains a keyboard handler that updates the number of "iterations", or depth of recursion of the L-System.
Typing in the numbers `0` to `9` will set the iteration count to that number.
Typing in `+` or `-` will increase or decreasea the iteration count.

Note that the keyboard handler is on the canvas.
The JAVA graphics window needs to have focus before it can receive keyboard events.

# Spiral Example 

The provided example `Spiral.java` implements a spiral curve.
This is a recursive construct that almost does what a typical L-System set does.
The biggest different is that the typical L-System only draws at `depth == 0`, instead of at all depths.

# Von Koch Snowflake

The Koch Snowflake is a relatively easy system to get started on.
See http://paulbourke.net/fractals/lsys/ and search for `Von Koch Snowflake` on the page.

## Constructing the snowflake

It is probably easier to understand these systems if they are explained as recursions.
For example, a construct will have an initial step and a generating step.
the generating step is usually recursive.

In the snowflake's case:

```
axiom = F++F++F
F -> F-F++F-F
angle = 60
```

The 0th iteration (or axiom) is:
```
F++F++F
```

And the 1st iteration evaluates to:
```
F-F++F-F  ++  F-F++F-F  ++  F-F++F-F
```

Where each of the initial `F`s are replaced by the generating (or recursive) step.

## Implementing the snowflake

1. Start with `Spiral.java`.
2. Modify the `drawIteration` method, for example, to contain the axiom.
3. Modify the `F` method, or create other methods for systems with larger grammars.
4. Use the `VectorImage::addLine` methods to add a new line to `VectorImage`'s `ArrayList`. There are two forms: one which takes 2 sets of (x,y) coordinates as doubles, and another which starts with a `Point2D` and returns the position of the new point after adding a line of `length` drawn at `angle`. You may find the second form more convenient to use, but you can use either form.
lsystem
### VectorImage.java

The `VectorImage` class contains a buffered image, but that is used only as the drawing interface - the actual data is an `ArrayList` of `Line2D` objects.
Whenever a line is added, a new `Line2D` object is inserted into the `ArrayList`.
This allows us to dynamically calculate the bounds of the final image.
Dynamic bounds calculation is used to make experimenting with different L-Systems easier.

## Experiments

- Try different L-Systems. The following sites have good examples:
  - http://paulbourke.net/fractals/lsys/
  - https://en.wikipedia.org/wiki/L-system
  
Note that the two sites assign different meanings to symbols, most noticeably `+` and `-`.
You shouldn't worry too much about this if you're consistent.
A reversed L-System is easily recognizable, and the point is to experiment with them, not to create one that necessarily matches the given specifications.
