Sort Lines Test
==============

## Generate file

```sh
./gradlew run --args="generate 5000 1000 foo.txt"
```

Generates `foo.txt` with `5000` lines and max line length of `1000`

## Sort file

```sh
./gradlew run --args="sort foo.txt bar.txt"
```

Sorts `foo.txt` and puts the result in `bar.txt`

## Verify sorting

```sh
./gradlew run --args="verify bar.txt"
```

Verifies if `bar.txt` is sorted. Prints the result
