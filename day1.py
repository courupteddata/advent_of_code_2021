"""
Day 1: Sonar Sweep
https://adventofcode.com/2021/day/1

"""


def parse_input(some_input: str) -> list[int]:
    """

    :param some_input: A newline seperated string of integers
    :return: the parsed input as a list of integers
    """
    return [int(number) for number in some_input.split("\n")]


def sonar_search(puzzle_input: list[int], window_length: int = 1) -> int:
    """
    :assumes: len(puzzle_input) > 0

    Counts the number of times where the depth increased
    :param puzzle_input: a list of numbers representing the depth
    :param window_length: the number of elements to look at for computing the current depth
    :return: number of times the depth increased
    """
    increased_count = 0
    prev = sum(puzzle_input[0:window_length])  # Create initial window
    for i, depth in enumerate(puzzle_input[window_length:], start=window_length):
        new_depth = prev - puzzle_input[i - window_length] + depth  # Remove window start and add new window end
        if new_depth > prev:
            increased_count += 1
        prev = new_depth

    return increased_count


def _main():
    test_input = """199
    200
    208
    210
    200
    207
    240
    269
    260
    263"""
    # Given test input has 7 measurements that are larger than the previous.
    assert sonar_search(parse_input(test_input)) == 7

    # When upping the window to three, there should be five sums larger than the previous.
    assert sonar_search(parse_input(test_input), 3) == 5

    # with open("test_file.txt", "r") as f:
    #    print(sonar_search(parse_input(f.read()), 3))


if __name__ == '__main__':
    _main()
