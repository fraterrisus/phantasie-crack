# Phantasie 1 Cracker

This project is a collection of Java code that I use to decode, decipher, and decompile the code and data that ships with the 1986 classic RPG *Phantasie*.

At the moment the jar just dumps the party's data to the command line.

## Compiling
`gradle jar` should do it; output is in `./build/libs/`

## Running
```
Usage: java -jar phantasie.jar [options] filename.img
Options:
  -b, --binary          Dump bytes instead of structured data
      --help            Display this message
  -s, --[no]spells      Enable display of spells (default: off)
```