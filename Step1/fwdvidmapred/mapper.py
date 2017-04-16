#!/usr/bin/env python

import sys
import os
import subprocess

for line in sys.stdin:
    line = line.strip()
    words = line.split()
    for word in words:
       print subprocess.check_output(['alpr','-c','us','-n','1',line]).decode('string_escape')
