#!/usr/bin/env python


import sys

import subprocess

for line in sys.stdin:
    line = line.strip()
    print subprocess.check_output(['alpr','-c','us','-n','1',line]).decode('string_escape')
   # print "mapper "+line
