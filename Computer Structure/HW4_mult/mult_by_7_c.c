unsigned int mult_by_7_c(unsigned int x) {
  unsigned int y = x << 3;
  y = y - x;
  return y;
}
