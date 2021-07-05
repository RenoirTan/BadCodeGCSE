# BadCodeGCSE

Stuff I found on r/badcode.

# Requirements

In order to build and run this programme, you need to have
[maven](https://maven.apache.org/) installed
(preferably using the latest version).
The minimum version of Java you can run this programme on is Java SE 11
(mostly because I didn't bother to check compatibility with Java SE 8 and also,
the VSCode extensions for Java needed Java SE 11 to even function in the first
place anyway).

# Build Instructions

Change directory into `./badcode-gsce` and run the following command:

```bash
mvn assembly:assembly
```

This will command will produce 2 jar files in the folder called
`./badcode-gsce/target`, of which only
`badcode-gsce-<VERSION>-jar-with-dependencies.jar` will work properly because
it contains all the 3rd party dependencies inside.

# Running the programme.

There are 3 subcommands for each of the 3 corresponding tasks listed in the
`.pdf` file in the root directory of the repository. They are called as
follows:

1. `musicquiz` - A guessing game where player try and guess the names of songs
using the name of the artist and the first letter of each word in the song
title.
2. `dice` - A game where players try and get the highest score from a series
of dice rolls. (NOT DONE)
3. `cards` - Essentially like *Scissors, Paper, Stone* but with cards for some
bizarre reason (NOT DONE).

To select which game to play run, enter the command below in a terminal of
your choice:

```bash
java -jar /path/to/badcode-gsce-<VERSION>-jar-with-dependencies.jar <GAME>
```

and substitute `<VERSION>` and `<GAME>` with the correct values.

Each game can also have their subcommands, but most of them are for debugging
purposes. Even if you don't pass a subcommand to each of the games, it will
still pick the default option and run the actual game itself, instead of one
of the myriad of debugging programmes available.
