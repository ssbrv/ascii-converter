package source.service.random

class SmallImageGenerator(seed: Long = System.nanoTime()) extends RandomImageGenerator(20, 200, 20, 400, seed)