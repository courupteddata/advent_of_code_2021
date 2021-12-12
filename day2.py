"""
Day 2: Dive!
https://adventofcode.com/2021/day/2
"""


def parse_input(some_input: str, use_aim: bool = False) -> tuple[int, int]:
    """
    Takes in some newline seperated input, where each line contains a command and a number
    Valid commands are forward, down, and up
    :param use_aim: Whether up and down should affect the depth
    :param some_input: new line separated string with valid commands
    :return: horizontal position, and depth
    """
    horizontal_pos = 0
    depth = 0
    aim = 0

    for line in some_input.split("\n"):
        match line.split():  # My first python 3.10 match statement : D, So exciting
            case ["forward", num]:
                horizontal_pos += int(num)
                if use_aim:
                    depth += aim * int(num)
            case ["down", num]:
                if use_aim:
                    aim += int(num)
                else:
                    depth += int(num)
            case ["up", num]:
                if use_aim:
                    aim -= int(num)
                else:
                    depth -= int(num)
            case _:
                print(f"Unexpected input: {line}")

    return horizontal_pos, depth


def _main():
    test_input = """forward 5
down 5
forward 8
up 3
down 8
forward 2"""

    # Test input should have a horizontal position of 15 and a depth of 10
    horizontal, depth = parse_input(test_input)
    assert horizontal == 15
    assert depth == 10

    # Test input using aim should have a horizontal position of 15 and a depth of 60
    horizontal, depth = parse_input(test_input, True)
    assert horizontal == 15
    assert depth == 60

    # with open("test_file.txt", "r") as f:
    #     horizontal, depth = parse_input(f.read(), True)
    #     print(horizontal, depth, horizontal * depth)


if __name__ == '__main__':
    _main()
