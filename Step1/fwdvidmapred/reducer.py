#!/usr/bin/env python

from operator import itemgetter
import sys

for line in sys.stdin:
    line = line.strip()
    if '-' in line:
      print line

