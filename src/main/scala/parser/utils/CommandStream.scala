package parser.utils

import common.utils.MappableStream
import parser.domain.Command

class CommandStream(commands: Seq[Command]) extends MappableStream[Command](commands)